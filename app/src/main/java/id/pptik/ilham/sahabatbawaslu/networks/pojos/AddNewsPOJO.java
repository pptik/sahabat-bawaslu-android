package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class AddNewsPOJO {
    @SerializedName("Content")
    public String content;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("Title")
    public String title;

    public AddNewsPOJO(String content) {
        this.content = content;
    }

    public String getRm() {
        return rm;
    }

    public String getRc() {
        return rc;
    }

    /*public class Results{
        @SerializedName("content_code")
        @Expose
        private int contentCode;

        @SerializedName("content_text")
        @Expose
        private String contentText;

        @SerializedName("activity_code")
        @Expose
        private int activityCode;

        @SerializedName("activity_text")
        @Expose
        private String activityText;

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("desc")
        @Expose
        private String desc;

        @SerializedName("status")
        @Expose
        private int status;

        @SerializedName("content_id")
        public String contentId;

        @SerializedName("created_at")
        public String createdAt;

        @SerializedName("_id")
        public String id;

        public String getId() {
            return id;
        }

    }*/
}

