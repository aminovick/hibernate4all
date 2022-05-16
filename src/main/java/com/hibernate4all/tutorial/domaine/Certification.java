package com.hibernate4all.tutorial.domaine;

/**
 * Created by Lenovo on 26/03/2022.
 */
public enum Certification {

    CERTIFICATION_PUBLIC(1,"public_certification")
    ,INTERDIT_MOIN_12_ANS(2,"interdit moin 12 ans")
    ,INTERDIT_MOIN_13_ANS (3,"interdit moin 13 ans"),
    INTERDIT_MOIN_18_ANS (4,"interdi moin 14 ans");

    private Integer key;
    private String description;

    public Integer getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    Certification(Integer key, String description) {
        this.key = key;
        this.description = description;
    }
}
