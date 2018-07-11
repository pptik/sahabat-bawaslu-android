package id.pptik.ilham.sahabatbawaslu.networks.pojos;

/**
 * Created by Ilham on 03/07/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class UploadImagePOJO {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("results")
    @Expose
    private Results results;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public class Results {

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
        @SerializedName("encoding")
        @Expose
        private String encoding;

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

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

    }
}


