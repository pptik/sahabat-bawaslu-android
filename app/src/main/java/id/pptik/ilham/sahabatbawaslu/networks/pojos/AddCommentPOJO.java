package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class AddCommentPOJO {
    @SerializedName("Comment")
    public String comment;

    @SerializedName("success")
    public Boolean success;

    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("Title")
    public String title;

    public AddCommentPOJO(String comment) {
        this.comment = comment;
    }

    public String getRm() {
        return rm;
    }

    public String getRc() {
        return rc;
    }

}

