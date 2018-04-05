package com.base.entity;

import com.base.common.QueryBase;

public class QueryRoleAssociateTree extends QueryBase {

    private long roleId;
    private long treeId;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getTreeId() {
        return treeId;
    }

    public void setTreeId(long treeId) {
        this.treeId = treeId;
    }
}
