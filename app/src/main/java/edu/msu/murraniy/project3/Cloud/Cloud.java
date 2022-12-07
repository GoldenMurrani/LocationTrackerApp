package edu.msu.murraniy.project3.Cloud;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.msu.murraniy.project3.Cloud.Models.CheckHere;
import edu.msu.murraniy.project3.Cloud.Models.Comment;
import edu.msu.murraniy.project3.Cloud.Models.CommentCatalog;
import edu.msu.murraniy.project3.Cloud.Models.CreateUser;
import edu.msu.murraniy.project3.Cloud.Models.ValidateUser;
import edu.msu.murraniy.project3.GpsActivity;
import edu.msu.murraniy.project3.R;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {



    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~kroskema/cse476/project3/";
    public static final String VALIDATEUSER_PATH = "gps-validateuser.php";
    public static final String CREATEUSER_PATH = "gps-createuser.php";
    public static final String GETLOCATIONS_PATH = "gps-getlocations.php";
    public static final String CHECKHERE_PATH = "gps-checkhere.php";
    public static final String GETCOMMENTS_PATH = "gps-getcomments.php";

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

    public GpsActivity.LocationInfo checkHere(double latitude, double longitude) {

        GpsService service = retrofit.create(GpsService.class);

        try{
            Response<CheckHere> response = service.checkHere(MAGIC, latitude, longitude).execute();

            if(response.isSuccessful()){
                CheckHere result = response.body();

                if (result.getStatus() != null && result.getStatus().equals("yes")) {
                    Log.e("CheckHere", "user checked in at a valid location");
                    return new GpsActivity.LocationInfo(result.getName(), result.getId());
                }
                Log.e("CheckHere", "Failed to validate, message = '" + result.getMessage() + "'");
                return null;

            }
            Log.e("CheckHere", "Failed to create, message = '" + response.code() + "'");
            return null;

        }catch (IOException e){
            Log.e("CheckHere", "Exception occurred while trying to check location!", e);
            return null;
        }catch (RuntimeException e) {	// to catch xml errors to help debug step 6
            Log.e("CheckHere", "Runtime Exception: " + e.getMessage());
            return null;
        }

    }

    /**
     * An adapter so that list boxes can display a list of filenames from
     * the cloud server.
     */
    public static class CommentCatalogAdapter extends BaseAdapter {

        /**
         * The items we display in the list box. Initially this is
         * null until we get items from the server.
         */
        private CommentCatalog catalog = new CommentCatalog("", new ArrayList(), "");

        private int locId = -1;
        public int getLocId() { return locId; }
        public void setLocId(int locId) { this.locId = locId; }

        /**
         * Constructor
         */
        public CommentCatalogAdapter(final View view) {
            // Create a thread to load the catalog
            new Thread(new Runnable() {

                @Override
                public void run() {

                    try{
                        catalog = getCommentCatalog(locId);

                        if (catalog.getStatus().equals("no")) {
                            String msg = "Loading catalog returned status 'no'! Message is = '" + catalog.getMessage() + "'";
                            throw new Exception(msg);
                        }
                        if (catalog.getItems().isEmpty()) {
                            String msg = "No available games found.";
                            throw new Exception(msg);
                        }

                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                // Tell the adapter the data set has been changed
                                notifyDataSetChanged();
                            }

                        });
                    }catch (Exception e){
                        // Error condition! Something went wrong
                        Log.e("CatalogAdapter", "Something went wrong when loading the catalog", e);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                String string;
                                // make sure that there is a message in the catalog
                                // if there isn't use the message from the exception
                                if (catalog.getMessage() == null) {
                                    string = e.getMessage();
                                } else {
                                    string = catalog.getMessage();
                                }
                                Toast.makeText(view.getContext(), string, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }

        public CommentCatalog getCommentCatalog(int locId) throws IOException, RuntimeException {
            GpsService service = retrofit.create(GpsService.class);

            Response<CommentCatalog> response = service.getComments(MAGIC, locId).execute();
            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("getCatalog", "Failed to get catalog, response code is = " + response.code());
                return new CommentCatalog("no", new ArrayList<Comment>(), "Server error " + response.code());
            }
            CommentCatalog catalog = response.body();
            if (catalog.getStatus().equals("no")) {
                String string = "Failed to get catalog, msg is = " + catalog.getMessage();
                Log.e("getCatalog", string);
                return new CommentCatalog("no", new ArrayList<Comment>(), string);
            };
            if (catalog.getItems() == null) {
                catalog.setItems(new ArrayList<Comment>());
            }
            return catalog;
        }

        @Override
        public int getCount() {
            return catalog.getItems().size();
        }

        @Override
        public Object getItem(int position) {
            return catalog.getItems().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentcatalog_item, parent, false);
            }

            TextView usernameTV = (TextView)view.findViewById(R.id.textUsername);
            TextView commentTV = (TextView)view.findViewById(R.id.textComment);

            usernameTV.setText(catalog.getItems().get(position).getUser());
            commentTV.setText(catalog.getItems().get(position).getComment());

            return view;
        }

    }

}
