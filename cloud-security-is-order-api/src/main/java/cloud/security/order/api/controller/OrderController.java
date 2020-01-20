package cloud.security.order.api.controller;

import cloud.security.order.api.domain.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @apiNode:
 * @since 2020/1/16
 * @author : weizc 
 */
@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     *
     * @param info
     *  @AuthenticationPrincipal
     *  @see org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver
     *
     * @return
     */
    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info //, @RequestHeader String headerUserName,
                           /* @AuthenticationPrincipal(expression = "#this.username")String username */){
       // log.info("user username is"+username);
        return info;
    }

}
