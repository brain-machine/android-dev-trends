package br.com.monitoratec.app;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import br.com.monitoratec.app.domain.GitHubApi;
import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.util.AppUtils;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView mImgGitHub;
    private TextView mTxtGitHub;
    private TextInputLayout mLayoutTxtUsername;
    private TextInputLayout mLayoutTxtPassword;
    private Button mBtnBasicAuth;

    private GitHubStatusApi mGitHubStatusApi;
    private GitHubApi mGitHubApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgGitHub = (ImageView) findViewById(R.id.ivGitHub);
        mTxtGitHub = (TextView) findViewById(R.id.tvGitHub);
        mLayoutTxtUsername = (TextInputLayout) findViewById(R.id.tilUsername);
        mLayoutTxtPassword = (TextInputLayout) findViewById(R.id.tilPassword);
        mBtnBasicAuth = (Button) findViewById(R.id.btBasicAuth);

        mBtnBasicAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (AppUtils.validateRequiredFields(MainActivity.this,
                        mLayoutTxtUsername, mLayoutTxtPassword)) {
                    String username = mLayoutTxtUsername.getEditText().getText().toString();
                    String password = mLayoutTxtPassword.getEditText().getText().toString();
                    String credential = Credentials.basic(username, password);
                    mGitHubApi.basicAuth(credential).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                String login = response.body().login;
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
        });

        mGitHubStatusApi = GitHubStatusApi.RETROFIT.create(GitHubStatusApi.class);
        mGitHubApi = GitHubApi.RETROFIT.create(GitHubApi.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeGitHubStatusColors(Status.Type.NONE.getColorRes(), getString(R.string.txt_loading));
        Call<Status> call = mGitHubStatusApi.lastMessage();
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    Status status = response.body();
                    changeGitHubStatusColors(status.type.getColorRes(), status.body);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        changeGitHubStatusColors(Status.Type.MAJOR.getColorRes(), errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                changeGitHubStatusColors(Status.Type.MAJOR.getColorRes(), t.getMessage());
            }
        });
    }

    private void changeGitHubStatusColors(int colorRes, String message) {
        mTxtGitHub.setText(message);
        int color = ContextCompat.getColor(MainActivity.this, colorRes);
        mTxtGitHub.setTextColor(color);
        DrawableCompat.setTint(mImgGitHub.getDrawable(), color);
    }

}
