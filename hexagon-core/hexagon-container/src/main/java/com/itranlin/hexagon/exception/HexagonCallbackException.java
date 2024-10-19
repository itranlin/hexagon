package com.itranlin.hexagon.exception;

/**
 * The type Hexagon callback exception.
 */
@SuppressWarnings("unused")
public class HexagonCallbackException extends RuntimeException {

    /**
     * Instantiates a new Hexagon callback exception.
     *
     * @param message the message
     */
    public HexagonCallbackException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Hexagon callback exception.
     *
     * @param throwable the throwable
     */
    public HexagonCallbackException(Throwable throwable) {
        super(throwable);
    }

}
