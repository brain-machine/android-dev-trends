package br.com.monitoratec.app.presentation.ui.repos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import javax.inject.Inject;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.Repo;
import br.com.monitoratec.app.domain.entity.User;
import br.com.monitoratec.app.presentation.base.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * List {@link Repo}'s activity (view).
 *
 * Created by falvojr on 1/14/17.
 */
public class ReposActivity extends BaseActivity implements ReposContract.View {

    public static final String EXTRA_CREDENTIAL = "Intent.Extra.Credential";
    public static final String EXTRA_USER = "Intent.Extra.User";

    @BindView(R.id.rvRepos) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.fab) FloatingActionButton mFab;

    @Inject ReposContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        ButterKnife.bind(this);
        super.getDaggerUiComponent().inject(this);
        mPresenter.setView(this);

        setSupportActionBar(mToolbar);
        mFab.setOnClickListener(view -> this.startNewRepoActivity());

        final String credential = getIntent().getStringExtra(EXTRA_CREDENTIAL);
        final User unusedUser = getIntent().getParcelableExtra(EXTRA_USER);
        if(credential != null && unusedUser != null) {
            mPresenter.loadRepos(credential);
        } else {
            this.showError(getString(R.string.msg_credential_not_found));
        }
    }

    @Override
    public void setupRepos(List<Repo> repos) {
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        ReposAdapter adapter = new ReposAdapter(repos);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void startNewRepoActivity() {
        Snackbar.make(mFab, "TODO 'POST /user/repo'", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(mFab, message, Snackbar.LENGTH_LONG).show();
    }
}
