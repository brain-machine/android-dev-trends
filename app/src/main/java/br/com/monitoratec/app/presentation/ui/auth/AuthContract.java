package br.com.monitoratec.app.presentation.ui.auth;

import br.com.monitoratec.app.model.entity.Status;
import br.com.monitoratec.app.model.entity.User;

/**
 * GitHub authentication MVP contract.
 *
 * Created by falvojr on 1/13/17.
 */
public interface AuthContract {

    interface View {
        void onGetStatusComplete(Status.Type statusType);

        void onGetUserComplete(String credential, User user);

        void showError(String message);
    }

    interface Presenter {
        void setView(AuthContract.View view);

        void getStatus();

        void getUser(String authorization);

        void getAccessTokenAndUser(String clientId, String clientSecret, String code);
    }
}
