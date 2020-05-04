package com.ph.lease.dao;

import com.ph.lease.entity.Material;
import com.ph.lease.entity.OrderDetail;

import java.util.List;

/**
 * @author PengHao
 */
public interface MaterialMapper {

    /**
     * 添加材料
     *
     * @param material
     * @return
     */
    int insert(Material material);

    /**
     * 根据ID获取材料信息
     *
     * @param material
     * @return
     */
    Material selectByPrimaryKey(Material material);

    /**
     * 根据名称模糊查询，按照库存量降序排序
     *
     * @param material
     * @return
     */
    List<Material> selectFuzzyOrderByStocksDesc(Material material);

    /**
     * 根据名称、型号、规格查询
     *
     * @param material
     * @return
     */
    int selectByNameModelSpecification(Material material);

    List<Material> selectOrderByStocksDesc();

    /**
     * 根据id更新材料信息
     *
     * @param material
     * @return
     */
    int updateByPrimaryKey(Material material);

    /**
     * 根据ID减少库存量
     *
     * @param orderDetail 只需要mid和amount两个属性即可
     * @return 操作成功的记录数
     */
    int reduceStocksByPrimaryKey(OrderDetail orderDetail);

    /**
     * 根据ID增加库存量
     *
     * @param orderDetail 只需要ID和amount两个属性
     * @return 操作成功的记录数
     */
    int increaseStocksByPrimaryKey(OrderDetail orderDetail);

    /**
     * 根据主键软删除材料
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

}
