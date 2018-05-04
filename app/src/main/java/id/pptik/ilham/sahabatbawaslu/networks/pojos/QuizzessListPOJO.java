package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 04/05/18.
 */

public class QuizzessListPOJO {
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
    private List<Result> results = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("created_at_string")
        @Expose
        private String createdAtString;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;
        @SerializedName("UserScore")
        @Expose
        private Integer userScore;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getCreatedAtString() {
            return createdAtString;
        }

        public void setCreatedAtString(String createdAtString) {
            this.createdAtString = createdAtString;
        }

        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public void setCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
        }

        public Integer getUserScore() {
            return userScore;
        }

        public void setUserScore(Integer userScore) {
            this.userScore = userScore;
        }

    }
}
