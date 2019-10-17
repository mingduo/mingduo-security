package com.mingduo.security.demo.web.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author : weizc
 * @description:
 * @since 2019/10/17
 */
@Component
@Slf4j
public class MockQueue {

    private String placeOrder;

    private String completeOrder;


    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) {

        new Thread(() -> {
            log.info("接到下单请求, " + placeOrder);

            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCompleteOrder(placeOrder);
            log.info("下单请求处理完毕," + placeOrder);

        }).start();
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
