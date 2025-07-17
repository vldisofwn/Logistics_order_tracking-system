package com.logistics.tracking.aspect;

import com.logistics.tracking.model.OperationLog;
import com.logistics.tracking.model.Order;
import com.logistics.tracking.model.OrderStatus;
import com.logistics.tracking.service.OperationLogService;
import com.logistics.tracking.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 订单服务切面
 * 用于拦截订单相关的敏感操作（如删除订单、更新订单状态）
 * 该切面不需要修改原有业务逻辑，利用 AOP 表达式实现非侵入式日志记录
 */
@Slf4j // 启用日志
@Aspect // 声明这是一个切面类
@Component // 注入 Spring 容器
@RequiredArgsConstructor // 自动注入 final 依赖
public class OrderServiceAspect {

    private final OperationLogService operationLogService; // 操作日志服务
    private final OrderService orderService; // 订单服务

    /**
     * 拦截删除订单操作，记录删除日志
     * 拦截表达式：execution(* com.logistics.tracking.service.OrderService.deleteOrder(..))
     * 匹配 OrderService 中的 deleteOrder 方法
     */
    @Before("execution(* com.logistics.tracking.service.OrderService.deleteOrder(..))")
    public void logBeforeDeleteOrder(JoinPoint joinPoint) {
        try {
            // 获取方法参数
            Object[] args = joinPoint.getArgs();
            if (args.length > 0 && args[0] instanceof String) {
                String orderId = (String) args[0];

                // 查询订单信息
                Optional<Order> orderOpt = orderService.getOrderById(orderId);
                if (orderOpt.isPresent()) {
                    Order order = orderOpt.get();

                    // 构建日志对象
                    OperationLog operationLog = new OperationLog();
                    operationLog.setOperationType("DELETE_ORDER"); // 操作类型
                    operationLog.setOperationDescription("删除订单"); // 操作描述
                    operationLog.setTargetId(orderId); // 目标 ID
                    operationLog.setTargetType("Order"); // 目标类型
                    operationLog.setOperationTime(LocalDateTime.now()); // 操作时间

                    // 设置操作人信息（简化为 system，实际可获取登录用户）
                    operationLog.setOperatorId("system");
                    operationLog.setOperatorName("System");

                    // 获取操作 IP（从当前请求上下文获取）
                    try {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        if (attributes != null) {
                            HttpServletRequest request = attributes.getRequest();
                            operationLog.setIp(request.getRemoteAddr());
                        }
                    } catch (Exception e) {
                        log.warn("获取请求信息失败", e);
                    }

                    // 记录订单详细信息
                    StringBuilder details = new StringBuilder();
                    details.append("订单ID: ").append(order.getId())
                            .append(", 发件人: ").append(order.getSenderName())
                            .append(", 收件人: ").append(order.getReceiverName())
                            .append(", 状态: ").append(order.getStatus())
                            .append(", 金额: ").append(order.getAmount());
                    operationLog.setDetails(details.toString());
                    operationLog.setSuccess(true); // 操作状态

                    // 保存日志
                    operationLogService.save(operationLog);
                    log.info("记录订单删除操作日志成功: {}", orderId);
                }
            }
        } catch (Exception e) {
            log.error("记录订单删除操作日志失败", e);
        }
    }

    /**
     * 拦截更新订单状态操作，记录状态变更日志
     * 拦截表达式：execution(* com.logistics.tracking.service.OrderService.updateOrderStatus(..))
     * 匹配 OrderService 中的 updateOrderStatus 方法
     */
    @AfterReturning(pointcut = "execution(* com.logistics.tracking.service.OrderService.updateOrderStatus(..))", returning = "result")
    public void logAfterUpdateOrderStatus(JoinPoint joinPoint, Object result) {
        try {
            // 获取方法参数
            Object[] args = joinPoint.getArgs();
            if (args.length >= 2 && args[0] instanceof String && args[1] instanceof OrderStatus) {
                String orderId = (String) args[0];
                OrderStatus status = (OrderStatus) args[1];

                // 构建日志对象
                OperationLog operationLog = new OperationLog();
                operationLog.setOperationType("UPDATE_ORDER_STATUS"); // 操作类型
                operationLog.setOperationDescription("更新订单状态"); // 操作描述
                operationLog.setTargetId(orderId); // 目标 ID
                operationLog.setTargetType("Order"); // 目标类型
                operationLog.setOperationTime(LocalDateTime.now()); // 操作时间

                // 设置操作人信息（简化为 system，实际可获取登录用户）
                operationLog.setOperatorId("system");
                operationLog.setOperatorName("System");

                // 记录状态变更详情
                operationLog.setDetails("订单 " + orderId + " 状态变更为: " + status);
                operationLog.setSuccess(true); // 操作状态

                // 保存日志
                operationLogService.save(operationLog);
                log.info("记录订单状态变更操作日志成功: {}", orderId);
            }
        } catch (Exception e) {
            log.error("记录订单状态变更操作日志失败", e);
        }
    }
}
