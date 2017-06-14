### Nginx环境搭建
		listen       8889;		监听端口，如果配置多个server的时候，端口需要配置不一样
        server_name  192.168.0.52:35840;		监听之后跳转到哪里去

		#配置启动进程（和CPU数量一致）
        worker_processes  1;
        #charset koi8-r;

        #access_log  logs/host.access.log  main;

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

###### Nginx命令：
cd nginx安装目录:
	#运行 		 start nginx
	#重启 		 nginx -s reload
	#查看配置是否生效	nginx -t
    #关闭服务	  nginx -s stop
