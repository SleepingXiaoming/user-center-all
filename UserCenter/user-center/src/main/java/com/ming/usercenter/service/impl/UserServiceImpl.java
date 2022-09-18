package com.ming.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ming.usercenter.common.ErrorCode;
import com.ming.usercenter.constant.UserConstant;
import com.ming.usercenter.exception.BusinessException;
import com.ming.usercenter.mapper.StudentMapper;
import com.ming.usercenter.pojo.Student;
import com.ming.usercenter.pojo.User;
import com.ming.usercenter.service.StudentService;
import com.ming.usercenter.service.UserService;
import com.ming.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户服务实现类
 *
 * @author Administrator
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2022-09-02 18:54:17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Resource
    private UserMapper userMapper;
    @Resource
    private StudentService studentService;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String email, String studentNumber) {
        // 校验 输入是否合法
        // 检验是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, email, studentNumber)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        // 检验是否符合要求
        Pattern emailCompile = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        if (!emailCompile.matcher(email).find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "邮箱格式错误");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度不得小于4");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不得小于8");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不相同");
        }
        // 校验学号
        String studentNumRegx = "^[ABS]\\d{8}$";
        Matcher studenttNumberMatcher = Pattern.compile(studentNumRegx).matcher(studentNumber);
        if (!studenttNumberMatcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号不符合规范");
        }
        // 判断用户名中是否包含特殊字符
        Matcher userAccountMatcher = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、 ？]").matcher(userAccount);
        if (userAccountMatcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户中含有非法字符");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentNumber", studentNumber);
        int count1 = this.count(queryWrapper);
        if (count1 > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "此学号已被注册");
        }
        // 判断账户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        int count = this.count(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "此账号已被注册");
        }

        // 加密
        String password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 通过学号获取学生姓名，如果不存在就赋值为游客
        Student studentServiceById = studentService.getById(studentNumber);

        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(password);
        user.setEmail(email);
        user.setStudentNumber(studentNumber);
        user.setUsername(studentServiceById!=null?studentServiceById.getUsername():"学生");


        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册失败，请稍后重试");
        }
        return user.getId();
    }


    @Override
    public User userLogin(String loginInWay, String inputPassword, HttpServletRequest request) {
        // 校验 输入是否合法
        // 检验是否为空
        if (StringUtils.isAnyBlank(loginInWay, inputPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "账户和密码不能为空");
        }
        if (inputPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }

        // 获取 加盐密码加密
        String password = DigestUtils.md5DigestAsHex((SALT + inputPassword).getBytes());

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userPassword", password);

        // 使用 邮件登录
        String emailRegx = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Matcher matcher = Pattern.compile(emailRegx).matcher(loginInWay);
        // 判断是否是通过邮箱登录
        if (matcher.find()) {
            userQueryWrapper.eq("email", loginInWay);
            User user = userMapper.selectOne(userQueryWrapper);
            if (user == null) {
                log.info("user login failed,userEmail cannot match userPassword");
                throw new BusinessException(ErrorCode.WRONG_LOGIN, "邮箱密码错误");
            }
            User safeUser = desensitizationUser(user);
            HttpSession session = request.getSession();
            session.setAttribute(UserConstant.USER_LOGIN_STATE, safeUser);
            log.info("用户信息,{}", safeUser);
            return safeUser;
        }

        // 使用账号 Account 进行登录
        // 检验是否符合要求
        if (loginInWay.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号输入有误，请查证后输入");
        }

        // 判断用户名中是否包含特殊字符
        Matcher loginInWayMatcher = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]").matcher(loginInWay);
        if (loginInWayMatcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号中包含特殊字符，请查证后输入");
        }

        userQueryWrapper.eq("userAccount", loginInWay);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            log.info("user login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.WRONG_LOGIN, "账号密码错误，请查证后输入");
        }

        User safeUser = desensitizationUser(user);
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, safeUser);
        return safeUser;
    }


    @Override
    public User desensitizationUser(User user) {
        if (user == null) {
            return null;
        }
        // 信息脱敏
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setStudentNumber(user.getStudentNumber());
        return safeUser;
    }

    @Override
    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        log.info("获取到注销请求");
        return 1;
    }


}