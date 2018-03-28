package id.pptik.ilham.sahabatbawaslu.view_models;

import android.databinding.BaseObservable;

import id.pptik.ilham.sahabatbawaslu.models.UserModel;

/**
 * Created by Ilham on 28/02/18.
 */

public class NewsViewModel extends BaseObservable {
    private String identificationNumber;
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String email;
    private String villageID;
    private String signupType;
    private String classCode;
    private String referenceCode;

    public NewsViewModel(UserModel userModel) {
        this.identificationNumber = userModel.identificationNumber;
        this.name = userModel.name;
        this.username = userModel.username;
        this.password = userModel.password;
        this.phoneNumber = userModel.phoneNumber;
        this.address = userModel.address;
        this.email = userModel.email;
        this.villageID = userModel.villageID;
        this.signupType = userModel.signupType;
        this.classCode = userModel.classCode;
        this.referenceCode = userModel.referenceCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getSignupType() {
        return signupType;
    }

    public void setSignupType(String signupType) {
        this.signupType = signupType;
    }
}
