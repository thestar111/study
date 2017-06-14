## Spring-Boot学习笔记
#### 1.IDE：Intelij, Eclipse(STS)
#### 2.JDK: 1.7以上
#### 3.build tool：gradle 3 and maven 3
## 新建项目

	1.添加依赖：
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.3.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>
    
    2.自定义获取yml配置文件
    添加注解，指定配置文件中前缀
    @Configuration	#标识改类为配置类,相当于在XML定义，可以在该类下使用@Bean注解
	@ConfigurationProperties(prefix="spring.datasource")	#指定配置文件中的配置项前缀
    




特别注意
【*】SpringBoot启动类(默认扫描自己子目录下的类)一定要放在所有类的父包目录下，这样系统启动的时候才会扫描启动类下的子目录下的Bean，否则系统会启动报错
