package br.com.monitoratec.app;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.monitoratec.app.domain.GitHubStatusApi;
import br.com.monitoratec.app.domain.entity.Status;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView mImgGitHub;
    private TextView mTxtGitHub;
    private TextInputLayout mLayoutTxtUsername;
    private TextInputLayout mLayoutTxtPassword;

    private GitHubStatusApi mGitHubStatusApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImgGitHub = (ImageView) findViewById(R.id.ivGitHub);
        mTxtGitHub = (TextView) findViewById(R.id.tvGitHub);
        mLayoutTxtUsername = (TextInputLayout) findViewById(R.id.tilUsername);
        mLayoutTxtPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        // 2012-12-07T18:11:55Z
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(GitHubStatusApi.BASE_URL)
                .build();

        mGitHubStatusApi = retrofit.create(GitHubStatusApi.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeGitHubStatusColors(Status.Type.NONE.getColorRes(), getString(R.string.txt_loading));
        Call<Status> call = mGitHubStatusApi.lastMessage();
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.isSuccessful()) {
                    Status status = response.body();
                    changeGitHubStatusColors(status.type.getColorRes(), status.body);
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        changeGitHubStatusColors(Status.Type.MAJOR.getColorRes(), errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                changeGitHubStatusColors(Status.Type.MAJOR.getColorRes(), t.getMessage());
            }
        });
    }

    private void changeGitHubStatusColors(int colorRes, String message) {
        mTxtGitHub.setText(message);
        int color = ContextCompat.getColor(MainActivity.this, colorRes);
        mTxtGitHub.setTextColor(color);
        DrawableCompat.setTint(mImgGitHub.getDrawable(), color);
    }

}
