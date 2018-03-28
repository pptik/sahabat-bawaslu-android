package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
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
        @SerializedName("dashboard")
        @Expose
        private Dashboard dashboard;

        public class Dashboard {
            @SerializedName("content_id")
            @Expose
            private String id;

            @SerializedName("title")
            @Expose
            private String title;

            @SerializedName("desc")
            @Expose
            private String desc;

            @SerializedName("synopsis")
            @Expose
            private String synopsis;

            @SerializedName("content_code")
            @Expose
            private Integer content_code;

            @SerializedName("activity_code")
            @Expose
            private Integer activityCode;

            @SerializedName("post_by")
            @Expose
            private PostBy postBy;

            @SerializedName("user_detail")
            @Expose
            private UserDetail userDetail;

            @SerializedName("files")
            @Expose
            private List<DashboardPOJO.Results.Dashboard.Files> files = null;

            @SerializedName("activity_text")
            @Expose
            private String activityText;

            @SerializedName("content_text")
            @Expose
            private String contentText;

            @SerializedName("news_type")
            @Expose
            private int newsType;

            @SerializedName("upvote")
            @Expose
            private int upvote;

            @SerializedName("downvote")
            @Expose
            private int downvote;

            @SerializedName("favorite")
            @Expose
            private int favorite;

            @SerializedName("upvoted")
            @Expose
            private boolean upvoted;

            @SerializedName("downvoted")
            @Expose
            private boolean downvoted;

            @SerializedName("favorited")
            @Expose
            private boolean favorited;

            @SerializedName("comment")
            @Expose
            private int comment;

            @SerializedName("created_at_from_now")
            @Expose
            private String createdAt;

            public String getId(){
                return id;
            }
            public String getTitle() {
                return title;
            }

            public String getDesc() {
                return desc;
            }

            public String getSynopsis() {
                return synopsis;
            }

            public Integer getContent_code() {
                return content_code;
            }

            public Integer getActivityCode() {
                return activityCode;
            }

            public String getActivityText() {
                return activityText;
            }

            public String getContentText() {
                return contentText;
            }

            public int getNewsType() {
                return newsType;
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

            //
            public boolean getUpvoted() {
                return upvoted;
            }

            public boolean getDownvoted() {
                return downvoted;
            }

            public boolean getFavorited() {
                return favorited;
            }

            public int getComment() {
                return comment;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public PostBy getPostBy() {
                return postBy;
            }

            public UserDetail getUserDetail() {
                return userDetail;
            }

            public void setPostBy(PostBy postBy) {
                this.postBy = postBy;
            }

            /*@SerializedName("results")
            @Expose
            private List<DashboardPOJO.Results> results = null;

            public List<DashboardPOJO.Results> getResults() {
                return results;
            }*/

            public List<DashboardPOJO.Results.Dashboard.Files> getFiles() {
                return files;
            }

            public class Files {
                @SerializedName("originalname")
                @Expose
                private String originalName;

                @SerializedName("filename")
                @Expose
                private String filename;

                @SerializedName("http_path")
                @Expose
                private String httpPath;

                public String getHttpPath() {
                    return httpPath;
                }

                public String getOriginalName() {
                    return originalName;
                }

                public String getFilename() {
                    return filename;
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

        public Dashboard getDashboard() {
            return dashboard;
        }
    }




}
