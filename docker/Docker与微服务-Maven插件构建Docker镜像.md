## Docker与微服务-使用Maven插件构建Docker镜像

## 1. Docker与微服务-使用Maven插件构建Docker镜像




## 2.工具

插件名称             |               官方地址
--------------------|-------------------------------------------------
docker-maven-plugin | https://github.com/spotify/docker-maven-plugin
docker-maven-plugin	| https://github.com/fabric8io/docker-maven-plugin
docker-maven-plugin | https://github.com/bibryam/docker-maven-plugin

## 3.使用插件构建Docker镜像

## 4.简单使用

###### 使用springbootdemo构建

###### 4.1 pom.xml添加以下配置
	<build>
        <plugins>
            <!-- docker的maven插件，官网：https://github.com/spotify/docker-maven-plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
                    <!-- 注意imageName一定要是符合正则[a-z0-9-_.]的，否则构建不会成功 -->
                    <!-- 详见：https://github.com/spotify/docker-maven-plugin    Invalid repository name ... only [a-z0-9-_.] are allowed-->
                    <imageName>zhouping/springbootdemo</imageName>
                    <baseImage>java</baseImage>
                    <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>


##### 4.2 执行命令
	mvn clean package docker:build
    docker images --显示镜像列表

###### 启动镜像
	docker run -p 8761:8761 microservice-discovery-eureka


## 5.使用Dockerfile进行构建


###### 5.1 新建Dockerfile文件
	FROM registry.cn-hangzhou.aliyuncs.com/acs/maven:3-jdk-8

    MAINTAINER zhouping "zhouping19911013@163.com"
    MAINTAINER version "online"

    EXPOSE 8089

    VOLUME /tmp
    ADD springboot.jar app.jar
    ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


###### 5.2 说明
	FROM registry.cn-hangzhou.aliyuncs.com/acs/maven:3-jdk-8
    由于下载国外镜像很慢，添加阿里镜像地址


###### 5.3 修改pom.xml
	<build>
        <plugins>
            <!-- docker的maven插件，官网：https://github.com/spotify/docker-maven-plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.13</version>
                <configuration>
                    <!-- 注意imageName一定要是符合正则[a-z0-9-_.]的，否则构建不会成功 -->
                    <!-- 详见：https://github.com/spotify/docker-maven-plugin    Invalid repository name ... only [a-z0-9-_.] are allowed-->
                    <imageName>zhouping/springbootdemo</imageName>
                    <!-- 指定Dockerfile所在的路径 -->
                    <dockerDirectory>${project.basedir}/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
        </plugins>
    </build>

## 6.将Docker镜像push到DockerHub上

###### 首先修改Maven的全局配置文件settings.xml，添加以下段落
	<servers>
      <server>
        <id>docker-hub</id>
        <username>你的DockerHub用户名</username>
        <password>你的DockerHub密码</password>
        <configuration>
          <email>你的DockerHub邮箱</email>
        </configuration>
      </server>
    </servers>


###### 项目pom.xml修改为如下：注意imageName的路径要和repo的路径一致

	<build>
        <plugins>
            <!-- docker的maven插件，官网：https://github.com/spotify/docker-maven-plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>0.4.12</version>
                <configuration>
                    <!-- 注意imageName一定要是符合正则[a-z0-9-_.]的，否则构建不会成功 -->
                    <!-- 详见：https://github.com/spotify/docker-maven-plugin Invalid repository
                        name ... only [a-z0-9-_.] are allowed -->
                    <!-- 如果要将docker镜像push到DockerHub上去的话，这边的路径要和repo路径一致 -->
                    <imageName>zping/test</imageName>
                    <!-- 指定Dockerfile所在的路径 -->
                    <dockerDirectory>${project.basedir}/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                    <!-- 以下两行是为了docker push到DockerHub使用的。 -->
                    <serverId>docker-hub</serverId>
                    <registryUrl>https://index.docker.io/v1/</registryUrl>
                </configuration>
            </plugin>
        </plugins>
    </build>

###### 执行命令
	mvn clean package docker:build  -DpushImage



## 7.将镜像push到私有仓库

###### 修改maven全局配置文件settings.xml
	<servers>
      <server>
        <id>docker-registry</id>
        <username>你的DockerHub用户名</username>
        <password>你的DockerHub密码</password>
        <configuration>
          <email>你的DockerHub邮箱</email>
        </configuration>
      </server>
    </servers>

###### 修改pom文件
	<plugin>
      <groupId>com.spotify</groupId>
      <artifactId>docker-maven-plugin</artifactId>
      <version>0.4.12</version>
      <configuration>
        <!-- 路径为：私有仓库地址/你想要的镜像路径 -->
        <imageName>zhouping/springbootdemo</imageName>
        <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
        <resources>
          <resource>
            <targetPath>/</targetPath>
            <directory>${project.build.directory}</directory>
            <include>${project.build.finalName}.jar</include>
          </resource>
        </resources>
        <!-- 与maven配置文件settings.xml一致 -->
        <serverId>docker-registry</serverId>
      </configuration>
    </plugin>

###### 执行
	mvn clean package docker:build  -DpushImage


###### 如果想要从私服上下载该镜像，执行：
	docker login reg.itmuch.com  # 然后输入账号和密码
	docker pull reg.itmuch.com/test-pull-registry

###### 8.将插件绑定在某个phase执行

		只需要将插件的goal 绑定在某个phase即可,所谓的phase和goal，可以这样理解：maven命令格式是：mvn phase:goal ，例如mvn package docker:build 那么，package 和 docker 都是phase，build 则是goal

###### 修改pom.xml文件
     <build>
        <plugins>
          <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>docker-maven-plugin</artifactId>
            <executions>
              <execution>
                <id>build-image</id>
                <phase>package</phase>
                <goals>
                  <goal>build</goal>
                </goals>
              </execution>
            </executions>
            <configuration>
              <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
              <baseImage>java</baseImage>
              <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
              <resources>
                <resource>
                  <targetPath>/</targetPath>
                  <directory>${project.build.directory}</directory>
                  <include>${project.build.finalName}.jar</include>
                </resource>
              </resources>
            </configuration>
          </plugin>
        </plugins>
      </build>

###### 如上，我们只需添加
	<executions>
      <execution>
        <id>build-image</id>
        <phase>package</phase>
        <goals>
          <goal>build</goal>
        </goals>
      </execution>
    </executions>



## 9.常见异常

###### 连接不上2375一般在Win7上出现
    Connect to localhost:2375 [localhost/127.0.0.1, localhost/0:0:0:0:0:0:0:1] failed: Connection refused: connect -> [Help 1]

###### 解决步骤：
###### docker-machine env
	$Env:DOCKER_TLS_VERIFY = "1"
    $Env:DOCKER_HOST = "tcp://192.168.99.100:2376"
    $Env:DOCKER_CERT_PATH = "C:\Users\Administrator\.docker\machine\machines\default

###### pom.xml文件中为插件添加配置
	<!-- 解决Connect to localhost:2375的问题的其中一种方式，注意要跟docker-machine env相一致 -->
	<dockerHost>https://192.168.99.100:2376</dockerHost>
    <dockerCertPath>C:\Users\Administrator\.docker\machine\machines\default</dockerCertPath>

###### 修改后pom.xml
	<plugin>
        <groupId>com.spotify</groupId>
        <artifactId>docker-maven-plugin</artifactId>
        <version>0.4.12</version>
        <configuration>
            <imageName>eacdy/test</imageName>
            <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>

            <!-- 解决Connect to localhost:2375的问题的其中一种方式，注意要跟docker-machine env相一致 -->
            <dockerHost>https://192.168.99.100:2376</dockerHost>
            <dockerCertPath>C:\Users\Administrator\.docker\machine\machines\default</dockerCertPath>
            <resources>
                <resource>
                    <targetPath>/</targetPath>
                    <directory>${project.build.directory}</directory>
                    <include>${project.build.finalName}.jar</include>
                </resource>
            </resources>
            <!-- 以下两行是为了docker push到DockerHub使用的。 -->
            <serverId>docker-hub</serverId>
            <registryUrl>https://index.docker.io/v1/</registryUrl>
        </configuration>
    </plugin>


## 11.TIPS

###### 1. imageName必须符合正则[a-z0-9-_.]，否则将会构建失败
###### 2. 插件默认使用localhost:2375去连接Docker，如果你的Docker端口不是2375，需要配置环境变量DOCKER_HOST=tcp://<host>:2375

## 12.代码地址任选其一

###### https://github.com/thestar111/springbootdemo.git

## 13.参考文档

###### 1. http://developer.51cto.com/art/201404/434879.html
###### 2. https://linux.cn/article-6131-rss.html

## 14. 国内镜像仓库加速地址
###### 1. https://eo17c4cg.mirror.aliyuncs.com
###### 2. http://ab971fb4.m.daocloud.io



## Docker Toolbox

	docker-machine ssh default
    sudo sed -i "s|EXTRA_ARGS='|EXTRA_ARGS='--registry-mirror=加速地址 |g" /var/lib/boot2docker/profile
    exit
    docker-machine restart default

## Docker for Windows
	在桌面右下角状态栏中右键 docker 图标，修改在 Docker Daemon 标签页中的 json ，把 加速地址(在加速器页面获取)加到"registry-mirrors"的数组里。点击 Apply 使设置生效

















