package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class VotePOJO {
    @SerializedName("ContentID")
    public String contentId;

    @SerializedName("ActivityCode")
    public int activityCode;

    @SerializedName("ContentCode")
    public int contentCode;

    @SerializedName("Title")
    public String title;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("rm")
    public String rm;

    @SerializedName("rc")
    public String rc;

    @SerializedName("results")
    @Expose
    public Results results;

    public VotePOJO(String contentId, int activityCode, int contentCode, String title) {
        this.contentId = contentId;
        this.activityCode = activityCode;
        this.contentCode = contentCode;
        this.title = title;
    }

    public Results getResults() {
        return results;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getRm() {
        return rm;
    }

    public String getRc() {
        return rc;
    }

    public class Results{
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

    }
}

