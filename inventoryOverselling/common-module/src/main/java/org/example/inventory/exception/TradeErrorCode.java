package org.example.inventory.exception;

public enum TradeErrorCode implements ErrorCode {

    /**
     * 库存回滚失败
     */
    INVENTORY_ROLLBACK_FAILED("INVENTORY_ROLLBACK_FAILED", "库存回滚失败");

    private String code;

    private String message;

    TradeErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
