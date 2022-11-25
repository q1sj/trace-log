# 日志追踪
开箱即用 0配置
```text
<dependency>
  <groupId>io.github.q1sj</groupId>
  <artifactId>trace-log</artifactId>
  <version>1.0.5-RELEASE</version>
</dependency>
```
v1.0.5 支持阿里ttl  
https://github.com/alibaba/transmittable-thread-local  
启动时添加jvm参数
```text
-javaagent:/xxx/transmittable-thread-local-2.14.2.jar
```
具体使用查看官方文档:https://github.com/alibaba/transmittable-thread-local#23-%E4%BD%BF%E7%94%A8java-agent%E6%9D%A5%E4%BF%AE%E9%A5%B0jdk%E7%BA%BF%E7%A8%8B%E6%B1%A0%E5%AE%9E%E7%8E%B0%E7%B1%BB

本项目使用效果:同一次请求所有日志输出相同的traceId
```text
2022-11-25 16:12:04.253  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [           main]
2022-11-25 16:12:04.255  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [       Thread-6]
2022-11-25 16:12:04.255  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [pool-1-thread-1]
2022-11-25 16:12:04.256  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [pool-1-thread-1]
2022-11-25 16:12:04.275  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [           main]
2022-11-25 16:12:04.275  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [onPool-worker-2]
2022-11-25 16:12:04.283  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [nPool-worker-11]
2022-11-25 16:12:04.283  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [           main]
2022-11-25 16:12:04.282  INFO [2a56e92e944c4b159a1e07353b7f7a1a] 29420 --- [onPool-worker-9]
2022-11-25 16:12:06.283  INFO [cdc9e63009ed44339758602c78ca10ee] 29420 --- [           main]
2022-11-25 16:12:06.284  INFO [cdc9e63009ed44339758602c78ca10ee] 29420 --- [nPool-worker-11]
2022-11-25 16:12:06.284  INFO [cdc9e63009ed44339758602c78ca10ee] 29420 --- [onPool-worker-2]
2022-11-25 16:12:06.284  INFO [cdc9e63009ed44339758602c78ca10ee] 29420 --- [       Thread-7]
2022-11-25 16:12:06.284  INFO [cdc9e63009ed44339758602c78ca10ee] 29420 --- [onPool-worker-9]
```
