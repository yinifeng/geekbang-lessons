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
        String userPassword = request.getParameter("userPassword");
        String userEmail = request.getParameter("userEmail");
        String userPhoneNumber = request.getParameter("userPhoneNumber");

        logger.info("用户注册信息：userName="+userName+"userEmail="+userEmail);
        //校验用户名数据库是否已经存在
        
        //构造数据库实体
        User user = new User();
        user.setId(0L);
        user.setName(userName);
        user.setPassword(userPassword);
        user.setEmail(userEmail);
        user.setPhoneNumber(userPhoneNumber);
        //保存数据库
        User userQuery = userService.queryUserById(1L);
        logger.info("userQuery###########"+userQuery);
        //写入到request
        request.setAttribute("user",user);
        return "user/details.jsp";
    }
}
