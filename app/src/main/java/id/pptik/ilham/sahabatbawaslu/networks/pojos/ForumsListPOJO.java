package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 20/04/18.
 */

public class ForumsListPOJO {
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
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("upvote")
        @Expose
        private Integer upvote;
        @SerializedName("downvote")
        @Expose
        private Integer downvote;
        @SerializedName("favorite")
        @Expose
        private Integer favorite;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private List<UpdatedAt> updatedAt = null;
        @SerializedName("tags")
        @Expose
        private List<String> tags = null;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("created_at_string")
        @Expose
        private String createdAtString;
        @SerializedName("loadmore")
        @Expose
        private Boolean loadmore;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;
        @SerializedName("comment")
        @Expose
        private Integer comment;

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

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public List<UpdatedAt> getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(List<UpdatedAt> updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public void setUserDetail(UserDetail userDetail) {
            this.userDetail = userDetail;
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

        public Boolean getLoadmore() {
            return loadmore;
        }

        public void setLoadmore(Boolean loadmore) {
            this.loadmore = loadmore;
        }

        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public void setCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
        }

        public Integer getComment() {
            return comment;
        }

        public void setComment(Integer comment) {
            this.comment = comment;
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


        public class UpdatedAt {

            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("username")
            @Expose
            private Object username;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public Object getUsername() {
                return username;
            }

            public void setUsername(Object username) {
                this.username = username;
            }

        }

        public class UserDetail {

            @SerializedName("display_picture")
            @Expose
            private String displayPicture;

            public String getDisplayPicture() {
                return displayPicture;
            }

            public void setDisplayPicture(String displayPicture) {
                this.displayPicture = displayPicture;
            }

        }
    }


}
