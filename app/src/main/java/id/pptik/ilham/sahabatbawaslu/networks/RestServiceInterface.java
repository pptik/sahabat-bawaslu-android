package id.pptik.ilham.sahabatbawaslu.networks;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.MaterialsListPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProvincesPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
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
    Call<LoginPOJO> userLogin(@Field("field") String field, @Field("force") Boolean force,
                               @Field("password") String password, @Field("app_id") Integer app_id);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
    })
    @FormUrlEncoded
    @POST("users/simple/signup")
    Call<SignUpPOJO> userSignUp(@Field("Username") String username, @Field("PhoneNumber") String phone_number,
                               @Field("Email") String email, @Field("Password") String password,
                                @Field("ReferenceCode") String reference_code,
                                @Field("Referenced") Boolean referenced,
                                @Field("SignupType") int signup_type);


    @FormUrlEncoded
    @POST("material/list")
    Call<MaterialsListPOJO> materialsList(@Field("Skip") int skip, @Header("access_token") String access_token);

    @FormUrlEncoded
    @POST("dashboard")
    Call<MaterialsListPOJO> dashboard(@Field("Skip") int skip, @Header("access_token") String access_token);

}
