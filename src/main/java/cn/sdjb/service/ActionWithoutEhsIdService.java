package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.AccidentTypeDao;
import cn.sdjb.dao.ActionDao;
import cn.sdjb.dao.DeptmentDao;
import cn.sdjb.dto.ActionWithoutEhsIdJqGridParam;
import cn.sdjb.dto.EhsJqGridParam;
import cn.sdjb.entity.Action;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ActionWithoutEhsIdService extends AbstractService<Action> {

    @Resource
    private ActionDao actionDao;
    @Autowired
    private AccidentTypeDao accidentTypeDao;
    @Autowired
    private DeptmentDao deptmentDao;




    @Override
    protected BaseDao<Action> getDao() {
        return actionDao;
    }

    @Override
    protected List<Action> selectByJqGridParam(JqGridParam jqGridParam) {
        EhsJqGridParam param = (EhsJqGridParam) jqGridParam;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(param.getSidx())) {
            sql.append("order by ").append(param.getSidx()).append(" ").append(param.getSord()).append("");
        }
        return actionDao.selectBySql("action",sql.toString());
    }


    public PageInfo<Action> selectByJqGridParam(ActionWithoutEhsIdJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        //关闭时间
        if(StringUtils.isNotEmpty(param.getStatus2())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                sql.append("a1 LEFT JOIN (select id,TIMESTAMPDIFF(SECOND,  close_time  , '").append(simpleDateFormat.format(new Date())+" 00:00:00").append("')as diff  from action) a2 on a1.id = a2.id");
        }
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getResponsibleMan())){
            sql.append(" and responsible_man like '%").append(param.getResponsibleMan()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())){
            sql.append(" and address = '").append(param.getAddress()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDept())){
            sql.append(" and responsible_dept = '").append(param.getResponsibleDept()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDirector())){
            sql.append(" and responsible_director like '%").append(param.getResponsibleDirector()).append("%'");
        }
        //是否关闭
        if(StringUtils.isNotEmpty(param.getStatus1())){
            if ("1".equals(param.getStatus1())){
                sql.append(" and real_close_time is not null");
            }else{
                sql.append(" and real_close_time is null");
            }

        }
        //关闭时间
        if(StringUtils.isNotEmpty(param.getStatus2())){
            if ("1".equals(param.getStatus2())){
                sql.append(" and a2.diff between 0 and 604800");
            }else{
                sql.append(" and a2.diff > 604800");
            }

        }
        //时间段
        if (StringUtils.isNotEmpty(param.getStartDate())&&StringUtils.isNotEmpty(param.getEndDate())){
            //时间字符串格式造型
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date startDate = sdf.parse(param.getStartDate() + " 00:00:00");
                Date endDate = sdf.parse(param.getEndDate() + " 23:59:59");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sql.append(" and close_time between '").append(sdf2.format(startDate)).append("' and '").append(sdf2.format(endDate)).append("'");
            }catch (Exception e){
            }
        }
        //获取ehs信息
        List<Action> actionList = actionDao.selectActionList(sql.toString());
        return new PageInfo<>(actionList);
    }
    public List<Action> selectListByJqGridParam(ActionWithoutEhsIdJqGridParam param ){
        StringBuilder sql = new StringBuilder();
        //关闭时间
        if(StringUtils.isNotEmpty(param.getStatus2())){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sql.append("a1 LEFT JOIN (select id,TIMESTAMPDIFF(SECOND,  close_time  , '").append(simpleDateFormat.format(new Date())+" 00:00:00").append("')as diff  from action) a2 on a1.id = a2.id");
        }
        sql.append(" where 1 = 1 ");
        if(StringUtils.isNotEmpty(param.getResponsibleMan())){
            sql.append(" and responsible_man like '%").append(param.getResponsibleMan()).append("%'");
        }
        if(StringUtils.isNotEmpty(param.getAddress())){
            sql.append(" and address = '").append(param.getAddress()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDept())){
            sql.append(" and responsible_dept = '").append(param.getResponsibleDept()).append("'");
        }
        if(StringUtils.isNotEmpty(param.getResponsibleDirector())){
            sql.append(" and responsible_director like '%").append(param.getResponsibleDirector()).append("%'");
        }
        //是否关闭
        if(StringUtils.isNotEmpty(param.getStatus1())){
            if ("1".equals(param.getStatus1())){
                sql.append(" and real_close_time is not null");
            }else{
                sql.append(" and real_close_time is null");
            }

        }
        //关闭时间
        if(StringUtils.isNotEmpty(param.getStatus2())){
            if ("1".equals(param.getStatus2())){
                sql.append(" and a2.diff between 0 and 604800");
            }else{
                sql.append(" and a2.diff > 604800");
            }

        }
        //时间段
        if (StringUtils.isNotEmpty(param.getStartDate())&&StringUtils.isNotEmpty(param.getEndDate())){
            //时间字符串格式造型
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                Date startDate = sdf.parse(param.getStartDate() + " 00:00:00");
                Date endDate = sdf.parse(param.getEndDate() + " 23:59:59");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sql.append(" and close_time between '").append(sdf2.format(startDate)).append("' and '").append(sdf2.format(endDate)).append("'");
            }catch (Exception e){
            }
        }
        //获取ehs信息
        List<Action> actionList = actionDao.selectActionList(sql.toString());
        return actionList;
    }

    public PageInfo<Map> selectByJqGridParam2(Integer id){
        StringBuilder sql = new StringBuilder();
        sql.append(" where 1 = 1 ");
        sql.append("and id = ").append(id);

        //获取ehs信息
        Action action = actionDao.selectAction(sql.toString());
        List<Map> imgs = new ArrayList<>();
        for (String img : action.getImgUrl().split("\\|")){
            HashMap<String,String> map = new HashMap<>();
            map.put("imgName",img);
            imgs.add(map);
        }
        return new PageInfo<>(imgs);
    }

    public List<String> getImgUrl(Integer id){
        String imgUrl = actionDao.selectImgUrlById(id);
        List<String> imgUrlList = Arrays.asList(imgUrl.split("\\|"));
        return imgUrlList;
    }
}
