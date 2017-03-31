package com.shine.model;

import javax.persistence.*;

@Table(name = "dict_category")
public class DictCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dict_name")
    private String dictName;

    @Column(name = "is_config")
    private Byte isConfig;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return dict_name
     */
    public String getDictName() {
        return dictName;
    }

    /**
     * @param dictName
     */
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    /**
     * @return is_config
     */
    public Byte getIsConfig() {
        return isConfig;
    }

    /**
     * @param isConfig
     */
    public void setIsConfig(Byte isConfig) {
        this.isConfig = isConfig;
    }
}