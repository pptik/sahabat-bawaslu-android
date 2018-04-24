package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 24/04/18.
 */

public class AddAnswerPOJO {
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
        @SerializedName("forum_id")
        @Expose
        private String forumId;
        @SerializedName("post_by")
        @Expose
        private PostBy postBy;
        @SerializedName("answer_content")
        @Expose
        private String answerContent;
        @SerializedName("upvote")
        @Expose
        private Integer upvote;
        @SerializedName("downvote")
        @Expose
        private Integer downvote;
        @SerializedName("favorite")
        @Expose
        private Integer favorite;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("selected")
        @Expose
        private Boolean selected;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private List<Object> updatedAt = null;
        @SerializedName("level")
        @Expose
        private Integer level;
        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("created_at_string")
        @Expose
        private String createdAtString;
        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;
        @SerializedName("synopsis")
        @Expose
        private String synopsis;
        @SerializedName("loadmore")
        @Expose
        private Boolean loadmore;
        @SerializedName("upvoted")
        @Expose
        private Boolean upvoted;
        @SerializedName("downvoted")
        @Expose
        private Boolean downvoted;
        @SerializedName("favorited")
        @Expose
        private Boolean favorited;
        @SerializedName("reply")
        @Expose
        private List<Object> reply = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getForumId() {
            return forumId;
        }

        public void setForumId(String forumId) {
            this.forumId = forumId;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public String getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(String answerContent) {
            this.answerContent = answerContent;
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

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
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

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
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

        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public void setCreatedAtFromNow(String createdAtFromNow) {
            this.createdAtFromNow = createdAtFromNow;
        }

        public String getSynopsis() {
            return synopsis;
        }

        public void setSynopsis(String synopsis) {
            this.synopsis = synopsis;
        }

        public Boolean getLoadmore() {
            return loadmore;
        }

        public void setLoadmore(Boolean loadmore) {
            this.loadmore = loadmore;
        }

        public Boolean getUpvoted() {
            return upvoted;
        }

        public void setUpvoted(Boolean upvoted) {
            this.upvoted = upvoted;
        }

        public Boolean getDownvoted() {
            return downvoted;
        }

        public void setDownvoted(Boolean downvoted) {
            this.downvoted = downvoted;
        }

        public Boolean getFavorited() {
            return favorited;
        }

        public void setFavorited(Boolean favorited) {
            this.favorited = favorited;
        }

        public List<Object> getReply() {
            return reply;
        }

        public void setReply(List<Object> reply) {
            this.reply = reply;
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
