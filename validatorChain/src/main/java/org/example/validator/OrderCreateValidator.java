package org.example.validator;

/**
 * 订单校验
 * @author liushaoya
 * @since 2025-11-04 19:34
 */
public interface OrderCreateValidator {
    /**
     * 设置下一个校验器
     *
     * @param nextValidator
     */
    public void setNext(OrderCreateValidator nextValidator);

    /**
     * 返回下一个校验器
     *
     * @return
     */
    public OrderCreateValidator getNext();

    /**
     * 校验
     *
     */
    public void validate();
}
