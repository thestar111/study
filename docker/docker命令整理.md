## Docker
	docker run --detach \
        --hostname gitlab.test.com \
        --publish 443:443 --publish 80:80 --publish 25:25 \
        --name git \
        --restart always \
        --volume /srv/gitlab/config:/etc/gitlab \
        --volume /srv/gitlab/logs:/var/log/gitlab \
        --volume /srv/gitlab/data:/var/opt/gitlab \
        gitlab/gitlab-ce:latest

###### 解释：
    publish https --publish http --publish ssh \

###### 删除容器
    docker rmi containerid




###### 进入容器(并以一个伪终端打开)
	docker exec -it bash


###### 查看应用装在哪里？
	which nginx

###### 查看容器信息：
	docker inspect container_id

###### 查看容器挂载卷对应目录
	docker inspect 挂载卷目录


###### 通过容器连接到redis中
	docker run --rm -it --link 当前容器别名:redis container_name /bin/bash

###### 在新建容器中连接redis
	redis-cli -h redis -p 6379

    $ docker run -d --name node1 -p 7001:6379 dockerfile/redis
    $ docker run -d --name node2 -p 7002:6379 dockerfile/redis
    $ docker run -d --name node3 -p 7003:6379 dockerfile/redis



## docker安装：

###### 安装前检查：
	a.内核版本：uname -a
	b.存储驱动Device Mapper:ls -l /sys/class/misc/device-mapper
###### 1.安装ubuntu维护的版本
	a.sudo apt-get install docker.io
	b.source /etc/bash_completion.d/docker.io
	c.sudo docker.io version
###### 2.安装docker维护的版本：
	a.检查APT的HTTPS支持 查看/use/lib/apt/methods/https文件是否存在
	  如果不存在，运行安装命令
	  apt-get update
	  apt-get install -y apt-transport-https
	b.添加docker的APT仓库
	  echo deb https://get.docker.com/ubuntu docker main > /etc/apt/sources.list.d/docker.list
	c.添加仓库的key
	  apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
	d.安装
	  apt-get update
	  apt-get install -y lxc-docker
	or:
		whereis curl
		a.sudo apt-get install -y curl
	    b.curl -sSL https://get.docker.com/ubuntu/|sudo sh
		c.docker version
	sudo docker run ubuntu echo 'hello world'

###### 使用非root用户：
	1.sudo groupadd docker
	2.sudo gpasswd -a wangs docker
	3.sudo service docker restart

###### 启动交互式容器：
	docker run -i -t ubuntu /bin/bash
	-i --iinteractive=true | fase 默认是false(告诉docker容器始终打开标准输入)
	-t --tty=true|false默认是false（告诉docker要为创建的容器分配一个伪tty终端）

###### 查看容器：
	docker ps [-a][-l] (-a列出所有的容器，-l列出最新创建的容器)
	docker inspect [id|name]
	docker run --name=container01 -i -t ubuntu /bin/bash

###### 重新启动停止的容器
	docker start [-i] 容器名

###### 删除停止的容器
	docker rm 容器名

###### 以守护形式运行容器：
	docker run -i -t ubuntu /bin/bash
	Ctrl+P Ctrl+Q退出交互式容器的bash,让容器在后台运行

###### 附加到运行中的容器：
	docker attach 容器名

###### 启动守护式容器：
	docker run -d 镜像名[COMMAND][ARG...]
	docker run --name dk1 -d ubuntu /bin/bash -c "while true;do echo hello world;sleep 1;done"

###### 查看容器日志：
	docker logs [-f][-t][-tail] 容器名
		-f --follows=true|false 默认为false（一直跟踪打印log）
		-t --timestamps=true|false 默认为false(显示时间戳)
		--tail="all"（返回结尾处多少数量的log）

###### 查看容器内进程
	docker top 容器名

###### 在运行中的容器内启动新进程
	docker exec [-d][-i][-t] 容器名 [COMMAND][ARG]

###### 停止守护式容器
	docker stop 容器名
	docker kill 容器名

###### 设置容器的端口映射
	run [-P][-p]
        -P,--publish-all=true|false默认为false(为容器暴漏的所有端口进行映射)
            docker run -P -i -t ubuntu /bin/bash
        -p,--publish=[]
            containerPort
                docker run -p 80 -i -t ubuntu /bin/bash
            hostPort:containerPort
                docker run -p 8080:80 -i -t ubuntu /bin/bash
            ip::containerPort
                docker run -p 0.0.0.0:80 -i -t ubuntu /bin/bash
            ip:hostProt:containerPort
                docker run -p 0.0.0.0:8080:80 -i -t ubuntu /bin/bash
## Nginx部署流程

######  创建映射80端口的交互式容器
###### 	安装Nginx
###### 	安装文本编辑器vim
###### 	创建静态页面
###### 	修改Nginx配置文件
###### 	运行Nginx

	docker run -p 80 --name web -i -t ubuntu /bin/bash
	apt-get install -y nginx
	apt-get install -y vim
	mkdir -p /var/www/html
	cd /var/www/html
	vim index.html
	whereis nginx
	ls /etc/nginx
	ls /etc/nginx/sites-enabled
	vim /etc/nginx/sites-enabled/default
	将root的值修改为/var/www/html;
	cd /
	nginx
	ps -ef
	Ctrl+P+Q
	docker port web
	curl http://127.0.0.1:49167
###### Docker Image镜像
	容器的基石
	层叠的只读文件系统
	联合加载（union mount）
###### Docker Image存储位置
	/var/lib/docker
	docker info 查看docker存储驱动和存储位置

###### 列出镜像
	docker images [OPTIONS][REPOSITORY]
		-a,--all=false 显示所有镜像
		-f,--filter=[] 显示时的过滤条件
		--no-trunc=false 不使用截断的形式来显示数据
		-q,--quiet=false 只显示镜像的唯一id
	docker images 显示在docker中已经安装的镜像
	REPOSITORY 仓库，一系列镜像的集合

###### 查看镜像的详细信息
	docker inspect[OPTIONS]CONTAINER|IMAGE[CONTAINER|IMAGE...]
		-f,--format=""

###### 删除镜像
	docker rmi [OPTIONS]IMAGE[IMAGE...]
		-f,--force=false Force removal of th image强制删除镜像
		--no-prune=false Do not delete untagged parents 保留被删镜像中被打标签的父镜像
	docker rmi ${docker images -q ubuntu}

###### 查找镜像
	Docker Hub
		https://registry.hub.docker.com
	docker search[OPTIONS] TERM
		--automated=false Only show automated builds 显示自动化构建出的镜像
		--no-trunc=false Dont`t truncate output
		-s,--stars=0 Only displays with at least x stars
		最多返回25个结果

###### 拉取镜像
	docker pull [OPTIONS] NAME [:TAG]
		-a,--all-tags=false Download all tagged images in the repository
	docker images -a
	docker pull ubuntu:14.04
	使用--registry-mirror选项
		1.修改：/etc/default/docker
		2.添加：DOCKER_OPTS="--REGISTRY-MIRROR=HTTP://MIRROR-ADDR"
	https://www.daocloud.io
	从daocloud生成地址
	vim /etc/default/docker
	service docker restart重新启动docker守护进程
	ps -ef | grep docker

###### 推送镜像
	docker push NAME[:TAG]

###### 构建镜像
###### docker commit 通过容器构建
    docker commit [OPTIONS]CONTAINER [REPOSITORY[:TAG]
        -a,--author="" Author
            e.g.,"John Hannibal Smith hannibal@a-team.com"
        -m,--message="" Commit message
        -p,--pause=true Pause container during commit (commit时会暂停容器，-p可以用来只是commit命令不暂停容器)
     docker run -it -p 80 --name commit_test ubuntu /bin/bash
     apt-get update
     apt-get nginx
     exit
     docker commit -a 'Tony' -m 'nginx' commit_test dormancypress/commit_test1
     docker run -d --name nginx_web1 -p 80 dormancypress/commit_test1 nginx -g "daemon off;"(-g告诉nginx以前台的方式运行，否则运行结束后容器也会停止)
     docker ps
     curl http://127.0.0.1:29992
######	docker build 通过Dockerfile文件构建
######	1.创建Dockerfile
######	2.使用 docker build 命令

######	创建第一个Dockerfile(包含了一系列命令的文本文件)
    #First Dockerfile
    FROM ubuntu:14.04 MAINTAINER dormancypress "dormancypress@outlook.com"
    RUN apt-get updateRUN apt-get update
    RUN apt-get install -y nginx
    EXPOSE 80
    mkdir -p dockerfile/df_test1
    cd dockerfile/df_test1
    vim Dockerfile
######	docker build [OPTIONS] PATH | URL | -
    --force-rm=false
    --no-cache=false
    --pull=false
    -q,--quiet=false
    --rm=true
    -t,--tag=""
	docker build -t='dormancypress/df_test1' .

###### Remote API
	Docker官方的Remote API Reference:
		https://docs.docker.com/reference/api/docker_remote_api/

###### Docker的客户端与守护进程
	连接方式
    1.unix:///var/run/docker.sock
    2.tcp://host:port
    3.fd://socketfd
###### 使用nc命令，实现socket的连接
	nc -U /var/run/docker.sock(-U指定使用的是socket)
		GET /info HTTP/1.1 通过socket发出一个http指令，用来查看socket的一个接口

###### Docker守护进程的配置和操作

###### 查看守护进程
    ps -ef | grep docker
    status docker

###### 	使用service 命令管理
    service docker start
    service docker stop
    service docker restart

###### 	Docker的启动选项
###### 	docker -d [OPTIONS] (-d代表以守护形式启动)
######	运行相关：
    -D,--debug=false
    -e,--exec-driver="native"
    -g,--graph="/var/lib/docker"
    --icc=true
    -l,--log-level="info"
    --label=[]
    -p,--pidfile="/var/run/docker.pid"

######			Docker服务器连接相关：
    -G,--group="docker"
    -H,--host=[]
    --tls=false
    --tlscacert="/home/sven/.docker/ca.pem"
    --tlscert="/home/sven/.docker/cert.pem"
    --tlskey="/home/sven/.docker/key.pem"
    --tlsverify=false

######			RemoteAPI相关：
    --api-enable-cors=false

###### 存储相关：
    -s,--storage-driver=""
    --selinux-enabled=false
    --storage-opt=[]

######			Registry相关：
    --insecure-registry=[]
    --registry-mirror=[]

######			网络设置相关：
    -b，--bridge=""
    --bip=""
    --fixed-cidr=""
    --fixed-cidr-v6=""
    --dns=[]
    --dns-search=[]
    --ip=0.0.0.0
    --ip-forward=true
    --ip-masq=true
    --iptables=true
    --ipv6=false
    --mtu=0
    Docker Command Line Reference
        https://docs.docker.com/reference/commandline/cli

######		启动配置文件
    /etc/default/docker
    vim /etc/default/docker
    DOCKER_OPTS="--label name:docker_server_1"
    service docker restart
    ps -ef | grep docker
    docker info
