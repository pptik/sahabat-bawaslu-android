package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 02/05/18.
 */

public class MaterialDetailVideoPOJO {
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

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public class Results {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("desc")
        @Expose
        private String desc;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("files")
        @Expose
        private List<String> files = null;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("upvote")
        @Expose
        private Integer upvote;
        @SerializedName("downvote")
        @Expose
        private Integer downvote;
        @SerializedName("favorite")
        @Expose
        private Integer favorite;
        @SerializedName("comment")
        @Expose
        private Integer comment;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private List<Object> updatedAt = null;
        @SerializedName("created_at_string")
        @Expose
        private String createdAtString;
        @SerializedName("synopsis")
        @Expose
        private String synopsis;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;

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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public List<String> getFiles() {
            return files;
        }

        public void setFiles(List<String> files) {
            this.files = files;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getUpvote() {
            return upvote;
        }

        public void setUpvote(Integer upvote) {
            this.upvote = upvote;
        }

        public Integer getDownvote() {
            return downvote;
        }

        public void setDownvote(Integer downvote) {
            this.downvote = downvote;
        }

        public Integer getFavorite() {
            return favorite;
        }

        public void setFavorite(Integer favorite) {
            this.favorite = favorite;
        }

        public Integer getComment() {
            return comment;
        }

        public void setComment(Integer comment) {
            this.comment = comment;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public List<Object> getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(List<Object> updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAtString() {
            return createdAtString;
        }

        public void setCreatedAtString(String createdAtString) {
            this.createdAtString = createdAtString;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public void setCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

        }
    }

}
