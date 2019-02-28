package com.store.demo.controller.management;

import com.store.demo.constants.UserConstants;
import com.store.demo.context.SessionContext;
import com.store.demo.domain.User;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.request.UserLoginForm;
import com.store.demo.request.UserResetPasswordForm;
import com.store.demo.response.UserView;
import com.store.demo.service.UserService;
import com.sug.core.platform.crypto.MD5;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;


@RestController
@RequestMapping(value = "/management/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public UserView login(@Valid @RequestBody UserLoginForm form) {
        User user = userService.getByName(form.getName());
        if(Objects.isNull(user) || !Objects.equals(user.getPassword(),MD5.encrypt(form.getPassword() + MD5_SALT))){
            throw new InvalidRequestException("invalid name or password","invalid name or password");
        }
        sessionContext.setUser(user);
        UserView userView = new UserView();
        BeanUtils.copyProperties(user,userView);
        return userView;
    }

    @RequestMapping(value = LOGOUT, method = RequestMethod.GET)
    @UserLoginRequired
    public ResponseView logout() {
        sessionContext.logout();
        return new ResponseView();
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    @UserLoginRequired
    public ResponseView resetPassword(@Valid @RequestBody UserResetPasswordForm form) {
        User user = sessionContext.getUser();
        if(!user.getPassword().equalsIgnoreCase(MD5.encrypt(form.getOriginPassword() + MD5_SALT))){
            throw new InvalidRequestException("invalid password","invalid password");
        }
        user.setPassword(MD5.encrypt(form.getNewPassword() + MD5_SALT));
        userService.update(user);

        return new ResponseView();
    }

    @RequestMapping(value = CURRENT, method = RequestMethod.GET)
    @UserLoginRequired
    public UserView current() {
        User user = sessionContext.getUser();
        UserView userView = new UserView();
        BeanUtils.copyProperties(user,userView);
        return userView;
    }
}
