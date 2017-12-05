package shine.map.local;

import io.vertx.core.shareddata.Shareable;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description: 自定义的bean ——> localMap 需要实现 Shareable
 * @author : 7le
 * @date: 2017/11/22
 */
public class RestAuth implements Shareable {

    private static final Logger LOG = LoggerFactory.getLogger(RestAuth.class);

    private static final long serialVersionUID = 8693766401825472766L;

    private String username;                // api调用方传来的唯一标识
    private String password;                // api调用方的密钥(md5串)
    private String source;                    // api调用方来源

    private List<String> allowIps;            // 允许该调用方访问的ip列表

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getAllowIps() {
        return allowIps;
    }

    public void setAllowIps(List<String> allowIps) {
        this.allowIps = allowIps;
    }

    /**
     * api客户端鉴权
     * 较验串函数 md5(apiUsername + ":" + md5(password) + ":" + (timestamp))
     *
     * @param ip
     * @param checkStr
     * @param timestamp
     * @return
     */
    public boolean checkAuth(String ip, String checkStr, Long timestamp) {
        if (!checkIp(ip)) {
            return false;
        }
        String verifyStr = DigestUtils.md5Hex(this.username + ":" + this.password + ":" + timestamp);
        if (LOG.isDebugEnabled()) {
            LOG.debug("check key: {}, verify key:{}", new Object[]{checkStr, verifyStr});
        }
        if (verifyStr.equals(checkStr)) {
            return true;
        }
        return false;
    }

    /**
     * 验证apiUser的ip
     *
     * @param ip
     * @return
     */
    private boolean checkIp(String ip) {
        if (allowIps == null || allowIps.isEmpty()) {
            LOG.warn("allow ip list is empty!");
            return false;
        }
        String iplist = "";
        for (String configIp : allowIps) {
            int index = configIp.indexOf("*");
            if (index != -1) {
                configIp = configIp.substring(0, index);
            }
            if (ip.equals(configIp) || ip.startsWith(configIp)) {
                return true;
            }
            iplist = iplist + "," + configIp;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("allow ip list: {}, remote ip: {}", new Object[]{iplist, ip});
        }
        return false;
    }
}
