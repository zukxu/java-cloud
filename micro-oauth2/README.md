# RSA加密

在JDK的bin目录下cmd，运行如下命令

```java
keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
```

其运行时输入的key值就是接口所用到的client_secret的值