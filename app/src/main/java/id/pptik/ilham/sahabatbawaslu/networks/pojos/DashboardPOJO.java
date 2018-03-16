package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class DashboardPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("skip")
    public int skip;

    @SerializedName("access_token")
    public String access_token;


    public DashboardPOJO(int skip, String access_token) {
        this.skip = skip;
        this.access_token = access_token;
    }

    @SerializedName("results")
    @Expose
    private List<DashboardPOJO.Results> results = null;

    public List<DashboardPOJO.Results> getResults() {
        return results;
    }

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("desc")
        @Expose
        private String desc;

        @SerializedName("content_code")
        @Expose
        private Integer content_code;

        @SerializedName("post_by")
        @Expose
        private PostBy postBy;

        @SerializedName("activity_text")
        @Expose
        private String activityText;

        @SerializedName("content_text")
        @Expose
        private String contentText;

        @SerializedName("upvote")
        @Expose
        private int upvote;

        @SerializedName("downvote")
        @Expose
        private int downvote;

        @SerializedName("favorite")
        @Expose
        private int favorite;

        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public Integer getContent_code() {
            return content_code;
        }

        public String getActivityText() {
            return activityText;
        }

        public String getContentText() {
            return contentText;
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

        public String getCreatedAt() {
            return createdAt;
        }

        public PostBy getPostBy() {
            return postBy;
        }

        public void setPostBy(PostBy postBy) {
            this.postBy = postBy;
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
