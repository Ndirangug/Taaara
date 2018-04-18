package ke.co.taara.taara;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import ke.co.taara.taara.Database.TaaraDbContract;
import ke.co.taara.taara.Database.TaaraDbHelper;

public class EditProfile extends AppCompatActivity {

    TaaraDbHelper mdbHelper;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    String username;
    FirebaseAuth mAuth;

    EditText firstName, secondName, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.secondname);
        phone = findViewById(R.id.phone);

        mAuth = FirebaseAuth.getInstance();
        username = mAuth.getCurrentUser().getEmail().toString();
        Log.i("USERNAME", username);

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
        String[] selectionArgs = {username};

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
        firstName.setText(c.getString(c.getColumnIndex(TaaraDbContract.TaaraUsers.COLUMN_NAME_FIRST_NAME)));
        secondName.setText(c.getString(c.getColumnIndex(TaaraDbContract.TaaraUsers.COLUMN_NAME_SECOND_NAME)));
        phone.setText(c.getString(c.getColumnIndex(TaaraDbContract.TaaraUsers.COLUMN_NAME_PHONE)));
    }

    public void saveChanges(View view){
        db = mdbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_FIRST_NAME, firstName.getText().toString());
        values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_SECOND_NAME, secondName.getText().toString());
        values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_PHONE, phone.getText().toString());
        long changedId;
        String whereClause = TaaraDbContract.TaaraUsers.COLUMN_NAME_EMAIL + "=?";
        String[] whereArgs = {username};
        changedId = db.update(TaaraDbContract.TaaraUsers.TABLE_NAME, values, whereClause, whereArgs);
        Log.i("CHANGED", String.valueOf(changedId));
        Intent intent = new Intent(getApplicationContext(), AccountInfo.class);
        startActivity(intent);

    }
}
