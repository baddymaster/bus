package com.example.ayushshah.bus;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public ImageButton FAB;
    public static final String TAG = MapsActivity.class.getSimpleName();

    List<Address> geocodeMatches = null;
    String Address1;
    String Address2;
    String State;
    String Zipcode;
    String Country;
    Double sLat, sLng, dLat, dLng;
    private GoogleApiClient mGoogleApiClient;
    private Spinner spinner1;


    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            Toast.makeText(parent.getContext(),
                    "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        setContentView(R.layout.activity_maps);
        setContentView(R.layout.activity_main);
        FAB = (ImageButton) findViewById(R.id.imageButton);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Hello World",Toast.LENGTH_SHORT).show();
                go(v);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void go(View view){
        //EditText dst = (EditText)findViewById(R.id.dstText);
        //String destination = dst.getText().toString();
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        String destination = String.valueOf(spinner1.getSelectedItem());
        //Geocoder coder = new Geocoder(this);
        //Toast.makeText(getApplicationContext(), "Alive", Toast.LENGTH_LONG).show();
        //try {
            /*ArrayList<Address> addresses = (ArrayList<Address>) coder
                    .getFromLocationName(destination, 100);
            for (Address add : addresses) {
                dLat = add.getLatitude();
                dLng = add.getLongitude();*/
                if(destination.equals("Faculty Building")) {
                    dLat = 28.5441084;
                    dLng = 77.2707973;
                }
                if(destination.equals("Faculty Gate")) {
                    dLat = 28.5450297;
                    dLng = 77.2700409;
                }
                if(destination.equals("Main Gate")) {
                    dLat = 28.5471172;
                    dLng = 77.2724013;
                }
                if(destination.equals("Hostel")) {
                    dLat = 28.546848;
                    dLng = 77.2735676;
                }
                if(destination.equals("Student Center")) {
                    dLat = 28.5462535;
                    dLng = 77.2729699;
                }
                if(destination.equals("Academic Building")) {
                    dLat = 28.5448498;
                    dLng = 77.2722461;
                }
            //}
        /*}catch (IOException e) {
            e.printStackTrace();
        }*/

        Intent intent;
        intent = new Intent(this, MapsActivity.class);
      /*  Bundle b= new Bundle();

        b.putDouble("srcLat", sLat);
        b.putDouble("srcLng", sLng);
        b.putDouble("dstLat", dLat);
        b.putDouble("dstLng", dLng);
        intent.putExtras(b);
      */
        try {
            intent.putExtra("srcLat", sLat.toString());
            intent.putExtra("srcLng", sLng.toString());
            intent.putExtra("dstLat", dLat.toString());
            intent.putExtra("dstLng", dLng.toString());
     /*   arr.add(sLng.toString());
        arr.add(dLat.toString());
        arr.add(dLng.toString());*/

            Toast.makeText(getApplicationContext(), "S " + sLat.toString() + " " + sLng.toString() + "D " + dLat.toString() + " " + dLng.toString(), Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        catch(NullPointerException e)
        {
            //Toast.makeText(getApplicationContext(), "Please switch on GPS", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this,"Please switch on GPS",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location!=null) {
            sLat = location.getLatitude();
            sLng = location.getLongitude();
            try {
                geocodeMatches =
                        new Geocoder(this).getFromLocation(sLat, sLng, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            if (!geocodeMatches.isEmpty()) {
                Address1 = geocodeMatches.get(0).getAddressLine(0);
                Address2 = geocodeMatches.get(0).getAddressLine(1);
                State = geocodeMatches.get(0).getAdminArea();
                Zipcode = geocodeMatches.get(0).getPostalCode();
                Country = geocodeMatches.get(0).getCountryName();
            }
            TextView source = (TextView) findViewById(R.id.srcText);
            source.setText(Address1 + " " + Address2);
            //Toast.makeText(getApplicationContext(), Address1 + Address2 + State, Toast.LENGTH_LONG).show();
            if (location == null) {
                // Blank for a moment...
            } else {
                handleNewLocation(location);
            }
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
