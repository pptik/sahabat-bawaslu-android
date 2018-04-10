package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class CommentsPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("ContentID")
    public String content_id;

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

            public String getUsername() {
                return username;
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
