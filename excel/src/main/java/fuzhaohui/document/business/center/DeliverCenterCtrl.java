package fuzhaohui.document.business.center;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.impl.DeliverBaseVoImpl;
import fuzhaohui.document.business.impl.DeliverOrderVoImpl;
import fuzhaohui.document.business.impl.MerchantVoImpl;
import fuzhaohui.document.business.model.*;
import net.sf.json.JSONArray;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日11:01:17$ $
 */
public class DeliverCenterCtrl {


    public static void main(String[] args) {
        //获取配送基础规则
        List<DeliverBaseVO> deliverBaseVOS =  DeliverBaseVoImpl.getDeliverBaseVo();

        Map<Integer,List<DeliverBaseVO>> mapBaseDeliver = new HashMap<>();
        for(DeliverBaseVO deliverBaseVO : deliverBaseVOS){
            if(mapBaseDeliver.get(deliverBaseVO.getShopCode())!=null){
                List<DeliverBaseVO> list = mapBaseDeliver.get(deliverBaseVO.getShopCode());
                List<DeliverBaseVO> listNew = new ArrayList<>();
                listNew.addAll(list);
                listNew.add(deliverBaseVO);
                mapBaseDeliver.put(deliverBaseVO.getShopCode(),listNew);
            }else{
                List<DeliverBaseVO> list = new ArrayList<>();
                list.add(deliverBaseVO);
                mapBaseDeliver.put(deliverBaseVO.getShopCode(),list);
            }
        }

        //获取配送配送下单基础规则
        List<DeliverOrderVO> deliverOrderVOS =  DeliverOrderVoImpl.getDeliverBaseVo();
        Map<Integer,List<DeliverOrderVO>> mapDeliverOrderVOS = new HashMap<>();
        for(DeliverOrderVO deliverOrderVO : deliverOrderVOS){
            if(mapDeliverOrderVOS.get(deliverOrderVO.getShopCode())!=null){
                List<DeliverOrderVO> list = mapDeliverOrderVOS.get(deliverOrderVO.getShopCode());
                List<DeliverOrderVO> listNew = new ArrayList<>();
                listNew.addAll(list);
                listNew.add(deliverOrderVO);
                mapDeliverOrderVOS.put(deliverOrderVO.getShopCode(),listNew);
            }else{
                List<DeliverOrderVO> list = new ArrayList<>();
                list.add(deliverOrderVO);
                mapDeliverOrderVOS.put(deliverOrderVO.getShopCode(),list);
            }
        }
        //根据shopCode分组数据
        List<MerchantVO> merchantVOS = MerchantVoImpl.getMerchantVOList();
        //组装
        List<DeliverDateCollection> deliverDateCollections = new ArrayList<>();
        List<String> merchantNames = new ArrayList<>();
        for(MerchantVO merchantVO : merchantVOS){
            if(mapDeliverOrderVOS.get(merchantVO.getShopCode()) == null && mapBaseDeliver.get(merchantVO.getShopCode()) == null){
                continue;
            }
            DeliverDateCollection deliverDateCollection = new DeliverDateCollection();
            deliverDateCollection.setMerchantVO(merchantVO);
            deliverDateCollection.setDeliverOrderVOList(mapDeliverOrderVOS.get(merchantVO.getShopCode()));
            deliverDateCollection.setDeliverBaseVOS(mapBaseDeliver.get(merchantVO.getShopCode()));
            deliverDateCollections.add(deliverDateCollection);
            merchantNames.add(merchantVO.getMerchantName());
        }
        //



        System.out.println("组装书局"+deliverDateCollections.size());
        System.out.println(JSON.toJSONString(deliverDateCollections));
        System.out.println(JSON.toJSONString(merchantNames));
    }


    public static String getPoiString(String poiString) {
        if(!StringUtils.hasLength(poiString)){
            return null;
        }
        String[] poiList =  poiString.split(";");
        if(null == poiList || poiList.length ==0){
            return null;
        }
        List<PoiDto> list = new ArrayList<>();
        int i =0;
        for(String str : poiList){
            i+=1;
            //System.out.println(i%2+"   " + poiString.length());
            if(i%2 == 0){
                //continue;
            }
            String[] point = str.split(",");
            PoiDto poiDto = new PoiDto();
            poiDto.setLng(new BigDecimal(point[0].toString().trim()));
            poiDto.setLat(new BigDecimal(point[1].toString().trim()));
            list.add(poiDto);
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return  jsonArray.toString();
    }

}
