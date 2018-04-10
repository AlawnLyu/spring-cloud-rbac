package com.produce.common.util.user;

import com.base.entity.Tree;
import com.base.entity.User;
import com.produce.common.util.node.NodeUtil;
import com.produce.sys.service.TreeService;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {

  /**
   * 实现对密码进行加盐值得MD5加密
   *
   * @param password
   * @param salt
   * @return
   */
  public static String encode(String password, String salt) {
    password += "{" + salt + "}";
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      byte[] bytes = md5.digest(password.getBytes());
      return new String(Hex.encode(bytes));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 功能描述：加载菜单节点的数据
   *
   * @return
   */
  public static List<Tree> loadUserTree(TreeService treeService, User user, boolean onlyDisplay) {
    Map<Long, Tree> treeMap = new HashMap<Long, Tree>();
    for (Tree tree : treeService.loadUserTree(user)) {
      if (onlyDisplay && tree.getDisplay() == 1) {
        treeMap.put(tree.getId(), tree);
      } else {
        treeMap.put(tree.getId(), tree);
      }
    }
    List<Tree> treeList = NodeUtil.getChildNodes(new ArrayList<Tree>(treeMap.values()), 0l);
    return treeList;
  }
}
