package com.example.archana.seva2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Archana on 5/11/2017.
 */

public class UpdateprofileActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        SharedPreferences sp1 = this.getSharedPreferences("Register", 0);

    }
    public void registration(View view) throws JSONException {

        EditText userName = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText address1 = (EditText) findViewById(R.id.address1);
        EditText address2 = (EditText) findViewById(R.id.address2);
        EditText city = (EditText) findViewById(R.id.city);
        EditText state = (EditText) findViewById(R.id.state);
        EditText zip = (EditText) findViewById(R.id.zip);
        EditText phone = (EditText) findViewById(R.id.phone);

        RequestParams params = new RequestParams();

        params.put("userName",userName.getText().toString());
        params.put("password", password.getText().toString() );
        params.put("address1",address1.getText().toString());
        params.put("address2", address2.getText().toString());
        params.put("city",city.getText().toString() );
        params.put("state", state.getText().toString());
        params.put("zip", zip.getText().toString());
        params.put("phone", phone.getText().toString());
        params.put("isVolunteer", "false");
        params.put("isAdmin", "false");

        /*params.put("userName","test");
        params.put("password", "johnny123");
        params.put("firstName", "john");
        params.put("lastName","doe");
        params.put("dob","2000-04-23T18:25:43.511Z");
        params.put("gender", "M");
        params.put("address1","101");
        params.put("address2", "");
        params.put("city","San Jose" );
        params.put("state", "CA");
        params.put("zip", "95112");
        params.put("phone", "");
        params.put("isVolunteer", "false");
        params.put("isAdmin", "false");*/

        String url = "users";
        RestClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("Success", String.valueOf(response));
                Toast.makeText(UpdateprofileActivity.this, "Profile successfully updated!",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UpdateprofileActivity.this, HomeActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.d("Failure", String.valueOf(response));
            }
        });

    }

}
