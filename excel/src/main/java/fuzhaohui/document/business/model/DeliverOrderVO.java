package fuzhaohui.document.business.model;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月1日11:06:52
 */
public class DeliverOrderVO {

    private String merchantName;//店铺名称

    private Integer shopCode;//店铺代码

    private String ruleName;//店铺配送规则名称

    private String model;//配送方式

    private String deliverRuleModelStartCreateOrderTime;//同一配送规则各配送时段截单设置下单开始时间

    private String deliverRuleModelEndCreateOrderTime;//同一配送规则各配送时段截单设置下单结束时间

    private Integer longestDay;//最早可配送天数

    private String longestStartTime;//同一配送规则各配送时段最早可配送时段开始时间

    private String longestEndTime;//同一配送规则各配送时段最早可配送时段结束时间

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getDeliverRuleModelStartCreateOrderTime() {
        return deliverRuleModelStartCreateOrderTime;
    }

    public void setDeliverRuleModelStartCreateOrderTime(String deliverRuleModelStartCreateOrderTime) {
        this.deliverRuleModelStartCreateOrderTime = deliverRuleModelStartCreateOrderTime;
    }

    public String getDeliverRuleModelEndCreateOrderTime() {
        return deliverRuleModelEndCreateOrderTime;
    }

    public void setDeliverRuleModelEndCreateOrderTime(String deliverRuleModelEndCreateOrderTime) {
        this.deliverRuleModelEndCreateOrderTime = deliverRuleModelEndCreateOrderTime;
    }

    public Integer getLongestDay() {
        return longestDay;
    }

    public void setLongestDay(Integer longestDay) {
        this.longestDay = longestDay;
    }

    public String getLongestStartTime() {
        return longestStartTime;
    }

    public void setLongestStartTime(String longestStartTime) {
        this.longestStartTime = longestStartTime;
    }

    public String getLongestEndTime() {
        return longestEndTime;
    }

    public void setLongestEndTime(String longestEndTime) {
        this.longestEndTime = longestEndTime;
    }
}
