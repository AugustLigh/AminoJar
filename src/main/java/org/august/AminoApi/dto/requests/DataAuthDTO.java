package org.august.AminoApi.dto.requests;

public class DataAuthDTO {
    private String email;
    private String secret;
    final private int clientType = 300;

    final private long timestamp = System.currentTimeMillis();

    public DataAuthDTO(String email, String secret) {
        this.email = email;
        this.secret = "0 "+secret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getClientType() {
        return clientType;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
