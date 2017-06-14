## Redis集群配置手册
#### 环境搭建

	以root用户登录linux系统，
    a.创建用户组
    	>groupadd -g 3000 gdsdp
    b.创建应用用户redis
    	> useradd -g gdsdp -d home/redis -s /usr/bin/csh -m redis
    c.修改用户密码
    	>passwd redis
#### 获取redis资源文件
	d.	以二进制方式上传redis安装包到redis用户的家目录下/home/redis
	e.	用redis用户登录服务器，解压redis安装包
#### 配置环境变量
	f.配置自家目录下的配置文件.cshrc文件
    	>setenv RUBY_HOME ${HOME}/redis/ruby
		 setenv RUBYLIB
         ${RUBY_HOME}/lib:${RUBY_HOME}/lib/ruby/site_ruby/2.1.0:${RUBY_HOME}/lib/ruby/site_ruby/2.1.0/x86_64-linux:
         ${RUBY_HOME}/lib/ruby/site_ruby:${RUBY_HOME}/lib/ruby/vendor_ruby/2.1.0:${RUBY_HOME}/lib/ruby/vendor_ruby/2.1.0/x86_64-linux:
         ${RUBY_HOME}/lib/ruby/vendor_ruby:${RUBY_HOME}/lib/ruby/2.1.0:${RUBY_HOME}/lib/ruby/2.1.0/x86_64-linux
		 setenv PATH ${RUBY_HOME}/bin:${PATH}
		 setenv PATH ${HOME}/redis:${PATH}
	g.使文件生效
    source .cshrc
#### 配置redis集群
	h.生成redis实例文件
    	>cd $HOME/redis/conf/
		>cp redis.conf.example redis.conf
	i.编辑redis.conf文件修改下面的内容
    	pidfile /home/redis/redis/conf/redis_46001.pid
        port 46001
        logfile "/home/redis/redis/log/46001.log"
		dbfilename dump_46001.rdb
		appendfilename "appendonly_46001.aof"
        #可选,集群需要此配置
		cluster-config-file nodes-46001.conf
        #单位<bytes>
		maxmemory 1024
        #算法可选
		maxmemory-policy allkeys-lru
        #只在备节点中需要加此配置,主机不需要
		slaveof 10.137.38.85  46001

	j.在redis末尾加添加下面内容
    bind 10.137.7.85
#### redis启动
	cd $HOME/redis/bin/ ./redis-server ../conf/redis.conf
#### 集群启动
	cd $HOME/redis/bin/ ./redis-trib.rb create --replicas 1 10.137.38.85:46001 10.137.38.85:46002 10.137.38.85:46003 10.137.39.68:46001 10.137.39.68:46002 10.137.39.68:46003
#### 配置后检查
	k.连接主机，设置数据后，会同步到指定的备份节点，登录备份节点，既可以获取数据
    	>cd $HOME/redis/bin ./redis-cli -c -h 10.137.38.85 -p 46001
    	>set foo 123






