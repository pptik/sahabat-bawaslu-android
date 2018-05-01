package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class CommentsPOJO {

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("ContentID")
    public String content_id;


    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("results")
    @Expose
    private List<CommentsPOJO.Results> results = null;


    public CommentsPOJO(String contentId, String access_token) {
        this.access_token = access_token;
        this.content_id = contentId;
    }

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("comment")
        @Expose
        private String comment;

        @SerializedName("level")
        @Expose
        private int level;

        @SerializedName("post_by")
        @Expose
        private PostBy postBy;

        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;

        @SerializedName("user_detail")
        @Expose
        private UserDetail userDetail;

        @SerializedName("upvote")
        @Expose
        private int upvote;

        @SerializedName("downvote")
        @Expose
        private int downvote;

        @SerializedName("favorite")
        @Expose
        private int favorite;

        @SerializedName("reply")
        @Expose
        private List<Reply> reply = null;

        public class Reply{
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("comment")
            @Expose
            private String comment;
            @SerializedName("comment_id")
            @Expose
            private String commentId;
            @SerializedName("level")
            @Expose
            private Integer level;
            @SerializedName("post_by")
            @Expose
            private PostBy postBy;
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
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private List<Object> updatedAt = null;
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
            @SerializedName("upvoted")
            @Expose
            private Boolean upvoted;
            @SerializedName("downvoted")
            @Expose
            private Boolean downvoted;
            @SerializedName("favorited")
            @Expose
            private Boolean favorited;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public String getCommentId() {
                return commentId;
            }

            public void setCommentId(String commentId) {
                this.commentId = commentId;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public PostBy getPostBy() {
                return postBy;
            }

            public void setPostBy(PostBy postBy) {
                this.postBy = postBy;
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

            public class PostBy_ {

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

        public class UserDetail {
            @SerializedName("display_picture")
            @Expose
            private String displayPicture;

            public String getDisplayPicture() {
                return displayPicture;
            }
        }

        /*public String getContent() {
            return content;
        }*/

        public String getCreatedAtFromNow() {
            return createdAtFromNow;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public UserDetail getUserDetail() {
            return userDetail;
        }

        public int getUpvote() {
            return upvote;
        }

        public int getDownvote() {
            return downvote;
        }

        public int getFavorite() {
            return favorite;
        }

        public String getComment() {
            return comment;
        }

        public String getId() {
            return id;
        }

        public int getLevel() {
            return level;
        }

        public List<Reply> getReply() {
            return reply;
        }

        public void setReply(List<Reply> reply) {
            this.reply = reply;
        }
    }



    public String getRc() {
        return rc;
    }

    public String getRm() {
        return rm;
    }

    public Boolean getSuccess() {
        return success;
    }

    public List<CommentsPOJO.Results> getResults() {
        return results;
    }

}
