##mongoDB环境搭建
1.下载mongoDB包：http://www.mongodb.org/downloads
2.安装路径，例如：比如d:\\mongodb
3.创建文件夹：
4.	d:\mongodb\data\db		存放数据文件
5.	d:\mongodb\data\log		存放日志文件

4.mangodb启动命令
	# mongod -dbpath "d:\mongodb\data\db" --port 10001 指定端口
    # MongoDB监听的端口是27017，mysql的是3306
    注意：如果保存，请使用如下命令 mongod --dbpath D:\mongodb\data --storageEngine=mmapv1
  	
5.基本使用命令：
	# mongo [ip地址]:[监听端口]/[数据库]	列如：mongo 127.0.0.1:27017/user
    # show dbs
	# show databases
    --切换数据库
    # use [数据库名] /**有则切换，没有则创建*/
    --显示当前数据
    > db
    --插入数据
    > db.c1.insert({name:"xiangyue"}) /*插入数据后，zping数据库被创建*/
    -- 删除数据库
    > db.dropDatabase
    -- 显示表
    > show tables or show collections
    -- 新建集合
    >  db.createCollection("c1")
    -- 创建集合并插入数据 先创建user集合，再插入数据
    > db.user.insert({name:”xiangyue”,age:23});
    -- 查询数据
    > db.user.find()
    -- 根据条件查询用户信息
    > db.user.find({name:"user2"})
    -- 删除数据
    >  db.user.remove({name:"user2"});
    -- 修改数据
    > db.user.update({name:"user3"},{_id:3,name:"user33",age:13});