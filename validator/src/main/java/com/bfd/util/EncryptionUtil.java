package com.bfd.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * <p>文件名称: EncryptionUtil.java</p>
 * 
 * <p>文件功能: 加密工具类,
 *       <ul>
 *          <li>SHA-1</li> 
 *          <li>MD5</li>
 *          <li>Base64 加解密</li>
 *          <li>计算HMAC256</li>
 *          <li>'aes-128-cbc' 算法加密</li>
 *          <li>aes-128-cbc' 解密.data要为16进制字符串 使用KS前，需要先解密</li>
 *          <li>加密用户密码</li>
 *          <li>计算KDF,四个输入参数--用于计算派生密钥</li>
 *          <li>计算KDF,三个输入参数 用于UP和DUP方式中的KS计算 Ks=KDF(H(A1) , ”PW_GBA_Ks”,nonce,cnonce)</li>
 *          <li>计算KDF,两个输入参数 用于WAP方式、HTTP+SMS、SMS+SMS中的KS计算 Ks=KDF(KEK, "WP_GBA_Ks", nonce) Ks=KDF(KEK,“HS_GBA_Ks” , nonce） Ks=KDF(KEK,“SS_GBA_Ks” , nonce）</li>
 *        </ul>
 * </p>
 *
 * <p>编程者: lljqiu</p>
 * 
 * <p>初作时间: 2014年11月19日 下午3:44:05</p>
 * 
 * <p>版本: version 1.0 </p>
 */
public class EncryptionUtil {

    private static final Logger logger         = LoggerFactory
                                                       .getLogger(ClientConstants.CLIENT_LOG);

    private static final char[] hexDigits      = "0123456789ABCDEF".toCharArray();

    // HMAC算法
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    /**
     * Description： SHA-1加密
     * <p>参数为String<p>
     * @param data 待加密数据
     * @return String
     * @author name：lljqiu
     **/
    public static String encryptSHA1(String data) {
        return data == null ? null : hashEncrypt(data, "SHA-1");
    }

    /**
     * Description： md5 加密
     *  <p>参数为String<p>
     * @param data
     * @return String
     * @author name：lljqiu
     *         <p>=
     *         ===========================================
     *         </p>
     *         Modified No： Modified By： Modified Date： Modified Description:
     *         <p>=
     *         ===========================================
     *         </p>
     **/
    public static String encryptMD5(String data) {
        return data == null ? null : hashEncrypt(data, "MD5");
    }

    /**
     * Description： encryptBASE64
     *  base64编码
     * @param data
     * @return String
     * @author name：lljqiu
     **/

    public static String encryptBASE64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * Description： decryptBASE64
     *  base64解码 <p>参数为String<p>
     * @param key
     * @return byte[]
     * @author name：lljqiu
     **/
    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    private static String hashEncrypt(String data, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(data.getBytes(ClientConstants.DEFAULT_ENCODING));
            return bytes2Hex(md.digest());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return algorithm;

    }

    /**
     * Description： byte ==>> String
     * 
     * @param bytes
     * @return String
     * @author name：lljqiu
     **/
    private static String bytes2Hex(byte[] bytes) {
        int len = bytes.length;
        char[] str = new char[len * 2];
        for (int i = 0; i < len; i++) {
            byte b = bytes[i];
            str[i * 2] = hexDigits[b >>> 4 & 0xF];
            str[i * 2 + 1] = hexDigits[b & 0xF];
        }
        return new String(str);
    }

    /**
     * Description：计算HMAC256
     * 
     * @param secret
     * @param data
     * @return
     * @return String
     * @author name：
     */
    public static String countHmacsha256(byte[] secret, String data) {
        String checksum = null;
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            byte[] dataBytes = data.getBytes(ClientConstants.DEFAULT_ENCODING);
            SecretKey secretkey = new SecretKeySpec(secret, HMAC_ALGORITHM);
            mac.init(secretkey);
            byte[] doFinal = mac.doFinal(dataBytes);
            checksum = ClientCommon.byte2hex(doFinal).toLowerCase();
        } catch (Exception e) {
            logger.error("发生错误", e);
        }
        return checksum;
    }

    /**
     * Description：'aes-128-cbc' 算法加密
     * 
     * @param pwd
     * @param data
     * @return
     * @return String
     * @author 拜力文：
     **/
    public static String encryptAes128Cbc(String pwd, byte[] data) {
        KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 需要自己手动设置
            random.setSeed(pwd.getBytes(ClientConstants.DEFAULT_ENCODING));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(data);
            return ClientCommon.byte2hex(result).toLowerCase(); // 加密
        } catch (Exception e) {
            logger.error("发生错误", e);
        }
        return null;

    }

    /**
     * Description：'aes-128-cbc' 解密.data要为16进制字符串 使用KS前，需要先解密
     * 
     * @param pwd
     * @param data
     * @return String
     * @author name：
     */
    public static String decryptAes128Cbc(String pwd, byte[] data) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");// 需要自己手动设置
            random.setSeed(pwd.getBytes(ClientConstants.DEFAULT_ENCODING));
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(data);
            return new String(result, ClientConstants.DEFAULT_ENCODING).toLowerCase();
        } catch (Exception e) {
            logger.error("aes解密失败", e);
        }
        return null;

    }

    /**
     * Description：加密用户密码
     * 
     * @param password
     *            用户传入的明文密码
     * @return
     * @return String 加密后的密码
     * @author 拜力文：
     **/
    public static String encryptPassword(String password) {
        String tmpPwd1 = encryptSHA1(ClientConstants.CLICOMMON_ENCRYPT_PASSWORD_FLAG + ":"
                + password);

        return tmpPwd1;
    }

    /**
     * Description：计算KDF,四个输入参数--用于计算派生密钥
     * 
     * @param ks
     * @param gbame
     *            "gba-me"
     * @param rand
     * @param username
     * @param idmpDomain
     *            "idmp.chinamobile.com"
     * @return byte串的十六进制形式
     * @return String
     * @author 拜力文
     */
    public static String countKs4Param(String ks, String gbame, String rand, String username,
                                       String idmpDomain) {
        try {
            String fc = "01";
            int liLen = 2;// KDF算法中li的长度，固定都是2个字节
            int fcLen = ClientCommon.hexStr2byte(fc).length;
            int gbameLen = gbame.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int nonceLen = rand.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int usernameLen = username.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int idmpDomainLen = idmpDomain.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int totalLen = fcLen + gbameLen + liLen + nonceLen + liLen + usernameLen + liLen
                    + idmpDomainLen + liLen;

            byte[] dest = new byte[totalLen];
            byte[] fcByte = ClientCommon.hexStr2byte(fc);
            byte[] gbameByte = gbame.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] nonceByte = rand.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] usernameByte = username.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] idmpDomainByte = idmpDomain.getBytes(ClientConstants.DEFAULT_ENCODING);

            byte[] p0LenByte = ClientCommon.IntToBytes2(gbameLen);
            byte[] nonceLenByte = ClientCommon.IntToBytes2(nonceLen);
            byte[] cnonceLenByte = ClientCommon.IntToBytes2(usernameLen);
            byte[] idmpDomainLenByte = ClientCommon.IntToBytes2(idmpDomainLen);

            System.arraycopy(fcByte, 0, dest, 0, fcLen);
            System.arraycopy(gbameByte, 0, dest, fcLen, gbameLen);
            System.arraycopy(p0LenByte, 0, dest, fcLen + gbameLen, liLen);
            System.arraycopy(nonceByte, 0, dest, fcLen + gbameLen + liLen, nonceLen);
            System.arraycopy(nonceLenByte, 0, dest, fcLen + gbameLen + liLen + nonceLen, liLen);
            System.arraycopy(usernameByte, 0, dest, fcLen + gbameLen + liLen + nonceLen + liLen,
                    usernameLen);
            System.arraycopy(cnonceLenByte, 0, dest, fcLen + gbameLen + liLen + nonceLen + liLen
                    + usernameLen, liLen);
            System.arraycopy(idmpDomainByte, 0, dest, fcLen + gbameLen + liLen + nonceLen + liLen
                    + usernameLen + liLen, idmpDomainLen);
            System.arraycopy(idmpDomainLenByte, 0, dest, fcLen + gbameLen + liLen + nonceLen
                    + liLen + usernameLen + liLen + idmpDomainLen, liLen);

            String hmac = countHmacsha256(ClientCommon.hexStr2byte(ks), new String(dest,
                    ClientConstants.DEFAULT_ENCODING));
            // SHA256的长度是32字节，64位hex,派生密钥取前16位即可,32位hex
            String psk = hmac.substring(0, 32);

            return psk;
        } catch (UnsupportedEncodingException e) {
            logger.error("发生错误", e);
        }
        return null;
    }

    /**
     * Description：计算KDF,三个输入参数 用于UP和DUP方式中的KS计算 Ks=KDF(H(A1) , ”PW_GBA_Ks”,nonce,cnonce)
     * 
     * @param key
     * @param p0
     *            取值为PW_GBA_Ks
     * @param nonce
     * @param cnonce
     * @return byte串的十六进制形式
     * @return String
     * @author 拜力文
     */
    public static String countKs3Param(String key, String p0, String nonce, String cnonce) {
        try {
            String fc = "01";
            int liLen = 2;// KDF算法中li的长度，固定都是2个字节
            int fcLen = ClientCommon.hexStr2byte(fc).length;
            int p0Len = p0.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int nonceLen = nonce.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int cnonceLen = cnonce.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int totalLen = fcLen + p0Len + liLen + nonceLen + liLen + cnonceLen + liLen;

            byte[] dest = new byte[totalLen];
            byte[] fcByte = ClientCommon.hexStr2byte(fc);
            byte[] p0Byte = p0.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] nonceByte = nonce.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] cnonceByte = cnonce.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] p0LenByte = ClientCommon.IntToBytes2(p0Len);
            byte[] nonceLenByte = ClientCommon.IntToBytes2(nonceLen);
            byte[] cnonceLenByte = ClientCommon.IntToBytes2(cnonceLen);

            System.arraycopy(fcByte, 0, dest, 0, fcLen);
            System.arraycopy(p0Byte, 0, dest, fcLen, p0Len);
            System.arraycopy(p0LenByte, 0, dest, fcLen + p0Len, liLen);
            System.arraycopy(nonceByte, 0, dest, fcLen + p0Len + liLen, nonceLen);
            System.arraycopy(nonceLenByte, 0, dest, fcLen + p0Len + liLen + nonceLen, liLen);
            System.arraycopy(cnonceByte, 0, dest, fcLen + p0Len + liLen + nonceLen + liLen,
                    cnonceLen);
            System.arraycopy(cnonceLenByte, 0, dest, fcLen + p0Len + liLen + nonceLen + liLen
                    + cnonceLen, liLen);

            String hmac = countHmacsha256(key.getBytes(ClientConstants.DEFAULT_ENCODING),
                    new String(dest, ClientConstants.DEFAULT_ENCODING));
            // SHA256的长度是32字节，64位hex,派生密钥取前16位即可,32位hex
            String ks = hmac.substring(0, 32);

            return ks;
        } catch (UnsupportedEncodingException e) {
            logger.error("发生错误", e);
        }
        return null;
    }

    /**
     * Description：计算KDF,两个输入参数 用于WAP方式、HTTP+SMS、SMS+SMS中的KS计算 Ks=KDF(KEK, "WP_GBA_Ks", nonce) Ks=KDF(KEK,“HS_GBA_Ks” , nonce） Ks=KDF(KEK,“SS_GBA_Ks” , nonce）
     * 
     * @param kek
     * @param p0
     * @param nonce
     * @return
     * @return String
     * @author 拜力文
     */
    public static String countKs2Param(String kek, String p0, String nonce) {
        try {
            String fc = "01";
            int liLen = 2;// KDF算法中li的长度，固定都是2个字节
            int fcLen = ClientCommon.hexStr2byte(fc).length;
            int p0Len = p0.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int nonceLen = nonce.getBytes(ClientConstants.DEFAULT_ENCODING).length;
            int totalLen = fcLen + p0Len + liLen + nonceLen + liLen;

            byte[] dest = new byte[totalLen];
            byte[] fcByte = ClientCommon.hexStr2byte(fc);
            byte[] p0Byte = p0.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] nonceByte = nonce.getBytes(ClientConstants.DEFAULT_ENCODING);
            byte[] p0LenByte = ClientCommon.IntToBytes2(p0Len);
            byte[] nonceLenByte = ClientCommon.IntToBytes2(nonceLen);

            System.arraycopy(fcByte, 0, dest, 0, fcLen);
            System.arraycopy(p0Byte, 0, dest, fcLen, p0Len);
            System.arraycopy(p0LenByte, 0, dest, fcLen + p0Len, liLen);
            System.arraycopy(nonceByte, 0, dest, fcLen + p0Len + liLen, nonceLen);
            System.arraycopy(nonceLenByte, 0, dest, fcLen + p0Len + liLen + nonceLen, liLen);

            String hmac = countHmacsha256(kek.getBytes(ClientConstants.DEFAULT_ENCODING),
                    new String(dest, ClientConstants.DEFAULT_ENCODING));
            // SHA256的长度是32字节，64位hex,派生密钥取前16位即可,32位hex
            String ks = hmac.substring(0, 32);

            return ks;
        } catch (UnsupportedEncodingException e) {
            logger.error("发生错误", e);
        }
        return null;
    }

    /** 
    * Description： 计算HMAC256Token
    *
    * @param secret
    * @param data
    * 
    * @return
    		String
    * @author name：lljqiu
     **/
    public static String countHmacForToken(byte[] secret, byte[] data) {
        String checksum = null;
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            byte[] dataBytes = data;
            SecretKey secretkey = new SecretKeySpec(secret, HMAC_ALGORITHM);
            mac.init(secretkey);
            byte[] doFinal = mac.doFinal(dataBytes);
            checksum = ClientCommon.byte2hex(doFinal).toLowerCase();
        } catch (Exception e) {
            logger.error("发生错误", e);
        }

        return checksum;
    }
}
