package edu.msu.murraniy.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import edu.msu.murraniy.project3.Cloud.Cloud;

public class MainActivity extends AppCompatActivity {

    private String username = "user";
    private String password = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok;
                try {
                    ok = cloud.validateUser(username, password);

                    if(!ok) {
                        /*
                         * If validation fails, display a toast
                         */
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(),
                                        R.string.validation_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{


                    }
                } catch (Exception e) {
                    // Error condition! Something went wrong
                    Log.e("LoginButton", "Something went wrong when validating user", e);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(view.getContext(), R.string.validation_fail, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}