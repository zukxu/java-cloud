package com.zukxu.other.stock.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存防超卖
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/4/9 0009 15:50
 */
@Data
@Accessors(chain = true)
public class StockRequestDTO {
    String goodsId;
    int num;
}
