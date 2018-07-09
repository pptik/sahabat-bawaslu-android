package id.pptik.ilham.sahabatbawaslu.networks.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ilham on 16/03/18.
 */

public class ProfilePOJO {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("rc")
    @Expose
    private String rc;
    @SerializedName("rm")
    @Expose
    private String rm;
    @SerializedName("results")
    @Expose
    private Results results;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
    public class Results {
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("identification_number")
        @Expose
        private String identificationNumber;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("ttl")
        @Expose
        private String ttl;
        @SerializedName("religion")
        @Expose
        private String religion;
        @SerializedName("degree")
        @Expose
        private String degree;
        @SerializedName("marital_status")
        @Expose
        private String maritalStatus;
        @SerializedName("job")
        @Expose
        private String job;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("display_picture")
        @Expose
        private String displayPicture;
        @SerializedName("role")
        @Expose
        private Integer role;
        @SerializedName("signup_type")
        @Expose
        private Integer signupType;
        @SerializedName("verified")
        @Expose
        private Boolean verified;
        @SerializedName("fcm_id")
        @Expose
        private String fcmId;
        @SerializedName("poin")
        @Expose
        private Integer poin;
        @SerializedName("following_count")
        @Expose
        private Integer followingCount;
        @SerializedName("follower_count")
        @Expose
        private Integer followerCount;
        @SerializedName("reference_code")
        @Expose
        private String referenceCode;

        @SerializedName("user_reference")
        @Expose
        private List<Object> userReference = null;

        @SerializedName("leader_poin")
        @Expose
        private Integer leaderPoin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentificationNumber() {
            return identificationNumber;
        }

        public void setIdentificationNumber(String identificationNumber) {
            this.identificationNumber = identificationNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getTtl() {
            return ttl;
        }

        public void setTtl(String ttl) {
            this.ttl = ttl;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDisplayPicture() {
            return displayPicture;
        }

        public void setDisplayPicture(String displayPicture) {
            this.displayPicture = displayPicture;
        }

        public Integer getRole() {
            return role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public Integer getSignupType() {
            return signupType;
        }

        public void setSignupType(Integer signupType) {
            this.signupType = signupType;
        }

        public Boolean getVerified() {
            return verified;
        }

        public void setVerified(Boolean verified) {
            this.verified = verified;
        }

        public String getFcmId() {
            return fcmId;
        }

        public void setFcmId(String fcmId) {
            this.fcmId = fcmId;
        }

        public Integer getPoin() {
            return poin;
        }

        public void setPoin(Integer poin) {
            this.poin = poin;
        }

        public Integer getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(Integer followingCount) {
            this.followingCount = followingCount;
        }

        public Integer getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(Integer followerCount) {
            this.followerCount = followerCount;
        }

        public String getReferenceCode() {
            return referenceCode;
        }

        public void setReferenceCode(String referenceCode) {
            this.referenceCode = referenceCode;
        }


        public List<Object> getUserReference() {
            return userReference;
        }

        public void setUserReference(List<Object> userReference) {
            this.userReference = userReference;
        }



        public Integer getLeaderPoin() {
            return leaderPoin;
        }

        public void setLeaderPoin(Integer leaderPoin) {
            this.leaderPoin = leaderPoin;
        }
        public class Location {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("coordinates")
            @Expose
            private List<Integer> coordinates = null;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<Integer> getCoordinates() {
                return coordinates;
            }

            public void setCoordinates(List<Integer> coordinates) {
                this.coordinates = coordinates;
            }

        }

    }
}
