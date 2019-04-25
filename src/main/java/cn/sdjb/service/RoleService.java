package cn.sdjb.service;


import cn.sdjb.core.AbstractService;
import cn.sdjb.core.BaseDao;
import cn.sdjb.core.jqGrid.JqGridParam;
import cn.sdjb.dao.MenuDao;
import cn.sdjb.dao.RoleDao;
import cn.sdjb.dao.RoleMenuDao;
import cn.sdjb.dto.RoleJqGridParam;
import cn.sdjb.entity.Role;
import cn.sdjb.entity.RoleMenu;
import cn.sdjb.util.SpringContextUtil;
import cn.sdjb.security.MySecurityMetadataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fonlin
 * @date 2018/4/25
 */
@Service
public class RoleService extends AbstractService<Role> {

    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleMenuDao roleMenuDao;
    @Resource
    private MenuDao menuDao;

    @Override
    protected BaseDao<Role> getDao() {
        return roleDao;
    }

    @Override
    protected List<Role> selectByJqGridParam(JqGridParam param) {
        RoleJqGridParam roleJqGridParam = (RoleJqGridParam) param;
        StringBuilder sql = new StringBuilder();
        sql.append("1=1 ");
        if (StringUtils.isNotEmpty(roleJqGridParam.getName())) {
            sql.append("and name like '%").append(roleJqGridParam.getName()).append("%' ");
        }
        if (StringUtils.isNotEmpty(roleJqGridParam.getRoleKey())) {
            sql.append("and role_key like '%").append(roleJqGridParam.getRoleKey()).append("%' ");
        }
        if (StringUtils.isNotEmpty(roleJqGridParam.getSidx())) {
            sql.append("order by ").append(roleJqGridParam.getSidx()).append(" ").append(roleJqGridParam.getSord()).append("");
        }
        return roleDao.selectBySql("role", sql.toString());
    }

    @Transactional
    public void savePermission(Integer roleId, List<String> codes) {
        //先删除所有的
        Condition condition = new Condition(RoleMenu.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        roleMenuDao.deleteByExample(condition);

        if (!CollectionUtils.isEmpty(codes)) {
            List<Integer> menuIds = menuDao.selectAllIdByCode(codes);
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (Integer menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                roleMenus.add(roleMenu);
            }
            roleMenuDao.insertList(roleMenus);
        }
        SpringContextUtil.getBean(MySecurityMetadataSource.class).refreshResources();
    }

    public List<Role> selectAllByUser(Integer userId) {
        return roleDao.selectAllByUser(userId);
    }

    public void deleteRole(Integer id) {
        Role role = new Role();
        role.setId(id);
        roleDao.delete(role);
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(id);
        roleMenuDao.delete(roleMenu);
    }
}
