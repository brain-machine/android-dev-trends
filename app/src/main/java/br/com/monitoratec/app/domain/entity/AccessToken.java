package br.com.monitoratec.app.domain.entity;

/**
 * Created by falvojr on 1/11/17.
 */

public class AccessToken {
    public String access_token;
    public String token_type;

    public String getAuthCredential() {
        final char firstChar = token_type.charAt(0);
        if (!Character.isUpperCase(firstChar)) {
            final String first = Character.toString(firstChar).toUpperCase();
            token_type = first + token_type.substring(1);
        }
        return token_type + " " + access_token;
    }
}
