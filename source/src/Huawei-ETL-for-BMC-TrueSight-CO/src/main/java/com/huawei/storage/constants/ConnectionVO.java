package com.huawei.storage.constants;

/**
 * Huawei Technologies  all rights reserved
 */
public class ConnectionVO {
    private String username;
    private String password;
    private String scope = "0";
    private String hostIP;
    private String ipControllerA;
    private String ipControllerB;
    private String controllerASFTPPort;
    private String controllerBSFTPPort;
    private String restPort;
    private String sftpPort;

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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getIpControllerA() {
        return ipControllerA;
    }

    public void setIpControllerA(String ipControllerA) {
        this.ipControllerA = ipControllerA;
    }

    public String getIpControllerB() {
        return ipControllerB;
    }

    public void setIpControllerB(String ipControllerB) {
        this.ipControllerB = ipControllerB;
    }

    public String getRestPort() {
        return restPort;
    }

    public void setRestPort(String restPort) {
        this.restPort = restPort;
    }

    public String getSftpPort() {
        return sftpPort;
    }

    public void setSftpPort(String sftpPort) {
        this.sftpPort = sftpPort;
    }

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public String getControllerASFTPPort() {
        return controllerASFTPPort;
    }

    public void setControllerASFTPPort(String controllerASFTPPort) {
        this.controllerASFTPPort = controllerASFTPPort;
    }

    public String getControllerBSFTPPort() {
        return controllerBSFTPPort;
    }

    public void setControllerBSFTPPort(String controllerBSFTPPort) {
        this.controllerBSFTPPort = controllerBSFTPPort;
    }
}
