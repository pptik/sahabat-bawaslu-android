package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class LoginPOJO {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("field")
    public String field;

    @SerializedName("password")
    public String password;

    @SerializedName("username")
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

    @SerializedName("results")
    @Expose
    public Results results;

    public LoginPOJO(String field, String password, Boolean force, Integer app_id) {
        this.field = field;
        this.password = password;
        this.force = force;
        this.app_id = app_id;
    }

    public Results getResults() {
        return results;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getField() {
        return field;
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

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("access_token")
        @Expose
        private String access_token;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUsername() {
            return username;
        }

        public String getAccess_token() {
            return access_token;
        }
    }
}

