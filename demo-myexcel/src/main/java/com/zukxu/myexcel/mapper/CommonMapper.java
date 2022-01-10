package com.zukxu.myexcel.mapper;

import com.zukxu.myexcel.entity.BsAreaCounty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonMapper {
    List<BsAreaCounty> getAreaCode();

    @Select("select * from bs_area_county")
    List<BsAreaCounty> listArea();

    /**
     * 根据cityId 查询 地市数据
     *
     * @param cityId
     * @param level
     * @return
     */
    List<BsAreaCounty> getChildArea(@Param("cityId") String cityId, @Param("level") String level);

    /**
     * 根据cityId level查询 地市数据
     *
     * @param cityId
     * @param level
     * @return
     */
    List<BsAreaCounty> getUserArea(@Param("cityId") String cityId, @Param("level") String level);

    List<BsAreaCounty> getAreaCodeList();

}