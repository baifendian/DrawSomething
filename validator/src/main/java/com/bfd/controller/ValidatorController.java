package com.bfd.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baifendian.util.SimpleCrypto;
import com.bfd.util.Base64;
import com.bfd.util.ClientConstants;
import com.bfd.util.HttpUtils;
import com.bfd.util.IdGenerater;
import com.bfd.util.ReadPropertiesUtil;
import com.bfd.vo.Img;

/** 
 * <p>文件名称: ValidatorController.java</p>
 * 
 * <p>文件功能: </p>
 *
 * <p>编程者: 拜力文</p>
 * 
 * <p>初作时间: 2016年6月28日 下午7:42:52</p>
 * 
 * <p>版本: version 1.0 </p>
 *
 * <p>输入说明: </p>
 *
 * <p>输出说明: </p>
 *
 * <p>程序流程: </p>
 * 
 * <p>============================================</p>
 * <p>修改序号:</p>
 * <p>时间:	 </p>
 * <p>修改者:  </p>
 * <p>修改内容:  </p>
 * <p>============================================</p>
 */
@Controller
@RequestMapping(value = "/")
public class ValidatorController {
    private static final Logger  logger = LoggerFactory.getLogger(ClientConstants.CLIENT_LOG);
    private HashMap<String, Img> map    = new HashMap<String, Img>();

    /** 
    * <p>方法名称：login</p>
    * <p>方法描述：首页入口</p>
    *<p> 创建时间：2016年7月9日上午9:54:06</p>
    * <p>@param map
    * <p>@param request
    * <p>@return String</p>
    *  
    * @author 拜力文
     **/
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login(ModelMap map, HttpServletRequest request) {
        return "index";
    }

    /** 
    * <p>方法名称：uploadPhoto</p>
    * <p>方法描述：上传图片</p>
    *<p> 创建时间：2016年7月9日上午9:54:21</p>
    * <p>@param image
    * <p>@param request void</p>
    *  
    * @author 拜力文
     **/
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadPhoto(@RequestParam("image") CommonsMultipartFile image,
                                  HttpServletRequest request) {
        logger.debug("接收到上行上传请求");
        JSONObject ret = new JSONObject();

        if (!image.isEmpty()) {
            Img img = new Img();

            String data = Base64.encodeBase64String(image.getBytes());
            String name = image.getOriginalFilename();
            int index = name.lastIndexOf(".");
            String suffix = name.substring(++index, name.length());
            img.setData(data);
            img.setSuffix(suffix);
            img.setDate(new Date());
            String id = IdGenerater.uuid();
            map.put(id, img);
            ret.put("id", id);
            clearMap();
            logger.debug("已保存到内存中，id为" + id);
        } else {
            ret.put("id", "");
        }
        logger.debug("响应为 " + ret.toJSONString());
        return ret;

    }

    private void clearMap() {
        if (map.size() > 2000) {
            Iterator<String> it = map.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                Img img = map.get(key);
                Date date = img.getDate();
                if (System.currentTimeMillis() - date.getTime() > 5 * 60 * 1000) {
                    it.remove();
                }
            }
        }

    }

    /** 
    * <p>方法名称：validate</p>
    * <p>方法描述：识别图片</p>
    *<p> 创建时间：2016年7月9日上午9:54:38</p>
    * <p>@param type
    * <p>@return JSONObject</p>
    *  
    * @author 拜力文
     **/
    @RequestMapping(value = "/validate")
    @ResponseBody
    public JSONObject validate(String type, String id) {
        logger.debug("接收到上行认证请求");
        JSONObject rets = new JSONObject();
        JSONObject req = rets;
        req.put("s_type", type);
        Img img = map.get(id);
        req.put("image_type", img.getSuffix());
        req.put("data", img.getData());
        getValidate(rets, req);
        logger.debug("响应为" + rets.toJSONString());
        return rets;

    }

    /** 
    * <p>方法名称：androidApi</p>
    * <p>方法描述：提供给安卓的验证接口</p>
    *<p> 创建时间：2016年7月9日上午11:02:05</p>
    * <p>@param data
    * <p>@param suffix
    * <p>@param type
    * <p>@return JSONObject</p>
    *  
    * @author 拜力文
     **/
    @RequestMapping(value = "/androidApi")
    @ResponseBody
    public JSONObject androidApi(String data, String suffix, String type) {
        logger.debug("接收到上行安卓认证请求");
        JSONObject rets = new JSONObject();
        JSONObject req = rets;
        req.put("s_type", type);
        req.put("image_type", suffix);
        req.put("data", data);
        getValidate(rets, req);
        logger.debug("上行安卓认证响应为" + rets.toJSONString());
        return rets;

    }

    public void getValidate(JSONObject rets, JSONObject req) {
        String url = (String) ReadPropertiesUtil.get("config", "url");
        String resStr = null;
        try {
            String reqStr = req.toJSONString();
            reqStr = SimpleCrypto.encrypt(reqStr);
            resStr = HttpUtils.sendHttpPostRequest(url, reqStr);
            resStr = SimpleCrypto.decrypt(resStr);
        } catch (Exception e) {
            e.printStackTrace();
            rets.put("code", "0");
            return;
        }
        JSONObject ret = JSONObject.parseObject(resStr);
        String code = ret.getString("return_code");
        if (code.equals("0")) {
            String ress = ret.getString("result");
            rets.put("result", ress);
            rets.put("code", "1");
        } else {
            rets.put("code", "0");
        }
    }

}
