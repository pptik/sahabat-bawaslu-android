package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class SignUpPOJO {
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

    @SerializedName("kode_kelas")
    public String kode_kelas;

    @SerializedName("kode_referensi")
    public String kode_referensi;

    @SerializedName("no_handphone")
    public int no_handphone;

    @SerializedName("force")
    public boolean force;

    @SerializedName("app_id")
    public Integer app_id;

    @SerializedName("results")
    @Expose
    public Results results;

    public SignUpPOJO(String field, String password, String username, int no_handphone,
                      String kode_kelas, String kode_referensi, Boolean force, Integer app_id) {
        this.field = field;
        this.username = username;
        this.no_handphone = no_handphone;
        this.kode_kelas = kode_kelas;
        this.kode_referensi = kode_referensi;
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

