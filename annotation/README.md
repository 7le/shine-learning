# spring-boot-shine

> spring-boot

### spring-boot 热部署
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>springloaded</artifactId>
        <version>1.2.5.RELEASE</version>
    </dependency>
</dependencies>
```
```
  添加以后，通过mvn spring-boot:run启动就支持热部署了。
　注意：使用热部署的时候，需要IDE编译类后才能生效，你可以打开自动编译功能，这样在你保存修改的时候，类就自动重新加载了。
　通过在IDEA下面的终端中运行mvn spring-boot:run命令
```

### application.properties

在resources 创建application.properties，内容如下：
```
server.port=9090

logging.level.shine.mybatis=TRACE

#druid
druid.url=jdbc:mysql://your_IP:3306/shine?useUnicode=true&characterEncoding=utf-8&useSSL=false
druid.driver-class=com.mysql.jdbc.Driver
druid.username=your_username
druid.password=your_password
druid.initial-size=1
druid.min-idle=1
druid.max-active=20
druid.test-on-borrow=true

#freemarker
spring.mvc.view.prefix=/templates/
spring.mvc.view.suffix=.ftl
spring.freemarker.cache=false
spring.freemarker.request-context-attribute=request

#pagehelper
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
```
