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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
/**
 * Created by Archana on 5/10/2017.
 */

public class AddUserActivity extends AppCompatActivity{

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        userName = sp1.getString("userName", null);
    }

    public void emergencyadd(View view) throws JSONException {
        //Log.d("click","success");
        //Log.d("click",userName);
        EditText emergfirstName1 = (EditText) findViewById(R.id.emergfirstname1);
        EditText emerglastName1 = (EditText) findViewById(R.id.emerglastname1);
        EditText emergaddress1 = (EditText) findViewById(R.id.emergaddress1);
        EditText emergaddress2 = (EditText) findViewById(R.id.emergaddress2);
        EditText emergcity = (EditText) findViewById(R.id.emergcity);
        EditText emergstate = (EditText) findViewById(R.id.emergstate);
        EditText emergzip = (EditText) findViewById(R.id.emergzip);
        EditText emergphone = (EditText) findViewById(R.id.emergphone);
        EditText emergrelation = (EditText) findViewById(R.id.emergrelation);

        RequestParams params = new RequestParams();

        params.put("firstName", emergfirstName1.getText().toString());
        params.put("lastName",emerglastName1.getText().toString() );
        params.put("address1",emergaddress1.getText().toString());
        params.put("address2", emergaddress2.getText().toString());
        params.put("city",emergcity.getText().toString() );
        params.put("state", emergstate.getText().toString());
        params.put("zip", emergzip.getText().toString());
        params.put("phone", "+1"+emergphone.getText().toString());
        params.put("relationship", emergrelation.getText().toString());
        //params.put("isVolunteer", "false");
        //params.put("isAdmin", "false");

        String url = "users/"+userName+"/emergencydetails";
        RestClient.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("click", String.valueOf(response));
                Toast.makeText(AddUserActivity.this, "Emergency contact successfully added!",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AddUserActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.d("click", String.valueOf(response));
            }
        });
    }

    }
