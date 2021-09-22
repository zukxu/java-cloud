## pinyin4j

对于汉字转换拼音的过程中，如果我们想要对拼音标注音调符号  
则字符输出**setVCharType**一定要选择**HanyuPinyinVCharType.WITH_U_UNICODE**

```java
 HanyuPinyinOutputFormat configFormat=new HanyuPinyinOutputFormat();
        configFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        configFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        configFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        return configFormat;
```