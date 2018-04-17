package ke.co.taara.taara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    String phoneFromDb;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        mCheckInDialog = new CheckInFragment();

        startShopping = findViewById(R.id.txtStartShopping);
        nameFromDb = findViewById(R.id.txtName);
        launchingIntent = getIntent();
        sharedPreferences = getSharedPreferences("SESSION", 0);
        username = sharedPreferences.getString("USERNAME", "default");
        mPhone = findViewById(R.id.txtPhone);


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
    }

    public void logOut(View view){
        mAuth.signOut();
        Intent signout = new Intent(getApplicationContext(), LogIn.class);
        startActivity(signout);
    }

    public void startShopping(android.view.View view){
        mCheckInDialog.show(getSupportFragmentManager(), "CHECK_IN_CONFIRM");
    //

    }

}
