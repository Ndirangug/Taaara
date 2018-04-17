package ke.co.taara.taara;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import ke.co.taara.taara.Database.TaaraDbContract;
import ke.co.taara.taara.Database.TaaraDbHelper;


public class SignUp extends AppCompatActivity {

    EditText mFirstName;
    EditText mSecondName;
    EditText mPhone;
    EditText mEmail;
    EditText mPassword;
    String email;
    String password;
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TaaraDbHelper mdbHelper;
    SQLiteDatabase db;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedPreferences = getSharedPreferences("SESSION", 0);
        editor = sharedPreferences.edit();

        mEmail = findViewById(R.id.editEmailSignUp);
        mPassword = findViewById(R.id.editPasswordSignUp);
        mFirstName = findViewById(R.id.editFirstNameSignUp);
        mSecondName = findViewById(R.id.editSecondNameSignUp);
        mPhone = findViewById(R.id.editPhone);

        mAuth = FirebaseAuth.getInstance();
        mdbHelper = new TaaraDbHelper(getApplicationContext());
        db = mdbHelper.getWritableDatabase();

    }



    public void signUp(android.view.View view){
        email = mEmail.getText().toString().toLowerCase();
        password = mPassword.getText().toString();
        final String fistname = mFirstName.getText().toString();
        final String secondName = mSecondName.getText().toString();
        final String phone = mPhone.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "SIGNED UP SUCCESSFULLY", Toast.LENGTH_LONG).show();

                            //write other details to db
                            // Create a new map of values, where column names are the keys
                            ContentValues values = new ContentValues();
                            values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_FIRST_NAME, fistname);
                            values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_SECOND_NAME, secondName);
                            values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_PHONE, phone);
                            values.put(TaaraDbContract.TaaraUsers.COLUMN_NAME_EMAIL, email);

                            long newUserId;
                            newUserId = db.insert(TaaraDbContract.TaaraUsers.TABLE_NAME, null, values);
                            Log.i("NEW USER: ", String.valueOf(newUserId) );
                            Intent logIn = new Intent(getApplicationContext(), LogIn.class);
                            startActivity(logIn);
                            mAuth.signInWithEmailAndPassword(email, password);
                            Log.w("SIGNUP", "Result: "+ task.getResult().toString()+ "exception: " + task.getException());
                            // Write a message to the database


                        }

                        else {
                            Toast.makeText(getApplicationContext(), "FAIL: SEEMS LIKE THIS EMAIL IS ALREADY TAKEN", Toast.LENGTH_LONG).show();
                            Log.w("AUTH", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });

    }

    public void logInInstead(View view){
        Intent logIn = new Intent(getApplicationContext(), LogIn.class);
        startActivity(logIn);
    }

    @Override
    protected void onResume() {
        if(!(mAuth.getCurrentUser() == null)){
            Intent loggedIn = new Intent(getApplicationContext(), AccountInfo.class);
            startActivity(loggedIn);
        }
        super.onResume();
    }

}
