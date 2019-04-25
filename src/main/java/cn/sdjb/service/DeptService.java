package cn.sdjb.service;

import cn.sdjb.core.BusinessException;
import cn.sdjb.core.ErrorCode;
import cn.sdjb.core.JSONResult;
import cn.sdjb.core.Result;
import cn.sdjb.util.AjaxUtil;
import cn.sdjb.controller.BaseController;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class DeptService extends BaseController {

    private Environment environment;

    String hrScheme = "";
    String hrHost = "";
    String hrPath = "";
    String imgPath = "";

    @Autowired
    public DeptService(Environment environment) {
        this.environment = environment;
        hrScheme = environment.getProperty("hr.scheme");
        hrHost = environment.getProperty("hr.host");
        hrPath = environment.getProperty("hr.path") ;
        imgPath = environment.getProperty("static.img.path");
    }

    public JSONArray getAddressArray(String address) {
        String result = AjaxUtil.doGet(hrScheme, hrHost, hrPath+address, null);
        JSONObject json = JSONObject.parseObject(result);
        String retMsg = json.getString("msg");
        if (!"Data fetched successfully".equals(retMsg)){
            throw new BusinessException(ErrorCode.HR_INTERFACE_ERROR);
        }
        return json.getJSONArray("data");
    }

    public Result getAddressResult(String address) {
        String result = AjaxUtil.doGet(hrScheme, hrHost, hrPath+address, null);
        JSONObject json = JSONObject.parseObject(result);
        String retMsg = json.getString("msg");
        if (!"Data fetched successfully".equals(retMsg)){
            throw new BusinessException(ErrorCode.HR_INTERFACE_ERROR);
        }
        return new JSONResult(json.getJSONArray("data"));
    }
}
