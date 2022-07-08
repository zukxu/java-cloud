package com.yhq.sensitive.util;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @author yhq
 * @date 2021年9月10日 14点53分
 */
@NoArgsConstructor
public class SensitiveWordInit {

    /**
     * 字符编码
     */
    private final String encoding = "utf-8";


    @SuppressWarnings("rawtypes")
    public HashMap sensitiveWordMap;

    /**
     * 读取敏感词库，把敏感词放到hash里面
     * @return 敏感词库map
     */
    @SneakyThrows
    public Map initKeyWord(String filePath){

        //读取敏感词库
        Set<String> keyWordSet = readSensitiveWordFile(filePath);
        //将敏感词库加入到HashMap中
        addSensitiveWordToHashMap(keyWordSet);

        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     *      isEnd = 0
     *      国 = {<br>
     *      	 isEnd = 1
     *           人 = {isEnd = 0
     *                民 = {isEnd = 1}
     *                }
     *           男  = {
     *           	   isEnd = 0
     *           		人 = {
     *           			 isEnd = 1
     *           			}
     *           	}
     *           }
     *      }
     *  五 = {
     *      isEnd = 0
     *      星 = {
     *      	isEnd = 0
     *      	红 = {
     *              isEnd = 0
     *              旗 = {
     *                   isEnd = 1
     *                  }
     *              }
     *      	}
     *      }
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key ;
        Map nowMap ;
        Map<String, String> newWorMap ;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            //关键字
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                //转换成char型
                char keyChar = key.charAt(i);
                //获取
                Object wordMap = nowMap.get(keyChar);

                //如果存在该key，直接赋值
                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }else{
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>(8);
                    //不是最后一个
                    newWorMap.put("isEnd", "0");
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    //最后一个
                    nowMap.put("isEnd", "1");
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     * @param filePath 文件路径如： D:\SensitiveWord.txt
     */
    @SneakyThrows
    private Set<String> readSensitiveWordFile(String filePath){
        Set<String> set;
        //读取文件
        File file = new File(filePath);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);
        try {
            //文件流是否存在
            if(!file.isFile() || !file.exists()){
                throw new Exception("敏感词库文件不存在");
            }
            set = new HashSet<>();
            BufferedReader bufferedReader = new BufferedReader(read);
            String txt ;
            //读取文件，将文件内容放入到set中
            while((txt = bufferedReader.readLine()) != null){
                set.add(txt);
            }

        } catch (Exception e) {
            throw e;
        }finally{
            //关闭文件流
            read.close();
        }
        return set;
    }
}
