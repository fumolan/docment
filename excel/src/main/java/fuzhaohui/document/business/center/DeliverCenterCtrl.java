package fuzhaohui.document.business.center;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.impl.DeliverBaseVoImpl;
import fuzhaohui.document.business.impl.DeliverOrderVoImpl;
import fuzhaohui.document.business.impl.MerchantVoImpl;
import fuzhaohui.document.business.model.DeliverBaseVO;
import fuzhaohui.document.business.model.DeliverOrderVO;
import fuzhaohui.document.business.model.MerchantVO;

import java.util.List;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日11:01:17$ $
 */
public class DeliverCenterCtrl {


    public static void main(String[] args) {
        //获取配送基础规则
        List<DeliverBaseVO> deliverBaseVOS =  DeliverBaseVoImpl.getDeliverBaseVo();
        System.out.println(JSON.toJSONString(deliverBaseVOS.get(0)));
        //获取配送配送下单基础规则
        List<DeliverOrderVO> deliverOrderVOS =  DeliverOrderVoImpl.getDeliverBaseVo();
        System.out.println(JSON.toJSONString(deliverOrderVOS.get(0)));
        //根据shopCode分组数据
        List<MerchantVO> merchantVOS = MerchantVoImpl.getMerchantVOList();
        System.out.println(JSON.toJSONString(merchantVOS.get(0)));
        //组装


    }

}
