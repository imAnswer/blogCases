package org.example.validator;

/**
 * @author liushaoya
 * @since 2025-11-04 19:37
 */
public abstract class BaseOrderCreateValidator implements OrderCreateValidator {

    protected OrderCreateValidator nextValidator;

    @Override
    public void setNext(OrderCreateValidator nextValidator) {
        this.nextValidator = nextValidator;
    }

    @Override
    public OrderCreateValidator getNext() {
        return nextValidator;
    }

    /**
     * 校验
     *
     * @throws Exception
     */
    @Override
    public void validate() {
        doValidate();

        if (nextValidator != null) {
            nextValidator.validate();
        }
    }

    /**
     * 校验方法的具体实现
     *
     */
    protected abstract void doValidate();
}
