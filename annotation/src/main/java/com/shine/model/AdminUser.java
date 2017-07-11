package com.shine.model;

import javax.persistence.*;

@Table(name = "admin_user")
public class AdminUser {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 登录名称
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0、普通用户 1、管理员
     */
    private Integer type;

    /**
     * 0、禁用 1、正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "cr_time")
    private Long crTime;

    /**
     * 最后登录时间
     */
    @Column(name = "last_time")
    private Long lastTime;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取登录名称
     *
     * @return login_name - 登录名称
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置登录名称
     *
     * @param loginName 登录名称
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取0、普通用户 1、管理员
     *
     * @return type - 0、普通用户 1、管理员
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0、普通用户 1、管理员
     *
     * @param type 0、普通用户 1、管理员
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取0、禁用 1、正常
     *
     * @return status - 0、禁用 1、正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0、禁用 1、正常
     *
     * @param status 0、禁用 1、正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return cr_time - 创建时间
     */
    public Long getCrTime() {
        return crTime;
    }

    /**
     * 设置创建时间
     *
     * @param crTime 创建时间
     */
    public void setCrTime(Long crTime) {
        this.crTime = crTime;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_time - 最后登录时间
     */
    public Long getLastTime() {
        return lastTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastTime 最后登录时间
     */
    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }
}