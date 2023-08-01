package com.zukxu.java8.test.testdemo.loop;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zukxu
 * CreateTime: 2021/6/22 0022 19:42
 */
@Data
@Accessors(chain = true)
public class RecordDTO {
    private Integer mid;
    private String title;
    private List<Record> content;

    @Override
    public String toString() {
        return "{" + "mid=" + mid + ", title='" + title + '\'' + '}';
    }
}
