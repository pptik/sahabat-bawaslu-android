package id.pptik.ilham.sahabatbawaslu.networks;

import java.util.List;

import id.pptik.ilham.sahabatbawaslu.networks.pojos.LoginPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.ProvincesPOJO;
import id.pptik.ilham.sahabatbawaslu.networks.pojos.SignUpPOJO;
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
    Call<LoginPOJO> userLogin(@Field("field") String field, @Field("force") Boolean force,
                               @Field("password") String password, @Field("app_id") Integer app_id);

    /*@Headers({
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("regions/provinces")
    Call<ProvincesPOJO> provincesList();*/

    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "current_device: test1",
            "current_number: test1",
            "carrier: test1"
    })
    @FormUrlEncoded
    @POST("users/signup")
    Call<SignUpPOJO> userSignUp(@Field("username") String field, @Field("no_handphone") int no_handphone,
                               @Field("email") String email, @Field("password") String password,
                                @Field("kode_kelas") String kode_kelas,
                                @Field("kode_referensi") String kode_referensi,
                                @Field("status_referensi") Boolean status_refernsi,
                                @Field("force") Boolean force,
                                @Field("app_id") int app_id);

}
