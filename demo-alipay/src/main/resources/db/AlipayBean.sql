-- auto Generated on 2021-04-18
-- DROP TABLE IF EXISTS alipay_bean;
CREATE TABLE alipay_bean
(
    create_time            DATETIME       NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
    create_by              VARCHAR(64)    NOT NULL COMMENT '创建人 ID',
    update_time            TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by              VARCHAR(64)    NOT NULL COMMENT '更新人 ID',
    id                     BIGINT (64) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
    out_trade_no           VARCHAR(64)    NOT NULL COMMENT '商户订单号，必填',
    subject                VARCHAR(256)   NOT NULL COMMENT '交易标题，必填',
    body                   VARCHAR(128)   NOT NULL COMMENT '商品描述，可空',
    total_amount           DECIMAL(13, 4) NOT NULL COMMENT '订单总金额，单位为元，精确到小数点后两位
	 * totalAmount=discountableAmount+unDiscountableAmount',
    discountable_amount    DECIMAL(13, 4) NOT NULL COMMENT '参与优惠计算的金额，单位为元，精确到小数点后两位',
    un_discountable_amount DECIMAL(13, 4) NOT NULL COMMENT '不参与优惠计算的金额，单位为元，精确到小数点后两位',
    payment                VARCHAR(50)    NOT NULL COMMENT '支付模式',
    `status`               INT (11) NOT NULL DEFAULT 0 COMMENT '支付状态(0:未支付 1：已支付 2：待退款 3：已退款 4：已关闭)',
    product_code           VARCHAR(64)    NOT NULL DEFAULT 'FAST_INSTANT_TRADE_PAY' COMMENT '商品交易码',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'alipay_bean';
