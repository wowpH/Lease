package com.ph.lease.controller;

import com.ph.lease.entity.Customer;
import com.ph.lease.entity.Material;
import com.ph.lease.entity.Message;
import com.ph.lease.service.CustomerService;
import com.ph.lease.service.MaterialService;
import com.ph.lease.service.OrderDetailService;
import com.ph.lease.service.OrderService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author PengHao
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/index.htm")
    public String index() {
        return "admin/index";
    }

    @RequestMapping("/customer.htm")
    public String customer() {
        return "admin/customer";
    }

    @RequestMapping("/material.htm")
    public String toMaterial() {
        // Log.log("AdminController类 toMaterial方法", "显示材料管理页面");
        return "admin/material";
    }

    @RequestMapping("add-customer.htm")
    public String addCustomer() {
        // Log.log("AdminController类 addCustomer方法", "显示添加用户页面");
        return "admin/add-customer";
    }

    @RequestMapping("/order.htm")
    public String toOrder() {
        // Log.log("AdminController 类 toOrder 方法", "跳转到订单页面");
        return "admin/order";
    }

    @RequestMapping("/change-password.htm")
    public String toChangePassword() {
        // Log.log("AdminController 类 toOrder 方法", "跳转到订单页面");
        return "admin/change-password";
    }

    @RequestMapping("/list-select-customer.do")
    @ResponseBody
    public String doListSelectCustomer() {
        // Log.log("获取客户信息");
        List<Customer> allCustomers = customerService.selectOrderByOrderCountDesc();
        // for (Customer c : allCustomers) {
        //     Log.log(c.toString());
        // }
        // Message message = new Message();
        // message.setMsg(allCustomers.toString());
        // message.setMsgCode(Message.SF_SUCCESS);
        // return message;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "客户信息");
        jsonObject.put("count", allCustomers.size());
        jsonObject.put("data", allCustomers);

        // Log.log("返回给前端的JSON数据", jsonObject.toString());

        return jsonObject.toString();
    }

    @RequestMapping("/list-select-material.do")
    @ResponseBody
    public String doListSelectMaterial() {
        // Log.log("AdminController.doListSelectMaterial");
        // Log.log("获取材料信息");
        List<Material> allMaterials = materialService.selectOrderByStocksDesc();
        // for (Material m : allMaterials) {
        //     Log.log(m.toString());
        // }
        // Message message = new Message();
        // message.setMsg(allCustomers.toString());
        // message.setMsgCode(Message.SF_SUCCESS);
        // return message;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "材料信息");
        jsonObject.put("data", allMaterials);
        jsonObject.put("count", allMaterials.size());

        // Log.log("返回给前端的JSON数据", jsonObject.toString());

        return jsonObject.toString();
    }

    @RequestMapping("/list-material.do")
    @ResponseBody
    public String doListMaterials(Integer page, Integer limit, Material material) {
        // Log.log("AdminController 类 doListMaterials 方法", "获取材料信息");
        // Log.log("page", page);
        // Log.log("limit", limit);
        // Log.log("待查询的材料", material.toString());

        // 按照库存量降序排序
        List<Material> allMaterials = materialService.selectFuzzyOrderByStocksDesc(material);

        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, allMaterials.size());

        List<Material> currentPageMaterials = allMaterials.subList(fromIndex, toIndex);

        // Log.log("第" + page + "页的" + limit + "个材料是");
        // for (Material m : currentPageMaterials) {
        //     Log.log(m.toString());
        // }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "材料信息");
        jsonObject.put("data", currentPageMaterials);
        jsonObject.put("count", allMaterials.size());

        // Log.log("返回给前端的数据是", jsonObject.toString());

        return jsonObject.toString();
    }

    @RequestMapping("/add-material.do")
    @ResponseBody
    public Message doAddMaterial(Material material) {
        // Log.log("AdminController 类 doAddMaterial 方法", "添加材料到数据库");
        // Log.log("待添加的材料", material.toString());

        int count = 0;
        Message message = new Message();

        // 检查是否存在该材料，相同的条件是，名称，型号，规格都相同
        count = materialService.selectByNameModelSpecification(material);
        if (count > 0) {
            message.setMsgCode(message.getFAILURE());
            message.setMsg("材料【" + material.getName() + "】已存在，请不要重复添加！");
            // Log.log("存在", message.toString());
            return message;
        } else {
            // Log.log("该材料不存在，可以添加", material.toString());
        }

        count = materialService.insert(material);
        if (count == 1) {
            // Log.log("添加成功");
            message.setMsgCode(message.getSUCCESS());
        } else {
            // Log.log("添加失败", "可能是该材料已存在");
            message.setMsgCode(message.getFAILURE());
            message.setMsg("未知原因！");
        }
        // Log.log("返回值", message.toString());
        return message;
    }

    @RequestMapping("/edit-material.do")
    @ResponseBody
    public Message doEditMaterial(Material material) {
        // Log.log("AdminController 类 doEditMaterial 方法", material.toString());
        int count = 0;
        Message message = new Message();

        count = materialService.selectByNameModelSpecification(material);
        if (count > 0) {
            message.setMsgCode(Message.SF_SUCCESS);
            message.setMsg(material.getName() + "已存在");
            // Log.log("已存在", message.toString());
            return message;
        }

        count = materialService.updateByPrimaryKey(material);
        if (count == 1) {
            message.setMsgCode(Message.SF_SUCCESS);
            message.setMsg("更新成功");
        } else {
            message.setMsgCode(Message.SF_FAILURE);
        }

        // Log.log("返回值", message.toString());
        return message;
    }

    @RequestMapping("/delete-material.do")
    @ResponseBody
    public Message doDeleteMaterial(Material material) {
        // Log.log("AdminController 类 doDeleteMaterial 方法", "删除材料操作");
        // Log.log("待删除的材料", material.toString());

        // 软删除，其实就是将有效性标志位改为false
        int count = materialService.deleteByPrimaryKey(material.getId());
        Message message = new Message();
        if (count == 1) {
            // Log.log("删除成功");
            message.setMsgCode(Message.SF_SUCCESS);
        } else {
            // Log.log("删除失败");
            message.setMsgCode(Message.SF_FAILURE);
        }
        return message;
    }

}
