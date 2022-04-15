package com.zukxu.java8.optional;

import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-15 10:58
 */
public class TestOptional {

    @Test
    void testOf() {
        OptionalSimpleDemo.testOf();
    }

    @Test
    void testOfNullable() {
        OptionalSimpleDemo.testOfNullable();
    }

    @Test
    void testOrElseAndOrElseGet() {
        OptionalSimpleDemo.testOrElseAndOrElseGet();
    }

    @Test
    void testOrElseThrow() {
        OptionalSimpleDemo.testOrElseThrow();
    }

    @Test
    void testMap() {
        OptionalSimpleDemo.testMap();
    }

    @Test
    void testFlatMap() {
        OptionalSimpleDemo.testFlatMap();
    }

    @Test
    void testIsPresent() {
        OptionalSimpleDemo.testIsPresent();
    }

    @Test
    void testIfPresent() {
        OptionalSimpleDemo.testIfPresent();
    }
    @Test
    void testFilter() {
        OptionalSimpleDemo.testFilter();
    }
}
