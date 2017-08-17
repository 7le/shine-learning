package com.java.httpClient;

public class ProxyServer {

	private String  ip;			// ip
    private String  ipName;		// 名称
    private int port;			// 端口
    private String  loginName;	// 登录名
	private String passwordMd5;	// 登录密码
	private Long useCount;		// 使用次数
	private Long useCondition;	// 能否使用

	public ProxyServer(){
	}
	
	public ProxyServer(String ip, String ipName, int port, String  loginName, String passwordMd5, Long useCount, Long useCondition){
		this.ip = ip;
		this.ipName = ipName;
		this.port = port;
		this.loginName = loginName;
		this.passwordMd5 = passwordMd5;
		this.useCount = useCount;
		this.useCondition = useCondition;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIpName() {
		return ipName;
	}

	public void setIpName(String ipName) {
		this.ipName = ipName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswordMd5() {
		return passwordMd5;
	}

	public void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
	}

	public Long getUseCount() {
		return useCount;
	}

	public void setUseCount(Long useCount) {
		this.useCount = useCount;
	}

	public Long getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(Long useCondition) {
		this.useCondition = useCondition;
	}

}
