package com.scu.accommodationmanagement.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    // 用于获取明文并对明文加密后返回加密后的密文
    public static String getMD5String(String input) {
        try {
            // 获取MD5算法的实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将输入的字符串转换为字节数组，并计算其MD5哈希
            byte[] messageDigest = md.digest(input.getBytes());

            // 创建一个StringBuilder来保存最终的MD5密文
            StringBuilder hexString = new StringBuilder();

            // 将每个字节转换为16进制的格式，并添加到StringBuilder中
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');  // 确保单字符的16进制数前面加上0
                }
                hexString.append(hex);
            }

            // 返回最终的MD5密文
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
}
