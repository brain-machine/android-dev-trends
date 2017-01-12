package br.com.monitoratec.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import br.com.monitoratec.app.domain.GitHubApi;
import br.com.monitoratec.app.domain.GitHubOAuthApi;
import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.AccessToken;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.util.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.ivGitHub)
    ImageView mImgGitHub;
    @BindView(R.id.tvGitHub)
    TextView mTxtGitHub;
    @BindView(R.id.tilUsername)
    TextInputLayout mLayoutTxtUsername;
    @BindView(R.id.tilPassword)
    TextInputLayout mLayoutTxtPassword;
    @BindView(R.id.btOAuth)
    Button mBtnOAuth;

    private GitHubStatusApi mGitHubStatusApi;
    private GitHubApi mGitHubApi;
    private GitHubOAuthApi mGitHubOAuthApi;
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mBtnOAuth.setOnClickListener(view -> {
            final String baseUrl = GitHubOAuthApi.BASE_URL + "authorize";
            final String clientId = getString(R.string.oauth_client_id);
            final String redirectUri = getOAuthRedirectUri();
            final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        mGitHubStatusApi = GitHubStatusApi.RETROFIT.create(GitHubStatusApi.class);
        mGitHubApi = GitHubApi.RETROFIT.create(GitHubApi.class);
        mGitHubOAuthApi = GitHubOAuthApi.RETROFIT.create(GitHubOAuthApi.class);

        mSharedPrefs = getSharedPreferences(getString(R.string.sp_file), MODE_PRIVATE);
    }

    @OnClick(R.id.btBasicAuth)
    protected void onBasicAuthClick(Button view) {
        if (AppUtils.validateRequiredFields(MainActivity.this,
                mLayoutTxtUsername, mLayoutTxtPassword)) {
            String username = mLayoutTxtUsername.getEditText().getText().toString();
            String password = mLayoutTxtPassword.getEditText().getText().toString();
            final String credential = Credentials.basic(username, password);
            mGitHubApi.basicAuth(credential).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        String login = response.body().login;
                        String credentialKey = getString(R.string.sp_credential_key);
                        mSharedPrefs.edit()
                                .putString(credentialKey, credential)
                                .apply();
                        Snackbar.make(view, login, Snackbar.LENGTH_LONG).show();
                    } else {
                        try {
                            String errorBody = response.errorBody().string();
                            Snackbar.make(view, errorBody, Snackbar.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void processOAuthRedirectUri() {
        // Os intent-filter's permitem a interação com o ACTION_VIEW
        final Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {
            String code = uri.getQueryParameter("code");
            if (code != null) {
                //Pegar o access token (Client ID, Client Secret e Code)
                String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);
                mGitHubOAuthApi.accessToken(clientId, clientSecret, code).enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if (response.isSuccessful()) {
                            AccessToken accessToken = response.body();
                            String credentialKey = getString(R.string.sp_credential_key);
                            mSharedPrefs.edit()
                                    .putString(credentialKey, accessToken.getAuthCredential())
                                    .apply();
                            Snackbar.make(mBtnOAuth, accessToken.access_token, Snackbar.LENGTH_LONG).show();
                        } else {
                            try {
                                String errorBody = response.errorBody().string();
                                Snackbar.make(mBtnOAuth, errorBody, Snackbar.LENGTH_LONG).show();
                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage(), e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Snackbar.make(mBtnOAuth, t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            } else if (uri.getQueryParameter("error") != null) {
                //TODO Tratar erro
            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeGitHubStatusColors(Status.Type.NONE);
        mGitHubStatusApi.lastMessage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Status>() {
                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, e.getMessage(), e);
                    changeGitHubStatusColors(Status.Type.MAJOR);
                }

                @Override
                public void onNext(Status status) {
                    changeGitHubStatusColors(status.type);
                }
            });
        processOAuthRedirectUri();
    }

    private void changeGitHubStatusColors(Status.Type statusType) {
        mTxtGitHub.setText(getString(statusType.getMessageRes()));
        int color = ContextCompat.getColor(MainActivity.this, statusType.getColorRes());
        mTxtGitHub.setTextColor(color);
        DrawableCompat.setTint(mImgGitHub.getDrawable(), color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCall:
                final String maikoNormalNumber = "+55 16 99387-0941";
                final Uri uri = Uri.fromParts("tel", maikoNormalNumber, null);
                final Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
