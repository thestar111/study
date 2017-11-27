## Tomcat部署注意事项

##### tomcat部署时，Context指定应用目录根路径时，应用程序被加载两次。

    问题分析：由于Host中指定了应用程序的根目录webapps，且虚拟目录webapps会默认被加载，由于自定义了应用程序的Context的根目录，导致容器又通过Context配置加载了应用一次，这样应用会被加载两次。
    解决方法：
    	1.去掉appBase，置为"", 修改Context中的docBase为"webapps/application名称"即可。
    	2.通过修改Host中配置解决，添加deployOnStartup="false" : 意思：部署应用通过Host中appBase加载，默认为true，修改为false后，不通过appBase路径部署应用


###### tomcat配置：

	unpackWARs：解压应用
    autoDeploy：当应用容器在运行中，新的web应用放入到appBase中时，则应该自动部署
    deployOnStartup：表示应该部署通过Host下的appBase路径下的加载应用



## Tomcat日志文件清理

###### 方法一： 手动清除


###### 方法二：修改Catalina.sh脚本


###### 方法三：分割日志工具cronolog


###### 方法四：使用Linux自带的logrotate
[原文连接地址](https://dzone.com/articles/how-rotate-tomcat-catalinaout)

	如何每天或者当它大于5M，自动切换catalina.out 文件
    创建文件
	1. vi /etc/logrotate.d/tomcat
	拷贝一下内容到上面这个文件
	2. /usr/share/apache-tomcat-8/logs/catalina.out {
		copytruncate
		daily
		rotate 7
		compress
		missingok
		size 5M
	}

###### 上面这个文件配置：
	确定 /usr/share/apache-tomcat-8/logs/catalina.out 就是你的tomcat的catalina.out路径
	daily - 每天切换 catalina.out
    rotate - 保持最多7个日志文件
	compress - 对切换出来的日志文件压缩
	size - 如果catalina.out文件大于5M，执行切换操作


工作原理：
	1. 每晚cron后台执行/etc/cron.daily/目录下的任务
	2. 这会触发/etc/cron.daily/logrotate文件，通常这在linux安装的时候包含了。 它会执行命令 /etc/cron.daily/logrotate
	3. /etc/logrotate.conf 这个配置文件包含了所有在 /etc/logrotate.d/ 的脚本。
	4. 这会触发我们之前写的 /etc/logrotate.d/tomcat 文件。


###### 手动运行logrotate

	/usr/sbin/logrotate /etc/logrotate.conf