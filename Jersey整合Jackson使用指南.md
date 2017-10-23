## Jersey整合Jackson出现的问题


## 解决对象属性为空，转成json时报错
###### 注意：
    to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS


###### No.1

	/**
     * <自定义ContextRecolver, 重写ObjectMappper>
     *
     * @author zping
     * @version 2017/7/21 0021
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    @Provider
    @Produces (MediaType.APPLICATION_JSON)
    public class H5MapperResolver implements ContextResolver<ObjectMapper>
    {

        private final ObjectMapper objectMapper;

        public H5MapperResolver ()
        {
            objectMapper = new ObjectMapper ();
            objectMapper.disable (SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }

        @Override
        public ObjectMapper getContext (Class<?> aClass)
        {
            return objectMapper;
        }
    }


###### No.2
	/**
     * <自定义 Json服务提供者>
     *
     * @author zping
     * @version 2017/7/21 0021
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */

    @Named
    @Provider
    public class H5JacksonJsonProvider extends JacksonJsonProvider
    {
        @Override
        public void writeTo (Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException
        {
            ObjectMapper mapper = locateMapper (type, mediaType);
            mapper.disable (SerializationFeature.FAIL_ON_EMPTY_BEANS);
            super.writeTo (value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
        }
    }

###### 注意：
	@Named 和Spring的 @Component功能相同。@Named可以有值，如果没有值生成的Bean名称默认和类名相同


