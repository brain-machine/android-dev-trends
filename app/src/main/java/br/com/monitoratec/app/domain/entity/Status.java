package br.com.monitoratec.app.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import br.com.monitoratec.app.R;

/**
 * Entidade da API GitHub Status.
 *
 * @see <a href="https://status.github.com/api/last-message.json">Last Message</a>
 *
 * Created by falvojr on 1/9/17.
 */
public class Status {
    @SerializedName("status")
    public Type type;
    public String body;
    public Date created_on;

    public enum Type {
        NONE(android.R.color.black),
        @SerializedName("good")
        GOOD(R.color.materialGreen500),
        @SerializedName("minor")
        MINOR(R.color.materialOrange500),
        @SerializedName("major")
        MAJOR(R.color.materialRed500);

        private int colorRes;

        Type(int colorRes) {
            this.colorRes = colorRes;
        }

        public int getColorRes() {
            return colorRes;
        }
    }
}
