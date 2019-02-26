package com.store.demo.interceptor;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Visitor;
import com.store.demo.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;


public class VisitorInterceptor extends HandlerInterceptorAdapter {

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VisitorInterceptor.class);

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private VisitorService visitorService;

    @Value("${spring.profiles.active:@null}")
    private String env;

    @Value("${server.servlet.session.cookie.max-age:@null}")
    private Integer cookieMaxAge;

    @Value("${server.servlet.session.cookie.domain:@null}")
    private String cookieDomain;

    @Value("${server.servlet.session.cookie.path:@null}")
    private String cookiePath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("SessionInterceptor ----------preHandle------------, URI=" + request.getRequestURI());

        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        Cookie cookie;

        String remoteAddress = (!"dev".equalsIgnoreCase(env) && Objects.nonNull(request.getHeader("X-Real-IP")))
                ? request.getHeader("X-Real-IP") : request.getRemoteAddr();

        sessionContext.setRemoteAddress(remoteAddress);

        if (Objects.nonNull(request.getCookies())
                && Objects.nonNull(cookie = Arrays.stream(request.getCookies()).filter(c -> c.getName().equals("visitor")).findFirst().orElse(null))) {
            Visitor visitor = sessionContext.getVisitor();
            if (Objects.nonNull(visitor) && visitor.getUuid().equalsIgnoreCase(cookie.getValue())) {
                return true;
            }
            visitor = visitorService.getByUuid(cookie.getValue());
            if (Objects.nonNull(visitor)) {
                sessionContext.setVisitor(visitor);
                return true;
            }
        }

        Visitor visitor = visitorService.create();

        sessionContext.setVisitor(visitor);
        cookie = new Cookie("visitor", visitor.getUuid());
        cookie.setDomain(cookieDomain);
        cookie.setPath(cookiePath);
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);

        return true;
    }
}

