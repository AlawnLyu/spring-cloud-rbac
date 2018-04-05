package com.base.entity;

public class UserAssociateRole {

    public UserAssociateRole() {
        super();
    }

    public UserAssociateRole(long userId, long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    private long userId;
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
