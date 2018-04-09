package com.rbac.rbacshow.sys.mapper;

import com.rbac.rbacshow.sys.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(source = "id", target = "id")
  com.base.entity.User userToBase(User user);

  @Mapping(source = "id", target = "id")
  User baseToUser(com.base.entity.User user);

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}
