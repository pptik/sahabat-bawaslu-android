package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class LoginPOJO {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    @SerializedName("user_nmae")
    public String username;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("rm")
    public String rm;

    @SerializedName("rc")
    public String rc;

    @SerializedName("force")
    public boolean force;

    @SerializedName("app_id")
    public Integer app_id;

    public LoginPOJO(String email, String password, Boolean force, Integer app_id) {
        this.email = email;
        this.password = password;
        this.force = force;
        this.app_id = app_id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRm() {
        return rm;
    }

    public String getRc() {
        return rc;
    }
}
