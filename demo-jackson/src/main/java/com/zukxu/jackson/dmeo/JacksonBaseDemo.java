package com.zukxu.jackson.dmeo;

import com.fasterxml.jackson.core.*;
import com.zukxu.jackson.bean.TestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * jackson 底层序列化原理
 * </p>
 *
 * @author xupu
 * @since 2022/5/24 18:00:07
 */
public class JacksonBaseDemo {

    private static final Logger logger = LoggerFactory.getLogger(JacksonBaseDemo.class);

    JsonFactory jsonFactory = new JsonFactory();

    /**
     * 用来验证反序列化的JSON字符串
     */
    final static String TEST_JSON_STR = "{\n" +
                                        "    \"id\":151,\n" +
                                        "    \"text\":\"我爱中国！\",\n" +
                                        "    \"createTime\":\"2022-05-25T10:51:00\"\n" +
                                        "}";

    /**
     * 用来验证序列化的TestEntity实例
     */
    final static TestEntity TEST_ENTITY = new TestEntity();

    /**
     * 准备好TEST_OBJECT对象的各个参数
     */
    static {
        TEST_ENTITY.setId(123456L);
        TEST_ENTITY.setText("this is a message for serializer test");
        TEST_ENTITY.setCreateTime(LocalDateTime.now());
    }


    /**
     * 反序列化测试(JSON -> Object)，入参是JSON字符串
     *
     * @param json JSON字符串
     *
     * @return
     *
     * @throws IOException
     */
    public TestEntity deserializeJSONStr(String json) throws IOException {

        JsonParser jsonParser = jsonFactory.createParser(json);

        if(jsonParser.nextToken() != JsonToken.START_OBJECT) {
            jsonParser.close();
            logger.error("起始位置没有大括号");
            throw new IOException("起始位置没有大括号");
        }

        TestEntity result = new TestEntity();

        try {
            // Iterate over object fields:
            while(jsonParser.nextToken() != JsonToken.END_OBJECT) {

                String fieldName = jsonParser.getCurrentName();

                logger.info("正在解析字段 [{}]", jsonParser.getCurrentName());

                // 解析下一个
                jsonParser.nextToken();

                switch(fieldName) {
                    case "id":
                        result.setId(jsonParser.getLongValue());
                        break;
                    case "text":
                        result.setText(jsonParser.getText());
                        break;
                    case "createTime":
                        result.setCreateTime(LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        break;
                    default:
                        logger.error("未知字段 '" + fieldName + "'");
                        throw new IOException("未知字段 '" + fieldName + "'");
                }
            }
        } catch(IOException e) {
            logger.error("反序列化出现异常 :", e);
        } finally {
            jsonParser.close(); // important to close both parser and underlying File reader
        }

        return result;
    }

    /**
     * 序列化测试(Object -> JSON)
     *
     * @param entity
     *
     * @return 由对象序列化得到的JSON字符串
     */
    public String serialize(TestEntity entity) throws IOException {
        String rlt;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JsonGenerator jsonGenerator = jsonFactory.createGenerator(byteArrayOutputStream, JsonEncoding.UTF8);

        try {
            jsonGenerator.useDefaultPrettyPrinter();

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", entity.getId());
            jsonGenerator.writeStringField("text", entity.getText());
            jsonGenerator.writeStringField("createTime", entity.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            jsonGenerator.writeEndObject();
        } catch(IOException e) {
            logger.error("序列化出现异常 :", e);
        } finally {
            jsonGenerator.close();
        }

        // 一定要在
        rlt = byteArrayOutputStream.toString();

        return rlt;
    }


    public static void main(String[] args) throws Exception {

        JacksonBaseDemo streamingDemo = new JacksonBaseDemo();

        // 执行一次对象转JSON操作
        logger.info("********************执行一次对象转JSON操作********************");
        String serializeResult = streamingDemo.serialize(TEST_ENTITY);
        logger.info("序列化结果是JSON字符串 : \n{}\n\n", serializeResult);

        // 用本地字符串执行一次JSON转对象操作
        logger.info("********************执行一次本地JSON反序列化操作********************");
        TestEntity deserializeResult = streamingDemo.deserializeJSONStr(TEST_JSON_STR);
        logger.info("\n本地JSON反序列化结果是个java实例 : \n{}\n\n", deserializeResult);
    }

}
