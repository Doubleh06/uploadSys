package cn.uploadSys.service;

import cn.uploadSys.core.AbstractService;
import cn.uploadSys.core.BaseDao;
import cn.uploadSys.core.jqGrid.JqGridParam;
import cn.uploadSys.dao.RoleDao;
import cn.uploadSys.dao.UserDao;
import cn.uploadSys.dao.UserRoleDao;
import cn.uploadSys.dto.UserDto;
import cn.uploadSys.dto.UserJqGridParam;
import cn.uploadSys.entity.Role;
import cn.uploadSys.entity.User;
import cn.uploadSys.entity.UserRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Service
public class UserService extends AbstractService<User> {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private RoleDao roleDao;


    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @Override
    protected BaseDao<User> getDao() {
        return userDao;
    }

    @Override
    protected List<User> selectByJqGridParam(JqGridParam param) {
        UserJqGridParam userJqGridParam = (UserJqGridParam) param;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(userJqGridParam.getUsername())) {
            sql.append("and username like '%").append(userJqGridParam.getUsername()).append("%' ");
        }
        if (StringUtils.isNotEmpty(userJqGridParam.getNickname())) {
            sql.append("and nickname like '%").append(userJqGridParam.getNickname()).append("%' ");
        }
        if (StringUtils.isNotEmpty(userJqGridParam.getSidx())) {
            sql.append("order by ").append(userJqGridParam.getSidx()).append(" ").append(userJqGridParam.getSord()).append("");
        }
        return userDao.selectBySql("user",sql.toString());
    }

    public PageInfo<Map>  selectUserByPage(JqGridParam param) {
        PageHelper.startPage(param.getPage(), param.getRows());
        List<Map> list = selectUserMapByPage(param);
        return new PageInfo<>(list);
    }

    public List<Map> selectUserMapByPage(JqGridParam param) {
        UserJqGridParam userJqGridParam = (UserJqGridParam) param;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(userJqGridParam.getUsername())) {
            sql.append("and username like '%").append(userJqGridParam.getUsername()).append("%' ");
        }
        if (StringUtils.isNotEmpty(userJqGridParam.getNickname())) {
            sql.append("and nickname like '%").append(userJqGridParam.getNickname()).append("%' ");
        }
        if (StringUtils.isNotEmpty(userJqGridParam.getSidx())) {
            sql.append("order by u.").append(userJqGridParam.getSidx()).append(" ").append(userJqGridParam.getSord()).append("");
        }
        List<Map> list = userDao.selectBySql2(sql.toString());
        return list;
    }

    @Transactional
    public void saveRoles(Integer userId, List<Integer> roleIds) {
        //先删除所有的
        Condition condition = new Condition(UserRole.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("userId", userId);
        userRoleDao.deleteByExample(condition);

        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UserRole> userRoles = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRoles.add(userRole);
            }
            userRoleDao.insertList(userRoles);
        }
    }

    public Role getRoleById(Integer id){
        return roleDao.getRoleById(id);
    }

    public UserDto getJoinUserCompany(Integer id){
       return userDao.getJoinUserCompany(id);

    }


    public void removeSession(String username) {
        for (Object userDetail : sessionRegistry.getAllPrincipals()) {
            String userName = ((org.springframework.security.core.userdetails.User) userDetail).getUsername();
            if (userName.equals(username)) {
                removeSession(userDetail);
            }
        }
    }

    public void removeSession(Object principal) {
        List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
        for (SessionInformation sessionInformation : sessionInformations) {
            sessionInformation.expireNow();
        }
    }

    public User selectByUsername(String username) {
        return userDao.findOneByUsername(username);
    }

    public void deleteUser(Integer id) {
        User user = new User();
        user.setId(id);
        userDao.delete(user);
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        userRoleDao.delete(userRole);
    }
}
