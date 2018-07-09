package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class UserLogsPOJO {
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

    public UserLogsPOJO withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public UserLogsPOJO withRc(String rc) {
        this.rc = rc;
        return this;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public UserLogsPOJO withRm(String rm) {
        this.rm = rm;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public UserLogsPOJO withResults(List<Result> results) {
        this.results = results;
        return this;
    }
    public class PostBy {

        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("username")
        @Expose
        private String username;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public PostBy withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public PostBy withUsername(String username) {
            this.username = username;
            return this;
        }

    }
    public class Result {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("content_code")
        @Expose
        private Integer contentCode;
        @SerializedName("content_text")
        @Expose
        private String contentText;
        @SerializedName("activity_code")
        @Expose
        private Integer activityCode;
        @SerializedName("activity_text")
        @Expose
        private String activityText;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("content_id")
        @Expose
        private String contentId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Result withId(String id) {
            this.id = id;
            return this;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public Result withPostBy(PostBy postBy) {
            this.postBy = postBy;
            return this;
        }

        public Integer getContentCode() {
            return contentCode;
        }

        public void setContentCode(Integer contentCode) {
            this.contentCode = contentCode;
        }

        public Result withContentCode(Integer contentCode) {
            this.contentCode = contentCode;
            return this;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public Result withContentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        public Integer getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(Integer activityCode) {
            this.activityCode = activityCode;
        }

        public Result withActivityCode(Integer activityCode) {
            this.activityCode = activityCode;
            return this;
        }

        public String getActivityText() {
            return activityText;
        }

        public void setActivityText(String activityText) {
            this.activityText = activityText;
        }

        public Result withActivityText(String activityText) {
            this.activityText = activityText;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Result withTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Result withDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Result withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public Result withContentId(String contentId) {
            this.contentId = contentId;
            return this;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Result withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public void setCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
        }

        public Result withCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
            return this;
        }

    }
}
