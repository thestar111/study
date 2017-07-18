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