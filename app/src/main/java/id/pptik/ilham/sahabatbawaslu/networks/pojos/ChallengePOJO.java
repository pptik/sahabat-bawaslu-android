
package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ChallengePOJO {

    @SerializedName("rc")
    private String mRc;
    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("rm")
    private String mRm;
    @SerializedName("success")
    private Boolean mSuccess;

    public String getRc() {
        return mRc;
    }

    public void setRc(String rc) {
        mRc = rc;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public String getRm() {
        return mRm;
    }

    public void setRm(String rm) {
        mRm = rm;
    }

    public Boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(Boolean success) {
        mSuccess = success;
    }

    public class Result {

        @SerializedName("title")
        private String mTitle;
        @SerializedName("content")
        private String mContent;
        @SerializedName("created_at")
        private String mCreatedAt;
        @SerializedName("expire")
        private String mExpire;
        @SerializedName("poin")
        private Integer mPoin;
        @SerializedName("_id")
        private String m_id;

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getExpire() {
            return mExpire;
        }

        public void setExpire(String expire) {
            mExpire = expire;
        }

        public Integer getPoin() {
            return mPoin;
        }

        public void setPoin(Integer poin) {
            mPoin = poin;
        }

        public String get_id() {
            return m_id;
        }

        public void set_id(String _id) {
            m_id = _id;
        }

        public String getmTitle() { return mTitle; }

        public void setmTitle(String mTitle) { this.mTitle = mTitle; }
    }

}
