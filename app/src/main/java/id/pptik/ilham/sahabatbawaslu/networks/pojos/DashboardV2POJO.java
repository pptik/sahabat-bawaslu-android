package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 09/07/18.
 */

public class DashboardV2POJO {
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

        @SerializedName("dashboard")
        @Expose
        private Dashboard dashboard;

        public Dashboard getDashboard() {
            return dashboard;
        }

        public void setDashboard(Dashboard dashboard) {
            this.dashboard = dashboard;
        }

        public class Dashboard {

            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("desc")
            @Expose
            private String desc;
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
            @SerializedName("content_id")
            @Expose
            private String contentId;
            @SerializedName("news_type")
            @Expose
            private Integer newsType;
            @SerializedName("files")
            @Expose
            private List<File> files = null;
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
            @SerializedName("user_detail")
            @Expose
            private UserDetail userDetail;
            @SerializedName("index")
            @Expose
            private Integer index;
            @SerializedName("created_at_string")
            @Expose
            private String createdAtString;
            @SerializedName("synopsis")
            @Expose
            private String synopsis;
            @SerializedName("loadmore")
            @Expose
            private Boolean loadmore;
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

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }

            public Integer getNewsType() {
                return newsType;
            }

            public void setNewsType(Integer newsType) {
                this.newsType = newsType;
            }

            public List<File> getFiles() {
                return files;
            }

            public void setFiles(List<File> files) {
                this.files = files;
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

            public class File {

                @SerializedName("originalname")
                @Expose
                private String originalname;
                @SerializedName("filename")
                @Expose
                private String filename;
                @SerializedName("http_path")
                @Expose
                private String httpPath;
                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("size")
                @Expose
                private Integer size;

                public String getOriginalname() {
                    return originalname;
                }

                public void setOriginalname(String originalname) {
                    this.originalname = originalname;
                }

                public String getFilename() {
                    return filename;
                }

                public void setFilename(String filename) {
                    this.filename = filename;
                }

                public String getHttpPath() {
                    return httpPath;
                }

                public void setHttpPath(String httpPath) {
                    this.httpPath = httpPath;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Integer getSize() {
                    return size;
                }

                public void setSize(Integer size) {
                    this.size = size;
                }

            }
        }
    }

}
