## RESTful ： Jersey实践
#### 介绍：

		REST(Representational State Transfer)只是一种风格，不是技术或标准。在REST中一切都被认为是一种资源，所谓资源就是一个实体，或者网路上说的一个具体信息，例如文本，图片，歌曲等都市一个资源。你可以用一个URI(统一资源定位符)，每一个资源都有一个特定的URI。通过URI既可以访问。HTTP协议是一个无状态协议，意味着所有的状态都是保存在服务器端，这样，如果客户端需要操作服务器，必须通过某种手段让服务器的状态转化，而REST就是让这种状态转化建立在表现层上，由表现层控制。
###### GET ： 用来获取资源，
###### POST ： POST用来新建资源（也可以用于更新资源）
###### PUT ： PUT用来更新资源
###### DELETE ： DELETE用来删除资源

#### 1.抽象层注解资源
	JAX-RS2的HTTP方法注解可以定义在接口和POJO中，置于接口中的方法名更具抽象性和通用性：示例
    @Path("book")
	public interface BookResource {
		//关注点1：GET注解从抽象类上移到接口
		@GET
    	public String getWeight();
	}
    实现类：
    public class EBookResourceImpl implements BookResource {
    	//关注点2：实现类无须GET注解
    	@Override
    	public String getWeight() {
    	    return "150M";
    	}
	}
    添加实现类到资源容器中
    //关注点3：加载的是实现类而不是接口
    return new ResourceConfig(EBookResourceImpl.class);



#### 2.路径区间	@PathSegment
	@GET
    @Path("{region:.+}/varvalue/{district:\\w+}")
    public String getByAddress(@PathParam("region") final List<PathSegment> region,@PathParam("district") fianl String district){
         final StringBuilder result=new StringBuilder();
         for (final PathSegment pathSegment:region){
           result.append(pathSegment.getPath()).append("-");
         }
    }
    另：对于查询参数动态给定的场景，可以定义PathSegment作为参数类型，通过getMatrixParameters()方法获取MultivaluedMap类型的查询参数信息，即可将参数条件作为一个整体解析。

#### 3.路径参数 @PathParam
	@GET
    @Path("form:\\d+}-{to:\\d+}")
    public String getMothed(@PathParam("form") final Integer form,@PathParam("to") final Integer to){.....}


#### 4.路径区间参数 @MatrixParam
	@Path("/books")
	public class BookService {

		@GET
		@Path("{year}")
		public Response getBooks(@PathParam("year") String year,
			@MatrixParam("author") String author,
			@MatrixParam("country") String country) {
			return Response
				.status(200)
				.entity("getBooks is called, year : " + year
					+ ", author : " + author + ", country : " + country)
				.build();
		}
	}
See following URI patterns AND Result ：
1. URI Pattern : /books/2011/
		getBooks is called, year : 2011, author : null, country : null
2. URI Pattern : /books/2011;author=mkyong
    	getBooks is called, year : 2011, author : mkyong, country : null
3. URI Pattern : /books/2011;author=mkyong;country=malaysia
    	getBooks is called, year : 2011, author : mkyong, country : malaysia
4. URI Pattern : /books/2011;country=malaysia;author=mkyong
    	getBooks is called, year : 2011, author : mkyong, country : malaysia



#### 5.路径正则表达式
	@Path(“users/{username: [a-zA-Z][a-zA-Z_0-9]*}”)
    这个正则表达式匹配由大小写字符、横杠和数字组成的字符串，如果正则校验不通过，则返回404（没有找到资源）
    @Path("form:\\d+}-{to:\\d+}")
    	from ： 只接受数字  to : 只接受数字
    @Path("{region:.+}/varvalue/{district:\\w+}")
    	region : .+ 表示至少有一个参数	district ： \\w+ 表示大小写字母

#### 6.表单参数 @FormParam
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public void post(@FormParam("name") String name) {
    	// Store the message
	}

#### 7.	允许注入参数到一个POJO中 @BeanParam
	定义Bean：
	import javax.ws.rs.QueryParam;
    import javax.xml.bind.annotation.XmlRootElement;
    import java.io.Serializable;

    @XmlRootElement(name = "user")
    public class User implements Serializable {

    	private static final long serialVersionUID = 1L;
        @QueryParam("name")
        private String name;
        @QueryParam("password")
        private String password;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
	}
    接口：
    @Path("/hello")
    public class HelloWorldController {
        @GET
        @Produces(MediaType.TEXT_PLAIN)
        public String helloWorld(@BeanParam User user){
            System.out.println(user.toString());
            return "hello world! ";
        }
    }

#### 8.强大的参数注入 @Context
###### @Context可以可以获取诸如 ServletConfig 、ServletContext 、HttpServletRequest 和 HttpServletResponse这些参数
    @GET
    public String get(@Context UriInfo ui) {
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        MultivaluedMap<String, String> pathParams = ui.getPathParameters();
    }
###### 获取Header
    @GET
    public String get(@Context HttpHeaders hh) {
        MultivaluedMap<String, String> headerParams = hh.getRequestHeaders();
        Map<String, Cookie> pathParams = hh.getCookies();
    }

#### 9.获取Cookie中参数 @CookieParam
	@CookieParam("Cookie名称") final String Cookie变量名称


#### 10.全局异常处理
###### 异常处理器要实现ExceptionMapper<Exception>接口,并复写返回方法,另外一定要使用@Provider标识该类为一个jersey的处理器，并需要添加到资源容器中
	@Provider
    public class DeviceExceptionMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception e) {
            Response.ResponseBuilder ResponseBuilder = null;

            if (e instanceof DeviceException){

                //截取自定义类型
                DeviceException exp = (DeviceException) e;
                ErrorEntity entity = new ErrorEntity(exp.getCode(),exp.getMessage());
                ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
            }else {
                ErrorEntity entity = new ErrorEntity(ErrorCode.OTHER_ERR.getCode(),e.getMessage());
                ResponseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
            }
            System.out.println("执行自定义异常");
            return ResponseBuilder.build();
        }
    }



#### 11.过滤器
###### ContainerRequestFilter
	/**
     * 对于request的过滤器
     * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
     * @author Niu Li
     * @date 2016/7/27
     * Provider //这个是匹配后增加参数或者减少参数
     */
    @PreMatching  //不知道为什么和后请求过滤器冲突,不能同时使用
    public class PreRequestFilter implements ContainerRequestFilter {
        @Override
        public void filter(ContainerRequestContext containerRequestContext) throws IOException {
            /**
             * 具体可以获取什么参数,加个断点就可以看到了
             */
            System.out.println("PreRequestFilter");
        }
    }


###### ContainerResponseFilter
	/**
     * 对于response的过滤器
     * 过滤器主要是用来操纵请求和响应参数像HTTP头，URI和/或HTTP方法
     * @author Niu Li
     * @date 2016/7/27
     */
    @Provider
    public class ResponseFilter implements ContainerResponseFilter {
        @Override
        public void filter(ContainerRequestContext containerRequestContext,
                           ContainerResponseContext containerResponseContext) throws IOException {
            /**
             * 具体可以获取什么参数,加个断点就可以看到了
             */
            System.out.println("执行回复过滤");
        }
    }

#### 12.拦截器
	reader用的不多,writer可以用来开启gzip压缩,这个倒是很实用,并且jersey开启gzip压缩很方便,乱码问题解决办法就是主动告诉浏览器使用哪一种编码解码就好了
###### ReaderInterceptor




###### WriterInterceptor
	public class GzipInterceptor implements WriterInterceptor {
        @Override
        public void aroundWriteTo(WriterInterceptorContext context)
                throws IOException, WebApplicationException {

            MultivaluedMap<String, Object> headers = context.getHeaders();
            headers.add("Content-Encoding", "gzip");
            String ContentType = context.getMediaType().toString();
            headers.add("Content-Type",ContentType+";charset=utf-8");//解决乱码问题
            final OutputStream outputStream = context.getOutputStream();
            context.setOutputStream(new GZIPOutputStream(outputStream));
            context.proceed();
            System.out.println("GZIP拦截器压缩");
        }
    }
#### 13.执行顺序
	还是之前那个抛出异常的方法,访问后先执行请求过滤器,再匹配到相应方法,执行方法体,然后有异常,执行异常拦截器,其次执行回复过滤。




#### 14.名称绑定


###### 名称绑定

	1.名称绑定，支持自定义注解，下面以记录日志为例，自定义Log自定义注解，支持方式、类上的注解
    @NameBinding
    @Target ({ElementType.TYPE, ElementType.METHOD})
    @Retention (RetentionPolicy.RUNTIME)
    public @interface Log
    {
    }
    2.绑定Provider
    @Log
    @Provider
    @Priority (Priorities.USER)
    public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter
    {

        private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

        @Override
        public void filter (ContainerRequestContext containerRequestContext) throws IOException
        {
            logger.info ("request log ... ");
        }

        @Override
        public void filter (ContainerRequestContext containerRequestContext,
                ContainerResponseContext containerResponseContext) throws IOException
        {
            logger.info ("response log ... ");
        }
    }
    3.注入Jersey容器中


###### 动态绑定
	1.动态绑定,可以更个性化的加载,在运行期只要匹配的动态绑定扩展的方法，面向切面的Provider就会被加载。动态的方式分配资源方法的筛选器和拦截器
















