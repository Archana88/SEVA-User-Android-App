package com.example.archana.seva2;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private LocationManager locationManager=null;
    private LocationListener locationListener=null;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb =null;

    private static final String TAG = "Debug";
    private Boolean flag = false;


    private String userName, firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sp1 = this.getSharedPreferences("Login", 0);
        userName = sp1.getString("userName", null);
        GlobalClass.setUserName(userName);

        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.append(", " + userName + "!");

        String url = "users/" + userName;
        RestClient.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    TextView firstName = (TextView) findViewById(R.id.firstName);
                    TextView lastName = (TextView) findViewById(R.id.lastName);
                    firstName.append(response.getString(""));
                    lastName.append(response.getString(""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.d("Failure", String.valueOf(response));
            }
        });

        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //  pb = (ProgressBar) findViewById(R.id.progressBar1);
        //  pb.setVisibility(View.INVISIBLE);


        btnGetLocation = (Button) findViewById(R.id.button3);
        btnGetLocation.setOnClickListener(this);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onClick(View v) {
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");


            //  pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                Log.v(TAG, "onCheck");
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // Toast.makeText(YourService.this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,1000,10f,locationListener);
            Log.v(TAG, "onlocation");
            Location l = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            String url = "requests/raise";
            RequestParams params = new RequestParams();
            params.put("lon", l.getLongitude() );
            params.put("lat", l.getLatitude() );
            params.put("raisedBy", userName);
            params.put("deviceID", FirebaseInstanceId.getInstance().getToken());

            Log.v(TAG, params.toString() );
            RestClient.post(url, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response){

                    Log.d("Success", String.valueOf(response));
                    Toast.makeText(HomeActivity.this, "Alarm successfully raised!",
                            Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String str, Throwable e) {
                    Log.d("Failure", String.valueOf(str));
                }
            });
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {
            Log.v("tag","location"+loc);
            editLocation.setText("");
            //      pb.setVisibility(View.INVISIBLE);
            //      Toast.makeText(getBaseContext(),"Location changed : Lat: " +
            //                      loc.getLatitude()+ " Lng: " + loc.getLongitude(),
            //              Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
        /*    String cityName=null;
            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address>  addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName=addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            } */

            //   String Longitude = longitude;
            //  String Longitude = longitude;

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }

    public void addEmergencyContacts (View view) {

        Intent myIntent = new Intent(HomeActivity.this, AddUserActivity.class);
        startActivity(myIntent);
    }

    public void deleteEmergencyContacts (View view) {

        Intent myIntent = new Intent(HomeActivity.this, DeleteUserActivity.class);
        startActivity(myIntent);
    }

    public void editProfile (View view) {

        Intent myIntent = new Intent(HomeActivity.this, EditProfileActivity.class);
        startActivity(myIntent);
    }

}


