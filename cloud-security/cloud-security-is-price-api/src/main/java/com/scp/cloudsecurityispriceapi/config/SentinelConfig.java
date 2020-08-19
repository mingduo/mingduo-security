package com.scp.cloudsecurityispriceapi.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collections;

/**
 * 
 * @apiNode:
 * @since 2020/4/3
 * @author : weizc 
 */
@Configuration
public class SentinelConfig implements ApplicationListener<ContextRefreshedEvent>{

    public static final String SETPRICE_PARAM="/setprice";
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        FlowRule rule=new FlowRule();
        rule.setResource("getprice");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);

        FlowRuleManager.loadRules(Collections.singletonList(rule));


        DegradeRule degradeRule=new DegradeRule();
        degradeRule.setResource(SETPRICE_PARAM);
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        degradeRule.setCount(1);
        degradeRule.setTimeWindow(10);

        DegradeRuleManager.loadRules(Collections.singletonList(degradeRule));

    }
}
