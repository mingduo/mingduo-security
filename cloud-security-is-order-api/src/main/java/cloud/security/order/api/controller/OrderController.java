package cloud.security.order.api.controller;

import cloud.security.order.api.domain.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public OrderInfo create(@RequestBody OrderInfo info , @RequestHeader("username") String headerUserName
            /*, @AuthenticationPrincipal(expression = "#this.username")String username*/ ){
        log.info("user username is"+headerUserName);
        return info;
    }

}
