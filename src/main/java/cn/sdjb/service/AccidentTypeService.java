package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.AccidentTypeDao;
import cn.sdjb.dto.AccidentTypeJqGridParam;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.entity.AccidentType;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccidentTypeService extends AbstractService<AccidentType> {


    @Autowired
    private AccidentTypeDao accidentTypeDao;

    @Override
    protected BaseDao<AccidentType> getDao() {
        return accidentTypeDao;
    }

    @Override
    protected List<AccidentType> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return accidentTypeDao.selectBySql("accident_type",sql.toString());
    }


    public PageInfo<AccidentType> selectByJqGridParam(AccidentTypeJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and name like '%").append(param.getName()).append("%'");
        }
        return new PageInfo<>(accidentTypeDao.selectAccidentTypeList(sql.toString()));
    }

    public void delete(Integer id){
        AccidentType accidentType = new AccidentType();
        accidentType.setId(id);
        accidentTypeDao.delete(accidentType);
    }
}
