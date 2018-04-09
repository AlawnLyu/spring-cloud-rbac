package com.rbac.rbacshow.sys.dao;

import com.rbac.rbacshow.sys.entity.User;

public interface UserDao {

  User findByLogin(String login);
}
