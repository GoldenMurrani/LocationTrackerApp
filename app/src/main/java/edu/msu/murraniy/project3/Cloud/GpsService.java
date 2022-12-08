package edu.msu.murraniy.project3.Cloud;


import static edu.msu.murraniy.project3.Cloud.Cloud.CHECKHERE_PATH;
import static edu.msu.murraniy.project3.Cloud.Cloud.GETCOMMENTS_PATH;
import static edu.msu.murraniy.project3.Cloud.Cloud.GETLOCATIONS_PATH;
import static edu.msu.murraniy.project3.Cloud.Cloud.VALIDATEUSER_PATH;
import static edu.msu.murraniy.project3.Cloud.Cloud.CREATEUSER_PATH;


import edu.msu.murraniy.project3.Cloud.Models.CheckHere;
import edu.msu.murraniy.project3.Cloud.Models.CommentCatalog;
import edu.msu.murraniy.project3.Cloud.Models.CreateUser;
import edu.msu.murraniy.project3.Cloud.Models.LocationList;
import edu.msu.murraniy.project3.Cloud.Models.ValidateUser;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface GpsService {

    @GET(VALIDATEUSER_PATH)
    Call<ValidateUser> validateUser(
            @Query("user") String userId,
            @Query("magic") String magic,
            @Query("pw") String password
    );

    @GET(CHECKHERE_PATH)
    Call<CheckHere> checkHere(
            @Query("magic") String magic,
            @Query("lat") double lat,
            @Query("lng") double lng
    );

    @GET(GETLOCATIONS_PATH)
    Call<LocationList> getLocations(
            @Query("magic") String magic
    );

    @GET(GETCOMMENTS_PATH)
    Call<CommentCatalog> getComments(
            @Query("magic") String magic,
            @Query("locId") int locId
    );

    @FormUrlEncoded
    @POST(CREATEUSER_PATH)
    Call<CreateUser> createUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );
}
