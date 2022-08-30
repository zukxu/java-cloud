package com.zukxu.demoliteflow.model.extend;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 扩展参数
 * </p>
 *
 * @author xupu
 * @since 2022-03-31 9:34
 */
@Data
@Accessors(chain = true)
public class Para {

    @JsonProperty("ParaID")
    @JSONField(name = "ParaID")
    private String ParaID;

    @JsonProperty("ParaVal")
    @JSONField(name = "ParaVal")
    private String ParaVal;

    public static Para[] buildParaList(String str) {
        return buildParaList(JSON.parseObject(str, Map.class));
    }

    public static Para[] buildParaList(Map map) {
        List<Para> list = new ArrayList<>();
        for(Object s : map.keySet()) {
            Para para = new Para().setParaID(String.valueOf(s))
                                  .setParaVal(ObjectUtil.isNotEmpty(map.get(s)) ? String.valueOf(map.get(s)) : "");
            list.add(para);
        }
        return list.toArray(new Para[0]);
    }

    public static Map<String, Object> toMap(List<Para> paraList) {
        HashMap<String, Object> map = new HashMap<>();
        paraList.forEach(t -> map.put(t.getParaID(), t.getParaVal()));
        return map;
    }

}
