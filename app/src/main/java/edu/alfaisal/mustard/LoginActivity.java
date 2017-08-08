package edu.alfaisal.mustard;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity {
    Button loginButton;             //Login Button
    EditText username;              //Username
    EditText password;              //password
    String grantType;               //Authentication grant type
    String apiURL;                  //API Address
    String authorization;           //authorization key
    RestAdapter adapter;            //API Adapter
    ConnectionAPI connection;       //API Connection object
    SharedPreferences accessTokenSP;  //Access Token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Initialization of LoginActivity object
        loginButton = (Button)findViewById(R.id.loginButton);
        username = (EditText)findViewById(R.id.userLogin);
        password = (EditText)findViewById(R.id.userPass);

        grantType = "password";
        apiURL = "http://hackathonapi.xyz:8080/api";
        authorization = "Basic Q0xJRU5UX0lEXzY0MjA3NDM3MzI0NDE5Mzk4MTpDTElFTlRfU0VDUkVUXzY0MjA3NDM3MzI0Mzk4NTc0MQ==";
        adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(apiURL).build();
        connection = adapter.create(ConnectionAPI.class);
        accessTokenSP = PreferenceManager.getDefaultSharedPreferences(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //establishing the connection
                connection.login(authorization, username.getText().toString(), password.getText().toString(), grantType, new Callback<Token>() {
                    @Override
                    public void success(Token token, Response response) {
                        //Moving the user to the Timeline page
                        startActivity(new Intent(LoginActivity.this, TimelineActivity.class));
                        //Soring Access Token

                        Editor accessEditor = accessTokenSP.edit();
                        accessEditor.putString("accessToken", token.getAccess_token());
                        accessEditor.apply();
                        Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //Displaying error message

                    }
                });
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
