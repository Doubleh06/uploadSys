package cn.uploadSys.security;


import cn.uploadSys.dao.RoleDao;
import cn.uploadSys.dao.UserDao;
import cn.uploadSys.entity.Menu;
import cn.uploadSys.entity.Role;
import cn.uploadSys.entity.User;
import cn.uploadSys.entity.security.Constants;
import cn.uploadSys.service.MenuService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * spring security登录时加载用户数据的核心接口
 *
 * @author fonlin
 * @date 2018/04/21
 */
@Service
public class MyUserDetailService implements UserDetailsService {

	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private MenuService menuService;
;

	/**
	 * 登录核心接口
	 * 除了用户名密码外，最重要的是把该用户对应的访问权限加载进来
	 *
	 * @param username 用户名
	 * @return	{@link UserDetail}
	 * @throws UsernameNotFoundException	用户名不存在抛出此异常
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User select = new User();
		select.setUsername(username);
		User user = userDao.selectOne(select);
		if (user == null) {
			throw new UsernameNotFoundException("用户名：" + username + "不存在");
		}
		List<Role> roles = roleDao.selectRoleByUser(user.getId());

		//获取权限
		Collection<GrantedAuthority> grantedAuthorities = obtainGrantedAuthorities(user);
		// 封装成spring security的user
		UserDetail userDetail = new UserDetail(user.getUsername(), user.getPassword(),
				user.getEnable(), true, true, true, grantedAuthorities);
		userDetail.setId(user.getId());
		userDetail.setNickname(user.getNickname());
		userDetail.setRoles(roles);

		return userDetail;
	}


	private Set<GrantedAuthority> obtainGrantedAuthorities (
			User user) {
		List<Menu> menus;
		//如果是超级管理员
		if (Constants.SUPER_ADMIN.equals(user.getUsername())) {
			menus = menuService.selectAll();
		} else {
			menus = menuService.selectAllEnabledByUser(user.getId());
		}
		Set<GrantedAuthority> authSet = new HashSet<>();
		if (!CollectionUtils.isEmpty(menus)) {
			for (Menu menu : menus) {
				authSet.add(new SimpleGrantedAuthority("ROLE_" + menu.getCode()));
			}
		}
		return authSet;
	}



}