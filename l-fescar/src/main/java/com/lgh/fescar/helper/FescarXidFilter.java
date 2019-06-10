package com.lgh.fescar.helper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.seata.core.context.RootContext;


public class FescarXidFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String xid = RootContext.getXID();
        String restXid = request.getHeader("Fescar-Xid");
        boolean bind = false;
        if((!StringUtils.hasText(xid)) && StringUtils.hasText(restXid)){
            RootContext.bind(restXid);
            bind = true;
        }
        try{
            filterChain.doFilter(request, response);
        } finally {
            if (bind) {
            	String unbindXid = RootContext.unbind();
                if (!restXid.equalsIgnoreCase(unbindXid)) {
                    logger.warn("xid in change during http rest from " + restXid + " to " + unbindXid);
                    if (unbindXid != null) {
                        RootContext.bind(unbindXid);
                        logger.warn("bind [" + unbindXid + "] back to RootContext");
                    }
                }
            }
        }
    }
}
