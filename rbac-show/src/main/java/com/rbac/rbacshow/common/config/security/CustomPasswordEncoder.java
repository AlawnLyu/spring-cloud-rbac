package com.rbac.rbacshow.common.config.security;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence charSequence) {
    Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    return encoder.encodePassword(charSequence.toString(), "lyu");
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    Md5PasswordEncoder encoder = new Md5PasswordEncoder();
    return encoder.isPasswordValid(s, charSequence.toString(), "lyu");
  }
}
