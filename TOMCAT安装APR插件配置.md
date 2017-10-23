## TOMCAT安装APR插件配置


#### 介绍

***

	Tomcat可以使用Apache Portable Runtime来提供卓越的性能及可扩展性，更好地与本地服务器技术的集成。Apache Portable Runtime是一个高度可移植的库，位于Apache HTTP Server 2.x的核心。APR有许多用途，包括访问高级IO功能（如sendfile，epoll和OpenSSL），操作系统级功能（随机数生成，系统状态等）以及本地进程处理（共享内存，NT管道和Unix套接字）



***

###### 官方要求：

> APR 1.2+ development headers (libapr1-dev package)
> OpenSSL 1.0.2+ development headers (libssl-dev package)
> JNI headers from Java compatible JDK 1.4+
> GNU development environment (gcc, make)


###### 生产环境:

> CentOS Linux release 7.3.1611 (Core) x86 64
> Server version: Apache Tomcat/8.5.16
> java version “1.8.0_144”


###### 1.安装相关依赖包

> yum -y install gcc gcc-c++ libtool* autoconf automake expat-devel perl perl-devel


###### 2.下载安装包

> wget  http://mirror.bit.edu.cn/apache/apr/apr-1.6.2.tar.gz
> wget  http://mirror.bit.edu.cn/apache/apr/apr-iconv-1.2.1.tar.gz
> wget  http://mirror.bit.edu.cn/apache/apr/apr-util-1.6.0.tar.gz
> wget  https://www.openssl.org/source/openssl-1.1.0f.tar.gz

###### 3.安装APR

>  tar zxvf apr-1.6.2.tar.gz
>  cd apr-1.6.2
>  vim configure
>  	默认值:
> RM='$RM'
> 修改为:
> RM='$RM -f'
> ./configure --prefix=/usr/local/apr
> make && make install

###### 4.安装apr-iconv

> tar zxvf apr-iconv-1.2.1.tar.gz
> cd apr-iconv-1.2.1
> ./configure --prefix=/usr/local/apr-iconv --with-apr=/usr/local/apr
> make && make install

###### 5.安装apr-util

> tar zxvf apr-util-1.6.0.tar.gz
> cd apr-util-1.6.0
> ./configure --prefix=/usr/local/apr-util --with-apr=/usr/local/apr --with-apr-iconv=/usr/local/apr-iconv/bin/apriconv
> make && make install

###### 6.安装OpenSSL

> tar zxvf openssl-1.1.0f.tar.gz
> cd openssl-1.1.0f
> ./config --prefix=/usr/local/openssl
> make -j 4 && make install

###### 7.安装tomcat-native

> cd /usr/local/tomcat/bin/
> tar zxvf tomcat-native.tar.gz
> cd tomcat-native-1.2.12-src/native
> ./configure --with-ssl=/usr/local/openssl --with-apr=/usr/local/apr --with-java-home=/usr/java/jdk1.8.0_144
> make && make install

** 注意:如果以上 configure 失败，可以执行 make distclean 清除 **

***

###### 8.添加变量内容

> vim /etc/profile.d/jdk.sh
> export LD_LIBRARY_PATH=/usr/local/apr/lib:$LD_LIBRARY_PATH
> source /etc/profile.d/jdk.sh

安装完成，此时启动tomcat报错，找不到tomcat下的native包，进入到/usr/local/apr/lib目录下执行

	安装完成后，libtcnative.1.so被放在了/usr/local/apr/，但是Tomcat的java.library.path并不包含这个目录，可以使用ln -s /usr/local/apr/lib/libtcnative-1.so /usr/lib64/libtcnative-1.so命令解决。

> ln -s /usr/local/apr/lib/libtcnative-1.so /usr/lib64/libtcnative-1.so




###### 9.接下来需要修改tomcat配置文件中的APR运行模式，并测试是否安装成功。

> vim /home/gcms/apache-tomcat-8.5.20/conf/server.xml

###### 默认值：

	<Connector port="28089" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />

###### 修改为：

	<Connector port="28089" protocol="org.apache.coyote.http11.Http11AprProtocol" connectionTimeout="20000" redirectPort="8443" />

###### 默认值

	<Connector port="8009" protocol="AJP/1.3" redirectPort="8443" />

###### 修改为：

	<Connector port="8009" protocol="org.apache.coyote.ajp.AjpAprProtocol" redirectPort="8443" />

###### 注意:可以看到红色部分，提示找不到基于APR的Apache Tomcat Native库，因此无法使用APR模式启动。

###### 解决方案：
	cp -R /usr/local/apr/lib/* /usr/lib64
    cp -R /usr/local/apr/lib/* /usr/lib


###### 重启TOMCAT并查看日志

	29-Aug-2017 14:45:19.200 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory 		[/home/gcms/apache-tomcat-8.5.20/webapps/host-manager]
	29-Aug-2017 14:45:19.517 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/host-manager] has finished in [318] ms
	29-Aug-2017 14:45:19.518 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/ROOT]
	29-Aug-2017 14:45:19.532 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/ROOT] has finished in [14] ms
	29-Aug-2017 14:45:19.533 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/docs]
	29-Aug-2017 14:45:19.546 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/docs] has finished in [12] ms
	29-Aug-2017 14:45:19.546 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/examples]
	29-Aug-2017 14:45:19.747 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/examples] has finished in [201] ms
	29-Aug-2017 14:45:19.748 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deploying web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/manager]
	29-Aug-2017 14:45:19.765 INFO [localhost-startStop-1] org.apache.catalina.startup.HostConfig.deployDirectory Deployment of web application directory [/home/gcms/apache-tomcat-8.5.20/webapps/manager] has finished in [17] ms
	29-Aug-2017 14:45:19.768 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["http-apr-28089"]
	29-Aug-2017 14:45:19.776 INFO [main] org.apache.coyote.AbstractProtocol.start Starting ProtocolHandler ["ajp-apr-28009"]
	29-Aug-2017 14:45:19.779 INFO [main] org.apache.catalina.startup.Catalina.start Server startup in 612 ms


**可以看到已经以apr协议模式启动成功，经基于APR的技术web压力测试，Tomcat的性能飙升**

###### 注意
	xml/apr_xml.c:35:19: fatal error: expat.h: 没有那个文件或目录
	compilation terminated.
	/etc/httpd/apr-util-1.6.0/build/rules.mk:206: recipe for target 'xml/apr_xml.lo' failed
	make[1]: * [xml/apr_xml.lo] Error 1
	make[1]: Leaving directory '/etc/httpd/apr-util-1.6.0'
	/etc/httpd/apr-util-1.6.0/build/rules.mk:118: recipe for target 'all-recursive' failed

###### 解决办法就是安装libexpat1-dev
	sudo apt-get install libexpat1-dev














































































