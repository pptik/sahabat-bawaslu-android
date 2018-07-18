package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 02/03/18.
 */

public class MaterialDetailPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("results")
    public Results results;


    @SerializedName("access_token")
    public String access_token;


    public MaterialDetailPOJO(String access_token) {
        this.access_token = access_token;
    }

    public Results getResults() {
        return results;
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
        @SerializedName("type")
        @Expose
        private int type;

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

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("desc")
        @Expose
        private String desc;

        @SerializedName("post_by")
        @Expose
        private PostBy postBy;

        public class PostBy{
            @SerializedName("username")
            @Expose
            private String username;
        }
        @SerializedName("files")
        @Expose
        private List<Files> files = null;


        public List<Files> getFiles() {
            return files;
        }

        public class Files{
            @SerializedName("http_path")
            @Expose
            private String http_path;

            public String getHttp_path() {
                return http_path;
            }
        }

        public int getType() {
            return type;
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

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }

        public PostBy getPostBy() {
            return postBy;
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
    }
}

