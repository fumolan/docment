package fuzhaohui.document.business.model;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author fuzh
 * @desc  地理坐标
 * @Date:$ 2018年9月13日22:13:13
 */
public class PoiDto implements Serializable{

    private BigDecimal lng;

    private BigDecimal lat;

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "{" +"lng" +'=' + lng +',' +"lat"+'=' + lat +"}";
    }

    public String toStringPlus() {
        StringBuilder s = new StringBuilder();
        s.append("lng=").append(lng).append(",").append("lat=").append(lat);
        return  s.toString();
    }

    public static void main(String[] args) {
        PoiDto po  = new PoiDto();
        po.setLng(new BigDecimal(36.198312));
        po.setLat(new BigDecimal(116.121312));
        List<PoiDto> list = new ArrayList<>();
        list.add(po);
        PoiDto po2  = new PoiDto();
        po2.setLng(new BigDecimal(36.198312));
        po2.setLat(new BigDecimal(116.121312));
        list.add(po2);
        System.out.println(JSON.toJSONString(list));
        net.sf.json.JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        System.out.println("==="+jsonArray.toString()+"==");

    }

}
