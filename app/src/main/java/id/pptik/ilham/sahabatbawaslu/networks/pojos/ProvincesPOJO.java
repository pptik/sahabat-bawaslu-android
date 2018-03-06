package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 02/03/18.
 */

public class ProvincesPOJO {
    @SerializedName("success")
    public Boolean success;

    @SerializedName("rm")
    public String rm;

    @SerializedName("rc")
    public String rc;

    @SerializedName("results")
    @Expose
    public List<Results> results;

    public ProvincesPOJO() {

    }

    public List<Results> getResults() {
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
        @SerializedName("_id")
        @Expose
        private String id;

        @SerializedName("province_name")
        @Expose
        private String province_name;

        @SerializedName("province_code")
        @Expose
        private String province_code;

        public String getId() {
            return id;
        }

        public String getProvinceName() {
            return province_name;
        }

        public String getProvinceCode() {
            return province_code;
        }

    }
}

