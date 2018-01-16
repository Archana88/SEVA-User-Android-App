package com.example.archana.seva2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

/**
 * Created by Archana on 5/10/2017.
 */

public class DeleteUserActivity extends AppCompatActivity {

    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteuser);

        SharedPreferences sp1 = this.getSharedPreferences("Register", 0);
        userName = sp1.getString("userName", null);

    }


}
