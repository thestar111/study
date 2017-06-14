##Kafka集群配置：

Kafka做为高速存取的MQ队列，需要持久化大量的数据，消息消费的偏移量和集群服务管理存储在Zookeeper上，测试时可以使用自带的zookeeper，商用环境使用时需要规划独立的zookeeper集群，zookeeper集群搭建方案请参考[zookeeper手工安装配置](mailto:genghuanzhong@huaewi.com).

####配置文件
	 Path:   /kafka_2.9.2-0.8.1/config/server.properties	属性配置文件
	 port = 9092     ==端口号，producer访问时需要的连接端口号==
	 log.dirs = /home/kafka/data/kafkalogs ==本地的kafka持久化文件存储==
	 num.partitions = 8   ==默认每个topic分片个数，kafka默认推荐建议8个partion==
	 zookeeper.connect = 127.0.0.1:2181  ==zookeeper连接信息，根据实际情况填写==
	 group.id = test-consumer-group  ==消费者分组ID==
	 zookeeper.connection.timeout.ms = 1000000  ==ZK连接超时时间==
	 metadata.broker.list = localhost:9092 ==代理列表==
	 serializer.class = kafka.serializer.DefaultEncoder ==序列化类==
	 producer.type = sync ==消息异步发送==
##启动和安装

####在**$KAFKA_HOME**目录下执行命令
	#启动脚本
	bin/kafka-server-start.sh config/server.properties &
	#关闭命令
	bin/kafka-server-stop.sh
