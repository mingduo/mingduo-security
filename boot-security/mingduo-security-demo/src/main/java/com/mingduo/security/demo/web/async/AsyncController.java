package com.mingduo.security.demo.web.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @description:
 * @since 2019/10/17
 * @author : weizc 
 */
@Slf4j
@RestController
public class AsyncController {

    @Autowired
    DeferredResultHolder deferredResultHolder;
    @Autowired
    MockQueue mockQueue;

    @RequestMapping("/order")
    public DeferredResult<String> order() throws InterruptedException {
        log.info("主线程开始" );
        Thread.sleep(TimeUnit.SECONDS.toMillis(1));


        String orderNum = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNum);

        DeferredResult<String> result = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNum, result);

   /*     Callable<String> result = new Callaible<String>() {
            @Override
            public String call() throws Exception {
                log.info("副线程开始" );
               Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                log.info("副线程返回");
                return "success";
            }
        };*/
        log.info("主线程返回" );

        return result;
    }
}
