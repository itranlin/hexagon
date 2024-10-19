package com.itranlin.hexagon.exception;

/**
 * The type Hexagon property load exception.
 */
@SuppressWarnings("unused")
public class HexagonPropertyLoadException extends RuntimeException {

    /**
     * Instantiates a new Hexagon property load exception.
     *
     * @param message the message
     */
    public HexagonPropertyLoadException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Hexagon property load exception.
     *
     * @param t the t
     */
    public HexagonPropertyLoadException(Throwable t) {
        super(t);
    }
}
