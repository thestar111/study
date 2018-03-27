## ElasticSearch

###### 1.安装ElasticSearch [下载](https://www.elastic.co/downloads/elasticsearch)

	运行： ./elasticsearch
    访问： http://ip:9200

###### 2.安装ElasticSearch下的Head插件 [下载](https://github.com/mobz/elasticsearch-head.git)

	运行： npm run start
    访问： http://ip:9100

###### 3.配置ElasticSearch和插件连接
		由于ElasticSearch 5.0版本后，Head插件不需要放到plugins目录下，需要安装nodejs环境来启动head插件，这样由于head插件和ElasticSearch是独立的进程，所以存在跨域问题，需要配置ElasticSearch的配置文件elasticsearch.yml


###### 配置如下：
	http.cors.enabled: true
	http.cors.allow-origin: "*"


	## 					打赏
![alipay_jpg](https://github.com/thestar111/resource/blob/master/alipay.jpg)
![wxpay_jpg](https://github.com/thestar111/resource/blob/master/wxpay.jpg)
