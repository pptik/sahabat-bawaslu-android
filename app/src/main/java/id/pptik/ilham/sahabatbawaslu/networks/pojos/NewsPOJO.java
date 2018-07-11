package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class NewsPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("access_token")
    public String access_token;

    @SerializedName("content_id")
    public String content_id;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("results")
    @Expose
    public Results results;


    public NewsPOJO(String contentId, String access_token) {
        this.access_token = access_token;
        this.content_id = contentId;
    }

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("content")
        @Expose
        private String content;

        @SerializedName("created_at_from_now")
        @Expose
        private String createdAtFromNow;

        @SerializedName("files")
        @Expose
        private List<File> files = null;

        @SerializedName("post_by")
        @Expose
        private PostBy postBy;

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

        @SerializedName("upvoted")
        @Expose
        private Boolean upvoted;

        @SerializedName("downvoted")
        @Expose
        private Boolean downvoted;

        @SerializedName("favorited")
        @Expose
        private Boolean favorited;

        @SerializedName("comment")
        @Expose
        private int comment;

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

        public List<File> getFiles() {
            return files;
        }

        public void setFiles(List<File> files) {
            this.files = files;
        }

        public String getContent() {
            return content;
        }

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

        public Boolean getUpvoted() {
            return upvoted;
        }

        public Boolean getDownvoted() {
            return downvoted;
        }

        public Boolean getFavorited() {
            return favorited;
        }

        public int getComment() {
            return comment;
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

    public Results getResults() {
        return results;
    }

}
