package com.system.order.impl.warehouseOrder.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.common.exception.CustomerAuthenticationException;
import com.system.common.utils.BigDecimalUtil;
import com.system.common.utils.DateUtil;
import com.system.common.utils.SecurityUtils;
import com.system.common.vo.SysUserVo;
import com.system.order.api.salesOrder.domain.SalesOrder;
import com.system.order.api.salesOrder.service.ISalesOrderService;
import com.system.order.api.warehouseOrder.domain.WarehouseItem;
import com.system.order.api.warehouseOrder.params.WarehouseItemParam;
import com.system.order.api.warehouseOrder.params.WarehouseItemSaveParam;
import com.system.order.api.warehouseOrder.service.IWarehouseItemService;
import com.system.order.api.warehouseOrder.vo.WarehouseItemAuditVo;
import com.system.order.api.warehouseOrder.vo.WarehouseItemCancelVo;
import com.system.order.api.warehouseOrder.vo.WarehouseItemDetail;
import com.system.order.api.warehouseOrder.vo.WarehouseItemVo;
import com.system.order.impl.warehouseOrder.mapper.WarehouseItemMapper;
import com.system.systembase.api.basicConfiguration.domain.SysClient;
import com.system.systembase.api.basicConfiguration.domain.SysVendor;
import com.system.systembase.api.basicConfiguration.service.ISysClientService;
import com.system.systembase.api.basicConfiguration.service.ISysVendorService;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author lutong
 * @data 2025-2-14 014 14:33
 */

@Slf4j
@Service
public class WarehouseItemServiceImpl extends ServiceImpl<WarehouseItemMapper, WarehouseItem> implements IWarehouseItemService {

    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IWarehouseAreaService warehouseAreaService;

    @Autowired
    private ISysVendorService sysVendorService;

    @Autowired
    private ISysClientService sysClientService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IGoodsInventoryInfoService goodsInventoryInfoService;

    private final ISalesOrderService salesOrderService;
    public WarehouseItemServiceImpl(@Lazy ISalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @Override
    public Integer selectCount(WarehouseItemParam param) {
        return this.baseMapper.getPageCount(param);
    }

    @Override
    public List<WarehouseItemVo> selectList(WarehouseItemParam param) {
        return this.baseMapper.getPageList(param);
    }

    @Override
    public Page<WarehouseItemVo> selectPage(WarehouseItemParam param) {
        if (param.getPageIndex() == null) {
            param.setPageIndex(1);
        }
        if (param.getPageSize() == null) {
            param.setPageSize(20);
        }

        Page<WarehouseItemVo> page = new Page<>();
        Integer count = this.selectCount(param);
        page.setTotal(count);

        if (count > 0) {
            List<WarehouseItemVo> list = this.selectList(param);
            if (list.size() > 0) {
                for (WarehouseItemVo vo : list) {
                    // 设置供应商名称
                    if (StringUtils.isNotBlank(vo.getGoodsSource()) && vo.getType() == 1) {
                        SysVendor vendor = sysVendorService.getByVendorCode(vo.getGoodsSource());
                        vo.setGoodsSourceName(vendor != null ? vendor.getRegisterName() : null);
                    }
                    // 设置客户名称
                    if (StringUtils.isNotBlank(vo.getGoodsDestination()) && vo.getType() == 2) {
                        SysClient client = sysClientService.getByClientCode(vo.getGoodsDestination());
                        vo.setGoodsDestinationName(client != null ? client.getRegisterName() : null);
                    }
                    // 设置仓库库区展示格式
                    Warehouse warehouse = warehouseService.getById(vo.getWarehouseId());
                    WarehouseArea warehouseArea = warehouseAreaService.getById(vo.getWarehouseAreaId());
                    vo.setWarehouse(warehouse.getWarehouseName() + "(" + warehouse.getWarehouseCode() + ")-" +
                            warehouseArea.getWarehouseAreaName() + "(" + warehouseArea.getWarehouseAreaCode() + ")");
                }
            }
            page.setRecords(list);
        }
        return page;
    }


    @Override
    public String insertAndUpdate(WarehouseItemSaveParam param) {
        SysUserVo user = SecurityUtils.getUser();
        if (user == null) {
            return "账号未登入，请重新登入!";
        }

        if (param.getId() == null) {
            // 新增
            String errMsg = this.insertValidated(param);
            if (errMsg != null) {
                return errMsg;
            }
            // 填充新增数据
            WarehouseItem item = this.fillInsertData(param, user);
            this.baseMapper.insert(item);

        } else {
            // 修改
            String errMsg = this.updateValidated(param);
            if (errMsg != null) {
                return errMsg;
            }
            // 填充修改数据
            WarehouseItem item = this.fillUpdateData(param, user);
            if (item.getItemStatus() == 3 || item.getItemStatus() == 4) {
                return "订单状态为【已完成】或【已取消】的订单, 无法更新!";
            }
            if (item.getInOutStatus() == 3) {
                return "出入库状态为【已收货/已出库】的订单, 无法更新!";
            }
            this.baseMapper.updateById(item);
        }
        return null;
    }

    @Override
    public String begin(Integer id) {
        SysUserVo user = SecurityUtils.getUser();
        WarehouseItem item = this.baseMapper.selectById(id);
        // 校验
        String errMSG = this.beginValidated(id, item, user);
        if (errMSG != null) {
            return errMSG;
        }

        item.setInOutStatus(2);
        // 填充修改用户数据
        this.fillUpdateUserData(item, user);
        this.baseMapper.updateById(item);
        return null;
    }

    @Override
    @Transactional
    public String confirmFinished(Integer id) {
        SysUserVo user = SecurityUtils.getUser();
        WarehouseItem item = this.baseMapper.selectById(id);
        // 校验
        String errMSG = this.confirmValidated(id, item, user);
        if (errMSG != null) {
            return errMSG;
        }

        // 判断是否需要审核、审核是否通过
        if (item.getIsAudit()) {
            item.setItemStatus(2);
            item.setInOutStatus(3);
        }
        if (!item.getIsAudit() || item.getAuditStatus() == 1) {
            item.setInOutStatus(3);
            item.setItemStatus(3);
            // 变更库存信息
            if (null == item.getAssociatedOrderNo() || item.getAssociatedOrderNo().equals("")) {
                this.adjustInventoryAndWarehouse(item);
            } else if (item.getType() == 2 && item.getAssociatedOrderNo().contains("SO")){
                // 完成出库单
                salesOrderService.finishOutWarehouseItem(item.getAssociatedOrderNo(), user);
            }
        }
        // 填充修改用户数据
        this.fillUpdateUserData(item, user);
        this.baseMapper.updateById(item);
        return null;
    }

    /**
     *  TODO 出入库流程
     *  1、创建工单 - ItemStatus=1,InOutStatus=1
     *  2、开始出库/入库 - ItemStatus=1,InOutStatus=2
     *      3、确认出库/入库完成
     *       （1）不审核 - ItemStatus=3,InOutStatus=3，结束
     *       （2）审核 - ItemStatus=2,InOutStatus=3
     *        4、审核
     *           （1）审核通过 - ItemStatus=3,InOutStatus=3，结束
     *           （2）审核不通过 - ItemStatus=2,InOutStatus=3,AuditStatus=2,打回（回到（2、开始出库/入库）结束状态）
     *
     */
    @Override
    @Transactional
    public String auditById(Integer id, Integer auditStatus, String auditReason) {
        SysUserVo user = SecurityUtils.getUser();
        WarehouseItem item = this.baseMapper.selectById(id);
        // 审核校验
        String errMSG = this.auditValidated(id, auditStatus, item, user);
        if (errMSG != null) {
            return errMSG;
        }

        item.setAuditStatus(auditStatus);
        item.setAuditDate(new Date());
        item.setAuditUserName(user.getLoginId());
        item.setAuditReason(auditReason);
        // 审核通过，设置工单状态和出入库状态
        if (auditStatus == 1) {
            item.setItemStatus(3);
            item.setInOutStatus(3);
            // 变更库存信息
            if (null == item.getAssociatedOrderNo() || item.getAssociatedOrderNo().equals("")) {
                this.adjustInventoryAndWarehouse(item);
            } else if (item.getType() == 2 && item.getAssociatedOrderNo().contains("SO")){
                // 完成出库单
                salesOrderService.finishOutWarehouseItem(item.getAssociatedOrderNo(), user);
            }
        } else if (auditStatus == 2) {
            // 回到（2、开始出库/入库）结束状态
            item.setItemStatus(1);
            item.setInOutStatus(2);
        }
        // 填充修改用户数据
        this.fillUpdateUserData(item, user);
        this.baseMapper.updateById(item);
        return null;
    }


    @Override
    public String cancelById(Integer id, String cancelReason) {
        SysUserVo user = SecurityUtils.getUser();
        WarehouseItem item = this.baseMapper.selectById(id);
        // 取消校验
        String errMSG = this.cancelValidated(id, item, user);
        if (errMSG != null) {
            return errMSG;
        }

        // 设置工单状态为已取消
        item.setItemStatus(4);
        item.setCancelDate(new Date());
        item.setCancelUserName(user.getLoginId());
        item.setCancelReason(cancelReason);
        // 取消单将不再审核
        item.setIsAudit(false);
        // 将 收货/出库状态 回退到 待收货/待出库
        item.setInOutStatus(1);
        // 填充修改用户数据
        this.fillUpdateUserData(item, user);
        this.baseMapper.updateById(item);
        return null;
    }


    @Override
    public WarehouseItemDetail getDetailById(Integer id) {
        WarehouseItemDetail detail = new WarehouseItemDetail();
        WarehouseItemAuditVo auditVo = new WarehouseItemAuditVo();
        WarehouseItemCancelVo cancelVo = new WarehouseItemCancelVo();
        WarehouseItem item = this.baseMapper.selectById(id);

        if (item != null) {
            // 填充明细数据
            this.fillDetailData(detail, item);
            // 填充审核单数据
            auditVo.setId(item.getId());
            auditVo.setItemNo(item.getItemNo());
            auditVo.setIsAudit(item.getIsAudit());
            auditVo.setAuditStatus(item.getAuditStatus());
            auditVo.setAuditUserName(item.getAuditUserName());
            auditVo.setAuditDate(item.getAuditDate());
            auditVo.setAuditReason(item.getAuditReason());
            // 填充取消单数据
            cancelVo.setId(item.getId());
            cancelVo.setItemNo(item.getItemNo());
            cancelVo.setItemStatus(item.getItemStatus());
            cancelVo.setCancelUserName(item.getCancelUserName());
            cancelVo.setCancelDate(item.getCancelDate());
            cancelVo.setCancelReason(item.getCancelReason());
            // 获取商品信息
            WarehouseGoods warehouseGoods = warehouseGoodsService.getById(item.getGoodsId());
            // 赋值
            detail.setAuditVo(auditVo);
            detail.setCancelVo(cancelVo);
            detail.setWarehouseGoods(warehouseGoods);
        }
        return detail;
    }


    @Override
    public List<WarehouseItem> findByAssociatedOrderNo(String associatedOrderNo, String companyUid) {
        LambdaQueryWrapper<WarehouseItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseItem::getAssociatedOrderNo, associatedOrderNo);
        wrapper.eq(WarehouseItem::getCompanyUid, companyUid);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public WarehouseItem findByItemNo(String itemNo, String companyUid) {
        LambdaQueryWrapper<WarehouseItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarehouseItem::getItemNo, itemNo);
        wrapper.eq(WarehouseItem::getCompanyUid, companyUid);
        return this.baseMapper.selectOne(wrapper);
    }

    /**
     *  新增校验
     * @param param
     * @return
     */
    private String insertValidated(WarehouseItemSaveParam param) {
        if (param.getItemNo() != null && !param.getItemNo().equals("")) {
            return "系统自动生成单号,无需手动输入!";
        }
        if (param.getType() == null) {
            return "类型不能为空!";
        }
        if (param.getType() == 1) {
            if (param.getGoodsSource() == null || param.getGoodsSource().equals("")) {
                return "货物来源(供应商编码)不能为空!";
            }
            SysVendor vendor = sysVendorService.getByVendorCode(param.getGoodsSource());
            if (vendor == null) {
                return "不存在编码为【" + param.getGoodsSource() + "】的供应商!";
            }
        } else if (param.getType() == 2){
            if (param.getGoodsDestination() == null || param.getGoodsDestination().equals("")) {
                return "货物去向(客户编码)不能为空!";
            }
            SysClient client = sysClientService.getByClientCode(param.getGoodsDestination());
            if (client == null) {
                return "不存在编码为【" + param.getGoodsDestination() + "】的客户!";
            }
        }
        if (param.getGoodsId() == null) {
            return "商品ID不能为空!";
        }
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(param.getGoodsId());
        if (warehouseGoods == null) {
            return "不存在ID为【" + param.getGoodsId() + "】的商品!";
        }
        if (param.getNumber() == null) {
            return "商品出入库商品数量不能为空!";
        }
        if (param.getIsAudit() == null) {
            return "是否审核不能为空!";
        }
        if (param.getNumber() <= 0) {
            return "出入库商品数量必须大于0!";
        }
        GoodsInventoryInfo info = goodsInventoryInfoService.getOneByGoodsId(param.getGoodsId());
        if (param.getType() == 2) {
            if (info.getQuantity() < param.getNumber()) {
                return "该商品库存数量为【" + info.getQuantity()+ "】,小于出库数量，请维护!";
            }
        }
        if (null != param.getAssociatedOrderNo() && !param.getAssociatedOrderNo().equals("")) {
            SalesOrder salesOrder = salesOrderService.getOneBySalesOrderNo(param.getAssociatedOrderNo());
            if (salesOrder == null){
                return "销售订单不存在单号【" + param.getAssociatedOrderNo() + "】,关联单号异常!";
            }
        }

        return null;
    }

    /**
     *  填充新增数据
     * @param param
     * @return
     */
    private WarehouseItem fillInsertData(WarehouseItemSaveParam param, SysUserVo user) {
        WarehouseItem item = new WarehouseItem();
        item.setType(param.getType());
        if (item.getType() == 1) {
            item.setGoodsSource(param.getGoodsSource());
        }
        if (item.getType() == 2) {
            item.setGoodsDestination(param.getGoodsDestination());
        }

        item.setExpectedInOutDate(DateUtil.getFormatForDate(param.getExpectedInOutDate(), DateUtil.YMD));
        item.setAssociatedOrderNo(param.getAssociatedOrderNo());
        item.setRemark(param.getRemark());
        item.setGoodsId(param.getGoodsId());
        item.setNumber(param.getNumber());
        item.setIsAudit(param.getIsAudit());

        // 获取商品数据
        WarehouseGoods warehouseGoods = warehouseGoodsService.getById(param.getGoodsId());
        item.setUnitPrice(warehouseGoods.getUnitPrice());
        item.setAmount(BigDecimalUtil.multiply(warehouseGoods.getUnitPrice(), BigDecimal.valueOf(item.getNumber())));
        item.setWarehouseId(warehouseGoods.getPreWarehouseId());
        item.setWarehouseAreaId(warehouseGoods.getPreWarehouseAreaId());

        // 填充默认数据
        item.setItemStatus(1);
        item.setInOutStatus(1);
        if (param.getIsAudit()) {
            item.setAuditStatus(0);
        }

        // 生成单号
        String itemNoKey = null;
        if (param.getType() == 1) {
            itemNoKey = "RK";
        } else if (param.getType() == 2) {
            itemNoKey = "CK";
        }
        String itemNo = redisService.createNo(itemNoKey);
        item.setItemNo(itemNo);

        // 填充新增用户数据
        this.fillInsertUserData(item, user);
        return item;
    }

    /**
     *  填充新增用户数据
     * @param item
     * @param user
     */
    private void fillInsertUserData(WarehouseItem item, SysUserVo user) {
        // 填充用户数据
        item.setCompanyUid(user.getCompanyUid());
        item.setCreateTime(new Date());
        item.setCreateUserName(user.getLoginId());
    }


    /**
     *  修改校验
     * @param param
     * @return
     */
    private String updateValidated(WarehouseItemSaveParam param) {
        if (param.getId() == null) {
            return "ID不能为空!";
        }
        return null;
    }


    /**
     *  填充修改数据
     * @param param
     * @param user
     * @return
     */
    private WarehouseItem fillUpdateData(WarehouseItemSaveParam param, SysUserVo user) {
        WarehouseItem item = new WarehouseItem();
        item.setId(param.getId());
        item.setExpectedInOutDate(DateUtil.getFormatForDate(param.getExpectedInOutDate(), DateUtil.YMD));
        item.setRemark(param.getRemark());

        // 填充用户数据
        this.fillUpdateUserData(item, user);
        return item;
    }

    /**
     *  填充修改用户数据
     * @param item
     * @param user
     */
    private void fillUpdateUserData(WarehouseItem item, SysUserVo user) {
        // 填充用户数据
        item.setUpdateTime(new Date());
        item.setUpdateUserName(user.getLoginId());
    }

    /**
     *  开始出入库校验
     * @param id
     * @param item
     * @param user
     * @return
     */
    private String beginValidated(Integer id, WarehouseItem item, SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (id == null) {
            return "ID不能为空!";
        }

        // 只有 ItemStatus=1,InOutStatus=1 的单据才能进行 开始出入库
        if (item.getItemStatus() == 2) {
            return "工单状态为【待验收】的工单,无法开始出入库!";
        } else if (item.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法开始出入库!";
        } else if (item.getItemStatus() == 4) {
            return "工单状态为【已取消】的工单,无法开始出入库!";
        }
        if (item.getType() == 1 && item.getInOutStatus() == 2) {
            return "入库状态为【入库中】的工单,无法开始入库!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 2) {
            return "出库状态为【出库中】的工单,无法开始出库!";
        } else if (item.getType() == 1 && item.getInOutStatus() == 3) {
            return "入库状态为【已入库】的工单,无法开始入库!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 3) {
            return "出库状态为【已出库】的工单,无法开始出库!";
        }

        return null;
    }

    /**
     *  确认出入库校验
     * @param id
     * @param item
     * @param user
     * @return
     */
    private String confirmValidated(Integer id, WarehouseItem item, SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (id == null) {
            return "ID不能为空!";
        }

        // 只有 ItemStatus=1,InOutStatus=2 的单据才能进行 确认出入库
        if (item.getItemStatus() == 2) {
            return "工单状态为【待验收】的工单,无法确认出入库!";
        } else if (item.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法确认出入库!";
        } else if (item.getItemStatus() == 4) {
            return "工单状态为【已取消】的工单,无法确认出入库!";
        }
        if (item.getType() == 1 && item.getInOutStatus() == 1) {
            return "入库状态为【待入库】的工单,无法确认入库!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 1) {
            return "出库状态为【待出库】的工单,无法确认出库!";
        } else if (item.getType() == 1 && item.getInOutStatus() == 3) {
            return "入库状态为【已入库】的工单,无法确认入库!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 3) {
            return "出库状态为【已出库】的工单,无法确认出库!";
        }

        return null;
    }

    /**
     *  审核校验
     * @param id
     * @param auditStatus
     * @param item
     * @param user
     * @return
     */
    private String auditValidated(Integer id, Integer auditStatus, WarehouseItem item, SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (id == null) {
            return "ID不能为空!";
        }
        if (item == null) {
            return "根据ID【" + id + "】无法查询出入库单号!";
        }
        if (auditStatus == null) {
            return "审核状态不能为空!";
        }
        if (!item.getIsAudit()) {
            return "该ID对应的出入库单不需要审核!";
        }
        if (item.getAuditStatus() == 1 || item.getAuditStatus() == 2) {
            return "请勿重复审核!";
        }
        // 只有 ItemStatus=2,InOutStatus=3 的单据才能进行审核
        if (item.getItemStatus() == 1) {
            return "工单状态为【进行中】的工单,无法审核!";
        } else if (item.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法审核!";
        } else if (item.getItemStatus() == 4) {
            return "工单状态为【已取消】的工单,无法审核!";
        }
        if (item.getType() == 1 && item.getInOutStatus() == 1) {
            return "入库状态为【待入库】的工单,无法审核!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 1) {
            return "出库状态为【待出库】的工单,无法审核!";
        } else if (item.getType() == 1 && item.getInOutStatus() == 2) {
            return "入库状态为【入库中】的工单,无法审核!";
        } else if (item.getType() == 2 && item.getInOutStatus() == 2) {
            return "出库状态为【出库中】的工单,无法审核!";
        }

        return null;
    }

    /**
     *  取消校验
     * @param id
     * @param item
     * @param user
     * @return
     */
    private String cancelValidated(Integer id, WarehouseItem item, SysUserVo user) {
        if (user == null) {
            return "账号未登入，请重新登入!";
        }
        if (id == null) {
            return "ID不能为空!";
        }
        if (item.getItemStatus() == 3) {
            return "工单状态为【已完成】的工单,无法取消!";
        } else if (item.getItemStatus() == 4) {
            return "请勿重复取消!";
        }

        return null;
    }

    /**
     *  填充明细数据
     * @param item
     * @return
     */
    private void fillDetailData(WarehouseItemDetail detail, WarehouseItem item) {
        detail.setId(item.getId());
        detail.setItemNo(item.getItemNo());
        detail.setType(item.getType());
        detail.setItemStatus(item.getItemStatus());
        detail.setInOutStatus(item.getInOutStatus());
        detail.setGoodsSource(item.getGoodsSource());
        detail.setExpectedInOutDate(item.getExpectedInOutDate());
        detail.setAssociatedOrderNo(item.getAssociatedOrderNo());
        detail.setUnitPrice(item.getUnitPrice());
        detail.setAmount(item.getAmount());
        detail.setNumber(item.getNumber());
        detail.setWarehouseId(item.getWarehouseId());
        detail.setWarehouseAreaId(item.getWarehouseAreaId());
        detail.setRemark(item.getRemark());
        detail.setCreateUserName(item.getCreateUserName());
        detail.setCreateTime(item.getCreateTime());
        // 获取货物来源(供应商名称)
        if (StringUtils.isNotBlank(item.getGoodsSource()) && item.getType() == 1) {
            SysVendor vendor = sysVendorService.getByVendorCode(item.getGoodsSource());
            detail.setGoodsSourceName(vendor != null ? vendor.getRegisterName() : null);
        }
        // 获取仓库信息
        Warehouse warehouse = warehouseService.getById(item.getWarehouseId());
        WarehouseArea warehouseArea = warehouseAreaService.getById(item.getWarehouseAreaId());
        detail.setWarehouse(warehouse.getWarehouseName() + "(" + warehouse.getWarehouseCode() + ")-" +
                warehouseArea.getWarehouseAreaName() + "(" + warehouseArea.getWarehouseAreaCode() + ")");
        // 货物去向(客户名称)
        if (StringUtils.isNotBlank(item.getGoodsDestination()) && item.getType() == 2) {
            SysClient client = sysClientService.getByClientCode(item.getGoodsDestination());
            detail.setGoodsDestinationName(client != null ? client.getRegisterName() : null);
        }

    }


    /**
     *  变更库存信息
     * @param item
     */
    public void adjustInventoryAndWarehouse(WarehouseItem item) {
        if (item.getGoodsId() == null) {
            throw new CustomerAuthenticationException("商品ID不能为空");
        }
        if (item.getType() == null) {
            throw new CustomerAuthenticationException("类型不能为空");
        }
        if (item.getNumber() == null) {
            throw new CustomerAuthenticationException("出入库商品数量不能为空");
        }

        GoodsInventoryInfo goodsInventoryInfo = goodsInventoryInfoService.getOneByGoodsId(item.getGoodsId());
        if (goodsInventoryInfo == null) {
            throw new CustomerAuthenticationException("商品ID【" + item.getGoodsId() + "】不存在库存信息");
        }

        int quantity = 0;
        if (item.getType() == 1) {
            quantity = goodsInventoryInfo.getQuantity() + item.getNumber();
        } else if (item.getType() == 2) {
            quantity = goodsInventoryInfo.getQuantity() - item.getNumber();
            if (quantity < 0) {
                throw new CustomerAuthenticationException("库存不足,无法出库！");
            }
        }
        // 修改库存信息
        goodsInventoryInfoService.updateByGoodsId(item.getGoodsId(), quantity);
    }
}
