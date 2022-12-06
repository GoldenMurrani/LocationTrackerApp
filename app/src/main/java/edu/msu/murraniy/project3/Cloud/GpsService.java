package edu.msu.murraniy.project3.Cloud;


import static edu.msu.murraniy.project3.Cloud.Cloud.VALIDATEUSER_PATH;
import static edu.msu.murraniy.project3.Cloud.Cloud.CREATEUSER_PATH;


import edu.msu.murraniy.project3.Cloud.Models.CreateUser;
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

    @FormUrlEncoded
    @POST(CREATEUSER_PATH)
    Call<CreateUser> createUser(
            @Field("user") String userId,
            @Field("magic") String magic,
            @Field("pw") String password
    );
}
