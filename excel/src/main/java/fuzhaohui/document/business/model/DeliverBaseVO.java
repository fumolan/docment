package fuzhaohui.document.business.model;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月1日11:06:21$ $
 */
public class DeliverBaseVO {

    private String merchantName;//店铺名称

    private Integer shopCode;//店铺代码

    private String ruleName;//店铺配送规则名称

    private String model;//配送方式

    private String deliverScope;//可配送商品

    private String reserveDay;//可预约天数

    private String deliverStartTime;//同一配送规则各配送时段开始时间

    private String deliverEndTime;//同一配送规则各配送时段结束时间

    private String weekMax;//同一配送规则各配送时段周内可预约最大值

    private String weekendMax;//同一配送规则各配送时段周末可预约最大值

    private String fee;//同一配送规则各配送时段服务费

    private String poi;

    public String getPoi() {
        return poi;
    }

    public void setPoi(String poi) {
        this.poi = poi;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getShopCode() {
        return shopCode;
    }

    public void setShopCode(Integer shopCode) {
        this.shopCode = shopCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeliverScope() {
        return deliverScope;
    }

    public void setDeliverScope(String deliverScope) {
        this.deliverScope = deliverScope;
    }

    public String getReserveDay() {
        return reserveDay;
    }

    public void setReserveDay(String reserveDay) {
        this.reserveDay = reserveDay;
    }

    public String getDeliverStartTime() {
        return deliverStartTime;
    }

    public void setDeliverStartTime(String deliverStartTime) {
        this.deliverStartTime = deliverStartTime;
    }

    public String getDeliverEndTime() {
        return deliverEndTime;
    }

    public void setDeliverEndTime(String deliverEndTime) {
        this.deliverEndTime = deliverEndTime;
    }

    public String getWeekMax() {
        return weekMax;
    }

    public void setWeekMax(String weekMax) {
        this.weekMax = weekMax;
    }

    public String getWeekendMax() {
        return weekendMax;
    }

    public void setWeekendMax(String weekendMax) {
        this.weekendMax = weekendMax;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
