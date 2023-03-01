package cn.uploadSys.service.upload;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dao.QczjHQDao;
import cn.uploadSys.dto.AllJqGridParam;
import cn.uploadSys.dto.ArrangeClassJqGridParam;
import cn.uploadSys.dto.StudentsJqGridParam;
import cn.uploadSys.entity.upload.QczjHQ;
import cn.uploadSys.util.AjaxUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class QczjHQService extends AbstractService<QczjHQ> {


    @Autowired
    private QczjHQDao qczjHQDao;
    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Object> template;



    @Override
    protected BaseDao<QczjHQ> getDao() {
        return qczjHQDao;
    }

    @Override
    protected List<QczjHQ> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return qczjHQDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<QczjHQ> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  phone like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getAppid())) {
            sql.append(" and uid = '").append(param.getAppid()).append("'");
        }
        sql.append(" ORDER BY create_time  desc ");
        return new PageInfo<>(qczjHQDao.getLeadsList(sql.toString()));
    }


    public List<QczjHQ> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getPhone())) {
            sql.append(" and  phone like  '%").append(param.getPhone()).append("%'");
        }
        if (StringUtils.isNotEmpty(param.getStartDate()) && StringUtils.isNotEmpty(param.getEndDate())) {
            sql.append(" and create_time > '").append(param.getStartDate()).append(" 00:00:00' and create_time < '").append(param.getEndDate()).append(" 23:59:59'");
        }
        if (null != param.getStatus()) {
            sql.append(" and status = ").append(param.getStatus());
        }
        if (StringUtils.isNotEmpty(param.getAppid())) {
            sql.append(" and uid = '").append(param.getAppid()).append("'");
        }
        sql.append(" ORDER BY create_time  desc ");
        return qczjHQDao.getLeadsList(sql.toString());
    }

    public PageInfo<Map> selectByJqGridParam(ArrangeClassJqGridParam param){
        PageHelper.startPage(param.getPage(), param.getRows());
        return new PageInfo<>(qczjHQDao.getStudentsByClassesId(param.getId()));
    }

    public int insert(QczjHQ qczjHQ){
        return qczjHQDao.insert(qczjHQ);
    }


    public void getUnfinishedInstance(){
        List<QczjHQ> qczjs = qczjHQDao.getUnfinishedInstance();
        log.info("查询状态例子总数为:{}",qczjs.size());
        qczjs.forEach(qczj -> {
//            getStatus(qczj.getCclid(),qczj.getUid());
        });
    }

    public void getStatus(String cclid,String appid) {
        //获取token_access
//        String accessToken = getAccessToken();

        String statusHost = env.getProperty("qczj.getStatus.access_host");
        String statusPath = env.getProperty("qczj.getStatus.access_path");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("appid", appid));
        params.add(new BasicNameValuePair("cclid", cclid));
//        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("querykey", env.getProperty("qczj.getStatus.queryKey")));

        String result = AjaxUtil.doGet("https",statusHost,statusPath,params);
        log.info(result.toString()+"-cclid:{}",cclid);
        if (StringUtils.isNotEmpty(result) && result.contains("returncode")) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String returnCode = jsonObject.getString("returncode");
            String status = jsonObject.getString("status");
            if (StringUtils.isNotEmpty(returnCode) && returnCode.equals("0")&&StringUtils.isNotEmpty(status)) {
                qczjHQDao.updateByCclid(Integer.parseInt(status),cclid,null);
            }
            if (StringUtils.isNotEmpty(returnCode) && returnCode.equals("110")) {
                String message = jsonObject.getString("message");
                qczjHQDao.updateByCclid(Integer.parseInt("1"),cclid,message);
            }

        }
    }



    

}
