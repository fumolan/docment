package fuzhaohui.document.business.model;

import java.util.Date;

public class DistributionRulePO {

    /**
     * 门店id
     */
    private Long storeId;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 配送模式
     */
    private String distributionMode;
    /**
     * 配送区域名称
     */
    private String distributionAreaName;
    /**
     * 从截单当天计算，配送时间显示至以后的
     */
    private Integer afterDay;
    /**
     * 可配送商品类型
     */
    private String distributionProduct;

    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 是否可用:默认0否;1是
     */
    private Integer isAvailable;

    private Integer isDeleted;
    /**
     * 创建人IP
     */
    private String createUserip;
    /**
     * 创建人MAC地址
     */
    private String createUsermac;
    /**
     * 创建时间-数据库操作时间
     */
    /**
     * 最后修改人IP
     */
    private String updateUserip;
    /**
     * 最后修改人MAC
     */
    private String updateUsermac;
    /**
     * 最后修改时间-数据库默认写入时间
     */
    private Date updateTimeDb;
    /**
     * 客户端程序的版本号
     */
    private String clientVersionno;

    private String poi;

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode;
    }

    public String getDistributionAreaName() {
        return distributionAreaName;
    }

    public void setDistributionAreaName(String distributionAreaName) {
        this.distributionAreaName = distributionAreaName;
    }

    public Integer getAfterDay() {
        return afterDay;
    }

    public void setAfterDay(Integer afterDay) {
        this.afterDay = afterDay;
    }

    public String getDistributionProduct() {
        return distributionProduct;
    }

    public void setDistributionProduct(String distributionProduct) {
        this.distributionProduct = distributionProduct;
    }

    public String getStatus() {
        return status;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCreateUserip() {
        return createUserip;
    }

    public void setCreateUserip(String createUserip) {
        this.createUserip = createUserip;
    }

    public String getCreateUsermac() {
        return createUsermac;
    }

    public void setCreateUsermac(String createUsermac) {
        this.createUsermac = createUsermac;
    }

    public String getUpdateUserip() {
        return updateUserip;
    }

    public void setUpdateUserip(String updateUserip) {
        this.updateUserip = updateUserip;
    }

    public String getUpdateUsermac() {
        return updateUsermac;
    }

    public void setUpdateUsermac(String updateUsermac) {
        this.updateUsermac = updateUsermac;
    }

    public Date getUpdateTimeDb() {
        return updateTimeDb;
    }

    public void setUpdateTimeDb(Date updateTimeDb) {
        this.updateTimeDb = updateTimeDb;
    }

    public String getClientVersionno() {
        return clientVersionno;
    }

    public void setClientVersionno(String clientVersionno) {
        this.clientVersionno = clientVersionno;
    }

    public String toInsertSql() {
        return "INSERT INTO `保存查询sql`.`distribution_rule` " +
                "(`store_id`, `rule_name`, `distribution_mode`, `distribution_area_name`, `status`, `after_day`, `poi`, `distribution_product`, `version_no`, `create_time`, `create_time_db`, `update_time`, `company_id`) VALUES " +
                "("+storeId+",'"+ruleName+"', '"+distributionMode+"', '"+distributionAreaName+"', '"+status+"',"+afterDay+", '"+poi+"','"+distributionProduct+"', '4', now(), now(),now(), '130');";
    }
}
