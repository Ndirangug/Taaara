package ke.co.taara.taara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

import ke.co.taara.taara.Database.TaaraDbContract;
import ke.co.taara.taara.Database.TaaraDbHelper;
import ke.co.taara.taara.Fragments.CheckInFragment;

public class AccountInfo extends AppCompatActivity {

    AppCompatDialogFragment mCheckInDialog;
    TextView startShopping;
    Intent launchingIntent;
    SharedPreferences sharedPreferences;
    String username;
    TextView nameFromDb;
    TaaraDbHelper mdbHelper;
    SQLiteDatabase db;
    String firstName;
    String secondName;
    String fullname;
    TextView mPhone;
    TextView txtEmail;
    String phoneFromDb;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    double latitude;
    double longitude;
    private FirebaseAuth mAuth;
    private String storename;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        textView = findViewById(R.id.storeName);

        startShopping = findViewById(R.id.txtStartShopping);
        nameFromDb = findViewById(R.id.txtName);
        launchingIntent = getIntent();
        sharedPreferences = getSharedPreferences("SESSION", 0);
        username = sharedPreferences.getString("USERNAME", "default");
        mPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);


        mAuth = FirebaseAuth.getInstance();
        String mEmail = mAuth.getCurrentUser().getEmail().toString().toLowerCase();

        mdbHelper = new TaaraDbHelper(getApplicationContext());
        db = mdbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TaaraDbContract.TaaraUsers.COLUMN_NAME_EMAIL,
                TaaraDbContract.TaaraUsers.COLUMN_NAME_FIRST_NAME,
                TaaraDbContract.TaaraUsers.COLUMN_NAME_SECOND_NAME,
                TaaraDbContract.TaaraUsers.COLUMN_NAME_PHONE

        };

        String selection = TaaraDbContract.TaaraUsers.COLUMN_NAME_EMAIL + "=?";
        String[] selectionArgs = {mEmail};

        Cursor c = db.query(
                TaaraDbContract.TaaraUsers.TABLE_NAME,          // The table to query
                projection,                                     // The columns to return
                selection,                                      // The columns for the WHERE clause
                selectionArgs,                                  // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                       // The sort order
        );

        c.moveToFirst();
        firstName = c.getString(c.getColumnIndexOrThrow(TaaraDbContract.TaaraUsers.COLUMN_NAME_FIRST_NAME));
        secondName = c.getString(c.getColumnIndexOrThrow(TaaraDbContract.TaaraUsers.COLUMN_NAME_SECOND_NAME));
        fullname = firstName + " " + secondName;
        nameFromDb.setText(fullname);
        phoneFromDb = c.getString(c.getColumnIndexOrThrow(TaaraDbContract.TaaraUsers.COLUMN_NAME_PHONE));
        mPhone.setText(phoneFromDb);
        txtEmail.setText(mEmail);


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                Toast.makeText(getApplicationContext(), "UNABLE TO ACCESS LOCATION.ENABLE LOCATION ACCESSS IN DEVICE SETTINGS TO ENJOY ALL FEATURES", Toast.LENGTH_LONG).show();
                                return;
                            }
                            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                            if (mLastLocation != null) {
                                latitude = mLastLocation.getLatitude();
                                longitude = mLastLocation.getLongitude();

                            }

                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, new LocationRequest(), new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();

                                }
                            });



                        }

                        @Override
                        public void onConnectionSuspended(int i) {

                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }



    }

    public void logOut(View view){
        mAuth.signOut();
        Intent signout = new Intent(getApplicationContext(), LogIn.class);
        startActivity(signout);
    }

    public void startShopping(android.view.View view){
        mCheckInDialog = new CheckInFragment();
        mCheckInDialog.show(getSupportFragmentManager(), "CHECK_IN_CONFIRM");
        Bundle bundle = new Bundle();
        bundle.putDouble("LONGITUDE", longitude);
        bundle.putDouble("LATITUDE", latitude);
        mCheckInDialog.setArguments(bundle);
    //

    }


    public void editProfile(View view){
        Intent intent = new Intent(getApplicationContext(), EditProfile.class);
        startActivity(intent);
    }

    protected void onStart() {

        mGoogleApiClient.connect();

         super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (Double.valueOf(latitude) == null) {
            Toast.makeText(getApplicationContext(), "ENABLE LOCATION IN DEVICE SETTINGS", Toast.LENGTH_LONG).show();
        }

        super.onResume();


}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        Log.i("BACK", "pressed");
    }
}
