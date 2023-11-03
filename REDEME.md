# 微服务框架

JDK19+SpringBoot3+SpringCloudAlibaba+Mybatis-flex

```text
├─cloud-api     #供远程调用的API            
├─cloud-auth    #认证服务
├─cloud-common  #基础通用模块及其包结构
│  └─common-core                    // 核心模块
│  └─common-knife4j                 // 接口文档自动生成模块
│  └─common-redis                   // Redis模块
│  └─……
│  └─src        #各模块目录结构
│      └─main
│          └─java
│              └─com
│                  └─zukxu
│                      └─common
│                          ├─common
│                          │  ├─anno
│                          │  ├─domain
│                          │  │  ├─dto
│                          │  │  ├─entity
│                          │  │  ├─form
│                          │  │  └─vo
│                          │  ├─enums
│                          │  └─exception
│                          ├─config
│                          ├─constant
│                          ├─handler
│                          ├─listener
│                          ├─module
│                          ├─third
│                          └─utils
├─cloud-gateway     #网关服务
├─cloud-modules     #微服务模块
│  └── cloud-admin                              // 统一用户微服务 [9000]
│  └── cloud-cms                                // 内容管理微服务 [9001]
│  └── cloud-crawler                            // 爬虫微服务 [9002]
│  └── ……

```

将各个外部组件都按照额外依赖的方式进行扩充，方便后续的依赖管理