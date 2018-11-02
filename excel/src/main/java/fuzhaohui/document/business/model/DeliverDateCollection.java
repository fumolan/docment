package fuzhaohui.document.business.model;

import java.util.List;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日14:26:36$ $
 */
public class DeliverDateCollection {

    private MerchantVO merchantVO;

    private List<DeliverOrderVO> deliverOrderVOList;

    private List<DeliverBaseVO> deliverBaseVOS;


    public MerchantVO getMerchantVO() {
        return merchantVO;
    }

    public void setMerchantVO(MerchantVO merchantVO) {
        this.merchantVO = merchantVO;
    }

    public List<DeliverOrderVO> getDeliverOrderVOList() {
        return deliverOrderVOList;
    }

    public void setDeliverOrderVOList(List<DeliverOrderVO> deliverOrderVOList) {
        this.deliverOrderVOList = deliverOrderVOList;
    }

    public List<DeliverBaseVO> getDeliverBaseVOS() {
        return deliverBaseVOS;
    }

    public void setDeliverBaseVOS(List<DeliverBaseVO> deliverBaseVOS) {
        this.deliverBaseVOS = deliverBaseVOS;
    }
}
