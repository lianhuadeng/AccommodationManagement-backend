package com.scu.accommodationmanagement.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String KEY = "SCU";
    public static String genToken(Map<String, Object> claims){
        return JWT.create()
                .withClaim("claims",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//过期时间
                .sign(Algorithm.HMAC256(KEY));//指定算法，配置密钥
    }

    public static Map<String, Object> parseToken(String token) {
        // 验证并解析令牌
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

}
