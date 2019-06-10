package com.lgh.fescar.helper;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;

/**
 * 
 * @ClassName: RequestHeaderInterceptor
 * @Description: feign 拦截，用于增加 fescar ID（分布式事物ID），
 * @Author lizhiting
 * @DateTime Apr 2, 2019 9:26:38 AM
 */
@Component
public class RequestHeaderInterceptorByFeign implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        String xid = RootContext.getXID();
        if(StringUtils.isNotBlank(xid)){
            template.header("Fescar-Xid",xid);
        }
    }
}
