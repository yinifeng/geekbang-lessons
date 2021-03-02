package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;

@Path("/user")
public class UserController implements PageController {
    private static Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    public UserController() {
        this.userService = new UserServiceImpl();
    }
    
    @POST
    @Path("/register") // /hello/world -> HelloWorldController
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        //获取注册页面输入信息
        String userName = request.getParameter("userName");
        if (userName == null){
            throw new RuntimeException("用户名不能为空");
        }
        String userPassword = request.getParameter("userPassword");
        if (userPassword == null){
            throw new RuntimeException("用户密码不能为空");
        }
        String userEmail = request.getParameter("userEmail");
        if (userEmail == null){
            throw new RuntimeException("用户邮箱不能为空");
        }
        String userPhoneNumber = request.getParameter("userPhoneNumber");
        if (userPhoneNumber == null){
            throw new RuntimeException("用户电话不能为空");
        }

        logger.info("用户注册信息：userName="+userName+"userEmail="+userEmail);
        //TODO 校验用户名数据库是否已经存在
        
        //构造数据库实体
        User user = new User();
        user.setName(userName);
        user.setPassword(userPassword);
        user.setEmail(userEmail);
        user.setPhoneNumber(userPhoneNumber);
        //保存数据库
        boolean register = userService.register(user);
        if (!register) {
            throw new RuntimeException("注册用户失败");
        }
        //写入到request
        request.setAttribute("user",user);
        return "user/details.jsp";
    }
}
