package com.itranlin.hexagon.exception;

/**
 * The type Hexagon unknown exception.
 */
@SuppressWarnings("unused")
public class HexagonUnknownException extends RuntimeException {
    /**
     * Instantiates a new Hexagon unknown exception.
     *
     * @param message the message
     */
    public HexagonUnknownException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Hexagon unknown exception.
     *
     * @param t the t
     */
    public HexagonUnknownException(Throwable t) {
        super(t);
    }

    /**
     * Instantiates a new Hexagon unknown exception.
     *
     * @param message the message
     * @param t       the t
     */
    public HexagonUnknownException(String message, Throwable t) {
        super(message, t);
    }
}
