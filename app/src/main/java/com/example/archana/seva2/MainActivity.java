package com.example.archana.seva2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.*;
import org.json.JSONException;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "HomeScreenMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        String isLoggedIn=sp1.getString("isLoggedIn", null);

        setTitle("Seva - User");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Click",token);
        /*if(isLoggedIn != null){
            Log.d("Logged In",isLoggedIn);
            GlobalClass.setTokenValue(sp1.getString("API_TOKEN", null));
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else{
            Log.d("Logged In","NOT SET");
        }*/


    }

    public void loginAuthenticate (View view){

        EditText usernameTextBox = (EditText) findViewById(R.id.username);
        String username = usernameTextBox.getText().toString();
        GlobalClass.setUserName(username);

        Log.d("FIREBASE_TOKEN", FirebaseInstanceId.getInstance().getToken());

        EditText passwordTextBox = (EditText) findViewById(R.id.password);
        String password = passwordTextBox.getText().toString();

        Log.d("userName",username);
        Log.d("password",password);

        RequestParams params = new RequestParams();
        params.put("userName", username);
        params.put("password", password);
        RestClient.post("authenticate", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String API_TOKEN = null;
                try {
                    API_TOKEN = response.getString("token");
                    GlobalClass.setTokenValue(API_TOKEN);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedPreferences sp=getSharedPreferences("Login", 0);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("isLoggedIn","yes");
                Ed.putString("userName",GlobalClass.getUserName());
                Ed.putString("API_TOKEN",API_TOKEN);
                Ed.commit();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                Log.d("second",String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                try {
                    TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
                    errorMessage.setText(response.getString("message"));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public void registrationtologin (View view) {


        Log.v("inside registration", "hello");

                Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(myIntent);
    }
    

}