package com.shine.model;

import javax.persistence.*;

public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;

    private String code;

    @Column(name = "dict_desc")
    private String dictDesc;

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
     * @return category_id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return dict_desc
     */
    public String getDictDesc() {
        return dictDesc;
    }

    /**
     * @param dictDesc
     */
    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }
}