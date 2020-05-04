package com.ph.lease.service;

import com.ph.lease.entity.Material;
import com.ph.lease.entity.Message;
import com.ph.lease.entity.OrderDetail;

import java.util.List;

/**
 * @author PengHao
 */
public interface MaterialService {

    /**
     * 添加材料
     *
     * @param material
     * @return
     */
    int insert(Material material);

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
     * 根据主键软删除材料
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 将订单明细中的材料数量从库存中减掉
     *
     * @param orderDetails 需要处理的订单明细
     * @return 处理结果：是否成功，失败的原因
     */
    Message reduceStocks(List<OrderDetail> orderDetails);

    /**
     * 将订单明细中的材料数量增加到库存中
     *
     * @param orderDetails 需要处理的订单明细
     * @return 处理结果：是否成功，失败的原因，理论上不会失败
     */
    Message increaseStocks(List<OrderDetail> orderDetails);

}
