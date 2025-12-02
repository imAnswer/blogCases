package org.example.inventory.exception;

public class TradeException extends BizException {
    public TradeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
