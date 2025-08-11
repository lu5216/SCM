package com.system.order.impl.salesOrder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.BigDecimalUtil;
import com.system.common.utils.DateUtil;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.params.SalesOrderParam;
import com.system.order.api.salesOrder.params.SalesOrderSaveParam;
import com.system.order.api.salesOrder.service.ISalesOrderService;
import com.system.order.api.salesOrder.vo.SalesOrderVo;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.params.WarehouseItemSaveParam;
import com.system.order.api.warehouseOrder.service.IWarehouseItemService;
import com.system.order.impl.salesOrder.mapper.SalesOrderMapper;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysCurrent;
import com.system.systembase.api.basicConfiguration.service.ISysClientService;
import com.system.systembase.api.basicConfiguration.service.ISysCurrentService;
import com.system.systembase.api.redisTemplate.service.IRedisService;
import com.system.warehouse.api.goods.domain.WarehouseGoods;
import com.system.warehouse.api.goods.service.IWarehouseGoodsService;
import com.system.warehouse.api.goodsWarehouse.domain.Warehouse;
import com.system.warehouse.api.goodsWarehouse.domain.WarehouseArea;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseAreaService;
import com.system.warehouse.api.goodsWarehouse.service.IWarehouseService;
import com.system.warehouse.api.inventoryInfo.domain.GoodsInventoryInfo;
import com.system.warehouse.api.inventoryInfo.service.IGoodsInventoryInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-20 020 17:04
 */

@Slf4j
@Service
public class SalesOrderServiceImpl extends ServiceImpl<SalesOrderMapper, SalesOrder> implements ISalesOrderService {

    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;

    @Autowired
    private IGoodsInventoryInfoService goodsInventoryInfoService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    @Autowired
    private IWarehouseItemService warehouseItemService;

    @Autowired
    private ISysCurrentService sysCurrentService;

    @Autowired
    private ISysClientService sysClientService;

    @Autowired
    private IRedisService redisService;

    @Override
    public Integer getItemCount(SalesOrderParam param) {
        return this.baseMapper.getItemCount(param);
    }

    @Override
    public List<SalesOrderVo> getItemList(SalesOrderParam param) {
        return this.baseMapper.getItemList(param);
    }

    @Override
    public Integer getGoodsCount(SalesOrderParam param) {
        return this.baseMapper.getGoodsCount(param);
    }

    @Override
    public List<SalesOrderVo> getGoodsList(SalesOrderParam param) {
        return this.baseMapper.getGoodsList(param);
    }


    @Override
    public Page<SalesOrderVo> selectPage(SalesOrderParam param) {
        if (param.getPageIndex() == null) {
            param.setPageIndex(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(20);
        }

        // 根据维度获取数据
        int count = 0;
        List<SalesOrderVo> list = null;
        if (param.getDimensionality() == 0) {
            count = this.getItemCount(param);
            list = this.getItemList(param);
        } else if (param.getDimensionality() == 1) {
            count = this.getGoodsCount(param);
            list = this.getGoodsList(param);
        }

        // 分页查询
        Page<SalesOrderVo> page = new Page<>(param.getPageIndex(), param.getPageSize());
        page.setTotal(count);
        page.setRecords(list);
        if (list != null && list.size() > 0) {
            for (SalesOrderVo vo : list) {
                // 获取库存数量
                if (vo.getGoodsId() != null) {
                    GoodsInventoryInfo goodsInventoryInfo = goodsInventoryInfoService.getOneByGoodsId(vo.getGoodsId());
                    vo.setQuantity(goodsInventoryInfo != null ? goodsInventoryInfo.getQuantity() : null);
                }

                // 获取客户名称
                if (vo.getClientId() != null) {
                    SysClient client = sysClientService.getById(vo.getClientId());
                    vo.setClientName(client != null ? client.getRegisterName() : null);
                }

                // 获取仓库信息
                Warehouse warehouse = warehouseService.getById(vo.getWarehouseId());
                WarehouseArea warehouseArea = warehouseAreaService.getById(vo.getWarehouseAreaId());
                vo.setWarehouse(warehouse.getWarehouseName() + "(" + warehouse.getWarehouseCode() + ")-" +
                        warehouseArea.getWarehouseAreaName() + "(" + warehouseArea.getWarehouseAreaCode() + ")");

                // 获取币制名称
                if (vo.getCurrencyId() != null) {
                    SysCurrent current = sysCurrentService.getById(vo.getCurrencyId());
                    vo.setCurrencyName(current.getCurrentName());
                }
            }
        }
        return page;
    }

    @Override
    public String insertAndUpdate(SalesOrderSaveParam param) throws ParseException {
        if (param.getId() == null) {
            // 新增
            String errMSG = this.insertValidated(param);
            if (errMSG != null) {
                return errMSG;
            }
            // 填充数据
            SalesOrder salesOrder = new SalesOrder();
            this.fillInsertData(salesOrder,param);
            this.baseMapper.insert(salesOrder);
        } else {
            // 修改
            String errMSG = this.updateValidated(param);
            if (errMSG != null) {
                return errMSG;
            }
            // 根据ID查询
            SalesOrder salesOrder = this.baseMapper.selectById(param.getId());
            if (salesOrder.getItemStatus() == 3 || salesOrder.getItemStatus() == 4) {
                return "订单状态为【已完成】或【已取消】的订单, 无法更新!";
            }
            this.fillUpdateData(salesOrder, param);
            this.baseMapper.updateById(salesOrder);
        }
        return null;
    }


    /**
     *  新增校验
     * @param param
     * @return
     */
    private String insertValidated(SalesOrderSaveParam param) {
        if (param.getItemNo() != null && !param.getItemNo().equals("")) {
            return "系统自动生成单号,无需手动输入!";
        }
        if (param.getGoodsId() == null) {
            return "商品ID不能为空!";
        }
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(param.getGoodsId());
        if (warehouseGoods == null) {
            return "不存在ID为【" + param.getGoodsId() + "】的商品!";
        }
        if (param.getNumber() == null || param.getNumber() <= 0) {
            return "销售数量不能小于0!";
        }
        GoodsInventoryInfo info = goodsInventoryInfoService.getOneByGoodsId(param.getGoodsId());
        if (info.getQuantity() < param.getNumber()) {
            return "该商品库存数量为【" + info.getQuantity()+ "】,小于销售数量，请维护!";
        }
        if (param.getClientId() == null) {
            return "客户ID不能为空!";
        }
        SysClient client = sysClientService.getById(param.getClientId());
        if (client == null) {
            return "不存在ID为【" + param.getClientId() + "】的客户!";
        }
        if (StringUtils.isBlank(param.getDeliveryAddress())) {
            return "交付地点不能为空!";
        }
        if (StringUtils.isBlank(param.getDeliveryData())) {
            return "交付日期不能为空!";
        }
        if (param.getIsAudit() == null) {
            return "是否审核不能为空!";
        }
        return null;
    }


    /**
     *  填充新增数据
     * @param param
     */
    private void fillInsertData(SalesOrder salesOrder, SalesOrderSaveParam param) throws ParseException {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }

        salesOrder.setItemStatus(1);
        if (param.getIsAudit()) {
            salesOrder.setAuditStatus(0);
        }
        salesOrder.setGoodsId(param.getGoodsId());
        salesOrder.setNumber(param.getNumber());
        salesOrder.setClientId(param.getClientId());
        salesOrder.setRemark(param.getRemark());
        salesOrder.setDeliveryDate(DateUtil.getFormatForDate(param.getDeliveryData(), "yyyy-MM-dd"));
        salesOrder.setDeliveryAddress(param.getDeliveryAddress());
        salesOrder.setIsAudit(param.getIsAudit());

        // 生成单号
        String itemNo = redisService.createNo("SO");
        salesOrder.setItemNo(itemNo);

        // 获取商品库相关数据
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(param.getGoodsId());
        salesOrder.setWarehouseId(warehouseGoods.getPreWarehouseId());
        salesOrder.setWarehouseAreaId(warehouseGoods.getPreWarehouseAreaId());
        salesOrder.setCurrencyId(warehouseGoods.getCurrencyId());
        salesOrder.setUnitPrice(warehouseGoods.getUnitPrice());
        salesOrder.setAmount(BigDecimalUtil.multiply(warehouseGoods.getUnitPrice(), BigDecimal.valueOf(param.getNumber())));

        // 填充新增用户数据
        this.fillInsertUserData(salesOrder, user);
    }

    /**
     *  填充新增用户数据
     * @param salesOrder
     * @param user
     */
    private void fillInsertUserData(SalesOrder salesOrder, SysUserVo user) {
        // 填充用户数据
        salesOrder.setCompanyUid(user.getCompanyUid());
        salesOrder.setCreateTime(new Date());
        salesOrder.setCreateUserName(user.getLoginId());
    }

    /**
     * 修改校验
     * @param param
     * @return
     */
    private String updateValidated(SalesOrderSaveParam param) {
        if (param.getId() == null) {
            return "ID不能为空!";
        }
        return null;
    }

    /**
     *  填充修改数据
     * @param salesOrder
     * @param param
     */
    private void fillUpdateData(SalesOrder salesOrder, SalesOrderSaveParam param) throws ParseException {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            throw new CustomerAuthenticationException("账号未登入，请重新登入!");
        }

        salesOrder.setId(param.getId());
        salesOrder.setRemark(param.getRemark());
        salesOrder.setDeliveryDate(DateUtil.getFormatForDate(param.getDeliveryData(), "yyyy-MM-dd"));
        salesOrder.setDeliveryAddress(param.getDeliveryAddress());

        // 填充修改用户数据
        this.fillUpdateUserData(salesOrder, user);
    }

    /**
     *  填充修改用户数据
     * @param salesOrder
     * @param user
     */
    private void fillUpdateUserData(SalesOrder salesOrder, SysUserVo user) {
        // 填充用户数据
        salesOrder.setUpdateTime(new Date());
        salesOrder.setUpdateUserName(user.getLoginId());
    }


    @Override
    public String orderReceiving(Integer id) {
        if (id == null) {
            return "ID不能为空";
        }
        SysUserVo user = SecurityUtils.getUser();
        SalesOrder salesOrder = this.baseMapper.selectById(id);
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (salesOrder.getItemStatus() != 1) {
            return "工单状态不为【待接单】,无法接单!";
        }
        salesOrder.setItemStatus(2);
        this.fillUpdateUserData(salesOrder, user);
        this.baseMapper.updateById(salesOrder);
        return null;
    }

    @Override
    public String createOutWarehouseItem(Integer id) {
        if (id == null) {
            return "ID不能为空";
        }
        SysUserVo user = SecurityUtils.getUser();
        SalesOrder salesOrder = this.baseMapper.selectById(id);
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (salesOrder.getItemStatus() != 2) {
            return "工单状态不为【进行中】,无法创建出库单!";
        }

        // 判断是否已创建出库单
        List<WarehouseItem> warehouseItemList = warehouseItemService.findByAssociatedOrderNo(salesOrder.getItemNo(), user.getCompanyUid());
        if (warehouseItemList.size() > 0) {
            return "该销售订单已创建出库单!";
        }

        // 生成出库订单
        WarehouseItemSaveParam param = new WarehouseItemSaveParam();
        SysClient client = sysClientService.getById(salesOrder.getClientId());
        param.setType(2);
        param.setGoodsDestination(client.getClientCode());
        param.setGoodsId(salesOrder.getGoodsId());
        param.setNumber(salesOrder.getNumber());
        param.setIsAudit(salesOrder.getIsAudit());
        param.setAssociatedOrderNo(salesOrder.getItemNo());
        param.setExpectedInOutDate(DateUtil.getDateForFormat(salesOrder.getDeliveryDate(), DateUtil.YMD_HMS));
        param.setRemark(salesOrder.getRemark());
        String result = this.warehouseItemService.insertAndUpdate(param);
        return result;
    }


    /**
     *  TODO 销售订单流程图
     *  1、创建工单 - ItemStatus=1
     *  2、接单 - ItemStatus=2
     *  3、创建出库订单 - 走 TODO 出入库流程
     *      出入库流程结束：（1）审批，ItemStatus=3
     *                   （2）不审批，ItemStatus=4, 结束
     *  4、审核
     *       (1）审核通过 - ItemStatus=4, 结束
     *      （2）审核不通过 - 打回到 2、接单 - ItemStatus=2的状态
     */
    @Override
    @Transactional
    public String auditById(Integer id, Integer auditStatus, String auditReason) {
        SysUserVo user = SecurityUtils.getUser();
        SalesOrder salesOrder = this.baseMapper.selectById(id);
        // 取消校验
        String errMSG = this.auditValidated(id, auditStatus, salesOrder, user);
        if (errMSG != null) {
            return errMSG;
        }
        salesOrder.setAuditStatus(auditStatus);
        salesOrder.setAuditUserName(user.getLoginId());
        salesOrder.setAuditDate(new Date());
        salesOrder.setAuditReason(auditReason);
        // 审核通过
        if (auditStatus == 1) {
            salesOrder.setItemStatus(4);
            // 变更库存信息
            this.adjustInventoryAndWarehouse(salesOrder);
        } else {
            salesOrder.setItemStatus(2);
        }
        this.fillUpdateUserData(salesOrder, user);
        this.baseMapper.updateById(salesOrder);
        return null;
    }

    /**
     *  校验配置
     * @param id
     * @param salesOrder
     * @param user
     * @return
     */
    private String auditValidated(Integer id, Integer auditStatus, SalesOrder salesOrder, SysUserVo user) {
        if (id == null) {
            return "ID不能为空!";
        }
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (auditStatus == null) {
            return "审核状态不能为空!";
        }
        if (!salesOrder.getIsAudit()) {
            return "该ID对应的销售单不需要审核!";
        }
        if (salesOrder.getAuditStatus() == 1) {
            return "请勿重复审核!";
        }
        if (salesOrder.getItemStatus() == 1) {
            return "工单状态为【待接单】的工单,无法审核!";
        } else if (salesOrder.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法审核!";
        } else if (salesOrder.getItemStatus() == 4) {
            return "工单状态为【已取消】的工单,无法审核!";
        }
        List<WarehouseItem> warehouseItemList = warehouseItemService.findByAssociatedOrderNo(salesOrder.getItemNo(), user.getCompanyUid());
        if (warehouseItemList.size() > 0) {
            WarehouseItem item = warehouseItemList.get(0);
            if (item.getItemStatus() != 3) {
                return "请先完成创建的出库单【" + item.getItemNo() + "】;";
            }
        }
        return null;
    }

    @Override
    public String cancelById(Integer id, String cancelReason) {
        SysUserVo user = SecurityUtils.getUser();
        SalesOrder salesOrder = this.baseMapper.selectById(id);
        // 取消校验
        String errMSG = this.cancelValidated(id, salesOrder, user);
        if (errMSG != null) {
            return errMSG;
        }
        // 设置工单状态为已取消
        salesOrder.setItemStatus(4);
        salesOrder.setCancelUserName(user.getLoginId());
        salesOrder.setCancelDate(new Date());
        salesOrder.setCancelReason(cancelReason);
        // 取消单将不再审核
        salesOrder.setIsAudit(false);
        // 填充修改用户数据
        this.fillUpdateUserData(salesOrder, user);
        this.baseMapper.updateById(salesOrder);
        return null;
    }

    /**
     *  取消配置
     * @param id
     * @param salesOrder
     * @param user
     * @return
     */
    private String cancelValidated(Integer id, SalesOrder salesOrder, SysUserVo user) {
        if (id == null) {
            return "ID不能为空!";
        }
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (salesOrder.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法取消!";
        } else if (salesOrder.getItemStatus() == 4) {
            return "请勿重复取消!";
        }
        return null;
    }

    @Override
    public void finishOutWarehouseItem(String itemNo, SysUserVo user) {
        SalesOrder salesOrder = this.getOneBySalesOrderNo(itemNo);
        if (salesOrder == null) {
            throw new CustomerAuthenticationException("未查询到销售订单！");
        }
        if (!salesOrder.getIsAudit()) {
            salesOrder.setItemStatus(3);
        }
        this.fillUpdateUserData(salesOrder, user);
        this.baseMapper.updateById(salesOrder);
    }

    @Override
    public SalesOrder getOneBySalesOrderNo(String salesOrderNo) {
        LambdaQueryWrapper<SalesOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SalesOrder::getItemNo, salesOrderNo);
        return this.baseMapper.selectOne(wrapper);
    }


    @Override
    public List<SalesOrder> getListByTime(String deliveryData) {
        LambdaQueryWrapper<SalesOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(SalesOrder::getDeliveryDate, deliveryData + "00:00:00");
        wrapper.le(SalesOrder::getDeliveryDate, deliveryData + "23:59:59");
        wrapper.ne(SalesOrder::getItemStatus, 4);
        wrapper.ne(SalesOrder::getItemStatus, 5);
        wrapper.orderByAsc(SalesOrder::getDeliveryDate);
        return this.baseMapper.selectList(wrapper);
    }

    /**
     *  变更库存信息
     * @param salesOrder
     */
    public void adjustInventoryAndWarehouse(SalesOrder salesOrder) {
        if (salesOrder.getGoodsId() == null) {
            throw new CustomerAuthenticationException("商品ID不能为空");
        }
        if (salesOrder.getNumber() == null) {
            throw new CustomerAuthenticationException("销售商品数量不能为空");
        }

        GoodsInventoryInfo goodsInventoryInfo = goodsInventoryInfoService.getOneByGoodsId(salesOrder.getGoodsId());
        if (goodsInventoryInfo == null) {
            throw new CustomerAuthenticationException("商品ID【" + salesOrder.getGoodsId() + "】不存在库存信息");
        }
        int quantity = goodsInventoryInfo.getQuantity() - salesOrder.getNumber();
        if (quantity < 0) {
            throw new CustomerAuthenticationException("库存不足,无法出库！");
        }

        // 修改库存信息
        goodsInventoryInfoService.updateByGoodsId(salesOrder.getGoodsId(), quantity);
    }
}
