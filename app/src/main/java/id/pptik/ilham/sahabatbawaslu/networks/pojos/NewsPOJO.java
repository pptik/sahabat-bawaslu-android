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

        @SerializedName("post_by")
        @Expose
        private PostBy postBy;

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

        public String getContent() {
            return content;
        }

        public PostBy getPostBy() {
            return postBy;
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
}
