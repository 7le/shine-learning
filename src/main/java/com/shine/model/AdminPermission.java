package com.shine.model;

import javax.persistence.*;

@Table(name = "admin_permission")
public class AdminPermission {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型 0、菜单 1、功能
     */
    private Integer type;

    /**
     * 状态 0、正常 1、禁用
     */
    private Integer state;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 地址
     */
    private String url;

    /**
     * 权限编码
     */
    @Column(name = "permCode")
    private String permcode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取上级ID
     *
     * @return pid - 上级ID
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置上级ID
     *
     * @param pid 上级ID
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取类型 0、菜单 1、功能
     *
     * @return type - 类型 0、菜单 1、功能
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0、菜单 1、功能
     *
     * @param type 类型 0、菜单 1、功能
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态 0、正常 1、禁用
     *
     * @return state - 状态 0、正常 1、禁用
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态 0、正常 1、禁用
     *
     * @param state 状态 0、正常 1、禁用
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取地址
     *
     * @return url - 地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置地址
     *
     * @param url 地址
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取权限编码
     *
     * @return permCode - 权限编码
     */
    public String getPermcode() {
        return permcode;
    }

    /**
     * 设置权限编码
     *
     * @param permcode 权限编码
     */
    public void setPermcode(String permcode) {
        this.permcode = permcode;
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}