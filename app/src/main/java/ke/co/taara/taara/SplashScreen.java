package ke.co.taara.taara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread splash = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    Intent quitSplash = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(quitSplash);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };

        splash.start();
    }
}
