package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.EmailDao;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.dto.EmailJqGridParam;
import cn.sdjb.entity.Email;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService extends AbstractService<Email> {


    @Autowired
    private EmailDao emailDao;

    @Override
    protected BaseDao<Email> getDao() {
        return emailDao;
    }

    @Override
    protected List<Email> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return emailDao.selectBySql("email",sql.toString());
    }


    public PageInfo<Email> selectByJqGridParam(EmailJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getAuthName())) {
            sql.append(" and  auth_name like '%").append(param.getAuthName()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())) {
            sql.append(" and  address = '").append(param.getAddress()).append("'");
        }
        return new PageInfo<>(emailDao.selectEmailList(sql.toString()));
    }

    public void delete(Integer id){
        Email email = new Email();
        email.setId(id);
        emailDao.delete(email);
    }

    public List<Email> getEmail(String address) {
        StringBuffer sb = new StringBuffer();
        sb.append(" where address = '").append(address).append("'");
        return emailDao.selectEmailList(sb.toString());

    }

    public void changeEmailByAuthName(String address,String authName){
        emailDao.changeAllUsingStatus(address);
        emailDao.changeEmailByAuthName(address,authName);
    }

    public Email getChosenEmailByAddress(String address){
        return emailDao.getChosenEmailByAddress(address);
    }

    public boolean clickSwitch(Integer id,String address,Integer isUsing){
        if (0==isUsing){
            //如果 启用-->关闭
            emailDao.closeInUsing(id);
        }else{
            //如果 关闭-->启动    先查看相同的地址是否有开启的  有报错，没有关闭
            List<Integer> list = emailDao.checkIsUsing(address);
            if(list.contains(0)){
                return false;
            }else {
                emailDao.openInUsing(id);
            }
        }
        return true;
    }
}
