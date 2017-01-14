package br.com.monitoratec.app.presentation.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import br.com.monitoratec.app.presentation.base.BaseActivity;
import br.com.monitoratec.app.presentation.helper.AppHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;

/**
 * GitHub authentication activity.
 *
 * Created by falvojr on 1/13/17.
 */
public class AuthActivity extends BaseActivity implements AuthContract.View {

    private static final String TAG = AuthActivity.class.getSimpleName();

    @BindView(R.id.ivGitHub) ImageView mImgGitHub;
    @BindView(R.id.tvGitHub) TextView mTxtGitHub;
    @BindView(R.id.tilUsername) TextInputLayout mLayoutTxtUsername;
    @BindView(R.id.tilPassword) TextInputLayout mLayoutTxtPassword;
    @BindView(R.id.btOAuth) Button mBtnOAuth;

    @Inject SharedPreferences mSharedPrefs;
    @Inject AppHelper mAppHelper;
    @Inject AuthContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        super.getDaggerUiComponent().inject(this);

        mPresenter.setView(this);

        this.bindUsingRx();
    }

    private void bindUsingRx() {
        RxTextView.textChanges(mLayoutTxtUsername.getEditText())
                .skip(1)
                .subscribe(text -> {
                    mAppHelper.validateRequiredFields(mLayoutTxtUsername);
                });
        RxTextView.textChanges(mLayoutTxtPassword.getEditText())
                .skip(1)
                .subscribe(text -> {
                    mAppHelper.validateRequiredFields(mLayoutTxtPassword);
                });
        RxView.clicks(mBtnOAuth).subscribe(aVoid -> {
            final String baseUrl = GitHubOAuthService.BASE_URL + "authorize";
            final String clientId = getString(R.string.oauth_client_id);
            final String redirectUri = getOAuthRedirectUri();
            final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    @OnClick(R.id.btBasicAuth)
    protected void onBasicAuthClick(Button view) {
        if (mAppHelper.validateRequiredFields(mLayoutTxtUsername, mLayoutTxtPassword)) {
            String username = mLayoutTxtUsername.getEditText().getText().toString();
            String password = mLayoutTxtPassword.getEditText().getText().toString();
            final String credential = Credentials.basic(username, password);
            mPresenter.callGetUser(credential);
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
                mPresenter.callAccessTokenGettingUser(clientId, clientSecret, code);
            } else if (uri.getQueryParameter("error") != null) {
                showError(uri.getQueryParameter("error"));
            }
            // Limpar os dados para evitar chamadas múltiplas
            getIntent().setData(null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeGitHubStatusColors(Status.Type.NONE);
        mPresenter.loadStatus();
        processOAuthRedirectUri();
    }

    private void changeGitHubStatusColors(Status.Type statusType) {
        mTxtGitHub.setText(getString(statusType.getMessageRes()));
        int color = ContextCompat.getColor(AuthActivity.this, statusType.getColorRes());
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

    @Override
    public void onLoadStatusComplete(Status.Type statusType) {
        changeGitHubStatusColors(statusType);
    }

    @Override
    public void onAuthSuccess(String credential, User user) {
        String credentialKey = getString(R.string.sp_credential_key);
        mSharedPrefs.edit().putString(credentialKey, credential).apply();
        Snackbar.make(mImgGitHub, credential, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mImgGitHub, message, Snackbar.LENGTH_LONG).show();
    }
}
