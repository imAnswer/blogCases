package org.example.inventory.statemachine;

/**
 * @author liushaoya
 * @since 2025-11-14 20:04
 */
public interface StateMachine<STATE, EVENT> {

    /**
     * 状态机转移
     *
     * @param state
     * @param event
     * @return
     */
    public STATE transition(STATE state, EVENT event);
}
