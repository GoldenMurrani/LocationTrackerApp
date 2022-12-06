package edu.msu.murraniy.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.msu.murraniy.project3.Cloud.Cloud;

public class MainActivity extends AppCompatActivity {

    private EditText EditTextUser, EditTextPass;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTextUser = findViewById(R.id.editTextUser);
        EditTextPass = findViewById(R.id.editTextPassword);
    }

    public void onLogin(View view) {
        username = EditTextUser.getText().toString();
        password = EditTextPass.getText().toString();

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
                                        R.string.validation_fail_user_or_pass,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                            //TODO: launch the activity with all the functionality
                            finish();
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

    public void onCreateUser(View view){
        username = EditTextUser.getText().toString();
        password = EditTextPass.getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Cloud cloud = new Cloud();
                final boolean ok;
                try {
                    ok = cloud.createUser(username, password);

                    if(!ok) {
                        /*
                         * If validation fails, display a toast
                         */
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(),
                                        R.string.user_create_fail,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                        //TODO: tell user they were created
                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(view.getContext(),
                                        R.string.user_create_success,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.e("LoginButton", "idk we won");
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