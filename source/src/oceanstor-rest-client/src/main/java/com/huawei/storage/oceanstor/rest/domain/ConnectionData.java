package com.huawei.storage.oceanstor.rest.domain;

public class ConnectionData {
	private String username;
	private String password;
	private String hostURL;
	private String scope = "0";
	private String keyStoreFile;
	private String keyStoreFilePass;
	private boolean strictCheckHostName;
	private String certificateFilePath;

	
	
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
	public String getHostURL() {
		return hostURL;
	}
	public void setHostURL(String hostURL) {
		this.hostURL = hostURL;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getKeyStoreFilePass() {
		return keyStoreFilePass;
	}

	public void setKeyStoreFilePass(String keyStoreFilePass) {
		this.keyStoreFilePass = keyStoreFilePass;
	}

	public boolean isStrictCheckHostName() {
		return strictCheckHostName;
	}

	public void setStrictCheckHostName(boolean strictCheckHostName) {
		this.strictCheckHostName = strictCheckHostName;
	}

    public String getCertificateFilePath() {
        return certificateFilePath;
    }

    public void setCertificateFilePath(String certificateFilePath) {
        this.certificateFilePath = certificateFilePath;
    }

    @Override
    public String toString() {
        return "ConnectionData{" +
                "username='" + username + '\'' +
                ", hostURL='" + hostURL + '\'' +
                ", scope='" + scope + '\'' +
                ", keyStoreFile='" + keyStoreFile + '\'' +
                ", keyStoreFilePass='" + keyStoreFilePass + '\'' +
                ", strictCheckHostName=" + strictCheckHostName +
                ", certificateFilePath='" + certificateFilePath + '\'' +
                '}';
    }

    public String getAllToString() {
		return "ConnectionData{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				", hostURL='" + hostURL + '\'' +
				", scope='" + scope + '\'' +
				", keyStoreFile='" + keyStoreFile + '\'' +
				", keyStoreFilePass='" + keyStoreFilePass + '\'' +
				'}';
	}

}
