package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class LeaderboardPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("success")
    public boolean success;

    public boolean isSuccess() {
        return success;
    }

    @SerializedName("access_token")
    public String access_token;


    public LeaderboardPOJO(String access_token) {
        this.access_token = access_token;
    }

    @SerializedName("results")
    @Expose
    private List<LeaderboardPOJO.Results> results = null;

    public List<LeaderboardPOJO.Results> getResults() {
        return results;
    }

    public class Results{

            @SerializedName("_id")
            @Expose
            private String id;

            @SerializedName("identification_number")
            @Expose
            private String identification_number;

            @SerializedName("email")
            @Expose
            private String email;

            @SerializedName("phone_number")
            @Expose
            private String phoneNumber;

            @SerializedName("ttl")
            @Expose
            private String ttl;

            @SerializedName("religion")
            @Expose
            private String religion;

            @SerializedName("degree")
            @Expose
            private String degree;

            @SerializedName("marital_status")
            @Expose
            private String marital_status;

            @SerializedName("job")
            @Expose
            private String job;

            @SerializedName("username")
            @Expose
            private String username;

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("password")
            @Expose
            private String password;

            @SerializedName("address")
            @Expose
            private String address;

            @SerializedName("display_picture")
            @Expose
            private String display_picture;

            @SerializedName("leader_poin")
            @Expose
            private Integer leader_poin;

            public String getUsername() {
                return username;
            }

            public String getDisplay_picture() {
                return display_picture;
            }

            public int getLeader_poin() {
                return leader_poin;
            }

            public String getId() {
                return id;
            }

            public String getIdentification_number() {
                return identification_number;
            }

            public String getEmail() {
                return email;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public String getTtl() {
                return ttl;
            }

            public String getReligion() {
                return religion;
            }

            public String getDegree() {
                return degree;
            }

            public String getMarital_status() {
                return marital_status;
            }

            public String getJob() {
                return job;
            }

            public String getName() {
                return name;
            }

            public String getPassword() {
                return password;
            }

            public String getAddress() {
                return address;
            }
    }

    public String getRc() {
        return rc;
    }

    public String getRm() {
        return rm;
    }
}
