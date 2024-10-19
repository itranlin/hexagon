package com.itranlin.hexagon.exception;

/**
 * The type Hexagon mate property not found exception.
 */
@SuppressWarnings("unused")
public class HexagonMatePropertyNotFoundException extends RuntimeException {

    /**
     * Instantiates a new Hexagon mate property not found exception.
     */
    public HexagonMatePropertyNotFoundException() {
    }

    /**
     * Instantiates a new Hexagon mate property not found exception.
     *
     * @param message the message
     */
    public HexagonMatePropertyNotFoundException(String message) {
        super(message);
    }
}
