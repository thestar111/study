## Tomcat优化
###### Tomcat的Connector是Tomcat接收HTTP请求的关键模块，我们可以配置它来指定IO模式，以及处理通过这个Connector接受到的请求的处理线程数以及其它一些常用的HTTP策略。其主要配置参数如下：
	--protocol默认值是BlockingIO
	1.protocol="org.apache.coyote.http11.Http11NioProtocol" 指定使用NIO模型来接受HTTP请求
	2.acceptorThreadCount="2"，使用NIO模型是接受的线程数目
	3.配置线程池来处理HTTP请求

## Apr插件提高Tomcat性能
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
