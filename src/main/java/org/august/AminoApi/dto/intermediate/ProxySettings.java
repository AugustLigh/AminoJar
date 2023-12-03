package org.august.AminoApi.dto.intermediate;


public final class ProxySettings {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ProxySettings(String host, int port) {
        this.host = host;
        this.port = port;
        username = null;
        password = null;
    }

    public ProxySettings(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
