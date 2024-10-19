package com.itranlin.hexagon.document.impl;

import com.itranlin.hexagon.document.api.model.ExtDocInterface;

/**
 * The interface Doc parser.
 */
public interface DocParser {

    /**
     * Parse ext doc.
     *
     * @param clazz docClazz
     * @return ext doc interface
     */
    ExtDocInterface parse(Class<?> clazz);
}
