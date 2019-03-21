package com.store.demo.controller.api;

import com.store.demo.constants.CaptchaConstants;
import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.request.LoginForm;
import com.store.demo.request.RegisterForm;
import com.store.demo.request.sms.CaptchaSendForm;
import com.store.demo.response.CustomerView;
import com.store.demo.response.sms.CaptchaView;
import com.store.demo.service.CartService;
import com.store.demo.service.CustomerService;
import com.store.demo.service.GoodsService;
import com.store.demo.service.sms.SmsService;
import com.sug.core.platform.crypto.MD5;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.util.RandomStringGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.CustomerConstants.FROZEN;

@RestController
@RequestMapping("/api")
public class CommonController {
    @Value("${aliyun.sms.whiteList:@null}")
    private String whiteList;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Autowired
    private CustomerService customerService;


    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CartService cartService;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = CAPTCHA, method = RequestMethod.POST)
    public CaptchaView registerCaptcha(@Valid @RequestBody CaptchaSendForm form) {
        String captcha = RandomStringGenerator.getRandomNumberStringByLength(6);
        try {
            if (!profiles.equalsIgnoreCase("dev")&&(!StringUtils.hasText(whiteList) || whiteList.contains(form.getPhone()))) {
                smsService.sendCaptcha(form.getPhone(), captcha);
            }
            sessionContext.setCaptcha(form.getPhone(), captcha, CaptchaConstants.REGISTER_LOGIN);
        } catch (Exception e) {
            throw new RuntimeException("send captcha fail : " + e.getMessage());
        }

        CaptchaView view = new CaptchaView();
        if (profiles.equalsIgnoreCase("dev")) {
            view.setCaptcha(captcha);
        }
        return view;
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public CustomerView login(@Valid @RequestBody LoginForm form) {
        Customer customer = customerService.getByPhone(form.getPhone());
        if (Objects.isNull(customer) || !customer.getPassword().equalsIgnoreCase(MD5.encrypt(form.getPassword() + MD5_SALT))) {
            throw new ResourceNotFoundException("用户不存在");
        }
        if (customer.getStatus().equals(FROZEN)) {
            throw new InvalidRequestException("该账户被冻结,请联系平台恢复");
        }

        sessionContext.login(customer);

        CustomerView view = new CustomerView();
        BeanUtils.copyProperties(customer, view);

        return view;
    }

    @RequestMapping(value = REGISTER, method = RequestMethod.POST)
    public CustomerView registerByPhone(@Valid @RequestBody RegisterForm form) {
        if (!sessionContext.validCaptcha(form.getPhone(), form.getCaptcha(), CaptchaConstants.REGISTER_LOGIN)) {
            throw new InvalidRequestException("验证码错误或超时");
        }
        Customer customer = customerService.getByPhone(form.getPhone());
        if (Objects.nonNull(customer)) {
            throw new InvalidRequestException("手机号已注册");
        }

        customer = new Customer();
        customer.setPhone(form.getPhone());
        customer.setPassword(MD5.encrypt(form.getPassword() + MD5_SALT));
        customer.setName(form.getPhone());
        customerService.create(customer);

        sessionContext.login(customer);

        sessionContext.removeCaptcha();

        CustomerView view = new CustomerView();
        BeanUtils.copyProperties(customer, view);

        return view;
    }

    @RequestMapping(value = LOGOUT, method = RequestMethod.GET)
    public ResponseView logout() {
        sessionContext.logout();
        return new ResponseView();
    }

    @RequestMapping(value = CURRENT, method = RequestMethod.GET)
    @CustomerLoginRequired
    public CustomerView detail() {
        Customer customer = sessionContext.getCustomer();

        CustomerView view = new CustomerView();
        BeanUtils.copyProperties(customer, view);
        return view;
    }


}
