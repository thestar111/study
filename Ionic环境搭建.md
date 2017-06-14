##Ionic Android开发环境搭建
	#Ionic简单介绍：
     	Ionic是一款基于PhoneGap及AngularJS开发Hybrid/Web APP的前端框架，类似的其他框架有Intel XDK等。
        简单来说就是可以将你的Web应用打包发布成IOS/Android APP，并且提供了PhoneGap之外很多强大的服务和新的特性。
        听起来还是很诱人的，事实上这也是目前最火的一中Hybrid APP开发方式。接下来介绍如何在Windows下搭建Ionic开发环境。
        在开始之前我假设你已经了解了如下概念。
                Java JDK 
                Apache Ant
                Android SDK
                NodeJS
        #以上名词这里就不赘述，如果有不清楚的可以自行查阅#
####（二）安装步骤：
	安装JDK
    安装Apache Ant打包工具
    安装Android SDK环境：添加两个路径到系统环境path路径下
    	 C:\Program Files (x86)\Android\android-sdk\tools;
         C:\Program Files (x86)\Android\android-sdk\platform-tools;
    安装nodejs
    进入cmd命令窗口下，输入如下指令：
    ->npm install -g cordova
    ->cordova -v
    ->npm install -g ionic
    ->ionic start myApp
    ->cd myApp
    #添加android sdk
    ->ionic platform add android
    #打包生成APP
    ->ionic build android
    #运行APP
    ->ionic run android