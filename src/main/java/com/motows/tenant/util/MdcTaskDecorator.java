package com.motows.tenant.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {
    private final static Logger LOG = LoggerFactory.getLogger(MdcTaskDecorator.class);

    @Override
    public Runnable decorate(Runnable runnable) {
        // Right now: Web thread context !
        // (Grab the current thread MDC data)
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        final RequestAttributes reqAtt = RequestContextHolder.getRequestAttributes();
        // final SecurityContext securityContext = SecurityContextHolder.getContext();
        return () -> {
            try {
                // Right now: @Async thread context !
                // (Restore the Web thread context's MDC data)
                MDC.setContextMap(contextMap);
                RequestContextHolder.setRequestAttributes(reqAtt);
                // SecurityContextHolder.setContext(securityContext);
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}


