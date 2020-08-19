package com.scp.cloudsecurityispriceapi.config;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.Collections;

/**
 * @author : weizc
 * @apiNode:
 * @since 2020/4/3
 */
public class SentinelFlowDemo {
    public static void main(String[] args) {
        initFlowRules();


        for (int i = 0; i < 30; i++) {
            try (Entry entry = SphU.entry("Hello");) {

                /*您的业务逻辑 - 开始*/
                System.out.println("hello world");
                /*您的业务逻辑 - 结束*/
            } catch (BlockException e) {
                /*流控逻辑处理 - 开始*/
                System.out.println("block!");
                /*流控逻辑处理 - 结束*/
            }

        }

    }

    private static void initFlowRules() {
        FlowRule rule = new FlowRule();
        rule.setResource("Hello");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        // Set limit QPS to 20.
        rule.setCount(20);
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }


}
