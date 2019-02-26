package com.store.demo.controller.management;

import com.store.demo.constants.CaptchaConstants;
import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.request.LoginForm;
import com.store.demo.request.sms.CaptchaSendForm;
import com.store.demo.response.CustomerView;
import com.store.demo.response.sms.CaptchaView;
import com.store.demo.service.CartService;
import com.store.demo.service.CustomerService;
import com.store.demo.service.GoodsService;
import com.store.demo.service.sms.SmsService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.SuccessView;
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

import static com.store.demo.constants.CommonConstants.CAPTCHA;
import static com.store.demo.constants.CommonConstants.LOGIN;
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
            if (!StringUtils.hasText(whiteList) || whiteList.contains(form.getPhone())) {
                smsService.sendCaptcha(form.getPhone(), captcha);
            }
            sessionContext.setCaptcha(form.getPhone(), captcha, CaptchaConstants.REGISTER_LOGIN);
        } catch (Exception e) {
            throw new RuntimeException("send captcha fail : " + e.getMessage());
        }

        CaptchaView view = new CaptchaView();
        if (!profiles.equalsIgnoreCase("prod")) {
            view.setCaptcha(captcha);
        }
        return view;
    }

    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public CustomerView login(@Valid @RequestBody LoginForm form) {
        if (!sessionContext.validCaptcha(form.getPhone(), form.getCaptcha(), CaptchaConstants.REGISTER_LOGIN)) {
            throw new InvalidRequestException("验证码错误或超时");
        }
        Customer customer = customerService.getByPhone(form.getPhone());
        if (Objects.isNull(customer)) {
            throw new ResourceNotFoundException("用户不存在");
        }
        if (customer.getStatus().equals(FROZEN)) {
            throw new InvalidRequestException("该账户被冻结,请联系平台恢复");
        }

        sessionContext.preLogin();

        sessionContext.login(customer);

        sessionContext.removeCaptcha();

        CustomerView view = new CustomerView();
        BeanUtils.copyProperties(customer, view);

        return view;
    }




}
