package fuzhaohui.document.business.model;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日11:50:12$ $
 */
public class MerchantVO {

    private String merchantName;
    private Integer shopCode;
    private Long merchantId;
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
