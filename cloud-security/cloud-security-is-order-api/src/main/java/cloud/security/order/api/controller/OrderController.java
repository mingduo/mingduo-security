package cloud.security.order.api.controller;

import cloud.security.order.api.domain.OrderInfo;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/1/16
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * @param info
     * @return
     * @AuthenticationPrincipal
     * @see org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader("username") String headerUserName
            , @AuthenticationPrincipal(expression = "#this") String token) {

        log.info("user username is" + headerUserName);
        return info;
    }


    /**
     * 创建订单
     * @param info
     * @return
     */
    @PostMapping("/createOrder")
    public OrderInfo createOrder(@RequestBody OrderInfo info) {

        try (Entry entry= SphU.entry("createOrder")){
            return info;
        } catch (BlockException e) {
            log.info("block");
        }
        return null;
    }


    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @GetMapping("/prices")
    public String getPrices(@AuthenticationPrincipal Authentication user) {


        String price = oAuth2RestTemplate.getForObject("http://localhost:8086/prices/1", String.class);

        log.info("details  is :" + user);

        log.info("price  is :" + price);
        return price;
    }


}
