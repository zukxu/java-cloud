# 分布式实现库存超卖现象

## mysql悲观锁/乐观锁

```java

/**
 * 库存扣减（伪代码 mysql悲观锁/乐观锁 )
 *
 * @param stockRequestDTO
 * @return Boolean
 */
public Boolean stockHandle(StockRequestDTO stockRequestDTO){
	//业务逻辑
	//执行SQL锁机制
	//乐观锁的自旋是需要在自己的业务逻辑中实现的。

}
```

### 使用悲观锁实现

```sql
begin; -- 开启事务
select stock_num
from t_stock t_stock
where goodsId = '12345' for
update; -- 获取并设置排他锁
update t_stock
set stock_num = stock_num - 1
where goodsId = '12345' ；-- 更新资源
commit; -- 提交事务并解锁

```

### 乐观锁实现

```sql
update t_stock
set stock_num = stock_num - 1,
    version   = version + 1
where goodsId = '12345'
  and version = 7;
-- 1.更新资源时先判断当前数据版本号和之前获取时是否一致
-- 2.如果版本号一致，更新资源并版本号+1
-- 3.若版本号不一致，返回错误并由业务系统进行自旋重试
```

## 伪代码 redis 分布式锁

```java

@Transactional(rollbackFor = {RuntimeException.class, Error.class}) public Boolean stockHandle(StockRequestDTO stockRequestDTO){
		// 省略日志打印...校验...前置处理等...
		try{
		Boolean redisLock=redisTemplate
		.opsForValue()
		.setIfAbsent(stockLockKey,true,30,TimeUnit.SECONDS);
		if(redisLock){
		redisTemplate.expire((stockLockKey,1,TimeUnit.SECONDS);
		Object stock=redisTemplate
		.opsForValue()
		.get(stockKeyPrefix.concat(stockRequestDTO.getGoodsId()));
		if(null==stock||Integer.parseInt(stock.toString())<=0){
		// 库存异常
		return false;
		}else{
		// 扣减库存
		stock=Integer.parseInt(stock.toString())-1;
		// 更新数据库、缓存等...
		return true;
		}
		}else{
		return false;
		}
		}finally{
		// 释放锁等等后置处理...
		redisTemplate.delete(stockLockKey);
		}
		}
```

### Semaphore

```java
public Boolean stockHandle(StockRequestDTO stockRequestDTO){
		try{
		semaphore.acquire();
		// 执行业务逻辑...
		}finally{
		semaphore.release();
		}
		}

```