package com.store.demo.service.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.store.demo.response.oss.UploadParamsView;
import com.sug.core.platform.crypto.HMAC;
import com.sug.core.platform.json.JSONBinder;
import com.sug.core.util.EncodingUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OssService {



    @Value("${aliyun.upload.host}")
    private String host;

    @Value("${aliyun.appId}")
    private String appId;

    @Value("${aliyun.appKey}")
    private String appKey;

    @Value("${aliyun.upload.endPoint}")
    private String endPoint;

    @Value("${aliyun.upload.bucket}")
    private String bucket;


    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public UploadParamsView getUploadParams() throws Exception {
        UploadParamsView view = new UploadParamsView();

        view.setUrl(host);
        view.setAppId(appId);
        view.setAppKey(appKey);
        view.setBucket(bucket);
        view.setHost(endPoint);
        return view;
    }

    private String getPolicy() throws Exception {
        PolicyText text = new PolicyText();
        text.setExpiration(setExpiration());
        List<Object> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        params.add("content-length-range");
        params.add(0);
        params.add(1048576000);
        list.add(params);
        text.setConditions(list);

        String test = JSONBinder.binder(PolicyText.class).toJSON(text);
        String base64 = EncodingUtils.encryptBASE64(test.getBytes("UTF-8"));
        return base64;
    }

    private String setExpiration(){
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DAY_OF_MONTH,1);

        return dateFormat.format(calendar.getTime());
    }
    public String getPublicObject(String key){
        return host + key;
    }

    public String getBucket(ImageConstants type){
        return host + type.getUrl();
    }
}
