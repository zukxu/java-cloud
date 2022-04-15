package com.zukxu.java8.stream;

import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-15 15:40
 */
public class TestStreamCase {
    @Test
    void testStream() {
        StreamCase.testStream();
    }

    @Test
    void testOptional() {
        StreamCase.testOptional();
    }

    @Test
    void testListStringDistinct() {
        StreamCase.testListStringDistinct();
    }

    @Test
    void testListObjectDistinct() {
        StreamCase.testListObjectDistinct();
    }

    @Test
    void testFilterDistinct() {
        StreamCase.testFilterDistinct();
    }
}
