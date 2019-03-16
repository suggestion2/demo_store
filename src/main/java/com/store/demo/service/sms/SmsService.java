package com.store.demo.service.sms;

import com.aliyuncs.exceptions.ClientException;
import com.store.demo.request.sms.SmsCaptchaForm;
import com.sug.core.platform.sms.aliyun.AliyunSmsService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    private AliyunSmsService2 smsService;

    @Value("${aliyun.sms.sign:@null}")
    private String sign;

    @Value("${aliyun.sms.captcha.template:@null}")
    private String captchaTemp;

    public void sendCaptcha(String phone,String captcha) throws ClientException, UnsupportedEncodingException {

        SmsCaptchaForm form = new SmsCaptchaForm();
        form.setCode(captcha);
        smsService.send(phone,sign,captchaTemp,form);
    }
}
