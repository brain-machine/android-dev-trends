package br.com.monitoratec.app.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * User entity for GitHub API.
 *
 * @see <a href="https://developer.github.com/v3/users/#get-a-single-user">Get User</a>
 *
 * Created by falvojr on 1/11/17.
 */
public class User {
    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;
}
