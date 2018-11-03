package fuzhaohui.document.business.center;

import com.alibaba.fastjson.JSON;
import fuzhaohui.document.business.impl.DeliverBaseVoImpl;
import fuzhaohui.document.business.impl.DeliverOrderVoImpl;
import fuzhaohui.document.business.impl.MerchantVoImpl;
import fuzhaohui.document.business.model.*;
import net.sf.json.JSONArray;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author fuzh
 * @desc
 * @Date:2018年11月2日11:01:17$ $
 */
public class DeliverCenterCtrl {


    public static void main(String[] args) {
        //获取配送基础规则
        Map<Integer, List<DeliverBaseVO>> mapBaseDeliver = getIntegerListMap();
        Map<Integer, List<DeliverOrderVO>> mapDeliverOrderVOS = getOrderDeliverListMap();
        //根据shopCode分组数据
        List<DeliverDateCollection> deliverDateCollections = getDeliverDateCollections(mapBaseDeliver, mapDeliverOrderVOS);
        //拼装sql文件
        //inster distributionRule
        List<DistributionRulePO> distributionRulePOS = getDistributionRulePOS(deliverDateCollections);
        System.out.println(distributionRulePOS.size()+"条规则");
        writeDistributionRuleFile(distributionRulePOS);

        List<DistributionRuleDetailPO> distributionRuleDetailPOS = getDistributionRuleDetailPOS(deliverDateCollections);
        System.out.println("详细规则："+distributionRuleDetailPOS.size());
        writeDistributionRuleDetailFile(distributionRuleDetailPOS);
        //构建distributionRule detail
        // INSERT INTO `merchant`.`distribution_rule_detail`
        // (`distribution_rule_id`, `begin_time`, `end_time`, `daily_max_mun`, `weekends_max_mun`, `service_charge`, `type`, `is_available`, `create_time`, `create_time_db`, `update_time`, `company_id`, `uuid`)
        // VALUES ('1014081701000000', '17:30:17', '20:30:22', '30', '150', '2', '1', '1', '2018-11-03 22:21:49', NULL, '2018-11-03 22:21:49', '130', 'caef7f704fc14a239a340b00224917ec');


        // INSERT INTO `merchant`.`distribution_rule_detail`
        // (`distribution_rule_id`, `begin_time`, `end_time`, `distribution_day`, `distribution_end_date`, `distribution_begin_date`, `type`, `is_available`, `create_time`, `create_time_db`, `update_time`, `company_id`, `uuid`)
        // VALUES ('1014081701000000', '00:00:00', '06:00:37', '0', '11:00:39', '07:00:31', '2', '1', '2018-11-03 22:21:49', NULL, '2018-11-03 22:21:49', '130', '5cd0dadeb3534b319eedc990bc57e24b');

        //type=1  base;
        //type =2 order



    }


    private static List<DistributionRuleDetailPO> getDistributionRuleDetailPOS(List<DeliverDateCollection> deliverDateCollections) {
        List<DistributionRuleDetailPO> distributionRuleDetailPOS = new ArrayList<>();
        for(DeliverDateCollection deliverDateCollection : deliverDateCollections){
            for(DeliverBaseVO deliverBaseVO : deliverDateCollection.getDeliverBaseVOS()){
                DistributionRuleDetailPO  distributionRuleDetailPO= new DistributionRuleDetailPO();
                distributionRuleDetailPO.setType("1");
                distributionRuleDetailPO.setModel(deliverBaseVO.getModel());
                distributionRuleDetailPO.setDistributionRuleId(deliverDateCollection.getMerchantVO().getMerchantId());
                distributionRuleDetailPO.setBeginTime(deliverBaseVO.getDeliverStartTime()+":00");
                distributionRuleDetailPO.setEndTime(deliverBaseVO.getDeliverEndTime()+":00");
                distributionRuleDetailPO.setDailyMaxMun(Long.valueOf(Double.valueOf(deliverBaseVO.getWeekMax()).intValue()));
                distributionRuleDetailPO.setWeekendsMaxMun(Long.valueOf(Double.valueOf(deliverBaseVO.getWeekendMax()).intValue()));
                distributionRuleDetailPO.setServiceCharge(Double.valueOf(deliverBaseVO.getFee()));
                distributionRuleDetailPO.setUuid(UUID.randomUUID().toString());
                distributionRuleDetailPO.setDistributionAreaName(deliverBaseVO.getRuleName());
                distributionRuleDetailPOS.add(distributionRuleDetailPO);
            }
            if(deliverDateCollection.getDeliverOrderVOList() == null){
                System.out.println(deliverDateCollection.getMerchantVO().getMerchantName()+"  下单规则为null");

            }else{
                for(DeliverOrderVO deliverOrderVO :deliverDateCollection.getDeliverOrderVOList()){
                    DistributionRuleDetailPO  distributionRuleDetailPO= new DistributionRuleDetailPO();
                    distributionRuleDetailPO.setType("2");
                    distributionRuleDetailPO.setModel(deliverOrderVO.getModel());
                    distributionRuleDetailPO.setDistributionRuleId(deliverDateCollection.getMerchantVO().getMerchantId());
                    distributionRuleDetailPO.setBeginTime(deliverOrderVO.getLongestStartTime()+":00");
                    distributionRuleDetailPO.setEndTime(deliverOrderVO.getLongestEndTime()+":00");
                    distributionRuleDetailPO.setDistributionDay(String.valueOf(deliverOrderVO.getLongestDay()-1));
                    distributionRuleDetailPO.setDistributionBeginDate(deliverOrderVO.getDeliverRuleModelStartCreateOrderTime()+":00");
                    distributionRuleDetailPO.setDistributionEndDate(deliverOrderVO.getDeliverRuleModelEndCreateOrderTime()+":00");
                    distributionRuleDetailPO.setUuid(UUID.randomUUID().toString());
                    distributionRuleDetailPO.setDistributionAreaName(deliverOrderVO.getRuleName());
                    distributionRuleDetailPOS.add(distributionRuleDetailPO);
                }
            }



        }
        return distributionRuleDetailPOS;
    }



    private static List<DistributionRulePO> getDistributionRulePOS(List<DeliverDateCollection> deliverDateCollections) {
        List<DistributionRulePO> distributionRulePOS = new ArrayList<>();
        Map<String,DistributionRulePO> distributionRulePOMap = new HashMap<>();
        for(DeliverDateCollection deliverDateCollection : deliverDateCollections){
            for(DeliverBaseVO deliverBaseVO : deliverDateCollection.getDeliverBaseVOS()){
                if(distributionRulePOMap.get(deliverBaseVO.getShopCode()+deliverBaseVO.getModel()) != null){
                    continue;
                }
                DistributionRulePO distributionRulePO = new DistributionRulePO();
                if(deliverBaseVO.getRuleName().contains("JINYONG") || deliverBaseVO.getRuleName().contains("禁用") || deliverBaseVO.getRuleName().contains("废弃") ){
                    distributionRulePO.setStatus("0");
                }else{
                    distributionRulePO.setStatus("1");
                }

                distributionRulePO.setStoreId(deliverDateCollection.getMerchantVO().getMerchantId());
                distributionRulePO.setDistributionMode(deliverBaseVO.getModel());
                distributionRulePO.setRuleName(deliverBaseVO.getRuleName());
                distributionRulePO.setDistributionAreaName(deliverBaseVO.getRuleName());
                distributionRulePO.setPoi(deliverBaseVO.getPoi());
                if(deliverBaseVO.getPoi() == null){
                    distributionRulePO.setPoi(deliverDateCollection.getMerchantVO().getPoi());
                }

                if(deliverBaseVO.getRuleName().startsWith("上海")){
                    if("C".equals(deliverBaseVO.getModel()) || "D".equals(deliverBaseVO.getModel())){
                        //不包括崇明岛
                        distributionRulePO.setPoi("[{\"lng\":121.933926,\"lat\":31.164735},{\"lng\":121.881002,\"lat\":31.182604},{\"lng\":121.851564,\"lat\":31.221009},{\"lng\":121.805522,\"lat\":31.26153},{\"lng\":121.747535,\"lat\":31.310738},{\"lng\":121.682543,\"lat\":31.362487},{\"lng\":121.616672,\"lat\":31.396079},{\"lng\":121.45464,\"lat\":31.482872},{\"lng\":121.443039,\"lat\":31.506544},{\"lng\":121.445177,\"lat\":31.531196},{\"lng\":121.411546,\"lat\":31.523189},{\"lng\":121.459359,\"lat\":31.555733},{\"lng\":121.447988,\"lat\":31.545281},{\"lng\":121.403451,\"lat\":31.539701},{\"lng\":121.439469,\"lat\":31.532402},{\"lng\":121.457033,\"lat\":31.528729},{\"lng\":121.451814,\"lat\":31.525956},{\"lng\":121.400273,\"lat\":31.541498},{\"lng\":121.382534,\"lat\":31.568852},{\"lng\":121.366903,\"lat\":31.561727},{\"lng\":121.350049,\"lat\":31.577629},{\"lng\":121.383277,\"lat\":31.554439},{\"lng\":121.376843,\"lat\":31.565302},{\"lng\":121.354536,\"lat\":31.572609},{\"lng\":121.38991,\"lat\":31.534155},{\"lng\":121.3982,\"lat\":31.551478},{\"lng\":121.328388,\"lat\":31.589487},{\"lng\":121.39356,\"lat\":31.550194},{\"lng\":121.345062,\"lat\":31.514755},{\"lng\":121.333595,\"lat\":31.510589},{\"lng\":121.330245,\"lat\":31.505665},{\"lng\":121.326513,\"lat\":31.505825},{\"lng\":121.326415,\"lat\":31.511992},{\"lng\":121.320001,\"lat\":31.50673},{\"lng\":121.317099,\"lat\":31.511793},{\"lng\":121.311861,\"lat\":31.511432},{\"lng\":121.304831,\"lat\":31.497481},{\"lng\":121.299654,\"lat\":31.49548},{\"lng\":121.297409,\"lat\":31.497416},{\"lng\":121.296292,\"lat\":31.494681},{\"lng\":121.28788,\"lat\":31.496863},{\"lng\":121.278593,\"lat\":31.490023},{\"lng\":121.275258,\"lat\":31.493349},{\"lng\":121.266797,\"lat\":31.483704},{\"lng\":121.259906,\"lat\":31.485415},{\"lng\":121.253678,\"lat\":31.483021},{\"lng\":121.254761,\"lat\":31.487855},{\"lng\":121.251019,\"lat\":31.487212},{\"lng\":121.248463,\"lat\":31.49919},{\"lng\":121.241471,\"lat\":31.499243},{\"lng\":121.234982,\"lat\":31.488498},{\"lng\":121.237125,\"lat\":31.487036},{\"lng\":121.225224,\"lat\":31.481884},{\"lng\":121.22007,\"lat\":31.482509},{\"lng\":121.221305,\"lat\":31.485386},{\"lng\":121.215636,\"lat\":31.483378},{\"lng\":121.193871,\"lat\":31.468053},{\"lng\":121.192151,\"lat\":31.460951},{\"lng\":121.187854,\"lat\":31.45834},{\"lng\":121.167969,\"lat\":31.455198},{\"lng\":121.154436,\"lat\":31.449193},{\"lng\":121.154688,\"lat\":31.441721},{\"lng\":121.169097,\"lat\":31.440031},{\"lng\":121.171554,\"lat\":31.436777},{\"lng\":121.168118,\"lat\":31.431035},{\"lng\":121.152402,\"lat\":31.427},{\"lng\":121.164715,\"lat\":31.414346},{\"lng\":121.155607,\"lat\":31.406543},{\"lng\":121.15936,\"lat\":31.404423},{\"lng\":121.154055,\"lat\":31.403383},{\"lng\":121.155717,\"lat\":31.400427},{\"lng\":121.149869,\"lat\":31.398398},{\"lng\":121.154598,\"lat\":31.391284},{\"lng\":121.120405,\"lat\":31.379692},{\"lng\":121.126981,\"lat\":31.374985},{\"lng\":121.112431,\"lat\":31.372062},{\"lng\":121.114753,\"lat\":31.357056},{\"lng\":121.123584,\"lat\":31.358136},{\"lng\":121.124882,\"lat\":31.34952},{\"lng\":121.137113,\"lat\":31.350396},{\"lng\":121.136169,\"lat\":31.341628},{\"lng\":121.140029,\"lat\":31.336069},{\"lng\":121.134933,\"lat\":31.325068},{\"lng\":121.136567,\"lat\":31.308405},{\"lng\":121.14556,\"lat\":31.308673},{\"lng\":121.145489,\"lat\":31.311947},{\"lng\":121.15126,\"lat\":31.3141},{\"lng\":121.158801,\"lat\":31.295888},{\"lng\":121.167789,\"lat\":31.289605},{\"lng\":121.158555,\"lat\":31.281253},{\"lng\":121.149382,\"lat\":31.280265},{\"lng\":121.149148,\"lat\":31.283176},{\"lng\":121.120855,\"lat\":31.291669},{\"lng\":121.109246,\"lat\":31.281482},{\"lng\":121.099874,\"lat\":31.290635},{\"lng\":121.100998,\"lat\":31.296131},{\"lng\":121.093796,\"lat\":31.298626},{\"lng\":121.087602,\"lat\":31.284521},{\"lng\":121.090943,\"lat\":31.282218},{\"lng\":121.089053,\"lat\":31.278513},{\"lng\":121.068593,\"lat\":31.274428},{\"lng\":121.067894,\"lat\":31.254208},{\"lng\":121.063383,\"lat\":31.252467},{\"lng\":121.070373,\"lat\":31.252502},{\"lng\":121.067468,\"lat\":31.242879},{\"lng\":121.073736,\"lat\":31.239254},{\"lng\":121.069125,\"lat\":31.232333},{\"lng\":121.075533,\"lat\":31.20281},{\"lng\":121.072461,\"lat\":31.201297},{\"lng\":121.078375,\"lat\":31.199239},{\"lng\":121.078023,\"lat\":31.192345},{\"lng\":121.075211,\"lat\":31.191683},{\"lng\":121.07613,\"lat\":31.18922},{\"lng\":121.081434,\"lat\":31.190765},{\"lng\":121.077122,\"lat\":31.18795},{\"lng\":121.082828,\"lat\":31.173019},{\"lng\":121.074977,\"lat\":31.154547},{\"lng\":121.066066,\"lat\":31.159403},{\"lng\":121.056356,\"lat\":31.156714},{\"lng\":121.051182,\"lat\":31.160709},{\"lng\":121.048551,\"lat\":31.155709},{\"lng\":121.051768,\"lat\":31.150875},{\"lng\":121.045054,\"lat\":31.143035},{\"lng\":121.039758,\"lat\":31.148142},{\"lng\":121.035025,\"lat\":31.147128},{\"lng\":121.034764,\"lat\":31.14983},{\"lng\":121.029341,\"lat\":31.144493},{\"lng\":121.011759,\"lat\":31.140932},{\"lng\":120.985026,\"lat\":31.140707},{\"lng\":120.959342,\"lat\":31.147622},{\"lng\":120.942299,\"lat\":31.147946},{\"lng\":120.914462,\"lat\":31.140592},{\"lng\":120.888027,\"lat\":31.141518},{\"lng\":120.875632,\"lat\":31.123646},{\"lng\":120.862372,\"lat\":31.11466},{\"lng\":120.86322,\"lat\":31.107526},{\"lng\":120.870712,\"lat\":31.106828},{\"lng\":120.872731,\"lat\":31.103657},{\"lng\":120.881047,\"lat\":31.106637},{\"lng\":120.885464,\"lat\":31.104466},{\"lng\":120.884803,\"lat\":31.101227},{\"lng\":120.895672,\"lat\":31.099893},{\"lng\":120.899062,\"lat\":31.103328},{\"lng\":120.903128,\"lat\":31.092058},{\"lng\":120.90885,\"lat\":31.091365},{\"lng\":120.910735,\"lat\":31.08531},{\"lng\":120.905753,\"lat\":31.083248},{\"lng\":120.900941,\"lat\":31.054916},{\"lng\":120.912774,\"lat\":31.041543},{\"lng\":120.907419,\"lat\":31.037086},{\"lng\":120.906806,\"lat\":31.027307},{\"lng\":120.91504,\"lat\":31.016592},{\"lng\":120.933813,\"lat\":31.01821},{\"lng\":120.946022,\"lat\":31.015355},{\"lng\":120.949821,\"lat\":31.023708},{\"lng\":120.957135,\"lat\":31.026547},{\"lng\":120.95475,\"lat\":31.034493},{\"lng\":120.958584,\"lat\":31.036682},{\"lng\":120.964838,\"lat\":31.035163},{\"lng\":120.966961,\"lat\":31.028363},{\"lng\":120.97141,\"lat\":31.026169},{\"lng\":120.96986,\"lat\":31.02271},{\"lng\":120.979605,\"lat\":31.024173},{\"lng\":120.997363,\"lat\":31.019459},{\"lng\":120.997517,\"lat\":31.000167},{\"lng\":121.001757,\"lat\":31.000864},{\"lng\":121.009224,\"lat\":30.984261},{\"lng\":121.00749,\"lat\":30.979702},{\"lng\":120.998758,\"lat\":30.976366},{\"lng\":121.001471,\"lat\":30.964295},{\"lng\":120.998719,\"lat\":30.964267},{\"lng\":121.00374,\"lat\":30.956197},{\"lng\":121.002252,\"lat\":30.949991},{\"lng\":121.007643,\"lat\":30.943421},{\"lng\":121.005674,\"lat\":30.916505},{\"lng\":121.011902,\"lat\":30.915012},{\"lng\":121.010072,\"lat\":30.910812},{\"lng\":121.00566,\"lat\":30.911906},{\"lng\":120.999459,\"lat\":30.90818},{\"lng\":120.997146,\"lat\":30.901629},{\"lng\":120.999877,\"lat\":30.895992},{\"lng\":121.014218,\"lat\":30.894113},{\"lng\":121.014895,\"lat\":30.889224},{\"lng\":121.024158,\"lat\":30.888281},{\"lng\":121.028721,\"lat\":30.881343},{\"lng\":121.022601,\"lat\":30.877102},{\"lng\":121.02224,\"lat\":30.866031},{\"lng\":121.01748,\"lat\":30.861722},{\"lng\":121.021567,\"lat\":30.859911},{\"lng\":121.01341,\"lat\":30.856684},{\"lng\":120.995825,\"lat\":30.839761},{\"lng\":120.998931,\"lat\":30.837648},{\"lng\":120.996564,\"lat\":30.829507},{\"lng\":121.000778,\"lat\":30.827316},{\"lng\":121.006233,\"lat\":30.834846},{\"lng\":121.010298,\"lat\":30.832242},{\"lng\":121.021039,\"lat\":30.841934},{\"lng\":121.036515,\"lat\":30.834567},{\"lng\":121.044803,\"lat\":30.820262},{\"lng\":121.050231,\"lat\":30.821572},{\"lng\":121.04693,\"lat\":30.832894},{\"lng\":121.056046,\"lat\":30.832195},{\"lng\":121.053981,\"lat\":30.836906},{\"lng\":121.067825,\"lat\":30.844902},{\"lng\":121.066692,\"lat\":30.851453},{\"lng\":121.072031,\"lat\":30.854651},{\"lng\":121.08699,\"lat\":30.855189},{\"lng\":121.103617,\"lat\":30.863242},{\"lng\":121.107926,\"lat\":30.856005},{\"lng\":121.111343,\"lat\":30.855874},{\"lng\":121.117708,\"lat\":30.857932},{\"lng\":121.117614,\"lat\":30.8615},{\"lng\":121.130183,\"lat\":30.853446},{\"lng\":121.125726,\"lat\":30.842684},{\"lng\":121.138364,\"lat\":30.842621},{\"lng\":121.140764,\"lat\":30.834485},{\"lng\":121.144233,\"lat\":30.836127},{\"lng\":121.144434,\"lat\":30.831057},{\"lng\":121.134634,\"lat\":30.815403},{\"lng\":121.133744,\"lat\":30.784591},{\"lng\":121.157468,\"lat\":30.78495},{\"lng\":121.166548,\"lat\":30.779685},{\"lng\":121.17637,\"lat\":30.78161},{\"lng\":121.180845,\"lat\":30.778535},{\"lng\":121.19338,\"lat\":30.781923},{\"lng\":121.192627,\"lat\":30.785267},{\"lng\":121.197308,\"lat\":30.787395},{\"lng\":121.203269,\"lat\":30.77979},{\"lng\":121.207388,\"lat\":30.780095},{\"lng\":121.205825,\"lat\":30.789738},{\"lng\":121.223408,\"lat\":30.792366},{\"lng\":121.227892,\"lat\":30.78172},{\"lng\":121.234514,\"lat\":30.778846},{\"lng\":121.238979,\"lat\":30.761794},{\"lng\":121.277761,\"lat\":30.738709},{\"lng\":121.274881,\"lat\":30.735421},{\"lng\":121.277653,\"lat\":30.732618},{\"lng\":121.276516,\"lat\":30.723076},{\"lng\":121.272921,\"lat\":30.721465},{\"lng\":121.274749,\"lat\":30.718191},{\"lng\":121.271697,\"lat\":30.715359},{\"lng\":121.275908,\"lat\":30.704127},{\"lng\":121.289049,\"lat\":30.689923},{\"lng\":121.473528,\"lat\":30.676443},{\"lng\":121.520071,\"lat\":30.677125},{\"lng\":121.555451,\"lat\":30.688512},{\"lng\":121.718105,\"lat\":30.726146},{\"lng\":121.998823,\"lat\":30.812345},{\"lng\":121.990238,\"lat\":31.037402},{\"lng\":121.948909,\"lat\":31.136517},{\"lng\":121.933926,\"lat\":31.164735}]");
                    }else if("E".equals(deliverBaseVO.getModel())){
                        //只包括崇明岛
                        distributionRulePO.setPoi("[{\"lng\":121.933926,\"lat\":31.164735},{\"lng\":121.960653,\"lat\":31.22489},{\"lng\":121.994386,\"lat\":31.39935},{\"lng\":122.006022,\"lat\":31.507726},{\"lng\":122.005714,\"lat\":31.619976},{\"lng\":121.874804,\"lat\":31.624791},{\"lng\":121.734775,\"lat\":31.672315},{\"lng\":121.605702,\"lat\":31.721479},{\"lng\":121.572128,\"lat\":31.728739},{\"lng\":121.519335,\"lat\":31.753332},{\"lng\":121.480211,\"lat\":31.759357},{\"lng\":121.453866,\"lat\":31.76847},{\"lng\":121.439748,\"lat\":31.776723},{\"lng\":121.425424,\"lat\":31.79217},{\"lng\":121.409257,\"lat\":31.817571},{\"lng\":121.374636,\"lat\":31.846578},{\"lng\":121.35843,\"lat\":31.857813},{\"lng\":121.320622,\"lat\":31.8733},{\"lng\":121.291897,\"lat\":31.87257},{\"lng\":121.23232,\"lat\":31.849139},{\"lng\":121.209973,\"lat\":31.836995},{\"lng\":121.16355,\"lat\":31.8022},{\"lng\":121.107678,\"lat\":31.768678},{\"lng\":121.12657,\"lat\":31.752617},{\"lng\":121.233355,\"lat\":31.681521},{\"lng\":121.271857,\"lat\":31.652068},{\"lng\":121.328388,\"lat\":31.589487},{\"lng\":121.39356,\"lat\":31.550194},{\"lng\":121.438446,\"lat\":31.514755},{\"lng\":121.392647,\"lat\":31.53049},{\"lng\":121.459334,\"lat\":31.491614},{\"lng\":121.436376,\"lat\":31.505825},{\"lng\":121.447265,\"lat\":31.511992},{\"lng\":121.429864,\"lat\":31.50673},{\"lng\":121.446188,\"lat\":31.511793},{\"lng\":121.451937,\"lat\":31.492698},{\"lng\":121.423621,\"lat\":31.512702},{\"lng\":121.413637,\"lat\":31.517141},{\"lng\":121.429932,\"lat\":31.516149},{\"lng\":121.48306,\"lat\":31.478286},{\"lng\":121.453104,\"lat\":31.496357},{\"lng\":121.425209,\"lat\":31.508572},{\"lng\":121.429655,\"lat\":31.515783},{\"lng\":121.42838,\"lat\":31.516181},{\"lng\":121.428159,\"lat\":31.509465},{\"lng\":121.430881,\"lat\":31.510005},{\"lng\":121.438386,\"lat\":31.516392},{\"lng\":121.424362,\"lat\":31.514786},{\"lng\":121.43504,\"lat\":31.522338},{\"lng\":121.431111,\"lat\":31.520264},{\"lng\":121.426179,\"lat\":31.531438},{\"lng\":121.402524,\"lat\":31.53884},{\"lng\":121.404667,\"lat\":31.529186},{\"lng\":121.412678,\"lat\":31.525207},{\"lng\":121.404778,\"lat\":31.524661},{\"lng\":121.406699,\"lat\":31.522269},{\"lng\":121.479308,\"lat\":31.481036},{\"lng\":121.444497,\"lat\":31.502015},{\"lng\":121.439343,\"lat\":31.500185},{\"lng\":121.52843,\"lat\":31.439595},{\"lng\":121.475586,\"lat\":31.475111},{\"lng\":121.466173,\"lat\":31.476135},{\"lng\":121.467798,\"lat\":31.468665},{\"lng\":121.464355,\"lat\":31.485715},{\"lng\":121.470931,\"lat\":31.475436},{\"lng\":121.493588,\"lat\":31.463839},{\"lng\":121.472379,\"lat\":31.484402},{\"lng\":121.476452,\"lat\":31.478784},{\"lng\":121.452581,\"lat\":31.492067},{\"lng\":121.471097,\"lat\":31.482922},{\"lng\":121.471285,\"lat\":31.481883},{\"lng\":121.496293,\"lat\":31.467217},{\"lng\":121.495938,\"lat\":31.467532},{\"lng\":121.451572,\"lat\":31.491461},{\"lng\":121.49806,\"lat\":31.452354},{\"lng\":121.508756,\"lat\":31.460537},{\"lng\":121.498326,\"lat\":31.461131},{\"lng\":121.529487,\"lat\":31.434423},{\"lng\":121.512225,\"lat\":31.431987},{\"lng\":121.532749,\"lat\":31.438611},{\"lng\":121.509275,\"lat\":31.444173},{\"lng\":121.516571,\"lat\":31.436585},{\"lng\":121.517684,\"lat\":31.453293},{\"lng\":121.505722,\"lat\":31.436447},{\"lng\":121.526582,\"lat\":31.428007},{\"lng\":121.536948,\"lat\":31.422416},{\"lng\":121.551983,\"lat\":31.424514},{\"lng\":121.497329,\"lat\":31.450098},{\"lng\":121.518603,\"lat\":31.447144},{\"lng\":121.497379,\"lat\":31.449073},{\"lng\":121.504624,\"lat\":31.438391},{\"lng\":121.511931,\"lat\":31.438577},{\"lng\":121.563882,\"lat\":31.411015},{\"lng\":121.574041,\"lat\":31.399571},{\"lng\":121.609124,\"lat\":31.38119},{\"lng\":121.526968,\"lat\":31.434869},{\"lng\":121.503372,\"lat\":31.43567},{\"lng\":121.544235,\"lat\":31.429959},{\"lng\":121.551774,\"lat\":31.420562},{\"lng\":121.593568,\"lat\":31.39482},{\"lng\":121.544986,\"lat\":31.428625},{\"lng\":121.579457,\"lat\":31.415171},{\"lng\":121.475761,\"lat\":31.472317},{\"lng\":121.480863,\"lat\":31.460038},{\"lng\":121.489227,\"lat\":31.455387},{\"lng\":121.501428,\"lat\":31.458672},{\"lng\":121.504949,\"lat\":31.446854},{\"lng\":121.490725,\"lat\":31.468065},{\"lng\":121.479281,\"lat\":31.453846},{\"lng\":121.507794,\"lat\":31.451166},{\"lng\":121.495169,\"lat\":31.451456},{\"lng\":121.516103,\"lat\":31.445752},{\"lng\":121.524277,\"lat\":31.441577},{\"lng\":121.508717,\"lat\":31.45318},{\"lng\":121.489301,\"lat\":31.472291},{\"lng\":121.552281,\"lat\":31.410901},{\"lng\":121.490695,\"lat\":31.451084},{\"lng\":121.495204,\"lat\":31.443211},{\"lng\":121.495906,\"lat\":31.450396},{\"lng\":121.512289,\"lat\":31.445372},{\"lng\":121.477932,\"lat\":31.466926},{\"lng\":121.50723,\"lat\":31.43734},{\"lng\":121.509074,\"lat\":31.440722},{\"lng\":121.495493,\"lat\":31.44931},{\"lng\":121.531396,\"lat\":31.434483},{\"lng\":121.499197,\"lat\":31.462761},{\"lng\":121.559361,\"lat\":31.410384},{\"lng\":121.556685,\"lat\":31.40975},{\"lng\":121.53567,\"lat\":31.426123},{\"lng\":121.523356,\"lat\":31.446989},{\"lng\":121.45407,\"lat\":31.495461},{\"lng\":121.45694,\"lat\":31.496955},{\"lng\":121.456569,\"lat\":31.494897},{\"lng\":121.460689,\"lat\":31.487623},{\"lng\":121.474044,\"lat\":31.471866},{\"lng\":121.507819,\"lat\":31.445634},{\"lng\":121.503174,\"lat\":31.445554},{\"lng\":121.527145,\"lat\":31.428455},{\"lng\":121.476979,\"lat\":31.466301},{\"lng\":121.531987,\"lat\":31.435295},{\"lng\":121.45229,\"lat\":31.492287},{\"lng\":121.452659,\"lat\":31.490818},{\"lng\":121.4958,\"lat\":31.452008},{\"lng\":121.452155,\"lat\":31.491154},{\"lng\":121.556815,\"lat\":31.416079},{\"lng\":121.576269,\"lat\":31.39429},{\"lng\":121.6221,\"lat\":31.381221},{\"lng\":121.501761,\"lat\":31.4612},{\"lng\":121.494203,\"lat\":31.443526},{\"lng\":121.475823,\"lat\":31.466527},{\"lng\":121.488321,\"lat\":31.469118},{\"lng\":121.612678,\"lat\":31.389071},{\"lng\":121.615418,\"lat\":31.392465},{\"lng\":121.573767,\"lat\":31.396421},{\"lng\":121.617561,\"lat\":31.384492},{\"lng\":121.592521,\"lat\":31.39252},{\"lng\":121.649274,\"lat\":31.362519},{\"lng\":121.638649,\"lat\":31.368092},{\"lng\":121.593044,\"lat\":31.391377},{\"lng\":121.516901,\"lat\":31.436744},{\"lng\":121.617901,\"lat\":31.387779},{\"lng\":121.627843,\"lat\":31.371524},{\"lng\":121.582348,\"lat\":31.403248},{\"lng\":121.650458,\"lat\":31.366603},{\"lng\":121.653796,\"lat\":31.369528},{\"lng\":121.661847,\"lat\":31.364673},{\"lng\":121.656817,\"lat\":31.36742},{\"lng\":121.68763,\"lat\":31.336806},{\"lng\":121.707868,\"lat\":31.329918},{\"lng\":121.674417,\"lat\":31.35709},{\"lng\":121.655158,\"lat\":31.356795},{\"lng\":121.663392,\"lat\":31.363803},{\"lng\":121.66292,\"lat\":31.369839},{\"lng\":121.683404,\"lat\":31.33082},{\"lng\":121.66133,\"lat\":31.359464},{\"lng\":121.697813,\"lat\":31.339703},{\"lng\":121.676575,\"lat\":31.352291},{\"lng\":121.699464,\"lat\":31.334036},{\"lng\":121.705352,\"lat\":31.324861},{\"lng\":121.688851,\"lat\":31.336107},{\"lng\":121.642593,\"lat\":31.371806},{\"lng\":121.755187,\"lat\":31.277038},{\"lng\":121.687131,\"lat\":31.343218},{\"lng\":121.71802,\"lat\":31.312544},{\"lng\":121.653125,\"lat\":31.36087},{\"lng\":121.729443,\"lat\":31.311151},{\"lng\":121.732936,\"lat\":31.307809},{\"lng\":121.733605,\"lat\":31.312922},{\"lng\":121.742578,\"lat\":31.299246},{\"lng\":121.708213,\"lat\":31.323256},{\"lng\":121.683576,\"lat\":31.345851},{\"lng\":121.789587,\"lat\":31.246865},{\"lng\":121.762481,\"lat\":31.284672},{\"lng\":121.76286,\"lat\":31.283611},{\"lng\":121.673691,\"lat\":31.356515},{\"lng\":121.714851,\"lat\":31.317086},{\"lng\":121.609053,\"lat\":31.401275},{\"lng\":121.696698,\"lat\":31.328831},{\"lng\":121.63527,\"lat\":31.375451},{\"lng\":121.780887,\"lat\":31.281453},{\"lng\":121.756103,\"lat\":31.289798},{\"lng\":121.637445,\"lat\":31.385512},{\"lng\":121.635575,\"lat\":31.368402},{\"lng\":121.666469,\"lat\":31.361361},{\"lng\":121.611648,\"lat\":31.392761},{\"lng\":121.657207,\"lat\":31.373483},{\"lng\":121.733957,\"lat\":31.301595},{\"lng\":121.696731,\"lat\":31.337326},{\"lng\":121.705118,\"lat\":31.328917},{\"lng\":121.712174,\"lat\":31.321715},{\"lng\":121.728637,\"lat\":31.311613},{\"lng\":121.730196,\"lat\":31.311901},{\"lng\":121.740058,\"lat\":31.301959},{\"lng\":121.753657,\"lat\":31.279272},{\"lng\":121.755037,\"lat\":31.276775},{\"lng\":121.753598,\"lat\":31.28258},{\"lng\":121.761492,\"lat\":31.267439},{\"lng\":121.729255,\"lat\":31.311321},{\"lng\":121.740442,\"lat\":31.294542},{\"lng\":121.752615,\"lat\":31.2907},{\"lng\":121.740739,\"lat\":31.30464},{\"lng\":121.709531,\"lat\":31.31673},{\"lng\":121.75157,\"lat\":31.285679},{\"lng\":121.758645,\"lat\":31.294635},{\"lng\":121.752134,\"lat\":31.289229},{\"lng\":121.786642,\"lat\":31.273821},{\"lng\":121.779709,\"lat\":31.277441},{\"lng\":121.738385,\"lat\":31.309776},{\"lng\":121.780051,\"lat\":31.283436},{\"lng\":121.734732,\"lat\":31.302516},{\"lng\":121.744155,\"lat\":31.307413},{\"lng\":121.764485,\"lat\":31.28421},{\"lng\":121.766222,\"lat\":31.268924},{\"lng\":121.879961,\"lat\":31.188559},{\"lng\":121.859707,\"lat\":31.204475},{\"lng\":121.890249,\"lat\":31.172097},{\"lng\":121.870889,\"lat\":31.175874},{\"lng\":121.870915,\"lat\":31.177784},{\"lng\":121.880764,\"lat\":31.170636},{\"lng\":121.882662,\"lat\":31.161982},{\"lng\":121.887237,\"lat\":31.165774},{\"lng\":121.875945,\"lat\":31.165306},{\"lng\":121.877409,\"lat\":31.168228},{\"lng\":121.901537,\"lat\":31.147041},{\"lng\":121.902681,\"lat\":31.149493},{\"lng\":121.919012,\"lat\":31.139006},{\"lng\":121.959199,\"lat\":31.119188},{\"lng\":121.955684,\"lat\":31.12433},{\"lng\":121.953504,\"lat\":31.124978},{\"lng\":121.951099,\"lat\":31.135016},{\"lng\":121.948909,\"lat\":31.136517},{\"lng\":121.933926,\"lat\":31.164735}]");
                    }
                }


                if("全部商品".equals(deliverBaseVO.getDeliverScope())){
                    distributionRulePO.setDistributionProduct("0,1,2,3");
                }else if("生鲜".equals(deliverBaseVO.getDeliverScope())){
                    distributionRulePO.setDistributionProduct("1,2");
                }
                distributionRulePO.setAfterDay(Double.valueOf(deliverBaseVO.getReserveDay()).intValue());

                distributionRulePO.setIsAvailable(0);
                distributionRulePO.setIsDeleted(0);
                distributionRulePOS.add(distributionRulePO);
                distributionRulePOMap.put(deliverBaseVO.getShopCode()+deliverBaseVO.getModel(),distributionRulePO);
            }
        }
        return distributionRulePOS;
    }


    private static void writeDistributionRuleDetailFile(List<DistributionRuleDetailPO> distributionRuleDetailPOS ){
        OutputStream stream = null;
        try {
            File directory = new File("");
            directory =  new File(directory.getCanonicalPath().toString()+"/source/output/distribution_rule_detail.sql");
            stream =  new FileOutputStream(directory);
            if(null != distributionRuleDetailPOS){
                //
                String truncateDistributionRule =  "TRUNCATE `保存查询sql`.`distribution_rule_detail`;"+ "\r\n";
                stream.write(truncateDistributionRule.getBytes());
                String alterAddModel = "ALTER TABLE `保存查询sql`.`distribution_rule_detail`	ADD COLUMN `model` VARCHAR(20) NULL DEFAULT NULL COMMENT '配送模式ABCD' AFTER `distribution_rule_id`;"+ "\r\n";
                stream.write(alterAddModel.getBytes());
                for(DistributionRuleDetailPO distributionRuleDetailPO : distributionRuleDetailPOS){
                    String string = distributionRuleDetailPO.toInsertSql() + "\r\n";
                    //将字符串数据转换为字节数组
                    byte[] bytes = string.getBytes();
                    //将字节数组写入到文件
                    stream.write(bytes);
                }
                String updateRuleId = "update `保存查询sql`.distribution_rule_detail sd,`保存查询sql`.distribution_rule s set sd.distribution_rule_id = s.id where s.store_id = sd.distribution_rule_id and s.distribution_mode = sd.model ;"+ "\r\n";
                stream.write(updateRuleId.getBytes());
                String alterDROPModel = "ALTER TABLE `保存查询sql`.`distribution_rule_detail` DROP COLUMN `model`;"+ "\r\n";
                stream.write(alterDROPModel.getBytes());
            }else{
                String string =  "\r\n";
                //将字符串数据转换为字节数组
                byte[] bytes = string.getBytes();
                //将字节数组写入到文件
                stream.write(bytes);
            }
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    //关闭字节流
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //输出sql文件

    private static void writeDistributionRuleFile(List<DistributionRulePO> distributionRulePOS ){
        OutputStream stream = null;
        try {
            File directory = new File("");
            directory =  new File(directory.getCanonicalPath().toString()+"/source/output/distribution_rule.sql");
            stream =  new FileOutputStream(directory);
            if(null != distributionRulePOS){
                //清空表
                String truncateDistributionRule =  "TRUNCATE `保存查询sql`.`distribution_rule`;"+ "\r\n";
                stream.write(truncateDistributionRule.getBytes());
                for(DistributionRulePO distributionRulePO : distributionRulePOS){
                    String string = distributionRulePO.toInsertSql() + "\r\n";
                    //将字符串数据转换为字节数组
                    byte[] bytes = string.getBytes();
                    //将字节数组写入到文件
                    stream.write(bytes);
                }
            }else{
                String string =  "\r\n";
                //将字符串数据转换为字节数组
                byte[] bytes = string.getBytes();
                //将字节数组写入到文件
                stream.write(bytes);
            }
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    //关闭字节流
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<DeliverDateCollection> getDeliverDateCollections(Map<Integer, List<DeliverBaseVO>> mapBaseDeliver, Map<Integer, List<DeliverOrderVO>> mapDeliverOrderVOS) {
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
        return deliverDateCollections;
    }

    private static Map<Integer, List<DeliverOrderVO>> getOrderDeliverListMap() {
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
        return mapDeliverOrderVOS;
    }

    private static Map<Integer, List<DeliverBaseVO>> getIntegerListMap() {
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
        return mapBaseDeliver;
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
