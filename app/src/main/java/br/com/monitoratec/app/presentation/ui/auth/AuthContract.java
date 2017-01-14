package br.com.monitoratec.app.presentation.ui.auth;

import br.com.monitoratec.app.domain.entity.Status;
import br.com.monitoratec.app.domain.entity.User;

/**
 * GitHub authentication MVP contract.
 *
 * Created by falvojr on 1/13/17.
 */
public interface AuthContract {

    interface View {
        void onLoadStatusComplete(Status.Type statusType);

        void onAuthSuccess(String credential, User entity);

        void showError(String message);
    }

    interface Presenter {
        void setView(AuthContract.View view);

        void loadStatus();

        void callGetUser(String authorization);

        void callAccessTokenGettingUser(String cliId, String cliSecret, String code);
    }
}
