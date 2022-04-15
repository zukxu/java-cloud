package com.zukxu.java8.stream;

import org.junit.jupiter.api.Test;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-04-15 15:29
 */
public class TestStream {
    @Test
    void createStream() {
        StreamDemo.createStream();
    }

    @Test
    void forEachStream() {
        StreamDemo.forEachStream();
    }

    @Test
    void filterStream() {
        StreamDemo.filterStream();
    }

    @Test
    void juHeStream() {
        StreamDemo.juHe();
    }

    @Test
    void mapStream() {
        StreamDemo.mapStream();
    }

    @Test
    void reduceStream() {
        StreamDemo.reduceStream();
    }

    @Test
    void toCollect() {
        StreamDemo.toCollect();
    }

    @Test
    void countCollect() {
        StreamDemo.countCollect();
    }

    @Test
    void groupCollect() {
        StreamDemo.groupCollect();
    }

    @Test
    void joinCollect() {
        StreamDemo.joinCollect();
    }

    @Test
    void reducingCollect() {
        StreamDemo.reducingCollect();
    }

    @Test
    void sortStream() {
        StreamDemo.sortStream();
    }

    @Test
    void comStream() {
        StreamDemo.comStream();
    }
}
