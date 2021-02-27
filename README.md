### 一、技术栈

​	后端：`SpringBoot、Mybatis、ZooKeeper、Dubbo、RocketMQ、Redis、seata`

​	前端：`Vue`

### 二、模块简介

1. #### Account 模块

   与账户、登录相关，实现单点登录，只需要一次登录即可访问所有模块

2. #### Commodity 模块

   商品管理模块

3. #### Order 模块

   实现下单操作

### 三、项目描述

​	一个商城项目，功能主要包括登录，商品管理，下单。

​	登录模块通过 Redis 、Cookie 实现，前端配置了有页面白名单，如果用户访问白名单的路由不用登录，访问不是白名单的路由会自动跳转到登录页面，用户输入密码后前端 MD5 加密，传输到后端，后端再进行一次加盐和 MD5 加密再验证数据库，验证成功生成 UUID 码作为token存储在Redis，然后再把这个token存储在主域名 Cookie ，通过Redis + Cookie 实现单点登录。

​	商品管理模块比较简单，管理界面可以增加、删除商品。

​	下单模块，用户在点击购买后，后端先查看 Redis 是否存在对应商品库存，没有就查询数据库，然后再存储到Redis；验证库存是否足够，足够的话通过分布式锁减库存，还进行了幂等性检查等操作，然后将下单信息发送到 RocketMQ 生产者，这里使用的是事务消息，消费者收到后进行下单操作，下单包括三个操作：

​	① Order 模块的订单生成

​	② Commodity 模块的减库存

​	③ Account 模块的支付

​	这几个操作必须要保证一致性，要实现分布式事务，实现分布式事务的方法有多种，这里与支付相关使用 TCC 等保证一致性较好的解决方案实现比较好，我这里使用的是`seata`来实现的分布式事务。

### 四、启动流程

​	需要配置好各个模块里面的Dubbo、seata地址，并启动Zookeeper、RocketMQ、seata

1. AccountService 模块
2. ShoppingAccount 模块
3. CommoditiesService 模块
4. ShoppCommodity 模块
5. OrderService 模块



最后：有问题可以发邮件 zllzsl2020@163.com，喜欢的话给个star~
