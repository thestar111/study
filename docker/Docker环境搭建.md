#### Docker环境安装

- 安装依赖

  ```
  sudo yum install -y yum-utils device-mapper-persistent-data lvm2 
  ```

- 设置阿里云镜像

  ```
  sudo yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo 
  ```

- 查询Docker版本

  ```
  yum list docker-ce --showduplicates | sort -r
  ```

- 安装Docker

  ```
  yum install -y docker-ce-18.06.3.ce-3.el7
  ```

- 配置镜像加速

  ```
  sudo tee /etc/docker/daemon.json <<-'EOF'
  {
    "registry-mirrors": ["你的加速器地址"]
  }
  EOF
  ```

- 重启Docker

  ```
  sudo systemctl daemon-reload
  sudo systemctl restart docker
  ```

- 安装启动Portainer

  ```
  docker volume create portainer_data  -- 创建数据卷
  docker run -d -p 9000:9000 -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer  -- 部署Portainer容器
  ```

- 访问地址

  ```
  http://103.131.171.71:9000
  ```

  

