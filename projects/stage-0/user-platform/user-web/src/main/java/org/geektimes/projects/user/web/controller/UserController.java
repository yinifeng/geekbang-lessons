package org.geektimes.projects.user.web.controller;

import org.geektimes.context.ComponentContext;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Set;
import java.util.logging.Logger;

@Path("/user")
public class UserController implements PageController {
    private static Logger logger = Logger.getLogger(UserController.class.getName());

    private final UserService userService;

    private final Validator validator;

    public UserController() {
        this.userService = ComponentContext.getInstance().getComponent("bean/UserService");
        this.validator = ComponentContext.getInstance().getComponent("bean/Validator");
    }
    
    @POST
    @Path("/register") // /hello/world -> HelloWorldController
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        User user = null;
        try {
            //获取注册页面输入信息
            String userName = request.getParameter("userName");
            String userPassword = request.getParameter("userPassword");
            String userEmail = request.getParameter("userEmail");
            String userPhoneNumber = request.getParameter("userPhoneNumber");

            logger.info("用户注册信息：userName="+userName+"userEmail="+userEmail);
            //TODO 校验用户名数据库是否已经存在

            //构造数据库实体
            user = new User();
            user.setName(userName);
            user.setPassword(userPassword);
            user.setEmail(userEmail);
            user.setPhoneNumber(userPhoneNumber);
            Set<ConstraintViolation<User>> userValidate = validator.validate(user);
            if (userValidate != null && !userValidate.isEmpty()) {
                throw new ConstraintViolationException(userValidate);
            }
            //保存数据库
            boolean register = userService.register(user);
            if (!register) {
                throw new RuntimeException("注册用户失败");
            }
        }catch (ConstraintViolationException ex){
            final Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            request.setAttribute("errorMsg",constraintViolations.iterator().next().getMessage());
            return "error/error.jsp";
        }catch (RuntimeException e) {
            request.setAttribute("errorMsg",e.getMessage());
            return "error/error.jsp";
        }
        //写入到request
        request.setAttribute("user",user);
        return "user/details.jsp";
    }
}
