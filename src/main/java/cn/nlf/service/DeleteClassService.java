package cn.nlf.service;


import cn.nlf.core.AbstractService;
import cn.nlf.core.BaseDao;
import cn.nlf.core.jqGrid.JqGridParam;
import cn.nlf.dao.ClassesDao;
import cn.nlf.dao.StudentsDetailDao;
import cn.nlf.dto.SignUpJqGridParam;
import cn.nlf.dto.StudentsJqGridParam;
import cn.nlf.entity.Classes;
import cn.nlf.entity.StudentsDetail;
import cn.nlf.util.MyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class DeleteClassService extends AbstractService<StudentsDetail> {


    @Autowired
    private StudentsDetailDao studentsDetailDao;
    private BigDecimal totalFee = BigDecimal.ZERO;

    @Override
    protected BaseDao<StudentsDetail> getDao() {
        return studentsDetailDao;
    }

    @Override
    protected List<StudentsDetail> selectByJqGridParam(JqGridParam jqGridParam) {
        StudentsJqGridParam param = (StudentsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return studentsDetailDao.selectBySql("classes",sql.toString());
    }


    public PageInfo<Map> selectByJqGridParam(SignUpJqGridParam param ){
        PageHelper.startPage(param.getPage(), param.getRows());
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 and sd.status = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  sd.student_name like  '%").append(param.getName()).append("%'");
        }
        if (null!=param.getYear() && null!=(param.getMonth())) {
            Integer month = param.getMonth();
            switch (month){
                case 13: sql.append(" and sd.delete_date between '").append(param.getYear()).append("-1-1'").append(" and '").append(param.getYear()).append("-6-30'");break;
                case 14: sql.append(" and sd.delete_date between '").append(param.getYear()).append("-7-1'").append(" and '").append(param.getYear()).append("-12-31'");break;
                case 15: sql.append(" and sd.delete_date between '").append(param.getYear()).append("-1-1'").append(" and '").append(param.getYear()).append("-12-31'");break;
                default: String startDate = param.getYear()+"-"+param.getMonth()+"-1";
                         String endDate = param.getYear()+"-"+param.getMonth()+ "-"+ MyUtil.getStartAndEndDate(param.getYear(),param.getMonth());
                         sql.append(" and sd.delete_date between '").append(startDate).append("' and '").append(endDate).append("'");
                         break;
            }
        }

        sql.append(" order by sd.delete_date desc");


        List<Map> listMap = studentsDetailDao.getDeleteClassList(sql.toString());
        totalFee = BigDecimal.ZERO;
        for(Map map:listMap){
            totalFee = totalFee.add(new BigDecimal(map.get("fee").toString()));
        }

        return new PageInfo<>(listMap);
    }

    public BigDecimal getTotalFee(){
        return totalFee;
    }





    

}
