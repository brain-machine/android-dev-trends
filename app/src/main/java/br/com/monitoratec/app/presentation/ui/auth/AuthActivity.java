package br.com.monitoratec.app.presentation.ui.auth;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.infraestructure.storage.service.GitHubOAuthService;
import br.com.monitoratec.app.presentation.base.BaseActivity;
import br.com.monitoratec.app.presentation.helper.AppHelper;
import br.com.monitoratec.app.presentation.ui.repos.ReposActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Credentials;

/**
 * GitHub authentication activity (view).
 *
 * Created by falvojr on 1/13/17.
 */
public class AuthActivity extends BaseActivity implements AuthContract.View {

    @BindView(R.id.ivGitHub) ImageView mImgGitHub;
    @BindView(R.id.tvGitHub) TextView mTxtGitHub;
    @BindView(R.id.tilUsername) TextInputLayout mLayoutTxtUsername;
    @BindView(R.id.tilPassword) TextInputLayout mLayoutTxtPassword;
    @BindView(R.id.btOAuth) Button mBtnOAuth;

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

    @Override
    protected void onResume() {
        super.onResume();
        // Restore default GitHub fields status
        this.onGetStatusComplete(Status.Type.NONE);
        // Get last status from GitHub Status API
        mPresenter.getStatus();
        // Process (if necessary) OAUth redirect callback
        this.processOAuthRedirectUri();
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
                final String normalNumber = "+55 16 99387-0941";
                final Uri uri = Uri.fromParts("tel", normalNumber, null);
                final Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetStatusComplete(Status.Type statusType) {
        int color = ContextCompat.getColor(AuthActivity.this, statusType.getColorRes());
        mTxtGitHub.setText(getString(statusType.getMessageRes()));
        mTxtGitHub.setTextColor(color);
        DrawableCompat.setTint(mImgGitHub.getDrawable(), color);
    }

    @Override
    public void onGetUserComplete(String credential, User user) {
        Intent intent = new Intent(this, ReposActivity.class);
        intent.putExtra(ReposActivity.EXTRA_CREDENTIAL, credential);
        intent.putExtra(ReposActivity.EXTRA_USER, user);
        startActivity(intent);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mImgGitHub, message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.btBasicAuth)
    void onBasicAuthClick(Button view) {
        if (mAppHelper.validateRequiredFields(mLayoutTxtUsername, mLayoutTxtPassword)) {
            String username = mLayoutTxtUsername.getEditText().getText().toString();
            String password = mLayoutTxtPassword.getEditText().getText().toString();
            final String credential = Credentials.basic(username, password);
            mPresenter.getUser(credential);
        }
    }

    private void bindUsingRx() {
        final EditText editTextUsername = mLayoutTxtUsername.getEditText();
        final EditText editTextPassword = mLayoutTxtPassword.getEditText();
        if (editTextUsername != null && editTextPassword != null) {
            RxTextView.textChanges(editTextUsername)
                    .skip(1)
                    .subscribe(text -> {
                        mAppHelper.validateRequiredFields(mLayoutTxtUsername);
                    });
            RxTextView.textChanges(editTextPassword)
                    .skip(1)
                    .subscribe(text -> {
                        mAppHelper.validateRequiredFields(mLayoutTxtPassword);
                    });
        }
        RxView.clicks(mBtnOAuth).subscribe(aVoid -> {
            final String baseUrl = GitHubOAuthService.BASE_URL + "authorize";
            final String clientId = getString(R.string.oauth_client_id);
            final String redirectUri = getOAuthRedirectUri();
            final Uri uri = Uri.parse(baseUrl + "?client_id=" + clientId + "&redirect_uri=" + redirectUri);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    private String getOAuthRedirectUri() {
        return getString(R.string.oauth_schema) + "://" + getString(R.string.oauth_host);
    }

    private void processOAuthRedirectUri() {
        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        final Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(this.getOAuthRedirectUri())) {
            // use the parameter your API exposes for the code (mostly it's "code")
            String code = uri.getQueryParameter("code");
            if (code != null) {
                // get access token and related User
                String clientId = getString(R.string.oauth_client_id);
                String clientSecret = getString(R.string.oauth_client_secret);
                mPresenter.getAccessTokenAndUser(clientId, clientSecret, code);
            } else if (uri.getQueryParameter("error") != null) {
                showError(uri.getQueryParameter("error"));
            }
            // Clear Intent Data preventing multiple calls
            getIntent().setData(null);
        }
    }
}
