package id.pptik.ilham.sahabatbawaslu.networks;

import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Ilham on 01/03/18.
 */

public interface RestServiceInterface {
    @Headers({
        "Content-Type: application/x-www-form-urlencoded",
        "current_device: test1",
        "current_number: test1",
        "carrier: test1"
    })
    @FormUrlEncoded
    @POST("users/signin")
    //Call<LoginPOJO> userLogin(@Body LoginPOJO login);
    Call<LoginPOJO> userLogin(@Field("field") String field, @Field("force") Boolean force,
                               @Field("password") String password, @Field("app_id") Integer app_id);
}
