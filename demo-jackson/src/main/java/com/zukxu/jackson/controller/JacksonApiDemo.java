package com.zukxu.jackson.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zukxu.jackson.controller.bean.TestEntity;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * api demo
 * </p>
 *
 * @author xupu
 * @since 2022/5/25 14:20:36
 */
public class JacksonApiDemo {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        //开启Jackson对LocalDateTime的支持
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws IOException {
        //serializerObject();
        //deserializerObject();
        //serializerMap();
        //serializerJsonNode();
        //toArray();
        toList();
    }

    private static List generateData(int n, int ch) {
        List<TestEntity> objList = new ArrayList<>();
        List<Map> mapList = new ArrayList<>();
        if(ch == 1) {
            for(int i = 0; i < n; i++) {
                Map<String, Object> addr = new HashMap<>();
                addr.put("city", "贵阳" + i);
                addr.put("street", "金融路" + i);

                List<String> hobby = new ArrayList<>();
                hobby.add("sing");
                hobby.add("dance");
                Map<String, Object> mapObject = new HashMap<>();
                mapObject.put("name", "Jackson" + i);
                mapObject.put("age", 18);
                mapObject.put("sex", "男");
                mapObject.put("Tel", "15885211479");
                mapObject.put("addr", addr);
                mapObject.put("hobby", hobby);
                mapObject.put("isAdmin", i / 2 == 0);
                mapList.add(mapObject);
            }
            return mapList;
        }
        if(ch == 2) {
            for(int i = 0; i < n; i++) {
                TestEntity entity = new TestEntity().setId((12345L * i)).setText("Jackson测试json序列化和反序列化" + i).setCreateTime(LocalDateTime.now());
                objList.add(entity);
            }
            return objList;
        }
        return null;
    }

    private static void toList() throws IOException {
        String jsonArrStr = "[{\"id\":0,\"text\":\"Jackson测试json序列化和反序列化0\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":12345,\"text\":\"Jackson测试json序列化和反序列化1\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":24690,\"text\":\"Jackson测试json序列化和反序列化2\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":37035,\"text\":\"Jackson测试json序列化和反序列化3\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":49380,\"text\":\"Jackson测试json序列化和反序列化4\",\"createTime\":\"2022-05-26T15:19:16.191\"}]";
        List<TestEntity> entityList = mapper.readValue(jsonArrStr, new TypeReference<List<TestEntity>>() {});
        for(TestEntity entity : entityList) {
            System.out.println(entity.toString());
        }
    }

    private static void toArray() throws IOException {
        String jsonArrStr = "[{\"id\":0,\"text\":\"Jackson测试json序列化和反序列化0\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":12345,\"text\":\"Jackson测试json序列化和反序列化1\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":24690,\"text\":\"Jackson测试json序列化和反序列化2\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":37035,\"text\":\"Jackson测试json序列化和反序列化3\",\"createTime\":\"2022-05-26T15:19:16.191\"},{\"id\":49380,\"text\":\"Jackson测试json序列化和反序列化4\",\"createTime\":\"2022-05-26T15:19:16.191\"}]";
        TestEntity[] entityArr = mapper.readValue(jsonArrStr, TestEntity[].class);
        for(TestEntity entity : entityArr) {
            System.out.println(entity.toString());
        }
    }

    /**
     * json反序列化Map
     *
     * @throws JsonProcessingException
     */
    private static void serializerJsonNode() throws JsonProcessingException {
        String mapJsonStr = "{\"sex\":\"男\",\"name\":\"Jackson\",\"Tel\":\"15885211479\",\"isAdmin\":false,\"addr\":{\"city\":\"贵阳\",\"street\":\"金融路\"},\"age\":18,\"hobby\":[\"sing\",\"dance\"]}";
        JsonNode jsonNode = mapper.readTree(mapJsonStr);
        String name = jsonNode.get("name").asText();
        int age = jsonNode.get("age").asInt();
        String city = jsonNode.get("addr").get("city").asText();
        String street = jsonNode.get("addr").get("street").asText();
        System.out.println(name + " " + age + " " + city + " " + street);

    }

    /**
     * map序列化
     *
     * @throws JsonProcessingException
     */
    private static void serializerMap() throws JsonProcessingException {
        Map<String, Object> map = (Map<String, Object>) generateData(1, 1).get(0);
        String mapJsonStr = mapper.writeValueAsString(map);
        System.out.println(mapJsonStr);
        Map<String, Object> mapFromStr = mapper.readValue(mapJsonStr, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * 对象反序列化
     *
     * @throws IOException
     */
    private static void deserializerObject() throws IOException {
        String jsonStr = "{\"id\":12345,\"text\":\"Jackson测试json序列化和反序列化\",\"createTime\":\"2022-05-26T14:40:56.85\"}";
        TestEntity testEntity = mapper.readValue(jsonStr, TestEntity.class);
        TestEntity testEntityFile = mapper.readValue(new File("demo-jackson/testEntity.json"), TestEntity.class);
        byte[] array = mapper.writeValueAsBytes(testEntity);
        TestEntity testEntityArray = mapper.readValue(array, TestEntity.class);
    }

    /**
     * 对象序列化
     *
     * @throws IOException
     */
    private static void serializerObject() throws IOException {
        //单个对象序列化
        TestEntity entity = (TestEntity) generateData(1, 2).get(0);
        //对象转字符串：
        String jsonStr = mapper.writeValueAsString(entity);
        System.out.println(jsonStr);
        //对象转文件
        mapper.writeValue(new File("testEntity.json"), entity);
        //对象转数组
        byte[] array = mapper.writeValueAsBytes(entity);
        System.out.println(array);
    }

}
