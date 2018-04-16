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
    public int skip;

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

            @SerializedName("username")
            @Expose
            private String username;

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

            public Integer getLeader_poin() {
                return leader_poin;
            }
    }

    public String getRc() {
        return rc;
    }

    public String getRm() {
        return rm;
    }
}
