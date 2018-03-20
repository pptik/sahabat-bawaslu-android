package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import android.support.annotation.ArrayRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 02/03/18.
 */

public class MaterialsListPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("skip")
    public int skip;

    @SerializedName("access_token")
    public String access_token;


    @SerializedName("results")
    @Expose
    private List<Results> results = null;

    public MaterialsListPOJO(int skip, String access_token) {
        this.skip = skip;
        this.access_token = access_token;
    }

    public List<Results> getResults() {
        return results;
    }

    public int getSkip() {
        return skip;
    }

    public String getRm() {
        return rm;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRc() {
        return rc;
    }

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("synopsis")
        @Expose
        private String desc;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        /*
        @SerializedName("files")
        @Expose
        private List<File> files = null;*/
        @SerializedName("upvote")
        @Expose
        private Integer upvote;
        @SerializedName("downvote")
        @Expose
        private Integer downvote;
        @SerializedName("favorite")
        @Expose
        private Integer comment;
        @SerializedName("comment")
        @Expose
        private Integer favorite;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;
        @SerializedName("updated_at")
        @Expose
        private List<Object> updatedAt = null;

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

        /*
        public List<File> getFiles() {
            return files;
        }

        public void setFiles(List<File> files) {
            this.files = files;
        }*/

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

        public Integer getComment() {
            return upvote;
        }

        public Integer getFavorite() {
            return favorite;
        }

        public void setFavorite(Integer favorite) {
            this.favorite = favorite;
        }

        public String getCreatedAt() {
            return createdAt;
        }
        public String getCreatedAtFromNow() {
            return createdAtFromNow;
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

