package org.august.AminoApi.dto.response;

import com.google.gson.annotations.SerializedName;

public class AuthDto {
    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private String auid;
    private String secret;
    private String sid;
    private UserProfile userProfile;

    @SerializedName("api:statuscode")
    private int statusCode;

}
