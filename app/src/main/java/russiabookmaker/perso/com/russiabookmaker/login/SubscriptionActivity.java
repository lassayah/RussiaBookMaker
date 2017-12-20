package russiabookmaker.perso.com.russiabookmaker.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import russiabookmaker.perso.com.russiabookmaker.HomeActivity;
import russiabookmaker.perso.com.russiabookmaker.R;

public class SubscriptionActivity extends AppCompatActivity {

    private TextView newUsername;
    private TextView newPassword;
    private TextView newPasswordConfirm;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newUsername = (TextView) findViewById(R.id.newUsernameEdit);
        newPassword = (TextView) findViewById(R.id.newPasswordEdit);
        newPasswordConfirm = (TextView) findViewById(R.id.newPasswordConfirmEdit);
        validateButton = (Button) findViewById(R.id.submitButton);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPassword.getText() == newPasswordConfirm.getText())
                    callService();
            }
        });

    }

    private void callService(){
        SubscriptionService subscriptionService = SubscriptionService.retrofit.create(SubscriptionService.class);
        final Call<User> call =
                subscriptionService.callSusbcription(newUsername.getText().toString(), newPassword.getText().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                System.out.println("user : " + user.isLoggedIn());
                if (user.isLoggedIn()) {
                    Intent intent = new Intent(SubscriptionActivity.this, HomeActivity.class);
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.login), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.login), newUsername.getText().toString());
                    editor.commit();
                    startActivity(intent);
                    finish();
                }
                else
                {

                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("callko", t.getMessage());
                System.out.println(t.getCause());
            }
        });
    }

}
