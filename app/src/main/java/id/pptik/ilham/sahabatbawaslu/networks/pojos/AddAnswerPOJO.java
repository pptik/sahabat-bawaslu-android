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
    @SerializedName("answerDetail")
    @Expose
    private AnswerDetail answerDetail;
    @SerializedName("activitylog")
    @Expose
    private Activitylog activitylog;

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

    public AnswerDetail getAnswerDetail() {
        return answerDetail;
    }

    public void setAnswerDetail(AnswerDetail answerDetail) {
        this.answerDetail = answerDetail;
    }

    public Activitylog getActivitylog() {
        return activitylog;
    }

    public void setActivitylog(Activitylog activitylog) {
        this.activitylog = activitylog;
    }

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
    @SerializedName("_id")
    @Expose
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public class AnswerDetail {

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
        @SerializedName("_id")
        @Expose
        private String id;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class Activitylog {

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
        @SerializedName("_id")
        @Expose
        private String id;

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
        }

        public Integer getContentCode() {
            return contentCode;
        }

        public void setContentCode(Integer contentCode) {
            this.contentCode = contentCode;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public Integer getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(Integer activityCode) {
            this.activityCode = activityCode;
        }

        public String getActivityText() {
            return activityText;
        }

        public void setActivityText(String activityText) {
            this.activityText = activityText;
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

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
