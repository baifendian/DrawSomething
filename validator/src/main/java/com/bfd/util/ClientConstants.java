package com.bfd.util;

import java.util.ArrayList;

public class ClientConstants {

    public static final String            DEFAULT_ENCODING                      = "UTF-8";
    public static final String            ERROR_PACKAGE                         = "com.cmcc.normandy.client.exception";
    public static final String            WAP_MOBILE_HEADER                     = "x-up-calling-line-id";
    public static final String            CLIENT_NORTH_VERSION_TYPE             = "north";
    public static final String            NORTH_SOURCEID                        = "000001";
    public static final String            SOUTH_SOURCEID                        = "000002";

    public static final ArrayList<String> AndroidAllToken                       = new ArrayList<String>();
    public static final ArrayList<String> IosAllToken                           = new ArrayList<String>();
    public static final ArrayList<String> WPAllToken                            = new ArrayList<String>();
    /** 客户端缓存标识 */
    public static final String            CLIAUTHWAY_CACHEFLAG                  = "client";                            // 客户端缓存标识
    /** 注册用户缓存标识 */
    public static final String            CLIAUTHWAY_RG_CACHEFLAG               = "1";                                 // 注册用户缓存标识
    /** 用户重置密码缓存标识 */
    public static final String            CLIAUTHWAY_RP_CACHEFLAG               = "2";                                 // 用户重置密码缓存标识
    /** 用户登录缓存标识 */
    public static final String            CLIAUTHWAY_DUP_CACHEFLAG              = "3";                                 // 用户登录缓存标识	
    /** Aoi token 缓存标识 */
    public static final String            CLIAUTHWAY_AT_CACHEFLAG               = "4";                                 // 用户登录缓存标识	

    /** 分离流程－验证码校验成功后的凭证标识 */
    public static final String            CLIAUTHWAY_CERT_CACHEFLAG             = "5";

    // 用户登录缓存标识	

    /**
     * 客户端登录共用常量
     */
    // 北方节点历史域名
    public static final String            CLICOMMON_IDMP_DOMAIN                 = "idmp.chinamobile.com";

    // 加密密码域名
    public static final String            CLICOMMON_ENCRYPT_PASSWORD_FLAG       = "fetion.com.cn";
    // public static final String CLICOMMON_ENCRYPT_PASSWORD_FLAG = "fetion";
    // 动态短信过期时间
    public static final int               CLICOMMON_DS_EXPIRE_TIME              = 5;
    // 发送动态短信msgName
    public static final String            CLICOMMON_SEND_CLIENT_DYNAMIC_SMS_REQ = "SendClientDynamicSMSReq";
    // 请求数据库中间件msgName
    public static final String            CLICOMMON_REQDMMSGNAME                = "dmClientReq";
    // 请求加密机msgName
    public static final String            CLICOMMON_REQSECMSGNAME               = "SendAVCalculateReq";
    // AES_KEY
    public static final String            CLICOMMON_AES_KEY                     = "0123456789abcdef";

    // 请求加密机加密密码
    public static final String            SEND_USER_PASSWORD_ENC_REQ            = "SendUserPasswordEncReq";

    // apptype
    public static final String            APP_TYPE_CLIENT                       = "5";
    // 数据短信请求
    public static final String            HS_CLIENT_AUTH_REQUEST                = "HSClientAuthRequest";
    // 认证成功
    public static final String            SERVICE_PASSPORT_SUCCESS_CODE         = "100000";
    // cache--->identi
    public static final int               DMSERVICE_CACHE_IDENTI                = 3;

    public static final String            CLIENT_LOG                            = "validate";
    public static final String            CLIENT_ATHENA_LOG                     = "client_athena_log";

    public static final String            USER_MOBILE_NUMBER                    = "mobile_number";
    // 动态密码
    public static final String            USER_DYNCODE                          = "dyncode";
    /** 同步模块日志*/
    public static final String            NORMANDY_SYNC_BIZ_LOG                 = "idmp-sync-log";
    /** 默认密码  */
    public static String                  SYNC_DEFAULT_PASSWORD                 = "fetiontest3243";

    /**
     * 平台编码sourceid
     */
    public static final String            SOURCEID_NORTH                        = "000001";                             //统一认证平台北方节点
    /**
     * 明文短信标识*/
    public static final byte              CT_COMMON_SMS                         = (byte) 0xFF;                          //明文短信

    /**快捷登录  callback*/
    public static final String            CALLBACKURL                           = "http://127.0.0.1/api/oneKeyResult";

    /** 北方节点用户默认开通(使用了一键登录功能，或者客户端登录功能)。该状态下，应允许用户主动注册*/
    public static final Integer           DEFAULT_OPEN                          = 101;

    public static final int               NORMAL_PHONE_STATUS                   = 0;                                    //正常状态用户

    // 0表示修改密码
    public static final int               CHANGE_PASSWORD_FLAG                  = 0;
    // 1表示重置密码
    public static final int               RESET_PASSWORD_FLAG                   = 1;
}
