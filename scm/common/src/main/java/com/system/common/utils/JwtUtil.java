package com.system.common.utils;

import com.system.common.exception.CustomerAuthenticationException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 *  JWT工具类
 * @author lutong
 * @data 2024-11-29 029 10:27
 */

@Slf4j
public class JwtUtil {

    // 有效期
    public static final long JWT_TTL = 24 * 60 * 60 * 1000L;   // 24小时

    // 设置秘钥明文（盐）
    public static final String JWT_KEY = "JwtKey020713";

    /**
     * 生成令牌
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT
     * @param subject token中存放的数据（JSON）
     * @param ttlMillis token过期时间,默认1天
     * @return builder.compact()
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 创建token
     * @param id id
     * @param subject token中存放的数据（JSON）
     * @param ttlMillis token超时时间
     * @return builder.compact()
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    /**
     * 生成JWT业务逻辑代码
     * @param subject token中存放的数据（JSON）
     * @param ttlMillis token超时时间
     * @param uuid uuid
     * @return Jwts.builder()
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JwtUtil.JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("")       // 签发者
                .setIssuedAt(now)    // 签发时间
                .signWith(signatureAlgorithm, secretKey)    // 使用HS256加密算法签名，秘钥
                .setExpiration(expDate);
    }

    /**
     *  生成加密后的秘钥
     * @return key
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode((JwtUtil.JWT_KEY));
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     *  解析
     * @param jwt jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        if (jwt == null) {
            throw new CustomerAuthenticationException("token为空！");
        }
        SecretKey secretKey = generalKey();
        JwtParser parser = Jwts.parser();
        JwtParser jwtParser = parser.setSigningKey(secretKey);
        Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jwt);
        return claimsJws.getBody();
    }
}
