package com.example.archana.seva2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Archana on 5/10/2017.
 */

public class RegisterActivity extends AppCompatActivity{

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        SharedPreferences sp1 = this.getSharedPreferences("Register", 0);

    }
    public void registration(View view) throws JSONException {

        EditText firstName = (EditText) findViewById(R.id.firstname);
        EditText lastName = (EditText) findViewById(R.id.lastname);
        EditText userName = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText dob = (EditText) findViewById(R.id.Dob);
        EditText address1 = (EditText) findViewById(R.id.address1);
        EditText address2 = (EditText) findViewById(R.id.address2);
        EditText city = (EditText) findViewById(R.id.city);
        EditText state = (EditText) findViewById(R.id.state);
        EditText zip = (EditText) findViewById(R.id.zip);
        EditText phone = (EditText) findViewById(R.id.phone);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        String sex = ((RadioButton)findViewById(radioSexGroup.getCheckedRadioButtonId())).getText().toString();
        String gender;
        if (sex == "male"){
            gender = "M";
        }
        else{
            gender = "F";
        }

        RequestParams params = new RequestParams();

        params.put("userName",userName.getText().toString());
        params.put("password", password.getText().toString() );
        params.put("firstName", firstName.getText().toString());
        params.put("lastName",lastName.getText().toString() );
        params.put("dob",dob.getText().toString() );
        params.put("gender", gender);
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
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.d("Failure", String.valueOf(response));
            }
        });

    }
}

