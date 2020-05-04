package com.ph.lease.service.impl;

import com.ph.lease.dao.MaterialMapper;
import com.ph.lease.entity.Material;
import com.ph.lease.entity.Message;
import com.ph.lease.entity.OrderDetail;
import com.ph.lease.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHao
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public int insert(Material material) {
        return materialMapper.insert(material);
    }

    @Override
    public List<Material> selectFuzzyOrderByStocksDesc(Material material) {
        return materialMapper.selectFuzzyOrderByStocksDesc(material);
    }

    @Override
    public int selectByNameModelSpecification(Material material) {
        return materialMapper.selectByNameModelSpecification(material);
    }

    @Override
    public List<Material> selectOrderByStocksDesc() {
        return materialMapper.selectOrderByStocksDesc();
    }

    @Override
    public int updateByPrimaryKey(Material material) {
        return materialMapper.updateByPrimaryKey(material);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return materialMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Message reduceStocks(List<OrderDetail> orderDetails) {
        // Log.log("MaterialServiceImpl.reduceStocks()");
        // Log.log("明细数", orderDetails.size());
        Message message = new Message();
        for (int i = 0; i < orderDetails.size(); ++i) {
            OrderDetail orderDetail = orderDetails.get(i);
            // Log.log(i, orderDetail.toString());
            int count = materialMapper.reduceStocksByPrimaryKey(orderDetail);
            if (count == 0) {
                Material material = new Material();
                material.setId(orderDetail.getMid());
                material = materialMapper.selectByPrimaryKey(material);
                message.setMsg("材料【" + material.getName() + "】库存不足！");
                // 出现不足后，需要将之前已经减掉的数量重新恢复
                increaseStocks(orderDetails.subList(0, i));
                // Log.log("减少失败");
                return message;
            }
        }
        message.setMsgCode(Message.SF_SUCCESS);
        message.setMsg("减少成功");
        // Log.log("返回值", message.toString());
        return message;
    }

    @Override
    public Message increaseStocks(List<OrderDetail> orderDetails) {
        Message message = new Message();
        for (int i = 0; i < orderDetails.size(); ++i) {
            OrderDetail orderDetail = orderDetails.get(i);
            int count = materialMapper.increaseStocksByPrimaryKey(orderDetail);
            if (count == 0) {
                message.setMsg("恢复库存量失败");
                return message;
            }
        }
        message.setMsgCode(Message.SF_SUCCESS);
        message.setMsg("恢复库存量成功");
        return message;
    }

}
