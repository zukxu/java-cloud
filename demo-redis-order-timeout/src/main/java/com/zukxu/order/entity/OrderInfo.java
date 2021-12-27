package com.zukxu.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * ${END}
 * </p>
 *
 * @author zukxu
 * CreateTime: 2021/2/28 0028 18:00
 */
@Data
@TableName(value = "order_info")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 订单状态（0待支付,1代发货,2待收货,3已完成,4已取消,5已评价,6未评价）
     */
    @TableField(value = "order_status")
    private Integer orderStatus;
    /**
     * 商品id
     */
    @TableField(value = "commodity_id")
    private String commodityId;
    @TableField(value = "group_id")
    private String groupId;

}