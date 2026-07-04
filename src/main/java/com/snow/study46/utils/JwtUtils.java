/*
 * @Author: maxuehao lilyneao@foxmail.com
 * @Date: 2026-07-04 00:29:17
 * @LastEditors: maxuehao lilyneao@foxmail.com
 * @LastEditTime: 2026-07-04 17:51:25
 * @Description: 文件概要说明
 */
package com.snow.study46.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
  // 签名, 一定要作为static静态属性生成, 才能保证不会每个用户登录都生成新签名, 对于每个用户都是固定的
  // 法一: 第三库自动生成(重启服务会重置签名)
  private Key sign = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  // 法二: 最少32位的固定字符串生成, 重启之后不会生成新签名
  // public static String signStr = "Xy9Qw4Rt2Pz7Mn3Bv6Lk8Jc1Fs5Gh0Ae";
  // private static Key sign =
  // Keys.hmacShaKeyFor(signStr.getBytes(StandardCharsets.UTF_8));

  // 生成token
  public String createToken(String id) {
    Map<String, String> payload = new HashMap<>();
    payload.put("id", id);

    // Header默认值{"alg":"HS256","typ":"JWT"}
    String token = Jwts.builder()
        // Jwts.builder().setHeaderParam("alg", "RS256"); // 自定义header
        .setClaims(payload)
        // sub Subject 主题，通常代表令牌所针对的用户或实体
        .setSubject(id) // 唯一标识
        // exp Expiration Time 过期时间，一个时间戳，在此时间之后令牌无效, 这里为24个小时
        .setExpiration(new Date(System.currentTimeMillis() + 24 * 3600 * 100))
        .signWith(sign) // 签名
        .compact(); // 结束, 生成token
    return token;
  }

  // 校验token
  public boolean verifyToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parser().setSigningKey(sign).build().parseClaimsJws(token);
      Claims payload = claims.getBody(); // 负载
      Header head = claims.getHeader(); // 头
      // 是否过期
      boolean isExpiration = payload.getExpiration().before(new Date(System.currentTimeMillis()));
      if (isExpiration)
        return false;
      // payload.get("key")
      // String id = payload.getSubject();
      // Log.info("id: " + id);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
