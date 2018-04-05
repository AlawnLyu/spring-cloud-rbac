package com.base.entity;

import com.base.common.QueryBase;

public class QueryUserAssociateRole extends QueryBase {

    private int userId;
    private long roleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
