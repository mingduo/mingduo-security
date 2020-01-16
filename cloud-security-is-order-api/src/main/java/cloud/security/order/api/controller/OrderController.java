package cloud.security.order.api.controller;

import cloud.security.order.api.domain.OrderInfo;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * @apiNode:
 * @since 2020/1/16
 * @author : weizc 
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public OrderInfo create(@RequestBody OrderInfo info, @RequestHeader String username){

        return info;
    }

}
