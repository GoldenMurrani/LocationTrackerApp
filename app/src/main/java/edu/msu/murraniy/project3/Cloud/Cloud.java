package edu.msu.murraniy.project3.Cloud;


import android.util.Log;

import java.io.IOException;

import edu.msu.murraniy.project3.Cloud.Models.CreateUser;
import edu.msu.murraniy.project3.Cloud.Models.ValidateUser;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {



    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~kroskema/cse476/project3/";
    public static final String VALIDATEUSER_PATH = "gps-validateuser.php";
    public static final String CREATEUSER_PATH = "gps-createuser.php";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();


    public boolean validateUser(String username, String password) throws IOException, RuntimeException {

        username = username.trim();
        password = password.trim();

        if(username.length() == 0){
            return false;
        }
        if(password.length() == 0){
            return false;
        }

        GpsService service = retrofit.create(GpsService.class);

        try{
            Response<ValidateUser> response = service.validateUser(username, MAGIC, password).execute();

            if(response.isSuccessful()){
                ValidateUser result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    Log.e("ValidateUser", "ya won: ");
                    return true;
                }
                Log.e("ValidateUser", "Failed to validate, message = '" + result.getMessage() + "'");
                return false;

            }
            Log.e("ValidateUser", "Failed to create, message = '" + response.code() + "'");
            return false;

        }catch (IOException e){
            Log.e("ValidateUser", "Exception occurred while trying to validate user!", e);
            return false;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("ValidateUser", "Runtime Exception: " + e.getMessage());
            return false;
        }


    }

    public boolean createUser(String username, String password) throws IOException, RuntimeException {

        username = username.trim();
        password = password.trim();

        if(username.length() == 0){
            return false;
        }
        if(password.length() == 0){
            return false;
        }

        GpsService service = retrofit.create(GpsService.class);

        try{
            Response<CreateUser> response = service.createUser(username, MAGIC, password).execute();

            if(response.isSuccessful()){
                CreateUser result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    Log.e("CreateUser", "user created ");
                    return true;
                }
                Log.e("CreateUser", "Failed to validate, message = '" + result.getMessage() + "'");
                return false;

            }
            Log.e("CreateUser", "Failed to create, message = '" + response.code() + "'");
            return false;

        }catch (IOException e){
            Log.e("CreateUser", "Exception occurred while trying to validate user!", e);
            return false;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("CreateUser", "Runtime Exception: " + e.getMessage());
            return false;
        }

    }

}
