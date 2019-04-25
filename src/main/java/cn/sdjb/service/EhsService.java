package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.AccidentLevelDao;
import cn.sdjb.dao.AccidentTypeDao;
import cn.sdjb.dao.ActionDao;
import cn.sdjb.dao.EhsDao;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.entity.AccidentLevel;
import cn.sdjb.entity.AccidentType;
import cn.sdjb.entity.Ehs;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EhsService extends AbstractService<Ehs> {

    @Resource
    private EhsDao ehsDao;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private AccidentLevelDao accidentLevelDao;
    @Autowired
    private ActionDao actionDao;



    @Override
    protected BaseDao<Ehs> getDao() {
        return ehsDao;
    }

    @Override
    protected List<Ehs> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return ehsDao.selectBySql("article",sql.toString());
    }


    public PageInfo<Map> selectByJqGridParam(EhsJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getAccidentMan())){
            sql.append(" and accident_man like '%").append(param.getAccidentMan()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())){
            sql.append(" and address = '").append(param.getAddress()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getDept())){
            sql.append(" and dept like '%").append(param.getDept()).append("%'");
        }
        if(null!=param.getAccidentType()){
            sql.append(" and accident_type = ").append(param.getAccidentType());
        }
        if (StringUtils.isNotEmpty(param.getStartDate())&&StringUtils.isNotEmpty(param.getEndDate())){
            //时间字符串格式造型
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date startDate = sdf.parse(param.getStartDate() + " 00:00:00");
                Date endDate = sdf.parse(param.getEndDate() + " 23:59:59");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sql.append(" and submit_time between '").append(sdf2.format(startDate)).append("' and '").append(sdf2.format(endDate)).append("'");
            }catch (Exception e){
            }
        }

        //获取事故种类信息
        List<AccidentType> accidentTypeList = accidentTypeDao.selectAll();
        List<AccidentLevel> accidentLevelList = accidentLevelDao.selectAll();
        //获取ehs信息
        List<Map> ehsList = ehsDao.selectEhsList(sql.toString());


        //获取用户信息
        for(Map ehs : ehsList){
            for(AccidentType accidentType : accidentTypeList){
                if(Integer.parseInt(ehs.get("accident_type").toString())==accidentType.getId()){
                    ehs.put("accident_type_name",accidentType.getName());
                }
            }
            for(AccidentLevel accidentLevel : accidentLevelList){
                if (null!=ehs.get("accident_level")){
                    if(Integer.parseInt(ehs.get("accident_level").toString())==accidentLevel.getId()){
                        ehs.put("accident_level_name",accidentLevel.getName());
                    }
                }
            }
            ehs.put("total_action",actionDao.getTotalById(Integer.parseInt(ehs.get("id").toString())));
        }
        return new PageInfo<>(ehsList);
    }

    public List<Map> selectListByJqGridParam(EhsJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getAccidentMan())){
            sql.append(" and accident_man like '%").append(param.getAccidentMan()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())){
            sql.append(" and address = '").append(param.getAddress()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getDept())){
            sql.append(" and dept like '%").append(param.getDept()).append("%'");
        }
        if(null!=param.getAccidentType()){
            sql.append(" and accident_type = ").append(param.getAccidentType());
        }
        if (StringUtils.isNotEmpty(param.getStartDate())&&StringUtils.isNotEmpty(param.getEndDate())){
            //时间字符串格式造型
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date startDate = sdf.parse(param.getStartDate() + " 00:00:00");
                Date endDate = sdf.parse(param.getEndDate() + " 23:59:59");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sql.append(" and submit_time between '").append(sdf2.format(startDate)).append("' and '").append(sdf2.format(endDate)).append("'");
            }catch (Exception e){
            }
        }

        //获取事故种类信息
        List<AccidentType> accidentTypeList = accidentTypeDao.selectAll();
        List<AccidentLevel> accidentLevelList = accidentLevelDao.selectAll();
        //获取ehs信息
        List<Map> ehsList = ehsDao.selectEhsList(sql.toString());


        //获取用户信息
        for(Map ehs : ehsList){
            for(AccidentType accidentType : accidentTypeList){
                if(Integer.parseInt(ehs.get("accident_type").toString())==accidentType.getId()){
                    ehs.put("accident_type_name",accidentType.getName());
                }
            }
            for(AccidentLevel accidentLevel : accidentLevelList){
                if (null!=ehs.get("accident_level")){
                    if(Integer.parseInt(ehs.get("accident_level").toString())==accidentLevel.getId()){
                        ehs.put("accident_level_name",accidentLevel.getName());
                    }
                }
            }
            ehs.put("total_action",actionDao.getTotalById(Integer.parseInt(ehs.get("id").toString())));
        }
        return ehsList;
    }

    public List<String> getImgUrl(Integer id){
        String imgUrl = ehsDao.selectImgUrlById(id);
        List<String> imgUrlList = Arrays.asList(imgUrl.split("\\|"));
        return imgUrlList;
    }
}
