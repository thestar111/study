## SASS环境搭建


## 1. 安装Ruby
	1. 下载ruby，默认安装即可
	2. 由于国内网络和谐原因，添加国内镜像源
		移除默认镜像源 ： gem sources --remove https://rubygems.org/
        添加国内镜像源 ： 淘宝的已经不维护了，gem sources -a https://gems.ruby-china.org huoz或者 gem sources -a http://gems.ruby-china.org



## 2. 安装Sass
	gem install sass




## 3. 编译Sass文件

	输入命令： sass test.scss test.css



## 4. 编译并压缩sass文件
	sass --style compressed test.sass test.css





## 5. SASS监听某个文件或目录，动态编译文件
	// watch a file
    sass --watch input.scss:output.css
    
	// watch a directory
	sass --watch app/sass:public/stylesheets



## 6. 样例

###### SASS允许使用变量，所有变量以$开头。
    $blue : #1875e7;
    div {
   		color : $blue;
    }
###### 如果变量需要镶嵌在字符串之中，就必须需要写在#{}之中。
    $side : left;
    	.rounded {
    		border-#{$side}-radius: 5px;
    }

###### SASS允许选择器嵌套。比如，下面的CSS代码：
    div h1 {
    	color : red;
    }
###### 可以写成：
    div {
        hi {
          color:red;
        }
    }
###### 属性也可以嵌套，比如border-color属性，可以写成：
    p {
        border: {
          color: red;
        }
    }


###### 在嵌套的代码块内，可以使用&引用父元素。比如a:hover伪类，可以写成：
    a {
    	&:hover { color: #ffb3ff; }
    }


#### 继承

###### SASS允许一个选择器，继承另一个选择器。比如，现有class1
	.class1 {
    	border: 1px solid #ddd;
    }


###### class2要继承class1，就要使用@extend命令
	.class2 {
		@extend .class1;
     	font-size:120%;
    }


### Mixin

###### 使用@mixin命令，定义一个代码块
	@mixin left {
        float: left;
        margin-left: 10px;
    }

###### 使用@include命令，调用这个mixin
	div {
    	@include left;
    }

###### mixin的强大之处，在于可以指定参数和缺省值

    @mixin left($value: 10px) {
        float: left;
        margin-right: $value;
    }

###### 下面是一个mixin的实例，用来生成浏览器前缀
    @mixin rounded($vert, $horz, $radius: 10px) {
        border-#{$vert}-#{$horz}-radius: $radius;
        -moz-border-radius-#{$vert}#{$horz}: $radius;
        -webkit-border-#{$vert}-#{$horz}-radius: $radius;
    }
###### 使用的时候，可以像下面这样调用

    #navbar li { @include rounded(top, left); }
    #footer { @include rounded(top, left, 5px); }

###### SASS提供了一些内置的颜色函数，以便生成系列颜色
	lighten(#cc3, 10%) // #d6d65c
    darken(#cc3, 10%) // #a3a329
    grayscale(#cc3) // #808080
    complement(#cc3) // #33c

###### @import命令，用来插入外部文件

	@import "path/filename.scss";

###### 如果插入的是.css文件，则等同于css的import命令

	@import "foo.css";


## 高级用法

###### @if可以用来判断：

	p {
        @if 1 + 1 == 2 { border: 1px solid; }
        @if 5 < 3 { border: 2px dotted; }
    }

###### 配套的还有@else命令
    @if lightness($color) > 30% {
    	background-color: #000;
    } @else {
    	background-color: #fff;
    }

#### 循环语句

	@for $i from 1 to 10 {
        .border-#{$i} {
        	border: #{$i}px solid blue;
        }
    }

###### while循环
	$i: 6;
    @while $i > 0 {
        .item-#{$i} { width: 2em * $i; }
        $i: $i - 2;
    }

#### 自定义函数

	@function double($n) {
    	@return $n * 2;
    }
    #sidebar {
    	width: double(5px);
    }





