package id.pptik.ilham.sahabatbawaslu.networks;

import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ilham on 01/03/18.
 */

public interface RestServiceInterface {
    @POST("users/signin")
    Call<LoginPOJO> createUser(@Body LoginPOJO login);
}
