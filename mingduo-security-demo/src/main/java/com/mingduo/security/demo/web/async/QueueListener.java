package com.mingduo.security.demo.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/17
 */
@Slf4j
@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    MockQueue mockQueue;
    @Autowired
    DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            while (true) {
                String completeOrder = mockQueue.getCompleteOrder();
                if (StringUtils.isNotBlank(completeOrder)) {
                    log.info("返回订单处理结果:" + completeOrder);

                    DeferredResult<String> deferredResult = deferredResultHolder.getMap().get(completeOrder);
                    deferredResult.setResult("place order success :"+completeOrder);
                    mockQueue.setCompleteOrder(null);
                }
            }
        }).start();
    }
}
