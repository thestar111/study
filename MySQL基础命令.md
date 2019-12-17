## MySQL基本命令

##### 连接数据库
```
> mysql -u用户名 -p密码
```
```
> mysql -u用户名 -p
Enter password: ******
```

##### 数据库管理

###### 查看所有数据库
```
mysql> show databases;
```

###### 创建数据库
```
mysql> create database 数据库名称;
```

###### 创建指定字符集数据库
```
mysql> create database 数据库名称;
    -> default character set gbk;
```

###### 查看数据库的字符集
```
mysql> show create database 数据库名称;
```

###### 删除数据库
```
mysql> drop database 数据库名称;
```

##### 表管理

###### 切换使用的数据库
```
mysql> use 数据库名称;
```

###### 查看所有表
```
mysql> show tables;
```

###### 创建表
```
mysql> create table 表名(列 列的类型，列 列的类型);
```

###### 查看表结构
```
mysql> desc 表名;
```

###### 删除表
```
mysql> drop table 表名;
```

###### 修改表的列
```
mysql> alter table 表名 add column 列 列的类型;
mysql> alter table 表名 drop column 列名;
mysql> alter table 表名 modify column 列名 新的类型;
mysql> alter table 表名 change column 列名 新的列名 新的类型;
```

##### 增删改数据

###### 插入数据
```
mysql> insert into 表名(列名,列名) values(值,值);
```

###### 开启事务的情况下，delete删除的数据可以恢复，truncate删除的数据不能恢复 删除表中全部数据
```
mysql> delete from 表名;
```

###### 删除所有满足条件的数据
```
mysql> delete from 表名 where 列名=值 and|or 列名=值;
```

###### 修改表中数据
```
mysql> update 表名 set 列=值 where 列=值;
```

##### 查询数据

###### 查询指定列数据
```
mysql> select 列名 from 表名;
```

###### 指定常量列（别名）
```
mysql> select 列名 as 别名 from 表名;
```

###### 去重查询
```
mysql> select distinct 列名 from 表名;
```

###### 按条件查询
```
mysql> select * from 表名 where 列名=值;
```
```
mysql> select * from 表名 where 列名=值 and 列名=值;
```

```
mysql> select * from 表名 where 列名 is null;
mysql> select * from 表名 where 列名 is not null;
```
###### 条件符号
```
>、<、>=、<=、=、<>、between and
```

###### 模糊查询
```
mysql> select * from 表名 where 列名 like '%模糊字段%';
mysql> select * from 表名 where 列名 like '模糊字段_';
```

##### 通过聚合函数查询

###### 查询表中数据条数
```
mysql> select count(*) from 表名;
```

###### 如果列没有数据不计算
```
mysql> select count(列名) from 表名;
```

###### 求平均值
```
mysql> select avg(列名) from 表名;
```

###### 求最极值
```
mysql> select max(列名) from 表名;
mysql> select min(列名) from 表名;
```

###### 求和
```
mysql> select sum(列名) from 表名;
```

###### 排序
```
mysql> select * from 表名 order by 列名;
mysql> select * from 表名 where 列名=值 order by 列名;
```

###### 指定排序格式
```
mysql> select * from 表名 order by 列名 asc;
mysql> select * from 表名 order by 列名 desc;
```

###### 指定多个排序标准
```
mysql> select * from 表名 order by 列名 asc,列名 desc;
```
###### 分组查询
```
mysql> select 列名,count(*) from 表名 group by 列名;
```

###### 筛选分组后排序
```
mysql> select 列名,count(*) from 表名 where 筛选条件 group by 列名 order 列名;
```

###### 对分组后的数据筛选
```
mysql> select 列名,count(*) from 表名 where 列名=值 group by 列名 having count(*)>1 order by 列名;
```

###### 分页查询
```
mysql> select * from 表名 limit 开始位置,数据条数;
```



