package br.com.monitoratec.app.domain.entity;

/**
 * Access Token entity for GitHub API OAuth.
 *
 * @see <a href="https://developer.github.com/v3/oauth/#2-github-redirects-back-to-your-site">Access Token</a>
 *
 * Created by falvojr on 1/11/17.
 */
public class AccessToken {
    private String access_token;
    private String token_type;

    public String getAuthCredential() {
        final char firstChar = token_type.charAt(0);
        if (!Character.isUpperCase(firstChar)) {
            final String first = Character.toString(firstChar).toUpperCase();
            token_type = first + token_type.substring(1);
        }
        return token_type + " " + access_token;
    }
}
