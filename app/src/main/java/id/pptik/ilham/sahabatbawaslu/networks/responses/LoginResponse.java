package id.pptik.ilham.sahabatbawaslu.networks.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class LoginResponse {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("user_nmae")
    public String username;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("rm")
    public String rm;

    @SerializedName("rc")
    public String rc;

    public LoginResponse(Boolean success, String user_id, String username, String access_token) {
        this.success = success;
        this.user_id = user_id;
        this.username = username;
        this.access_token = access_token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getUser_id() {
        return user_id;
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
