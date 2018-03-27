## TOMCAT安全配置并并发优化
###### Tomcat的Connector是Tomcat接收HTTP请求的关键模块，我们可以配置它来指定IO模式，以及处理通过这个Connector接受到的请求的处理线程数以及其它一些常用的HTTP策略。其主要配置参数如下：
	--protocol默认值是BlockingIO
	1.protocol="org.apache.coyote.http11.Http11NioProtocol" 指定使用NIO模型来接受HTTP请求
	2.acceptorThreadCount="2"，使用NIO模型是接受的线程数目
	3.配置线程池来处理HTTP请求

## Apr插件提高Tomcat性能

###### Tomcat 通过启用APR插件的运行模式，通过系统级别的IO方式来提高tomcat的请求和响应时间

	  (1)安装APR tomcat-native插件
        apr-1.3.8.tar.gz   安装在/usr/local/apr
        #tar zxvf apr-1.3.8.tar.gz
        #cd apr-1.3.8
        #./configure;make;make install

        apr-util-1.3.9.tar.gz  安装在/usr/local/apr/lib
        #tar zxvf apr-util-1.3.9.tar.gz
        #cd apr-util-1.3.9
        #./configure --with-apr=/usr/local/apr ----with-java-home=JDK;make;make install

        #cd apache-tomcat-6.0.20/bin
        #tar zxvf tomcat-native.tar.gz
        #cd tomcat-native/jni/native
        #./configure --with-apr=/usr/local/apr;make;make install

 	 (2)设置 Tomcat 整合 APR
         修改 tomcat 的启动 shell （startup.sh），在该文件中加入启动参数：
         CATALINA_OPTS="$CATALINA_OPTS -Djava.library.path=/usr/local/apr/lib" 。

  	(3)判断安装成功:
         如果看到下面的启动日志，表示成功。
         2007-4-26 15:34:32 org.apache.coyote.http11.Http11AprProtocol init

	[APR 安装指南](https://www.renwole.com/archives/361)

## **TOMCAT配置优化事项**

###### 1.Tomcat内存优化

	Tomcat内存优化主要是对 tomcat 启动参数优化，我们可以在 tomcat 的启动脚本 catalina.sh 中设置 java_OPTS 参数。
	JAVA_OPTS参数说明
	-server 启用jdk 的 server 版；
	-Xms java虚拟机初始化时的最小内存；
	-Xmx java虚拟机可使用的最大内存；
	-XX: PermSize 内存永久保留区域
	-XX:MaxPermSize 内存最大永久保留区域
	服务器参数配置
	现公司服务器内存一般都可以加到最大2G ，所以可以采取以下配置：
	JAVA_OPTS=’-Xms1024m -Xmx2048m -XX: PermSize=256M -XX:MaxNewSize=256m -XX:MaxPermSize=256m’
	配置完成后可重启Tomcat ，通过以下命令进行查看配置是否生效：
	首先查看Tomcat 进程号：
	sudo lsof -i:28082
	我们可以看到Tomcat 进程号是 12222 。
	　　查看是否配置生效：
	sudo jmap – heap 12222
	　　我们可以看到MaxHeapSize 等参数已经生效





###### **2.Tomcat并发优化（Tomcat连接相关参数）**
	在Tomcat 配置文件 server.xml 中的
    <Connector port="28082"
	 protocol="HTTP/1.1"
	 maxHttpHeaderSize="8192"
	 minProcessors="100"
	 maxProcessors="1000"
	 acceptCount="1000"
	 redirectPort="8443"
	 disableUploadTimeout="true"/>


###### **参数说明**
	maxThreads 客户请求最大线程数
	minSpareThreads Tomcat初始化时创建的 socket 线程数
	maxSpareThreads Tomcat连接器的最大空闲 socket 线程数
	enableLookups 若设为true, 则支持域名解析，可把 ip 地址解析为主机名
	redirectPort 在需要基于安全通道的场合，把客户请求转发到基于SSL 的 redirectPort 端口
	acceptAccount 监听端口队列最大数，满了之后客户请求会被拒绝（不能小于maxSpareThreads ）
	connectionTimeout 连接超时
	minProcessors 服务器创建时的最小处理线程数
	maxProcessors 服务器同时最大处理线程数
	URIEncoding URL统一编码


###### **配置实例**

	<Connector port="20808"
	 protocol="HTTP/1.1"
	 maxHttpHeaderSize="8192"
	 maxThreads="1000"
	 minSpareThreads="100"
	 maxSpareThreads="1000"
	 minProcessors="100"
	 maxProcessors="1000"
	 enableLookups="false"
	 URIEncoding="utf-8"
	 acceptCount="1000"
	 redirectPort="8443"
	 disableUploadTimeout="true"/>

###### 3.**Tomcat缓存优化**

###### **参数说明**
	compression 打开压缩功能
	compressionMinSize 启用压缩的输出内容大小，这里面默认为2KB
	compressableMimeType 压缩类型
	connectionTimeout 定义建立客户连接超时的时间. 如果为 -1, 表示不限制建立客户连接的时间

###### **配置实例**

	<Connector port="28082"
	 protocol="HTTP/1.1"
	 maxHttpHeaderSize="8192"
	 maxThreads="1000"
	 minSpareThreads="100"
	 maxSpareThreads="1000"
	 minProcessors="100"
	 maxProcessors="1000"
	 enableLookups="false"
	 compression="on"
	 compressionMinSize="2048"
	 compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain"
	 connectionTimeout="20000"
 	 URIEncoding="utf-8"
	 acceptCount="1000"
	 redirectPort="8443"
	 disableUploadTimeout="true"/>

###### **4.参考配置**

###### ** 参考网络对服务器做过如下配置，拿出来分享下：**
	<Connector port="28082"
	 protocol="HTTP/1.1"
	 maxHttpHeaderSize="8192"
 	 maxThreads="1000"
     minSpareThreads="25"
     maxSpareThreads="75"
     enableLookups="false"
     compression="on"
     compressionMinSize="2048"
     compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain"
     connectionTimeout="20000"
     URIEncoding="utf-8"
     acceptCount="200"
     redirectPort="8443"
     disableUploadTimeout="true" />


###### **优化后配置**
###### **2>更改后的配置**

	<Connector port="28082"
    protocol="HTTP/1.1"
    maxHttpHeaderSize="8192"
    maxThreads="1000"
    minSpareThreads="100"
    maxSpareThreads="1000"
    minProcessors="100"
    maxProcessors="1000"
    enableLookups="false"
    compression="on"
    compressionMinSize="2048"
    compressableMimeType="text/html,text/xml,text/javascript,text/css,text/plain"
    connectionTimeout="20000"
    URIEncoding="utf-8"
    acceptCount="1000"
    redirectPort="8443"
    disableUploadTimeout="true"/>

## Tomcat安全配置

###### **1.禁用8005端口**


	telnet localhost 8005 然后输入 SHUTDOWN 就可以关闭 Tomcat，为了安全我们要禁用该功能
	默认值:
	<Server port="8005" shutdown="SHUTDOWN">
	修改为:
	<Server port="-1" shutdown="SHUTDOWN">


###### **2.应用程序安全&关闭自动部署**


	默认值:

	<Host name="localhost" appBase="webapps"
	 	unpackWARs="true" autoDeploy="true">
	修改为:

	<Host name="localhost" appBase="webapps"
	 	unpackWARs="false" autoDeploy="false" reloadable="false">

###### 3.maxThreads 连接数限制修改配置

	默认值:
	<!--
	 <Executor name="tomcatThreadPool" namePrefix="catalina-exec-"
	 maxThreads="150" minSpareThreads="4"/>
	 -->
	修改为:

	<Executor
	  name="tomcatThreadPool"
	  namePrefix="catalina-exec-"
	  maxThreads="500"
	  minSpareThreads="30"
      maxIdleTime="60000"
      prestartminSpareThreads = "true"
	  maxQueueSize = "100"
	/>

##### 参数解释：

	maxThreads：最大并发数，默认设置 200，一般建议在 500 ~ 800，根据硬件设施和业务来判断
	minSpareThreads：Tomcat 初始化时创建的线程数，默认设置 25
	maxIdleTime：如果当前线程大于初始化线程，那空闲线程存活的时间，单位毫秒，默认60000=60秒=1分钟。
	prestartminSpareThreads：在 Tomcat 初始化的时候就初始化 minSpareThreads 的参数值，如果不等于 true，minSpareThreads 的值就没啥效果了
	maxQueueSize：最大的等待队列数，超过则拒绝请求


###### 4.Connector 参数优化配置

	默认值:
	<Connector
	 port="8080"
	 protocol="HTTP/1.1"
	 connectionTimeout="20000"
	 redirectPort="8443"
	 />
	修改为:

	<Connector
	 executor="tomcatThreadPool"
	 port="8080"
	 protocol="org.apache.coyote.http11.Http11Nio2Protocol"
	 connectionTimeout="60000"
	 maxConnections="10000"
	 redirectPort="8443"
	 enableLookups="false"
	 acceptCount="100"
	 maxPostSize="10485760"
	 maxHttpHeaderSize="8192"
	 compression="on"
	 disableUploadTimeout="true"
	 compressionMinSize="2048"
	 acceptorThreadCount="2"
	 compressableMimeType="text/html,text/plain,text/css,application/javascript,application/json,application/x-font-ttf,application/x-font-		otf,image/svg+xml,image/jpeg,image/png,image/gif,audio/mpeg,video/mp4"
	 URIEncoding="utf-8"
	 processorCache="20000"
	 tcpNoDelay="true"
	 connectionLinger="5"
	 server="Server Version 11.0"
	 />

###### 参数解释：

	protocol： Tomcat 8 设置 nio2 更好：org.apache.coyote.http11.Http11Nio2Protocol
	protocol： Tomcat 6 设置 nio 更好：org.apache.coyote.http11.Http11NioProtocol
	protocol： Tomcat 8 设置 APR 性能飞快：org.apache.coyote.http11.Http11AprProtocol 更多详情：[Tomcat 8.5 基于 Apache Portable Runtime（APR）库性能优化](http://www.renwole.com/archives/361 "Tomcat 8.5)
	connectionTimeout： Connector接受一个连接后等待的时间(milliseconds)，默认值是60000。
	maxConnections： 这个值表示最多可以有多少个socket连接到tomcat上
	enableLookups ： 禁用DNS查询
	acceptCount ： 当tomcat起动的线程数达到最大时，接受排队的请求个数，默认值为100。
	maxPostSize ： 设置由容器解析的URL参数的最大长度，-1(小于0)为禁用这个属性，默认为2097152(2M) 请注意， FailedRequestFilter 过滤器可以用来拒绝达到了极限值的请求。
	maxHttpHeaderSize ： http请求头信息的最大程度，超过此长度的部分不予处理。一般8K。
	compression ： 是否启用GZIP压缩 on为启用（文本数据压缩） off为不启用， force 压缩所有数据
	disableUploadTimeout ： 这个标志允许servlet容器使用一个不同的,通常长在数据上传连接超时。 如果不指定,这个属性被设置为true,表示禁用该时间超时。
	compressionMinSize ： 当超过最小数据大小才进行压缩
	acceptorThreadCount ： 用于接受连接的线程数量。增加这个值在多CPU的机器上,尽管你永远不会真正需要超过2。 也有很多非维持连接,您可能希望增加这个值。默认值是1。
	compressableMimeType ： 配置想压缩的数据类型
	URIEncoding ： 网站一般采用UTF-8作为默认编码。
	processorCache ： 协议处理器缓存的处理器对象来提高性能。 该设置决定多少这些对象的缓存。-1意味着无限的,默认是200。 如果不使用Servlet 3.0异步处理,默认是使用一样的maxThreads设置。 如果使用Servlet 3.0异步处理,默认是使用大maxThreads和预期的并发请求的最大数量(同步和异步)。
    tcpNoDelay ： 如果设置为true,TCP_NO_DELAY选项将被设置在服务器套接字,而在大多数情况下提高性能。这是默认设置为true。
    connectionLinger ： 秒数在这个连接器将持续使用的套接字时关闭。默认值是 -1,禁用socket 延迟时间。
    server ： 隐藏Tomcat版本信息，首先隐藏HTTP头中的版本信息

###### 5.隐藏或修改 Tomcat 版本号

	 # cd /usr/local/tomcat/lib/
	 # unzip catalina.jar
	 # cd org/apache/catalina/util

	 # vim ServerInfo.properties
	 server.info=Apache Tomcat/8.5.16
	 server.number=8.5.16.0
	 server.built=Jun 21 2017 17:01:09 UTC

###### 6.删除禁用默认管理页面以及相关配置文件

	 # rm -rf /usr/local/apache-tomcat-8.5.16/webapps/*
	 # rm -rf /usr/local/apache-tomcat-8.5.16/conf/tomcat-users.xml

###### 7. 新建${TOMCAT_HOME}/bin/setenv.sh文件，内容如下：

	CATALINA_OPTS="-server -Xmx4G -Xms4G"

