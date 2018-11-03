package fuzhaohui.document.business.model;

import java.util.Date;

public class DistributionRuleDetailPO {

    private Long id;
    /**配送规则主表id*/
    private Long distributionRuleId;
    /**开始时间*/
    private String beginTime;
    /**结束时间*/
    private String endTime;
    /**日常可预约最大可配送量*/
    private Long dailyMaxMun;
    /**周末可预约最大值*/
    private Long weekendsMaxMun;
    /**服务费*/
    private Double serviceCharge;
    /**最早配送时效 天数*/
    private String distributionDay;
    /**最早配送时效end*/
    private String distributionEndDate;
    /**最早配送时效 begin*/
    private String distributionBeginDate;
    /**1：配送时间  2：截单时间 */
    private String type;
    /**是否可用:默认0否;1是*/
    private Integer isAvailable;
    /**是否删除*/
    private Integer isDeleted;
    /**公司ID*/
    private Long companyId;

    private String uuid;

    private Integer versionNo;
    private Long createUserid;
    private String createUsername;
    private String createUserip;
    private String createUsermac;
    private Date createTime;
    private Date createTimeDb;
    private String serverIp;
    private Long updateUserid;
    private String updateUsername;
    private String updateUserip;
    private String updateUsermac;
    private Date updateTime;
    private Date updateTimeDb;
    private String clientVersionno;

    private String model;

    private String distributionAreaName;

    public String getDistributionAreaName() {
        return distributionAreaName;
    }

    public void setDistributionAreaName(String distributionAreaName) {
        this.distributionAreaName = distributionAreaName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistributionRuleId() {
        return distributionRuleId;
    }

    public void setDistributionRuleId(Long distributionRuleId) {
        this.distributionRuleId = distributionRuleId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getDailyMaxMun() {
        return dailyMaxMun;
    }

    public void setDailyMaxMun(Long dailyMaxMun) {
        this.dailyMaxMun = dailyMaxMun;
    }

    public Long getWeekendsMaxMun() {
        return weekendsMaxMun;
    }

    public void setWeekendsMaxMun(Long weekendsMaxMun) {
        this.weekendsMaxMun = weekendsMaxMun;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getDistributionDay() {
        return distributionDay;
    }

    public void setDistributionDay(String distributionDay) {
        this.distributionDay = distributionDay;
    }

    public String getDistributionEndDate() {
        return distributionEndDate;
    }

    public void setDistributionEndDate(String distributionEndDate) {
        this.distributionEndDate = distributionEndDate;
    }

    public String getDistributionBeginDate() {
        return distributionBeginDate;
    }

    public void setDistributionBeginDate(String distributionBeginDate) {
        this.distributionBeginDate = distributionBeginDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public Long getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Long createUserid) {
        this.createUserid = createUserid;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTimeDb() {
        return createTimeDb;
    }

    public void setCreateTimeDb(Date createTimeDb) {
        this.createTimeDb = createTimeDb;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Long getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Long updateUserid) {
        this.updateUserid = updateUserid;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        if("1".equals(type)){
            return "INSERT INTO `保存查询sql`.`distribution_rule_detail` " +
                    "  (`distribution_rule_id`,`model`,`distribution_area_name`, `begin_time`, `end_time`, `daily_max_mun`, `weekends_max_mun`, `service_charge`, `type`, `is_available`, `create_time`, `create_time_db`, `update_time`, `company_id`, `uuid`)" +
                    "  VALUES ('"+distributionRuleId+"','"+model+"','"+distributionAreaName+"', '"+beginTime+"', '"+endTime+"', '"+dailyMaxMun+"', '"+weekendsMaxMun+"', '"+serviceCharge+"', '1', '1', now(),  now(),  now(), '130', '"+uuid+"');";
        }else if("2".equals(type)){
            return "INSERT INTO `保存查询sql`.`distribution_rule_detail` " +
                    " (`distribution_rule_id`,`model`,`distribution_area_name`, `begin_time`, `end_time`, `distribution_day`, `distribution_end_date`, `distribution_begin_date`, `type`, `is_available`, `create_time`, `create_time_db`, `update_time`, `company_id`, `uuid`)" +
                    " VALUES ('"+distributionRuleId+"','"+model+"','"+distributionAreaName+"', '"+beginTime+"', '"+endTime+"', '"+distributionDay+"', '"+distributionEndDate+"', '"+distributionBeginDate+"', '2', '1',now(), now(),  now(), '130', '"+uuid+"');";
        }
        return "\n" ;
    }
}
