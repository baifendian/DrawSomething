package com.bfd.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> 文件名称: ClientCommon.java </p>
 * <p> 文件功能: 工具类 </p>
 * <p> 编程者: 拜力文 </p>
 * <p> 初作时间: 2014-6-30 下午3:35:28 </p>
 * <p> 版本: version 1.0 </p>
 * <p> 输入说明: </p>
 * <p> 输出说明: </p>
 * <p> 程序流程: </p>
 */
public class ClientCommon {
    private static final Logger        logger               = LoggerFactory
                                                                    .getLogger(ClientConstants.CLIENT_LOG);
    private static PatternCompiler     compiler             = new Perl5Compiler();

    private static Pattern             emailPattern         = null;
    private static Pattern             msisdnPattern        = null;
    private static Pattern             andidPattern         = null;
    private static Pattern             mobileMsisdnPattern  = null;
    private static Pattern             unicomMsisdnPattern  = null;
    private static Pattern             numberPattern        = null;
    private static Pattern             teleComMsisdnPattern = null;
    private static PatternMatcherInput input                = null;
    private static List<String>        authWays             = new ArrayList<String>();

    static {
        try {
            emailPattern = compiler
                    .compile("^([a-zA-Z0-9\\_\\-\\.])+@([a-zA-Z0-9\\_\\-\\.])+(\\.([a-zA-Z0-9])+)+$");

            msisdnPattern = compiler
                    .compile("^(13[0-9]|14[57]|15[0-9]|16[0-9]|17[06-8]|18[0-9])[0-9]{8}$");
            andidPattern = compiler.compile("^[0-9]{9,15}$");
            // 中国移动134,135，136，137，138，139，147，150，151，152，157，158，159，182，183，184，187，188 1705
            mobileMsisdnPattern = compiler
                    .compile("^1(3[4-9]|47|5[012789]|78|8[23478])[0-9]{8}|1705[0-9]{7}$");
            // 中国联通130、131、132、155、156、185、186、145
            unicomMsisdnPattern = compiler
                    .compile("1(3[012]|45|5[56]|76|8[56])[0-9]{8}|1709[0-9]{7}");
            // 中国电信133、153、180、181、189
            teleComMsisdnPattern = compiler.compile("1(33|53|77|8[019])[0-9]{8}|1700[0-9]{7}");

            numberPattern = compiler.compile("^[0-9]*$");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean isOldBtid(String btid) {
        String domin = getDomainFromBtid(btid);
        if (ClientConstants.CLICOMMON_IDMP_DOMAIN.equals(domin)) {
            return true;
        }
        return false;

    }

    public static String getDomainFromBtid(String btid) {
        return btid.split("@")[1];

    }

    /**
     * Description：isNumber
     * 
     * @param num
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isNumber(String num) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(num);
        if (matcher.matches(input, numberPattern)) {
            return true;
        }
        return false;
    }

    /** 
    * Description：判断是否为ip
    * @param ip
    * @return
    * @return boolean
    * @author name：拜力文
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     **/
    public static boolean isIp(String ip) {
        String[] split = ip.split("\\.");
        if (split.length == 4) {
            return true;
        }
        return false;
    }

    /**
     * Description：isMobieNumber
     * 
     * @param mobileNumber
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isMobieNumber(String mobileNumber) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(mobileNumber);
        if (matcher.matches(input, msisdnPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：手机号格式判断,中国移动手机号
     * 
     * @param msisdn
     * @return
     * @return boolean
     * @author 
     */
    public static boolean isMobileMsiSdn(String msisdn) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(msisdn);
        if (matcher.matches(input, mobileMsisdnPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：判断是否是联通号码
     * 
     * @param msisdn
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isUnicomMsisdn(String msisdn) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(msisdn);
        if (matcher.matches(input, unicomMsisdnPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：判断是否是电信号码
     * 
     * @param msisdn
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isTelecomMsisdn(String msisdn) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(msisdn);
        if (matcher.matches(input, teleComMsisdnPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：isEmail
     * 
     * @param email
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isEmail(String email) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(email);
        if (matcher.matches(input, emailPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：isAndId
     * 
     * @param andId
     * @return
     * @return boolean
     * @author name：
     */
    public static boolean isAndId(String andId) {
        PatternMatcher matcher = new Perl5Matcher();
        input = new PatternMatcherInput(andId);
        if (matcher.matches(input, andidPattern)) {
            return true;
        }
        return false;
    }

    /**
     * Description：得到认证类型
     * 
     * @param authorization
     * @return
     * @return String
     * @author name：
     */
    public static String getAuthType(String authorization) {

        String[] splitAuth = authorization.split(" ");
        return splitAuth[0];
    }

    /**
     * Description：解析請求參數 
     *     解析KS消息，重新分装为Map
     * 
     * @param authorization
     * @return
     * @return HashMap<String,String>
     * @author name：
     */
    public static HashMap<String, String> getParam(String authorization) {
        HashMap<String, String> map = new HashMap<String, String>();
        String[] splitAuth = authorization.split(" ");

        if (splitAuth.length < 2) {
            return map;
        }
        String flag = splitAuth[0].trim();
        String paramSplitFlag = "\"";
        String[] paramsArray = splitAuth[1].split(",");
        for (int i = 0; i < paramsArray.length; i++) {
            String[] temp = paramsArray[i].split(paramSplitFlag);
            String key = temp[0];
            String value = "";
            if (temp.length > 1) {
                value = temp[1];
            }
            map.put(key.substring(0, key.length() - 1), value.trim());
        }
        return map;
    }

    /**
     * Description：将二进制转换成16进制字符串
     * 
     * @param b
     * @return
     * @return String
     * @author name：
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * Description：将十六进制转换成2进制
     * 
     * @param hex
     * @return
     * @return byte[]
     * @author name：
     */
    public static byte[] hex2byte(String hex) {
        byte[] ret = new byte[8];
        byte[] tmp = null;
        try {
            tmp = hex.getBytes(ClientConstants.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("发生错误", e);
        }

        for (int i = 0; i < 8; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    /**
     * Description：将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
     * 
     * @param src0
     * @param src1
     * @return
     * @return byte
     * @author name：
     */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0;
        byte ret = 0;
        try {
            _b0 = Byte.decode(
                    "0x" + new String(new byte[] { src0 }, ClientConstants.DEFAULT_ENCODING))
                    .byteValue();
            _b0 = (byte) (_b0 << 4);
            byte _b1 = Byte.decode(
                    "0x" + new String(new byte[] { src1 }, ClientConstants.DEFAULT_ENCODING))
                    .byteValue();
            ret = (byte) (_b0 ^ _b1);
        } catch (Exception e) {
            logger.error("发生错误", e);
        }

        return ret;
    }

    /**
     * Description：生成随机数
     * 
     * @param len
     * @return
     * @return String
     * @author name：
     */
    public static String genNonce(int len) {
        StringBuffer buf = new StringBuffer();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] charArray = chars.toCharArray();
        for (int i = 0; i < len; i++) {
            double random = Math.random();
            buf.append(charArray[(int) (random * charArray.length)]);
        }
        return buf.toString();
    }

    /**
     * Description：生成不以0开头的随机数
     * 
     * @param len
     * @return
     * @return String
     * @author name：
     */
    public static String genNonceNot0(int len) {
        StringBuffer buf = new StringBuffer();
        String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        double rand = Math.random();
        buf.append(charStr.charAt((int) (rand * charStr.length())));
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        char[] charArray = chars.toCharArray();
        for (int i = 0; i < len - 1; i++) {
            double random = Math.random();
            buf.append(charArray[(int) (random * charArray.length)]);
        }
        return buf.toString();
    }

    /**
     * Description：生成纯数字的随机数
     * 
     * @param len
     * @return
     * @return Integer
     * @author name：
     */
    public static String genNumberRandom(int len) {
        StringBuffer buf = new StringBuffer();
        String chars[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        int first = (int) (Math.ceil((double) Math.random() * 9));
        buf.append(first);
        for (int i = 0; i < len - 1; i++) {
            buf.append(chars[(int) (Math.random() * chars.length)]);
        }
        return buf.toString();
    }

    /**
     * Description：生成sqn
     * 
     * @return
     * @return int
     * @author name：
     */
    public static int genSQN() {
        return Integer.parseInt(genNumberRandom(8));
    }

    /**
     * Description：转化手机号码为11位
     * 
     * @param mobileNumber
     * @return
     * @return String
     * @author name：
     */
    public static String tranMobileNumber(String mobileNumber) {
        if (mobileNumber.length() > 11) {
            int index = mobileNumber.length() - 11;
            mobileNumber = mobileNumber.substring(index, mobileNumber.length());
        }
        return mobileNumber;
    }

    /**
     * Description：从B-TID中得到H(A1) 输入btid : B-TID=base64(MD5(nonce,cnonce))@IDMP域名 输出：rand = base64(MD5(nonce,cnonce))--有修改，直接用base64编码后的
     * 
     * @param btid
     * @return
     * @return String
     * @author name：
     */
    public static String getRandFromBtid(String btid) {
        String tmp = btid.split("@")[0];
        return tmp;
    }

    /**
     * Description：将十六进制的字符串转换成字节数据
     * 
     * @param strhex
     * @return
     * @return byte[]
     * @author name：
     */
    public static byte[] hexStr2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    /**
     * Description：将int转换成字节数组
     * 
     * @param i
     * @return
     * @return byte[]
     * @author 拜力文
     **/
    public static byte[] IntToBytes2(int i) {
        byte abyte0[] = new byte[2];
        abyte0[1] = (byte) (0xff & i);
        abyte0[0] = (byte) ((0xff00 & i) >> 8);

        return abyte0;
    }

    /**
     * Description：将字节数组转为int
     * 
     * @param b
     * @return
     * @return int
     * @author 拜力文
     **/
    public static int bytes2ToInt(byte[] b) {
        return ((b[0] & 0xff) << 8) | (b[1] & 0xff);
    }

    /**
     * 读取流中的数据，
     * @return
     *     String 
     */
    public static String getData(InputStream in) throws IOException {
        StringBuilder dd = new StringBuilder();
        int length = 0;
        byte[] data = new byte[1024];
        while ((length = in.read(data)) != -1) {
            dd.append(new String(data, 0, length, ClientConstants.DEFAULT_ENCODING));
        }
        return dd.toString();
    }

    /**
     * Description：截取指定字节数组
     * 
     * @param src
     *            源字节数组
     * @param begin
     *            起始下标
     * @param end
     *            截止下标
     * @return
     * @return String
     * @author name：
     */
    public static String subBytesToString(byte[] src, int begin, int end) {
        // byte[] bs = new byte[end - begin];
        StringBuffer sb = new StringBuffer();
        for (int i = begin; i < end; i++)
            sb.append(src[i]);
        return sb.toString();
    }

    /**
     * Description：截取指定字节数组
     * 
     * @param src
     *            源字节数组
     * @param begin
     *            起始下标
     * @param end
     *            截止下标
     * @return
     * @return byte[]
     * @author name：
     */
    public static byte[] subBytes(byte[] src, int begin, int end) {
        byte[] bs = new byte[end - begin];
        for (int i = begin; i < end; i++)
            bs[i - begin] = src[i];
        return bs;
    }

    /**
     * Description：生成UUID
     * 
     * @return
     * @return String
     * @author 拜力文：
     **/
    public static String getUUid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();

    }

    /**
     * Description：解析CK:query参数,获取appId
     * 
     * @param authorization
     * @return
     * @return HashMap<String,String>
     * @author name：
     */
    public static String getAppid(String query) {
        String[] splitQuery = query.split(" ");
        String appId = null;
        if (splitQuery.length == 2) {
            String[] paramsArray = splitQuery[1].split(",");
            for (String str : paramsArray) {
                String[] temp = str.split("=");
                if ("appid".equals(temp[0])) {
                    appId = temp[1].substring(1, temp[1].length() - 1);
                }
            }
        }
        return appId;
    }

    /**
    * 
    * Description： 判断 Authorization 中的authWay类型是否有效
    * @param operType
    * @return
    * @return boolean
    * @author name：
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
    *
    */
    public static boolean validateAuthWay(String authWay) {

        return authWays.contains(authWay);
    }

    /**
     * 
    * Description：String转map
    * @param mapString
    * @return
    * @return Map<String,String>
    * @author name：
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     *
     */
    public static Map<String, String> transStringToMap(String mapString) {
        Map<String, String> map = new HashMap<String, String>();
        java.util.StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, ","); entrys.hasMoreTokens(); map
                .put(items.nextToken().trim(), items.hasMoreTokens() ? items.nextToken().trim()
                        : null))
            items = new StringTokenizer(entrys.nextToken(), "=");
        return map;
    }

    /** 
    * Description：读取指定路径log4j.xml
    * @param path
    * @return void
    * @author name：拜力文
    * <p>============================================</p>
    * Modified No： 
    * Modified By： 
    * Modified Date： 
    * Modified Description: 
    * <p>============================================</p>
     **/
    public static void readLog4jXml(String path) {
        if (!path.isEmpty() && !path.startsWith("$") && new File(path).exists()) {
            logger.error("外部配置存在，加载外部log4j.xml配置");
            LogManager.resetConfiguration();
            DOMConfigurator.configure(path);
        }

    }

    public static void main(String[] args) {
        System.out.println(ClientCommon.isIp("fsffsdfsdfs"));
    }

}
