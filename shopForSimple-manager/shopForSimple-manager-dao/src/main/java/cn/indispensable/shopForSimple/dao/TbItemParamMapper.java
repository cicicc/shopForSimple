package cn.indispensable.shopForSimple.dao;

import cn.indispensable.shopForSimple.pojo.TbItemParam;
import cn.indispensable.shopForSimple.pojo.TbItemParamExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbItemParamMapper {
    int countByExample(TbItemParamExample example);

    int deleteByExample(TbItemParamExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItemParam record);

    int insertSelective(TbItemParam record);

    List<TbItemParam> selectByExampleWithBLOBs(TbItemParamExample example);

    List<TbItemParam> selectByExample(TbItemParamExample example);

    TbItemParam selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByExampleWithBLOBs(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByExample(@Param("record") TbItemParam record, @Param("example") TbItemParamExample example);

    int updateByPrimaryKeySelective(TbItemParam record);

    int updateByPrimaryKeyWithBLOBs(TbItemParam record);

    int updateByPrimaryKey(TbItemParam record);
}