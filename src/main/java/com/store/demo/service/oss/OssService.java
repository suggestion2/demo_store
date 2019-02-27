package com.store.demo.service.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
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



    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public String getPublicObject(String key){
        return host + key;
    }

    public String getBucket(ImageConstants type){
        return host + type.getUrl();
    }
}
