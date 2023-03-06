package cn.uploadSys.entity.VO;

import lombok.Data;
import lombok.ToString;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2023/2/28 下午3:47
 */
@Data
@ToString
public class QczjCarInfo2VO {
    private Integer brandId;
    private String brandName;
    private Integer seriesId;
    private String seriesName;
    private Integer productId;
    private String productName;

    public QczjCarInfo2VO() {
    }

    public QczjCarInfo2VO(Integer brandId, String brandName, Integer seriesId, String seriesName, Integer productId, String productName) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.seriesId = seriesId;
        this.seriesName = seriesName;
        this.productId = productId;
        this.productName = productName;
    }
}
