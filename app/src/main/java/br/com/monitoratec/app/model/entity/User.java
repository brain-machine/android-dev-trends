package br.com.monitoratec.app.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * User entity for GitHub API.
 *
 * @see <a href="https://developer.github.com/v3/users/#get-a-single-user">Get User</a>
 * @see <a href="https://github.com/mcharmas/android-parcelable-intellij-plugin">Android Parcelable Generator</a>
 * <p>
 * Created by falvojr on 1/11/17.
 */
public class User implements Parcelable {

    public String login;
    @SerializedName("avatar_url")
    public String avatarUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.login);
        dest.writeString(this.avatarUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.login = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
