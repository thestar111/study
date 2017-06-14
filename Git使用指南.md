## Git 使用指南
#### 将本地项目上传到GitHub上

    1.建立本地Git仓库
    	进入项目目录下，执行 : git init
    2.将本地项目添加到暂存区
    	git add .
    3.将暂存区项目文件添加到本地仓库
    	git commit -m "comment"
    4.在GitHub上创建一个仓库repository
    5.将本地项目关联到GitHub上：
    	git remote add origin https://github.com/thestar111/springbootdemo.git	注意：在这一步时如果出现错误：fatal:remote origin already exists
        那就先输入 git remote rm origin
    6.将本地项目上传到GitHub上
    	git push -u origin master
        *注意如果报错：
        	git pull --rebase origin master
            git push origin master
