package cn.nlf.dto;

import cn.nlf.entity.Menu;
import cn.nlf.entity.Role;
import cn.nlf.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * @author fonlin
 * @date 2018/4/25
 */
public class DtoTransfer {

    public static User userDto2Entity(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static Role roleDto2Entity(RoleDto dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        return role;
    }


    public static Menu menuDto2Entity(MenuDto dto) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(dto, menu);
        return menu;
    }

}
