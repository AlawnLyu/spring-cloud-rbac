package com.base.entity;

import com.base.common.QueryBase;

public class QueryOrgGroup extends QueryBase {

    private long existingNum;
    private String groupCode;
    private String name;
    private String node;
    private long num;
    private String parentNode;

    public long getExistingNum() {
        return existingNum;
    }

    public void setExistingNum(long existingNum) {
        this.existingNum = existingNum;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getParentNode() {
        return parentNode;
    }

    public void setParentNode(String parentNode) {
        this.parentNode = parentNode;
    }
}
