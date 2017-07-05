## 让自己的网站支持HTTPS

## 获取证书

###### 用Java自带的keytools工具生成证书

    keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650

###### 参数说明
	1.-storetype 指定密钥仓库类型
    2.-keyalg 生证书的算法名称，RSA是一种非对称加密算法
    3.-keysize 证书大小
    4.-keystore 生成的证书文件的存储路径
    5.-validity 证书的有效期


## Spring Boot启用https

###### 修改application.properties或者修改application.yml文件

	server.ssl.key-store=keystore.p12
    server.ssl.key-store-password=111111
    server.ssl.keyStoreType=PKCS12
    server.ssl.keyAlias:tomcat


## 将HTTP请求转到HTTPS请求


###### 程序入口类添加Http转换器

	@Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //Connector监听的http的端口号
        connector.setPort(8080);
        connector.setSecure(false);
        //监听到http的端口号后转向到的https的端口号
        connector.setRedirectPort(8443);
        return connector;
    }





























































