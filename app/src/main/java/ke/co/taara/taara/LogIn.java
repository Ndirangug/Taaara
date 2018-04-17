package ke.co.taara.taara;

import android.content.Intent;
import android.content.SharedPreferences;
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


public class LogIn extends AppCompatActivity {

    EditText mUserName;
    EditText mUserPassword;
    String mEmail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        sharedPreferences = getSharedPreferences("SESSION", 0);
        editor = sharedPreferences.edit();


        mUserName = findViewById(R.id.editEmailLogIn);
        mUserPassword = findViewById(R.id.editPasswordLogin);

        mAuth = FirebaseAuth.getInstance();

    }

    public void showAccountInfo(android.view.View view){
        String email = mUserName.getText().toString();
        String password = mUserPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){
                           //sign in success
                           String user = mAuth.getCurrentUser().getEmail().toString();
                           editor.putString("CURRENTUSER", user).commit();
                           Intent loggedIn = new Intent(getApplicationContext(), AccountInfo.class);
                           startActivity(loggedIn);

                       } else{
                           Log.w("AUTH", "signInWithEmail:failure", task.getException());
                           Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_login), Toast.LENGTH_LONG);
                       }
                    }
                });



    }

    public void signUpInstead(View view){
        Intent signUp = new Intent(getApplicationContext(), SignUp.class);
        startActivity(signUp);
    }

    @Override
    protected void onResume() {
        mUserName.setText("");
        mUserPassword.setText("");
        if(!(mAuth.getCurrentUser() == null)){
            Intent loggedIn = new Intent(getApplicationContext(), AccountInfo.class);
            startActivity(loggedIn);
        }
        super.onResume();
    }


}
