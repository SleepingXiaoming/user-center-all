package com.ming.usercenter.controller;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ming.usercenter.common.BaseResponse;
import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.constant.UserConstant;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.pojo.User;
import com.ming.usercenter.pojo.request.*;
import com.ming.usercenter.service.UserService;
import com.ming.usercenter.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String email = userRegisterRequest.getEmail();
        String studentNumber = userRegisterRequest.getStudentNumber();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, email, studentNumber)) {
//            return ResultUtils.error(ErrorCode.NULL_ERROR, "参数不能为空");
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数不能为空");
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword, email, studentNumber);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        String loginInWay = userLoginRequest.getLoginInWay();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(loginInWay, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数不能为空");
        }
        User user = userService.userLogin(loginInWay, userPassword, request);
        return ResultUtils.success(user);
    }


    /**
     * 用户注销
     *
     * @param request
     */
    @PostMapping("/outLogin")
    public BaseResponse<Integer> outLogin(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "");
        }
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 用户搜索
     *
     * @param userSearch
     * @param request
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(/*@RequestParam*/ UserSearch userSearch, HttpServletRequest request) {

        log.info("调用了search方法");

        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "该账号无权限");
        }
        Object username1 = request.getAttribute("data");
        System.out.println(username1);
        Object username2 = request.getSession().getAttribute("username");
        System.out.println(username2);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (userSearch != null) {
            String username = userSearch.getUsername();
            String account = userSearch.getAccount();
            Integer gender = userSearch.getGender();
            String phone = userSearch.getPhone();
            String email = userSearch.getEmail();
            Integer userStatus = userSearch.getUserStatus();
            Date createTime = userSearch.getCreateTime();
            Integer userRole = userSearch.getUserRole();
            String studentNumber = userSearch.getStudentNumber();
            if (!StringUtils.isBlank(username)) {
                queryWrapper.like("username", username);
            }
            if (!StringUtils.isBlank(username)) {
                queryWrapper.like("account", account);
            }
            if (!StringUtils.isBlank(phone)) {
                queryWrapper.like("phone", phone);
            }
            if (!StringUtils.isBlank(email)) {
                queryWrapper.like("email", email);
            }
            if (!StringUtils.isBlank(studentNumber)) {
                queryWrapper.like("studentNumber", studentNumber);
            }
            if (gender != null) {
                queryWrapper.eq("gender", gender);
            }
            if (userStatus != null) {
                queryWrapper.eq("userStatus", userStatus);
            }
            if (userRole != null) {
                queryWrapper.eq("userRole", userRole);
            }
            if (createTime != null) {
                queryWrapper.eq("createTime", createTime);
            }
        }


//        if (StringUtils.isNotBlank(username)) {
//            queryWrapper.like("username", username);
//        }
        List<User> users = userService.list(queryWrapper);
        // 信息脱敏
        List<User> list = users.stream().map(user -> userService.desensitizationUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }


    @DeleteMapping("/rule")
    public BaseResponse<Boolean> deleteById(@RequestBody UserDelete userDelete, HttpServletRequest request) {
        log.info("调用delete成功");
        // 仅管理员查询
        if (!isAdmin(request) || userDelete.getId() <= 0) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }
        return ResultUtils.success(userService.removeById(userDelete.getId()));
    }


    /**
     * 获取当前用户信息
     *
     * @param session
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getUser(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        Long id = currentUser.getId();
        // 重新从数据库中查询新的数据，将数据进行更新
        // TODO 校验使用是否合法
        User user = userService.getById(id);
        User safeUser = userService.desensitizationUser(user);
        return ResultUtils.success(safeUser);
    }

    @RequestMapping("/rule")
    public BaseResponse<Boolean> deleteUser() {

        return ResultUtils.success(true);
    }


    /**
     * 测试请求的方法、路径
     *
     * @param request
     */
    @RequestMapping("/")
    public void test(HttpServletRequest request) {
        String method = request.getMethod();
        log.info(method);
        String uri = request.getRequestURI();
        log.info(uri);
    }


    /**
     * 判断用户权限 鉴限
     *
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员查询
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        return user != null && user.getUserRole() == UserConstant.ADMIN_ROLE;
    }
}
