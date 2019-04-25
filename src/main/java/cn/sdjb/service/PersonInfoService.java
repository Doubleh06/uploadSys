package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.PersonInfoDao;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.dto.PersonInfoJqGridParam;
import cn.sdjb.entity.PersonInfo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoService extends AbstractService<PersonInfo> {


    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    protected BaseDao<PersonInfo> getDao() {
        return personInfoDao;
    }

    @Override
    protected List<PersonInfo> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return personInfoDao.selectBySql("person_info",sql.toString());
    }


    public PageInfo<PersonInfo> selectByJqGridParam(PersonInfoJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getUserId())) {
            sql.append(" and user_id like '%").append(param.getUserId()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and name like '%").append(param.getName()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getEmail())) {
            sql.append(" and email like '%").append(param.getEmail()).append("%'");
        }
        return new PageInfo<>(personInfoDao.selectAccidentTypeList(sql.toString()));
    }

    public void delete(Integer id){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(id);
        personInfoDao.delete(personInfo);
    }
}
