package com.zukxu.excel.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReportMapper {

    //TODO sql语法问题
    @Select("select COUNTY_ID from bs_area_county where COUNTY_NAME='#{countyName}'")
    String getIdByName(@Param("countyName") String countyName);
}