package cn.uploadSys.service.upload;


import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.QczjDao;
import cn.uploadSys.dto.*;
import cn.uploadSys.entity.upload.Qczj;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QczjService extends AbstractService<Qczj> {


    @Autowired
    private QczjDao qczjDao;



    @Override
    protected BaseDao<Qczj> getDao() {
        return qczjDao;
    }

    @Override
    protected List<Qczj> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return qczjDao.selectBySql("qczj",sql.toString());
    }


    public PageInfo<Qczj> selectByJqGridParam(AllJqGridParam param ) throws ParseException {
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
//        sql.append(" ORDER BY week , start_time ASC ");
        return new PageInfo<>(qczjDao.getStudentsList(sql.toString()));
    }


    public List<Qczj> selectByJqGridParamNoPage(AllJqGridParam param ) throws ParseException {
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
        return qczjDao.getStudentsList(sql.toString());
    }

    public PageInfo<Map> selectByJqGridParam(ArrangeClassJqGridParam param){
        PageHelper.startPage(param.getPage(), param.getRows());
        return new PageInfo<>(qczjDao.getStudentsByClassesId(param.getId()));
    }

    public int insert(Qczj qczj){
        return qczjDao.insert(qczj);
    }



    

}
