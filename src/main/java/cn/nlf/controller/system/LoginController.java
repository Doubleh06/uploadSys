package cn.nlf.controller.system;


import cn.nlf.core.ErrorCode;
import cn.nlf.core.Result;
import cn.nlf.dto.UpdatePasswordDto;
import cn.nlf.entity.User;
import cn.nlf.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author fonlin
 * @date 2018/1/2
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView hello(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout,
                              @RequestParam(value = "expired", required = false) String expired,
                              @RequestParam(value = "noUser", required = false) String noUser,
                              @RequestParam(value = "disabled", required = false) String disabled,
                              @RequestParam(value = "errPass", required = false) String errPass) {
        ModelAndView mv = new ModelAndView();

        if (error != null) {
            mv.addObject("msg", "用户名密码不正确！");
        }
        if (logout != null) {
            mv.addObject("msg", "成功登出！");
        }
        if (expired != null) {
            mv.addObject("msg", "登录状态失效！");
        }
        if (noUser != null) {
            mv.addObject("msg", "用户名不正确！");
        }
        if (disabled != null) {
            mv.addObject("msg", "当前账户不可用！");
        }
        if (errPass != null) {
            mv.addObject("msg", "密码错误！");
        }
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String forbidden() {
        return "/403";
    }



    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public Result updatePassword(@RequestBody UpdatePasswordDto dto) {
        if(!dto.getNewPassword().equals(dto.getRepeatNewPassword())){
            return new Result(405,"两次密码输入不一致");
        }
        User user = userService.select(dto.getUserId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(dto.getOldPassword(),user.getPassword())){
            return new Result(406,"旧密码输入错误");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userService.updateSelective(user);
        return new Result(ErrorCode.OK);
    }

    @RequestMapping(value = "/updateInitPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result updateInitPassword(@RequestBody UpdatePasswordDto dto) {
        if(!dto.getNewPassword().equals(dto.getRepeatNewPassword())){
            return new Result(405,"两次密码输入不一致");
        }
        User user = userService.selectByUsername(dto.getUsername());
        if(user!=null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(dto.getOldPassword(),user.getPassword())){
                return new Result(406,"旧密码输入错误");
            }
            user.setPassword(encoder.encode(dto.getNewPassword()));
            userService.updateSelective(user);
            return new Result(ErrorCode.OK);
        }
        return new Result(407,"用户不存在");
    }

    @RequestMapping(value = "/initPassword")
    public String initPassword() {
        return "/password";
    }


}
