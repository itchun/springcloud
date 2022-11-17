官方文档：https://docs.spring.io/spring-cloud-openfeign/docs
openfegin 在 fegin基础上进行了拓展，支持了springmvc注解 其本质还是restTemplate 当然了在其细纠其就是http了
ribbon（热无哎笨）: 低版本的负载均衡,默认是轮询
Spring Cloud 2020版本以后，默认移除了对Netflix的依赖，其中就包括Ribbon，官方默认推荐使用Spring Cloud Loadbalancer正式替换Ribbon，并成为了Spring Cloud负载均衡器的唯一实现。
spring-cloud-starter-loadbalancer: 高版本的负载均衡,默认是轮询
hystrix（哈啊思吹个思）: 熔断 



