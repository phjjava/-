package com.jp.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * json转换工具类
 * @author Administrator
 *
 */
public class JacksonUtil {

    /** 创建 ObjectMapper*/   
    private static final ObjectMapper objectMapper = new ObjectMapper();    
    /** 格式化时间的string */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     *  默认空的构造方法
     */
    private JacksonUtil() {}
    
    /**   
     *  获取实例
     * @return   
     */   
    public static ObjectMapper getInstance() {
         //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //为了避免某个对象没有属性而报错
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //序列化时null转化为""空串
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()  
        {  
            public void serialize(  
                    Object value,  
                    JsonGenerator jg,  
                    SerializerProvider sp) throws IOException, JsonProcessingException  
            {  
                jg.writeString("");  
            }  
        }); 
        //objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        
        return objectMapper;    
    }
    
    /**
     * json转换为java对象
     * @param json  字符串
     * @param clazz  对象的class
     * @return  返回对象
     */
    public static <T> T fromJsonToObject(String json, Class<T> clazz) {   
       return fromJsonToObject(json, clazz, DATE_TIME_FORMAT);   
    } 
    
    public static <T> T fromJsonToObject(String json, Class<T> clazz, String format) {
        getInstance().setDateFormat(new SimpleDateFormat(format));
        try {   
            return getInstance().readValue(json, clazz);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        return null;
    }
  
    /**
     * java对象转换为json字符串
     * @param object java对象
     * @return json字符串
     */
    public static String fromObjectToJson(Object object) {   
        return fromObjectToJson(object, DATE_TIME_FORMAT);   
    }
    
    public static String fromObjectToJson(Object object, String format) {
        getInstance().setDateFormat(new SimpleDateFormat(format));
        try {   
            return getInstance().writeValueAsString(object);   
        } catch (Exception e) {   
            e.printStackTrace();   
            throw new RuntimeException("解析对象错误");   
        }
    }
    
    /**   
     * 获取泛型的Collection Type  
     * @param collectionClass 泛型的Collection   
     * @param elementClasses 元素类   
     * @return JavaType Java类型   
     * @since 1.0   
     */   
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?> elementClasse) {   
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasse);   
    }
    
    public static <T> List<T> fromJsonToList(String json, Class<T> elementClasse) {   
        return fromJsonToList(json, elementClasse, DATE_TIME_FORMAT);   
    }
    
    public static <T> List<T> fromJsonToList(String json, Class<T> elementClasse, String format) {   
        getInstance().setDateFormat(new SimpleDateFormat(format));
        try {   
            return getInstance().readValue(json, getCollectionType(List.class, elementClasse));
        } catch (Exception e) {   
            e.printStackTrace();   
            throw new RuntimeException("解析json错误");   
        }  
    }
}
