package com.zukxu.myexcel.model;

import com.zukxu.myexcel.constant.Const;
import com.zukxu.myexcel.entity.BsAreaCounty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tree select树结构实体类
 *
 * @author zukxu
 */
@Data
@Accessors(chain = true)
public class TreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /**
     * 节点名称
     */
    private String label;
    /**
     * 父id
     */
    private String pid;
    /**
     * 组织机构level
     */
    private String level;

    /**
     * 子节点
     */
    private List<TreeSelect> children;

    public TreeSelect() {}


    public TreeSelect(BsAreaCounty entity, String level) {
        this.level=level;
        if (Const.AREA_LEVEL_PROVINCE.equals(level)) {
            this.id = entity.getProvinceId();
            this.label = entity.getProvinceName();
            this.children = new ArrayList<>();
        }
        if (Const.AREA_LEVEL_CITY.equals(level)) {
            this.id = entity.getCityId();
            this.label = entity.getCityName();
            this.pid = entity.getProvinceId();
            this.children = new ArrayList<>();
        }
        if (Const.AREA_LEVEL_COUNTY.equals(level)) {
            this.id = entity.getCountyId();
            this.pid = entity.getCityId();
            this.label = entity.getCountyName();
            this.children = new ArrayList<>();
        }
    }
}
