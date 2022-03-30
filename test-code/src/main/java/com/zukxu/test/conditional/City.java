package com.zukxu.test.conditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author xupu
 * @since 2022-03-30 14:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市code
     */
    private Integer cityCode;
}
