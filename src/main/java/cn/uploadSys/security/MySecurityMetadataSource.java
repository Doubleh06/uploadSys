package cn.uploadSys.security;

import cn.uploadSys.dao.MenuDao;
import cn.uploadSys.entity.Menu;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;


/**
 * 加载资源与权限的对应关系
 *
 * */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	@Resource
	private MenuDao menuDao;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		if (resourceMap != null) {
			Set<ConfigAttribute> set = new HashSet<>();
			for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
				set.addAll(entry.getValue());
			}
			return set;
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	@PostConstruct
	private void loadResources() {
		if (resourceMap == null) {
			List<Menu> menus = menuDao.selectAll();
			resourceMap = new HashMap<>();
			for (Menu m : menus) {
				//这里做一个资源一个权限
				Collection<ConfigAttribute> configAttributes = new ArrayList<>();
				ConfigAttribute configAttribute = new SecurityConfig("ROLE_" + m.getCode());
				configAttributes.add(configAttribute);
				resourceMap.put(m.getUrl(), configAttributes);
			}
		}
	}

	/**
	 * 根据url获取其需要的权限
	 *
	 * @param object	url
	 * @return	Collection<ConfigAttribute>
	 * @throws IllegalArgumentException
	 *
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if (resourceMap == null) {
			loadResources();
		}
		if (requestUrl.contains("?")) {
			requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
		}
		return resourceMap.get(requestUrl);
	}

	/**
	 * 刷新权限
	 */
	public synchronized void refreshResources() {
		//TODO 线程问题：比如说这里设为null，但是其他线程正在使用resourceMap
		resourceMap = null;
		loadResources();
	}

}