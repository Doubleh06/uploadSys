package cn.uploadSys.service.upload;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.uploadSys.core.BusinessException;
import cn.uploadSys.entity.VO.QczjCarInfo2VO;
import cn.uploadSys.entity.VO.QczjCarInfoVO;
import cn.uploadSys.util.AdaptiveWidthUtils;
import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class QczjGetCarInfoService{

    private static final String stQueryKey = "mxoVEqZ6Two=";
    private static final String ndQueryKey = "6zglgujmr+8=";

    @Autowired
    private Environment env;
    @Autowired
    private QczjGetAccessTokenService qczjGetAccessTokenService;


    //[{"letter":"A","list":[{"name":"奥迪","id":33},{"name":"阿尔法・罗密欧","id":34},{"name":"阿斯顿・马丁","id":35},{"name":"AC Schnitzer","id":117},{"name":"安凯客车","id":221},{"name":"ALPINA","id":276},{"name":"埃安","id":313},{"name":"爱驰","id":327},{"name":"AUXUN傲旋","id":378},{"name":"阿维塔","id":502},{"name":"安徽猎豹","id":514},{"name":"AITO","id":509}]},{"letter":"B","list":[{"name":"北汽瑞翔","id":472},{"name":"百智新能源","id":486},{"name":"比德文汽车","id":387},{"name":"铂驰","id":392},{"name":"比克汽车","id":440},{"name":"宝骐汽车","id":351},{"name":"比速汽车","id":271},{"name":"北汽道达","id":301},{"name":"宝沃","id":231},{"name":"宝骏","id":120},{"name":"博速","id":140},{"name":"北汽威旺","id":143},{"name":"北汽制造","id":154},{"name":"北京汽车","id":173},{"name":"北汽幻速","id":203},{"name":"北汽新能源","id":208},{"name":"奔驰","id":36},{"name":"布加迪","id":37},{"name":"别克","id":38},{"name":"宾利","id":39},{"name":"保时捷","id":40},{"name":"比亚迪","id":75},{"name":"北汽昌河","id":79},{"name":"奔腾","id":95},{"name":"标致","id":13},{"name":"本田","id":14},{"name":"宝马","id":15},{"name":"北京","id":27}]},{"letter":"C","list":[{"name":"长安","id":76},{"name":"长城","id":77},{"name":"成功汽车","id":196},{"name":"长安欧尚","id":163},{"name":"长安凯程","id":294},{"name":"车驰汽车","id":366},{"name":"长安跨越","id":299},{"name":"创维汽车","id":400},{"name":"橙仕","id":477},{"name":"长安深蓝","id":530}]},{"letter":"D","list":[{"name":"电动屋","id":480},{"name":"东风御风","id":451},{"name":"大运","id":405},{"name":"东风富康","id":406},{"name":"东风EV新能源","id":447},{"name":"东风・瑞泰特","id":326},{"name":"大乘汽车","id":341},{"name":"电咖","id":280},{"name":"东风风光","id":259},{"name":"东风风行","id":165},{"name":"DS","id":169},{"name":"东风风度","id":187},{"name":"东风小康","id":142},{"name":"东风风神","id":113},{"name":"东南","id":81},{"name":"道奇","id":41},{"name":"大发","id":92},{"name":"东风","id":32},{"name":"大众","id":1}]},{"letter":"F","list":[{"name":"丰田","id":3},{"name":"福特","id":8},{"name":"菲亚特","id":11},{"name":"福田","id":96},{"name":"法拉利","id":42},{"name":"福迪","id":141},{"name":"飞凡汽车","id":438},{"name":"飞碟汽车","id":434}]},{"letter":"G","list":[{"name":"国金汽车","id":304},{"name":"国机智骏","id":369},{"name":"高合汽车","id":383},{"name":"广汽集团","id":329},{"name":"观致","id":152},{"name":"GMC","id":112},{"name":"广汽吉奥","id":108},{"name":"光冈","id":116},{"name":"广汽传祺","id":82}]},{"letter":"H","list":[{"name":"悍马","id":43},{"name":"黄海","id":97},{"name":"红旗","id":91},{"name":"华普","id":85},{"name":"海马","id":86},{"name":"华泰","id":87},{"name":"哈飞","id":24},{"name":"海格","id":150},{"name":"哈弗","id":181},{"name":"华骐","id":184},{"name":"恒天","id":164},{"name":"汉龙汽车","id":386},{"name":"红星汽车","id":336},{"name":"华泰新能源","id":260},{"name":"汉腾汽车","id":267},{"name":"华凯","id":245},{"name":"华利","id":237},{"name":"华颂","id":220},{"name":"合创","id":376},{"name":"宏远汽车","id":446},{"name":"华晨新日","id":442},{"name":"恒驰","id":428},{"name":"恒润汽车","id":469},{"name":"华梓汽车","id":498}]},{"letter":"I","list":[{"name":"Icona","id":188}]},{"letter":"J","list":[{"name":"金旅","id":175},{"name":"九龙","id":151},{"name":"金龙","id":145},{"name":"江铃","id":119},{"name":"吉利汽车","id":25},{"name":"Jeep","id":46},{"name":"捷豹","id":44},{"name":"金杯","id":83},{"name":"江淮","id":84},{"name":"极氪","id":456},{"name":"江南汽车","id":543},{"name":"集度","id":516},{"name":"金冠汽车","id":419},{"name":"江铃集团新能源","id":270},{"name":"ARCFOX极狐","id":272},{"name":"钧天","id":356},{"name":"捷达","id":358},{"name":"捷尼赛思","id":371},{"name":"几何汽车","id":373},{"name":"捷途","id":319},{"name":"君马汽车","id":297},{"name":"Polestar极星","id":308}]},{"letter":"K","list":[{"name":"卡升","id":224},{"name":"克慕勒","id":508},{"name":"克蒂汽车","id":501},{"name":"科尼赛克","id":100},{"name":"开瑞","id":101},{"name":"凯迪拉克","id":47},{"name":"克莱斯勒","id":9},{"name":"KTM","id":109},{"name":"卡尔森","id":156},{"name":"卡威","id":199},{"name":"开沃汽车","id":213},{"name":"凯翼","id":214},{"name":"�j驰","id":528}]},{"letter":"L","list":[{"name":"雷丁","id":215},{"name":"陆地方舟","id":204},{"name":"Lorinser","id":118},{"name":"理念","id":124},{"name":"雷诺","id":10},{"name":"兰博基尼","id":48},{"name":"路虎","id":49},{"name":"路特斯","id":50},{"name":"林肯","id":51},{"name":"雷克萨斯","id":52},{"name":"铃木","id":53},{"name":"劳斯莱斯","id":54},{"name":"陆风","id":88},{"name":"莲花汽车","id":89},{"name":"力帆汽车","id":80},{"name":"猎豹汽车","id":78},{"name":"莱茵汽车","id":497},{"name":"LIMGENE凌际","id":519},{"name":"雷达汽车","id":536},{"name":"凌宝汽车","id":420},{"name":"岚图汽车","id":425},{"name":"龙程汽车","id":444},{"name":"LUMMA","id":441},{"name":"LOCAL MOTORS","id":241},{"name":"领克","id":279},{"name":"零跑汽车","id":318},{"name":"LEVC","id":320},{"name":"LITE","id":335},{"name":"领途汽车","id":343},{"name":"理想汽车","id":345},{"name":"罗夫哈特","id":346}]},{"letter":"M","list":[{"name":"敏安汽车","id":349},{"name":"迈莎锐","id":374},{"name":"迈迈","id":381},{"name":"摩登汽车","id":443},{"name":"迈巴赫","id":55},{"name":"MINI","id":56},{"name":"玛莎拉蒂","id":57},{"name":"马自达","id":58},{"name":"名爵","id":20},{"name":"MELKUS","id":126},{"name":"迈凯伦","id":129},{"name":"摩根","id":168},{"name":"迈越","id":552}]},{"letter":"N","list":[{"name":"纳智捷","id":130},{"name":"哪吒汽车","id":309}]},{"letter":"O","list":[{"name":"欧拉","id":331},{"name":"欧联","id":242},{"name":"OBBIN","id":523},{"name":"欧朗","id":146},{"name":"欧宝","id":59},{"name":"讴歌","id":60}]},{"letter":"P","list":[{"name":"帕加尼","id":61},{"name":"朋克汽车","id":449}]},{"letter":"Q","list":[{"name":"奇瑞新能源","id":487},{"name":"奇鲁汽车","id":464},{"name":"前途","id":235},{"name":"乔治・巴顿","id":222},{"name":"起亚","id":62},{"name":"奇瑞","id":26},{"name":"启辰","id":122},{"name":"全球鹰","id":219},{"name":"骐铃汽车","id":210}]},{"letter":"R","list":[{"name":"如虎","id":174},{"name":"瑞麒","id":103},{"name":"荣威","id":19},{"name":"日产","id":63},{"name":"瑞驰新能源","id":296},{"name":"容大智造","id":337},{"name":"RAM","id":452},{"name":"瑞风汽车","id":525},{"name":"睿蓝汽车","id":416}]},{"letter":"S","list":[{"name":"神州","id":426},{"name":"上��","id":402},{"name":"SHELBY","id":388},{"name":"速达","id":433},{"name":"SONGSAN MOTORS","id":436},{"name":"沙龙汽车","id":503},{"name":"申龙汽车","id":482},{"name":"思皓","id":330},{"name":"SERES赛力斯","id":325},{"name":"斯达泰克","id":238},{"name":"SWM斯威汽车","id":269},{"name":"萨博","id":64},{"name":"斯巴鲁","id":65},{"name":"世爵","id":66},{"name":"斯柯达","id":67},{"name":"三菱","id":68},{"name":"双龙","id":69},{"name":"smart","id":45},{"name":"双环","id":90},{"name":"陕汽通家","id":149},{"name":"上汽大通MAXUS","id":155},{"name":"思铭","id":162},{"name":"赛麟","id":205},{"name":"盛唐","id":564}]},{"letter":"T","list":[{"name":"TECHART","id":202},{"name":"腾势","id":161},{"name":"特斯拉","id":133},{"name":"Tramontana","id":125},{"name":"天际汽车","id":339},{"name":"坦克","id":458},{"name":"拓锐斯特","id":520}]},{"letter":"W","list":[{"name":"万象汽车","id":522},{"name":"伟昊汽车","id":507},{"name":"维努斯","id":537},{"name":"WALD","id":445},{"name":"威马汽车","id":291},{"name":"魏牌","id":283},{"name":"蔚来","id":284},{"name":"五菱汽车","id":114},{"name":"五十铃","id":167},{"name":"潍柴英致","id":192},{"name":"威麟","id":102},{"name":"威兹曼","id":99},{"name":"沃尔沃","id":70},{"name":"未奥汽车","id":573}]},{"letter":"X","list":[{"name":"雪佛兰","id":71},{"name":"雪铁龙","id":72},{"name":"现代","id":12},{"name":"西雅特","id":98},{"name":"新龙马汽车","id":197},{"name":"新凯","id":185},{"name":"小鹏","id":275},{"name":"SRM鑫源","id":306},{"name":"星途","id":350},{"name":"新特汽车","id":324},{"name":"AM晓奥","id":439},{"name":"新吉奥","id":527},{"name":"小虎","id":457}]},{"letter":"Y","list":[{"name":"雅升汽车","id":467},{"name":"越界","id":546},{"name":"野马新能源","id":398},{"name":"一汽凌河","id":399},{"name":"IMSA英飒","id":429},{"name":"宇通客车","id":298},{"name":"云雀汽车","id":317},{"name":"远程","id":382},{"name":"银隆新能源","id":375},{"name":"裕路","id":307},{"name":"云度","id":286},{"name":"御捷","id":232},{"name":"一汽","id":110},{"name":"野马汽车","id":111},{"name":"依维柯","id":144},{"name":"永源","id":93},{"name":"英菲尼迪","id":73}]},{"letter":"Z","list":[{"name":"中兴","id":74},{"name":"中华","id":22},{"name":"众泰","id":94},{"name":"之诺","id":182},{"name":"知豆","id":206},{"name":"中国重汽VGV","id":393},{"name":"智己汽车","id":448},{"name":"自游家","id":511}]}]
    public List<QczjCarInfoVO> getCarBrands(String appId, String queryKey) {
        try {
            String clientId = env.getProperty("qczj.getCarBrand.client_id");
            String clientSecret = env.getProperty("qczj.getCarBrand.client_secret");
            String url = env.getProperty("qczj.getCarBrand.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarBrandsAccessToken");
            String importUrl = String.format(url, accessToken, appId, URLEncoder.encode(queryKey,"UTF-8"));
            HttpResponse<JsonNode> json = Unirest.get(importUrl).asJson();
            JSONArray arrays = json.getBody().getObject().getJSONObject("result").getJSONArray("brandlist");
            List<QczjCarInfoVO> brandsVOS = new ArrayList<>();
            arrays.forEach((array)->{
                 brandsVOS.addAll(JSON.parseArray(JSON.parseObject(array.toString()).getJSONArray("list").toString(), QczjCarInfoVO.class));
            });

            return brandsVOS;
        } catch (Exception e) {
            log.info("获取车品牌异常");
        }
        return null;
    }

    //{"brandid":34,"serieslist":[{"name":"Giulia朱丽叶","id":3825},{"name":"Stelvio斯坦维","id":4196},{"name":"阿尔法・罗密欧156","id":179},{"name":"阿尔法・罗密欧GT","id":401}],"brandname":"阿尔法・罗密欧"}
    public List<QczjCarInfoVO> getCarSeries(String appId, String queryKey, Integer brandId) {
        try {
            String clientId = env.getProperty("qczj.getCarSeries.client_id");
            String clientSecret = env.getProperty("qczj.getCarSeries.client_secret");
            String url = env.getProperty("qczj.getCarSeries.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarSeriesAccessToken");

            String getUrl = String.format(url, accessToken, appId, URLEncoder.encode(queryKey,"UTF-8"), brandId);
            HttpResponse<JsonNode> json = Unirest.get(getUrl).asJson();
            return JSON.parseArray(json.getBody().getObject().getJSONObject("result").getJSONArray("serieslist").toString(), QczjCarInfoVO.class);

        } catch (Exception e) {
            log.info("获取车系牌异常");
        }
        return null;
    }

    public List<QczjCarInfoVO> getAllCarSeries(String appId, String queryKey, List<QczjCarInfoVO> brands) {
        List<QczjCarInfoVO> allSeries = new ArrayList<>();
        brands.forEach(brand->{
            allSeries.addAll(getCarSeries(appId,queryKey,brand.getId()));
        });
        return allSeries;
    }


    //{"seriesname":"Stelvio斯坦维","seriesid":4196,"productlist":[{"name":"2018款 2.9T 510HP 四叶草版","id":27647},{"name":"2017款 2.0T 200HP 豪华版","id":30211},{"name":"2017款 2.0T 200HP 精英版","id":30712},{"name":"2017款 2.0T 280HP 豪华版","id":30713},{"name":"2019款 2.9T 510HP NRING纽博格林限量版","id":34979},{"name":"2019款 2.9T 510HP F1限量版","id":37925},{"name":"2019款 2.0T 280HP 豪华版","id":40390},{"name":"2019款 2.9T 510HP 四叶草版","id":40391},{"name":"2019款 2.0T 280HP Black Package黑标限量版","id":41093},{"name":"2020款 2.0T 280HP 赛道限量版","id":42786},{"name":"2020款 2.0T 280HP 豪华版","id":42805},{"name":"2020款 2.0T 280HP 豪华运动版","id":42806},{"name":"2020款 2.0T 280HP VIRTU限量版","id":46111},{"name":"2020款 2.0T 280HP 黯夜魅影限量版","id":47864},{"name":"2020款 2.0T 280HP 黯夜魅影运动限量版","id":47881},{"name":"2021款 2.0T 280HP Veloce Ti 竞速版","id":49240},{"name":"2021款 2.0T 280HP 豪华版","id":49640},{"name":"2021款 2.0T 280HP 豪华运动版","id":49641},{"name":"2022款 2.9T 四叶草版","id":56134},{"name":"2022款 2.0T 280HP Ti驾控版","id":59802},{"name":"2022款 2.0T 280HP Veloce赛道版","id":59803},{"name":"2023款 2.0T 280HP Estrema","id":60825}]}
    public List<QczjCarInfoVO> getCarProducts(String appId, String queryKey,String seriesId) {
        try {
            String clientId = env.getProperty("qczj.getCarProduct.client_id");
            String clientSecret = env.getProperty("qczj.getCarProduct.client_secret");
            String url = env.getProperty("qczj.getCarProduct.url");
            String accessToken = qczjGetAccessTokenService.getAccessToken(clientId,clientSecret,"getCarProductsAccessToken");

            String getUrl = String.format(url, accessToken, appId, URLEncoder.encode(queryKey,"UTF-8"), seriesId);
            HttpResponse<JsonNode> json = Unirest.get(getUrl).asJson();
            return JSON.parseArray(json.getBody().getObject().getJSONObject("result").getJSONArray("productlist").toString(), QczjCarInfoVO.class);
        } catch (Exception e) {
            log.info("获取车型牌异常");
        }
        return null;
    }

    public List<QczjCarInfoVO> getAllCarProducts(String appId, String queryKey, List<QczjCarInfoVO> series) {
        List<QczjCarInfoVO> allProducts = new ArrayList<>();
        series.forEach(s->{
            allProducts.addAll(getCarProducts(appId,queryKey,s.getId().toString()));
        });
        return allProducts;
    }

    public void exportCarInfo(HttpServletResponse response,String appId) throws Exception {
        String queryKey = "";
        log.info("appId:{}",appId);
        switch (appId){
            case "1312" : queryKey = stQueryKey;break;
            case "1313" : queryKey = ndQueryKey;break;
            default: throw new BusinessException("appId 或 queryKey异常");
        }


        String column1Name1 = "车品牌代码";
        String column1Name2 = "车品牌名称";
        String column1Name3 = "车系代码";
        String column1Name4 = "车系名称";
        String column1Name5 = "车型代码";
        String column1Name6 = "车型名称";

        List<String> headList = new ArrayList<>();
        headList.add(column1Name1);
        headList.add(column1Name2);
        headList.add(column1Name3);
        headList.add(column1Name4);
        headList.add(column1Name5);
        headList.add(column1Name6);

        //在内存操作，写到浏览器
        ExcelWriter writer= ExcelUtil.getWriter(true);

        // 设置表头的宽度
        writer.addHeaderAlias("brandId",column1Name1);
        writer.addHeaderAlias("brandName",column1Name2);
        writer.addHeaderAlias("seriesId",column1Name3);
        writer.addHeaderAlias("seriesName",column1Name4);
        writer.addHeaderAlias("productId",column1Name5);
        writer.addHeaderAlias("productName",column1Name6);

        // 默认的，未添加alias的属性也会写出，如果想只写出加了别名的字段，可以调用此方法排除之
        writer.setOnlyAlias(true);

//        writer.writeHeadRow(headList);

//        List<QczjCarInfoVO> brands = getCarBrands(appId,queryKey);
//        System.out.println("brands集合数量："+brands.size());
//        for(int i=0;i<brands.size();i++){
//            Integer brandId = brands.get(i).getId();
//            writer.writeCellValue(0,i+1,brandId);
//            writer.writeCellValue(1,i+1,brands.get(i).getName());
//        }
//
//        //根据品牌查车型
//        List<QczjCarInfoVO> series = getAllCarSeries(appId,queryKey,brands);
//        System.out.println("series集合数量："+series.size());
//        for(int i=0;i<series.size();i++){
//            Integer seriesId = series.get(i).getId();
//            writer.writeCellValue(2,i+1,seriesId);
//            writer.writeCellValue(3,i+1,series.get(i).getName());
//        }
//
//        List<QczjCarInfoVO> products = getAllCarProducts(appId, queryKey, series);
//        System.out.println("products集合数量："+products.size());
//        for(int i=0;i<products.size();i++){
//            Integer productId = products.get(i).getId();
//            writer.writeCellValue(4,i+1,productId);
//            writer.writeCellValue(5,i+1,products.get(i).getName());
//        }
        List<QczjCarInfo2VO> rows = getCarInfoRows(appId, queryKey);
        writer.write(rows,true);


        AdaptiveWidthUtils.setSizeColumn(writer.getSheet(), 5);
        //设置content—type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode("车辆信息模版","UTF-8")+".xlsx");
        ServletOutputStream outputStream= response.getOutputStream();

        //将Writer刷新到OutPut
        writer.flush(outputStream,true);
        outputStream.close();
        writer.close();
    }
    public List<QczjCarInfo2VO> getCarInfoRows(String appId,String queryKey){
        List<QczjCarInfo2VO> lists = new ArrayList<>();
        List<QczjCarInfoVO> brands = getCarBrands(appId,queryKey);
        brands.forEach(brand->{
            List<QczjCarInfoVO> serieses = getCarSeries(appId,queryKey,brand.getId());
            serieses.forEach(series->{
                List<QczjCarInfoVO> products = getCarProducts(appId, queryKey, series.getId().toString());
                products.forEach(product->{
                    QczjCarInfo2VO vo = new QczjCarInfo2VO(brand.getId(),brand.getName(),series.getId(),series.getName(),product.getId(),product.getName());
                    System.out.println(vo.toString());
                    lists.add(vo);
                });
            });
        });
        return lists;
    }

}
