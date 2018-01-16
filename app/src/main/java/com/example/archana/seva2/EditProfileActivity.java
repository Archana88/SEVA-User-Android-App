package com.example.archana.seva2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONException;

/**
 * Created by Archana on 5/11/2017.
 */

public class EditProfileActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        SharedPreferences sp1 = this.getSharedPreferences("Register", 0);
    }


    public void updateprofile(View view) throws JSONException {

        Intent intent = new Intent(EditProfileActivity.this, UpdateprofileActivity.class);
        startActivity(intent);

    }
}
