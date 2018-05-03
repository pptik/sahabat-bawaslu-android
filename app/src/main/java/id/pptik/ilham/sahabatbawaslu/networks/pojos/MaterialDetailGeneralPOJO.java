package id.pptik.ilham.sahabatbawaslu.networks.pojos;

//Digunakan untuk mendapatkan jenis mateir
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 02/03/18.
 */

public class MaterialDetailGeneralPOJO {
    @SerializedName("rc")
    public String rc;

    @SerializedName("rm")
    public String rm;

    @SerializedName("results")
    public Results results;


    @SerializedName("access_token")
    public String access_token;


    public MaterialDetailGeneralPOJO(String access_token) {
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


        public int getType() {
            return type;
        }
    }
}

