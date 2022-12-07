package edu.msu.murraniy.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import edu.msu.murraniy.project3.Cloud.Cloud;

public class LoginActivity extends AppCompatActivity {

    private EditText EditTextUser, EditTextPass;
    private CheckBox showPass_check;

    private String username = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditTextUser = findViewById(R.id.editTextUser);
        EditTextPass = findViewById(R.id.editTextPassword);
        showPass_check = findViewById(R.id.showPass);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // Also, dont forget to add overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //   int[] grantResults)
                // to handle the case where the user grants the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        showPass_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    EditTextPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    EditTextPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

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
                    } else {
                        //launch the activity with all the functionality
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, GpsActivity.class);
                                intent.putExtra("userID", username);
                                startActivity(intent);
                            }
                        });
                        //TODO: do we need to keep the login page on the activity stack?
//                        finish();
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