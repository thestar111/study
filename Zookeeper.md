#Zookeeper安装配置
1.下载Zookeeper安装包，解压至随意目录下
2.修改conf下的zoo_sample.cfg文件为zoo.cfg,因为Zookeeper启动时会自动找改文件
3.Dubbo注册中心详解（四种注册中心）：
- 	Multicast注册中心
- 	Zookeeper注册中心
- 	Redis注册中心
- 	Simple注册中心

4.参数配置说明：
- 	tickTime=2000    服务器与客户端之间维持心跳的时间间隔
- 	dataDir=D:/devtools/zookeeper-3.2.2/data   数据目录（需要自己手动创建,不然启动报错）
- 	clientPort=2181客户端连接Zookeeper的端口,Zookeeper会监听该端口
- 	initLimit=5  配置 Zookeeper 接受客户端（是指Zookeeper服务器集群连接到leader的 follower服务器）初始化连接时最长能忍受多少个心跳时间间隔数。当已经超过 5 个心跳的时间（也就是 tickTime）长度后 Zookeeper 服务器还没有收到客户端的返回信息，那么表明这个客户端连接失败。总的时间长度就是 5*2000=10 秒
- 	syncLimit=2  这个配置项标识 Leader 与 Follower 之间发送消息，请求和应答时间长度，最长不能超过多少个 tickTime 的时间长度，总的时间长度就是 2*2000=4 秒
- 	Zookeeper集群配置：
- 	在data目录下新建一个myid文件，并不带后缀，内容为server.x中的x值
- 	server.1=192.168.211.1:2888:3888
- 	server.2=192.168.211.2:2888:3888
server.A=B：C：D：其中 A 是一个数字，表示这个是第几号服务器；B 是这个服务器的 ip 地址；C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口；D 表示的是万一集群中的 Leader 服务器挂了，需要一个端口来重新进行选举，选出一个新的 Leader，而这个端口就是用来执行选举时服务器相互通信的端口。如果是伪集群的配置方式，由于 B 都是一样，所以不同的 Zookeeper 实例通信端口号不能一样，所以要给它们分配不同的端口号。
集群配置：
除了修改 zoo.cfg 配置文件，集群模式下还要配置一个文件 myid，这个文件在 dataDir 目录下，这个文件里面就有一个数据就是 A 的值，Zookeeper 启动时会读取这个文件，拿到里面的数据与 zoo.cfg 里面的配置信息比较从而判断到底是那个 server。

5.先启动Zookeeper,再启动dubbo-admin管理后台
