## Grunt实战
	Grunt和 Grunt 插件是通过 npm 安装并管理的，npm是 Node.js 的包管理器。
	Grunt 0.4.x 必须配合Node.js >= 0.8.0版本使用。；奇数版本号的 Node.js 被认为是不稳定的开发版。
	在安装 Grunt 前，请确保当前环境中所安装的 npm 已经是最新版本，执行 npm update -g npm 指令进行升级（在某些系统中可能需要 sudo 指令）。
	如果你已经安装了 Grunt，现在需要参考一些文档手册，那就请看一看 Gruntfile 实例 和如何 配置任务吧。
## 安装 CLI（相对于windows环境）
	#输入命令（此时名利执行完之后，grunt加入到系统环境下，grunt-cli任务很简单，调用与gruntfile在同一级目录下的grunt）
	>npm install -g grunt-cli
## 创建新的 Grunt 项目
	需要在项目下，新建文件gruntfile.js和 package.json
    >npm init  命令会创建一个基本的package.json文件
## 安装Grunt 和 Grunt插件
	#执行如下命令格式，安装grunt插件
    >npm install <module> --save-dev
    有如下插件：
    	grunt
    	#js文件压缩
		grunt-contrib-uglify
        #js语法检查插件
        grunt-contrib-jshint
        #清理目录插件
        grunt-contrib-clean
        #文件合并插件
        grunt-contrib-concat
        #文件复制插件
        grunt-contrib-copy
        #css语法检查插件
        grunt-contrib-csslint
        #css文件压缩插件
        grunt-contrib-cssmin
        #压缩html插件
        grunt-contrib-htmlmin
        #文件压缩监控
        grunt-contrib-watch
        #根据html中的注释，替换其中的js、css文件，换成合并压缩后的js、css文件
        grunt-usemin
        #压缩输出时间
        time-grunt
        #模拟后台json数据返回
        grunt-restful-mock
## Gruntfile文件
	//检查css语法
		csslint:{
			src:['css/*.css']
		},
		//检查js文件中的语法
		jshint:{
			all:['js/*.js']
		},
		//复制task
		copy: {
			options: {
				separator : ';'
			},
			main:{
				files:[ 
					{
						/*启用下面*/
						expand:true,
						/*相对于src目录*/
						cwd:'',
						src:['*.jsp'],
						dest:'dist'
					},
					{
						expand:true,
						cwd:'images/',
						src:['*','**/*'],
						dest:'dist/images'
					}
				]
			} 
		},
		//清除目录下的文件
		clean:{
			src:'dist/'
		},
		//文件合并
		concat:{
			options:{
				/*文件合并，添加分割符*/
				separator:';',
				stripBanners: true
			},
			js:{
				src:[
					'js/coupon.js'
				],
				dest:'dist/js/m.js'
			}
			/**css:{
				src:[
					'css/*.css'
				],
				dest:'dist/css/m.css'
			}*/
		},
		
		//js文件压缩
		uglify:{
			dist:{
				files:{
					'dist/js/m.min.js':['<%=concat.js.dest %>']//引用concat下的合并js文件
				}
			},
            minjs: { //最小化、混淆所有 js/ 目录下的 JavaScript 文件
                files: [{
                    expand: true,
                    cwd: 'js/',
                    src: ['*.js', '*.min.js'],
                    dest: 'dist/js/',
                    ext: '.min.js'
                }]
            }
		},
		//css压缩
		cssmin:{
			options:{
				keepSpecialComments:0,//移除css文件中的所有注释
				ascii_only: true//防止中文乱码
			},
			minify:{
				expand:true,
				cwd:'css/',
				src:['*.css'],
				dest:'dist/css/',
				ext:'.min.css',
			},
			//合并并压缩css
			compress :{
				files:{
					'dist/css/m.min.css' :[
						'css/boilerplate.css',
						'css/coupon.css'
					]
				}
			}
		},
		//压缩html
		htmlmin:{
			dist:{
				options:{
					removeComments:true,
					collapseWhitespace:true
				},
				files:{
					'dist/index.jsp':'dist/index.jsp'
				}
			}
		},
		useminPrepare: {
  			html: 'index.jsp'
		},
		//根据html中的注释，替换其中的js、css文件，换成合并压缩后的js、css文件
		usemin:{
			html:'dist/*.jsp'
		},
