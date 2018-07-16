package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TeamListPOJO {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("rc")
    @Expose
    private String rc;
    @SerializedName("rm")
    @Expose
    private String rm;
    @SerializedName("results")
    @Expose
    private Results results;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public TeamListPOJO withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public TeamListPOJO withRc(String rc) {
        this.rc = rc;
        return this;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public TeamListPOJO withRm(String rm) {
        this.rm = rm;
        return this;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public TeamListPOJO withResults(Results results) {
        this.results = results;
        return this;
    }
    public class Results {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("display_picture")
        @Expose
        private String displayPicture;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("teammates")
        @Expose
        private List<Teammate> teammates = null;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Results withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Results withUsername(String username) {
            this.username = username;
            return this;
        }

        public String getDisplayPicture() {
            return displayPicture;
        }

        public void setDisplayPicture(String displayPicture) {
            this.displayPicture = displayPicture;
        }

        public Results withDisplayPicture(String displayPicture) {
            this.displayPicture = displayPicture;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Results withEmail(String email) {
            this.email = email;
            return this;
        }

        public List<Teammate> getTeammates() {
            return teammates;
        }

        public void setTeammates(List<Teammate> teammates) {
            this.teammates = teammates;
        }

        public Results withTeammates(List<Teammate> teammates) {
            this.teammates = teammates;
            return this;
        }

    }
    public class Teammate {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("display_picture")
        @Expose
        private String displayPicture;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Teammate withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Teammate withUsername(String username) {
            this.username = username;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Teammate withEmail(String email) {
            this.email = email;
            return this;
        }

        public String getDisplayPicture() {
            return displayPicture;
        }

        public void setDisplayPicture(String displayPicture) {
            this.displayPicture = displayPicture;
        }

        public Teammate withDisplayPicture(String displayPicture) {
            this.displayPicture = displayPicture;
            return this;
        }

    }
}
