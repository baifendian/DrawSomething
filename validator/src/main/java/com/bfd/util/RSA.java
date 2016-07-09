package com.bfd.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sansec.impl.asn1.ASN1Sequence;
import com.sansec.impl.asn1.pkcs.RSAPrivateKeyStructure;

@Component
public class RSA {
    private static final Logger   logger              = LoggerFactory
                                                              .getLogger(ClientConstants.CLIENT_LOG);
    // 加解密算法
    public static final String    KEY_ALGORITHM       = "RSA";
    /** 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER    = new BouncyCastleProvider();
    // 签名算法 MD5withRSA SHA256withRSA SHA1withRSA NONEwithRSA
    public static final String    SIGNATURE_ALGORITHM = "SHA256withRSA";
    private String                privateKeyStr;
    private String                publicKeyStr;
    private RSAPublicKey          publicKey;
    private RSAPrivateKey         privateKey;
    public RSA                    rsa;

    @PostConstruct
    public void init() {
        try {
            rsa = RSA.readKeyPair("clientConfig/serverPrivateKey.pem",
                    "clientConfig/serverPublicKey.pem");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Description：公钥加密
     * 
     * @param encodeData
     * @return
     * @return String
     * @author 拜力文
     **/
    public String publicKeyEncrypt(String sourceData) {
        Cipher cipher = null;
        try {
            byte[] data = sourceData.getBytes(ClientConstants.DEFAULT_ENCODING);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher = Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
            byte[] output = cipher.doFinal(data);
            return ClientCommon.byte2hex(output);// byte2hex(output);
        } catch (Exception e) {
            logger.error("发生错误", e);
        }
        return null;
    }

    /**
     * Description：私钥解密
     * 
     * @param encodeData 需要解密的数据 hex
     * @return 解密后的数据
     * @return String
     * @author 拜力文
     */
    public String privateKeyDecrypt(String encodeData) {
        Cipher cipher = null;
        try {
            byte[] data = ClientCommon.hexStr2byte(encodeData);
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher = Cipher.getInstance(KEY_ALGORITHM, DEFAULT_PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] output = cipher.doFinal(data);
            return new String(output, ClientConstants.DEFAULT_ENCODING);// byte2hex(output);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发生错误", e);
        }
        return null;
    }

    /**
     * Description：签名验证
     * 
     * @param sourceData
     * @param signData
     * @return
     * @return boolean
     * @author 拜力文：
     **/
    public boolean signVerify(String sourceData, String signData) {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(this.publicKey);
            byte[] srcData = sourceData.getBytes(ClientConstants.DEFAULT_ENCODING);
            signature.update(srcData);
            return signature.verify(ClientCommon.hexStr2byte(signData));
        } catch (Exception e) {
            logger.error("发生错误", e);
            return false;
        }

    }

    /**
     * Description：生成签名
     * 
     * @param data
     * @return
     * @return String
     * @author 拜力文：
     **/
    public String generalSign(byte[] data) {
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(this.privateKey);
            signature.update(data);
            return ClientCommon.byte2hex(signature.sign());
        } catch (Exception e) {
            logger.error("发生错误", e);
            return null;
        }

    }

    /** 
    * Description：生成公私钥对
    * @return
    * @throws Exception
    * @return RSA
    * @author name：拜力文
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     **/
    public static RSA genKeyPair() throws Exception {

        KeyPairGenerator keyPairGen = null;
        keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKey64 = EncryptionUtil.encryptBASE64(privateKey.getEncoded());
        String publicKey64 = EncryptionUtil.encryptBASE64(publicKey.getEncoded());
        RSA rsa = new RSA();
        rsa.setPrivateKey(privateKey);
        rsa.setPublicKey(publicKey);
        rsa.setPrivateKeyStr(privateKey64);
        rsa.setPublicKeyStr(publicKey64);
        return rsa;

    }

    /** 
    * Description：根据公私钥对内容读取公私钥对
    * @param privateKeyStr
    * @param publicKeyStr
    * @return
    * @throws Exception
    * @return RsaVo
    * @author name：拜力文
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     **/
    public static RSA readServerKeyPair(String privateKeyStr, String publicKeyStr) throws Exception {

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //读取私钥
        byte[] privateKeyByte = EncryptionUtil.decryptBASE64(privateKeyStr);

        RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure(
                (ASN1Sequence) ASN1Sequence.fromByteArray(privateKeyByte));
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(),
                asn1PrivKey.getPrivateExponent());
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(priKeySpec);
        //读取公钥
        byte[] publicKeyByte = EncryptionUtil.decryptBASE64(publicKeyStr);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyByte);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        RSA rsaVo = new RSA();
        rsaVo.setPrivateKeyStr(privateKeyStr);
        rsaVo.setPublicKeyStr(publicKeyStr);
        rsaVo.setPrivateKey(privateKey);
        rsaVo.setPublicKey(publicKey);
        return rsaVo;

    }

    /** 
    * Description：根据文件路径读取公私钥对
    * @param serverPrivateKeyPath
    * @param serverPublicKeyPath
    * @return
    * @throws Exception
    * @return RSA
    * @author name：拜力文
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     **/
    public static RSA readKeyPair(String serverPrivateKeyPath, String serverPublicKeyPath)
            throws Exception {
        RSA rsaVo = new RSA();
        StringBuffer sb = null;
        BufferedReader br = null;

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 读取平台私钥
        InputStream inServerPrivateKeyPath = ClientCommon.class.getClassLoader()
                .getResourceAsStream(serverPrivateKeyPath);
        InputStream inServerPublicKeyPath = ClientCommon.class.getClassLoader()
                .getResourceAsStream(serverPublicKeyPath);
        String tempStr = null;
        // 读取私钥
        br = new BufferedReader(new InputStreamReader(inServerPrivateKeyPath,
                ClientConstants.DEFAULT_ENCODING));
        sb = new StringBuffer();
        while ((tempStr = br.readLine()) != null) {
            if (tempStr != null && !tempStr.startsWith("-")) {
                sb.append(tempStr + "\n");
            }
        }
        br.close();
        String priKeyStr = sb.toString();
        byte[] privateKeyByte = EncryptionUtil.decryptBASE64(priKeyStr);

        RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure(
                (ASN1Sequence) ASN1Sequence.fromByteArray(privateKeyByte));
        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(asn1PrivKey.getModulus(),
                asn1PrivKey.getPrivateExponent());

        // PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(privateKeyByte);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(priKeySpec);

        // 读取公钥
        br = new BufferedReader(new InputStreamReader(inServerPublicKeyPath,
                ClientConstants.DEFAULT_ENCODING));
        sb = new StringBuffer();
        while ((tempStr = br.readLine()) != null) {
            if (tempStr != null && !tempStr.startsWith("-")) {
                sb.append(tempStr + "\n");
            }
        }
        br.close();
        String pubKeyStr = sb.toString();
        byte[] publicKeyByte = EncryptionUtil.decryptBASE64(pubKeyStr);
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyByte);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        rsaVo.setPrivateKey(privateKey);
        rsaVo.setPublicKey(publicKey);
        rsaVo.setPrivateKeyStr(priKeyStr);
        rsaVo.setPublicKeyStr(pubKeyStr);
        return rsaVo;

    }

    public String getPrivateKeyStr() {
        return privateKeyStr;
    }

    public void setPrivateKeyStr(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }

    public String getPublicKeyStr() {
        return publicKeyStr;
    }

    public void setPublicKeyStr(String publicKeyStr) {
        this.publicKeyStr = publicKeyStr;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(RSAPublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(RSAPrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public static void main(String[] args) throws Exception {
        /* 使用时先将包路径改好，解决报错
         * 将配置文件放在项目资源目录下，
         * server.publicKeyEncrypt("aaaa") 使用这个方法做加密数据
         * client.generalSign("aaaa".getBytes()) 使用这个方法做签名
         * */
        final RSA server = RSA.readKeyPair("clientConfig/serverPrivateKey.pem",
                "clientConfig/serverPublicKey.pem");
        BigInteger modulus = server.getPublicKey().getModulus();
        System.out.println(modulus);
        System.out.println(ClientCommon.byte2hex(modulus.toByteArray()));
        BigInteger publicExponent = server.getPublicKey().getPublicExponent();
        System.out.println(publicExponent);
        System.out.println(ClientCommon.byte2hex(publicExponent.toByteArray()));
        String privateKeyDecrypt = server
                .privateKeyDecrypt("67953d92437f998abefe1bff3f6be8bc9f716a42e68835449b4bff4b759c554461d944c13e919407a86430a0b9f0ce735d74ad530ad6a943b050ca5190a47a3c04714a026acb63138e5986d182e921e4f1a4e4b0521a7dceebe031313614b5fd4b12730b5b0e651a12d5f6433152ec85d86a7a4aef17962c8d5ab939cab6085f");
        System.out.println(privateKeyDecrypt);
        String publicKeyEncrypt = server.publicKeyEncrypt("admin");
        System.out.println(publicKeyEncrypt);
        String privateKeyDecrypt2 = server.privateKeyDecrypt(publicKeyEncrypt);
        System.out.println(privateKeyDecrypt2);

    }
}
