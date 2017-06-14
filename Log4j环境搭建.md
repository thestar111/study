## Log4j2环境搭建

	1.去官方下载log4j,导入jar包
		log4j-core-xx.jar
		log4j-api-xx.jar
    2.开始使用：
    	如果要在某个类下使用log4j做日志记录：
        private static Logger logger = LogManager.getLogger(MyApp.class.getName());
    3.日志级别
    	log4j规定了默认的几个级别：trace<debug<info<warn<error<fatal等
### 配置文件详解
	例子1：
    <appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level 					%logger{36} - %msg%n"/>
        </Console>
    </appenders>
     <loggers>
        <!--我们只让这个logger输出trace信息，其他的都是error级别-->
        <!--
        additivity开启的话，由于这个logger也是满足root的，所以会被打印两遍。
        不过root logger 的level是error，为什么Bar 里面的trace信息也被打印两遍呢
        -->
        <logger name="cn.lsw.base.log4j2.Hello" level="trace" additivity="false">
            <appender-ref ref="Console"/>
        </logger>
        <root level="error">
            <appender-ref ref="Console"/>
        </root>
    </loggers>
    
先简单介绍一下下面这个配置文件：

　　1）根节点configuration，然后有两个子节点：appenders和loggers（都是复数，意思就是可以定义很多个appender和logger了）（如果想详细的看一下这个xml的结构，可以去jar包下面去找xsd文件和dtd文件）

　　2）appenders：这个下面定义的是各个appender，就是输出了，有好多类别，这里也不多说（容易造成理解和解释上的压力，一开始也未必能听懂，等于白讲），先看这个例子，只有一个Console，这些节点可不是随便命名的，Console就是输出控制台的意思。然后就针对这个输出设置一些属性，这里设置了PatternLayout就是输出格式了，基本上是前面时间，线程，级别，logger名称，log信息等，差不多，可以自己去查他们的语法规则。

　　3）loggers下面会定义许多个logger，这些logger通过name进行区分，来对不同的logger配置不同的输出，方法是通过引用上面定义的logger，注意，appender-ref引用的值是上面每个appender的name，而不是节点名称。
  
	 例子2：
     <configuration status="error">
    <!--先定义所有的appender-->
    <appenders>
        <!--这个输出控制台的配置-->
        <Console name="Console" target="SYSTEM_OUT">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="trace" onMatch="ACCEPT" onMismatch="DENY"/>
            <!--这个都知道是输出日志的格式-->
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </Console>
        <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用-->
        <File name="log" fileName="log/test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>

        <!--这个会打印出所有的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFile" fileName="logs/app.log"
                     filePattern="log/$${date:yyyy-MM}/app-%d{MM-dd-yyyy}-%i.log.gz">
            <PatternLayout pattern="%d{yyyy-MM-dd 'at' HH:mm:ss z} %-5level %class{36} %L %M - %msg%xEx%n"/>
            <SizeBasedTriggeringPolicy size="50MB"/>
        </RollingFile>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <!--建立一个默认的root的logger-->
        <root level="trace">
            <appender-ref ref="RollingFile"/>
            <appender-ref ref="Console"/>
        </root>

    </loggers>
	</configuration>
