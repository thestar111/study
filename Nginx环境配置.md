## Nginx环境搭建

    listen       8889;		监听端口，如果配置多个server的时候，端口需要配置不一样
    server_name  192.168.0.52;		# Nginx中的server_name指令主要用于配置基于名称的虚拟主机，server_name指令在接到请求后的匹配顺序

    #配置启动进程（和CPU总核数一致）
    worker_processes  1;
    #charset koi8-r;

    #一个nginx进程打开的最多文件描述数目，理论值应该是最多打开文件数（系统的值ulimit -u）建议与ulimit -u的值一致
    worker_rlimit_nofile 65535;

    #access_log  logs/host.access.log  main;

## Linux 查看CPU总核数

    # 总核数 = 物理CPU个数 X 每颗物理CPU的核数
	# 总逻辑CPU数 = 物理CPU个数 X 每颗物理CPU的核数 X 超线程数

	# 查看物理CPU个数
	cat /proc/cpuinfo| grep "physical id"| sort| uniq| wc -l

	# 查看每个物理CPU中core的个数(即核数)
	cat /proc/cpuinfo| grep "cpu cores"| uniq

	# 查看逻辑CPU的个数
	cat /proc/cpuinfo| grep "processor"| wc -l

## Nginx 安装

###### 编译
	./configure --prefix=/usr/local/nginx --sbin-path=/usr/local/nginx/sbin/nginx --conf-path=/usr/local/nginx/conf/nginx.conf --error-log-path=/var/log/nginx/error.log  --http-log-path=/var/log/nginx/access.log  --pid-path=/var/run/nginx/nginx.pid --lock-path=/var/lock/nginx.lock  --user=nginx --group=nginx  --with-http_stub_status_module --with-http_gzip_static_module --http-client-body-temp-path=/var/tmp/nginx/client/ --http-proxy-temp-path=/var/tmp/nginx/proxy/ --http-fastcgi-temp-path=/var/tmp/nginx/fcgi/ --http-uwsgi-temp-path=/var/tmp/nginx/uwsgi --http-scgi-temp-path=/var/tmp/nginx/scgi --with-pcre

###### Fastdfs模块添加编译
	./configure --prefix=/usr/local/nginx --add-module=../fastdfs-nginx-module/src/ --sbin-path=/usr/local/nginx/sbin/nginx --conf-path=/usr/local/nginx/conf/nginx.conf --error-log-path=/var/log/nginx/error.log  --http-log-path=/var/log/nginx/access.log  --pid-path=/var/run/nginx/nginx.pid --lock-path=/var/lock/nginx.lock  --user=nginx --group=nginx  --with-http_stub_status_module --with-http_gzip_static_module --http-client-body-temp-path=/var/tmp/nginx/client/ --http-proxy-temp-path=/var/tmp/nginx/proxy/ --http-fastcgi-temp-path=/var/tmp/nginx/fcgi/ --http-uwsgi-temp-path=/var/tmp/nginx/uwsgi --http-scgi-temp-path=/var/tmp/nginx/scgi --with-pcre

#### http 服务配置

        NGNIX开启压缩：
        	gzip on;
        	gzip_buffers 4 8k;
            #压缩级别
        	gzip_comp_level 2;
            #超过20压缩
        	gzip_min_length 20;
        	gzip_proxied off;
        	gzip_types text/css;
        	gzip_vary off;

        location / {	匹配路径
            root   html;	匹配当前路径的时候，会到这个html的文件夹下匹配
            index  index.html index.htm;	当没有指定主页的时候，默认选择这个指定的文件
            proxy_pass http://192.168.0.52:35840	代理路径，相当于转发
        }

        #error_page  404              /404.html;	出错的时候，跳转的页面

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}

## Nginx Server 配置
	server {
        listen       80;
        server_name  ethio-play.com;
        # 永久重定向配置 301
        rewrite ^(.*) http://www.ethio-play.com$1 permanent;
    }

###### nginx自动匹配多级域名配置

	server {
        listen       80;
        server_name  ~^(?<subdomain>\w+)\.yourdomain\.com$;
        root   html/$subdomain;
        index  index.html index.htm index.php;
        fastcgi_intercept_errors on;
        error_page  404      = /404.html;
        location / {
                # This is cool because no php is touched for static content.
                # include the "?$args" part so non-default permalinks doesn't
                # break when using query string
                try_files $uri $uri/ =404;
       }

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        location ~ \.php$ {
            fastcgi_pass   127.0.0.1:9000;
            fastcgi_index  index.php;
            fastcgi_param  SCRIPT_FILENAME  $document_root$fastcgi_script_name;
            fastcgi_param  domain $subdomain;
            include        fastcgi_params;
        }

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        location ~ /\.ht {
            deny  all;
        }
	}

## 配置反向代理

	location ~ \.jsp$ {  	配置JSP页面又tomcat处理
        proxy_pass http://localhost:8080;
    }
    location ~ \.(html|js|css|png|gif)$ {  	配置一些静态资源有nginx处理
        root D:/software/developerTools/server/apache-tomcat-7.0.8/webapps/ROOT;
    }
## 配置多个静态私服
	* server外添加了一个upstream，而直接在proxy_pass里面直接用http://+upstream的名称来使用， upstream中的server元素必须要注意，不能加http://，但proxy_pass中必须加
	upstream local_tomcat {
    	server localhost:8080 weight=1;	配置多个私服，weight值越大请求得到的机会越大
    	server localhost:9999 weight=5;	配置多个私服，weight值越大请求得到的机会越大
	}
	server{
        location / {
           proxy_pass http://local_tomcat;
        }
        #......其他省略
	}
## nginx正则表达式：
    eg:
    语法:location [=|~|~*|^~] /uri/ { … }
    注意：
     ~* 前缀选择不区分大小写的匹配
     ~ 选择区分大小写的匹配
     例子：
	location = / {
	# 只匹配 / 查询。
	[ configuration A ]
	}

	location / {
	# 匹配任何查询，因为所有请求都已 / 开头。但是正则表达式规则和长的块规则将被优先和查询匹配。
	[ configuration B ]
	}

	location ^~ /images/ {
	# 匹配任何已 /images/ 开头的任何查询并且停止搜索。任何正则表达式将不会被测试。
	[ configuration C ]
	}

	location ~* \.(gif|jpg|jpeg)$ {
	# 匹配任何已 gif、jpg 或 jpeg 结尾的请求。然而所有 /images/ 目录的请求将使用 Configuration C。
	[ configuration D ]
	}
    Nginx正则表达式location匹配：
    location ^~ /business/
	{
    	#root
		alias	D:\\apidoc\\portal\\business\\apidocs\\;
		#expires定义用户浏览器缓存的时间为7天，如果静态页面不常更新，可以设置更长，这样可以节省带宽和缓解服务器的压力
		#expires      7d;
	}
    root 和 alias区别：
    eg:http://localhost:8079/business/index.html
    alias也是用来设置文件资源路径的，他与root不同点就是在于如何解读跟location后面的URL参数
    1.使用alias的时候，他会将URL中的business部分丢掉,请求路径会变为D:\\apidoc\\portal\\business\\apidocs\\index.html
    2.使用root的时候，business不会丢弃掉，他会使用完整的url来匹配，请求路径会变为D:\\apidoc\\portal\\business\\apidocs\\business/index.html

## Nginx 正则规则表达式

	~      为区分大小写的匹配。
    ~*     不区分大小写的匹配（匹配firefox的正则同时匹配FireFox）。
    !~     不匹配的
    !~*    不匹配的

    .     匹配除换行符以外的任意字符
    \w     匹配字母或数字或下划线或汉字
    \s     匹配任意的空白符
    \d     匹配数字
    \b     匹配单词的开始或结束
    ^     匹配字符串的开始
    $     匹配字符串的结束

    *     重复零次或更多次
    +     重复一次或更多次
    ?     重复零次或一次
    {n}     重复n次
    {n,}     重复n次或更多次
    {n,m}     重复n到m次
    *?     重复任意次，但尽可能少重复
    +?     重复1次或更多次，但尽可能少重复
    ??     重复0次或1次，但尽可能少重复
    {n,m}?     重复n到m次，但尽可能少重复
    {n,}?     重复n次以上，但尽可能少重复

    \W     匹配任意不是字母，数字，下划线，汉字的字符
    \S     匹配任意不是空白符的字符
    \D     匹配任意非数字的字符
    \B     匹配不是单词开头或结束的位置
    [^x]     匹配除了x以外的任意字符
    [^aeiou]     匹配除了aeiou这几个字母以外的任意字符

    捕获     (exp)     匹配exp,并捕获文本到自动命名的组里
    (?<name>exp)     匹配exp,并捕获文本到名称为name的组里，也可以写成(?'name'exp)
    (?:exp)     匹配exp,不捕获匹配的文本，也不给此分组分配组号
    零宽断言     (?=exp)     匹配exp前面的位置
    (?<=exp)     匹配exp后面的位置
    (?!exp)     匹配后面跟的不是exp的位置
    (?<!exp)     匹配前面不是exp的位置

#### Nginx命令：
	cd nginx安装目录:
	#运行 		 start nginx
	#重启 		 nginx -s reload
	#查看配置是否生效	nginx -t
    #关闭服务	  nginx -s stop
