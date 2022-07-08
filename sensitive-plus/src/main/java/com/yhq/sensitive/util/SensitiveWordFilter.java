package com.yhq.sensitive.util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词过滤
 * @author yhq
 * @date 2021年9月10日 14点53分
 */
@Slf4j
public class SensitiveWordFilter {

	public Map sensitiveWordMap;

	/**
	 * 最小匹配规则
	 */
	public static int minMatchTYpe = 1;

	/**
	 * 最大匹配规则
	 */
	public static int maxMatchType = 2;

	@Getter
	@Setter
	private String filePath;
	
	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitiveWordFilter(String filePath){
		sensitiveWordMap = new SensitiveWordInit().initKeyWord(filePath);
	}

	/**
	 * 判断文字是否包含敏感字符
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 */
	public boolean isContainSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			//判断是否包含敏感字符
			int matchFlag = this.checkSensitiveWord(txt, i, matchType);
			//大于0存在，返回true
			if(matchFlag > 0){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文字中的敏感词
	 * @param txt txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			//判断是否包含敏感字符
			int length = checkSensitiveWord(txt, i, matchType);
			//存在,加入list中
			if(length > 0){
				sensitiveWordList.add(txt.substring(i, i+length));
				//减1的原因，是因为for会自增
				i = i + length - 1;
			}
		}
		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @return
	 */
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		//获取所有的敏感词
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word;
		String replaceString;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * @param replaceChar
	 * @param length
	 * @return
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return 如果存在，则返回敏感词字符的长度，不存在返回0
	 */
	public int checkSensitiveWord(String txt,int beginIndex,int matchType){
		//敏感词结束标识位：用于敏感词只有1位的情况
		boolean  flag = false;
		//匹配标识数默认为0
		int matchFlag = 0;
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			//获取指定key
			nowMap = (Map) nowMap.get(word);
			//存在，则判断是否为最后一个
			if(nowMap != null){
				//找到相应key，匹配标识+1
				matchFlag++;
				//如果为最后一个匹配规则,结束循环，返回匹配标识数
				if("1".equals(nowMap.get("isEnd"))){
					//结束标志位为true
					flag = true;
					//最小规则，直接返回,最大规则还需继续查找
					if(SensitiveWordFilter.minMatchTYpe == matchType){
						break;
					}
				}
			}
			else{
				//不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){
			//长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}

}
