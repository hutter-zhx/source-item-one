package com.example.source.item.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;


public class DigestUtils {

    /**
     * 功能描述: MD5加密账号密码
     */
    public static String Md5(String userName,String password){
        return new Md5Hash(password, ByteSource.Util.bytes(userName), 2).toHex();

    }
}
