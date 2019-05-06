package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.MachineDao;
import cn.sdjb.dto.MachineJqGridParam;
import cn.sdjb.dto.PersonMachineJqGridParam;
import cn.sdjb.entity.Machine;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService extends AbstractService<Machine> {


    @Autowired
    private MachineDao machineDao;

    @Override
    protected BaseDao<Machine> getDao() {
        return machineDao;
    }

    @Override
    protected List<Machine> selectByJqGridParam(JqGridParam jqGridParam) {
        PersonMachineJqGridParam param = (PersonMachineJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return machineDao.selectBySql("machine",sql.toString());
    }


    public PageInfo<Machine> selectByJqGridParam(MachineJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getName())) {
            sql.append(" and  name like  '%").append(param.getName()).append("%'");
        }
        return new PageInfo<>(machineDao.selectMachineList(sql.toString()));
    }

    public void delete(Integer id){
        Machine machine = new Machine();
        machine.setId(id);
        machineDao.delete(machine);
    }
    

}
