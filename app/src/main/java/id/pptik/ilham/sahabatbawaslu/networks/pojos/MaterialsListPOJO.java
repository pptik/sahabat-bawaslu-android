package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ilham on 02/03/18.
 */

public class MaterialsListPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("skip")
    public int skip;

    @SerializedName("results")
    @Expose
    public Results results;

    public MaterialsListPOJO(int skip) {
        this.skip = skip;
    }

    public Results getResults() {
        return results;
    }

    public String getRm() {
        return rm;
    }

    public String getRc() {
        return rc;
    }

    public class Results{
        @SerializedName("_id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

    }
}

