package cn.uploadSys.entity.VO;

import lombok.Data;

/**
 * @author dh
 * @version 1.0
 * @description: TODO
 * @date 2023/2/28 下午3:47
 */
@Data
public class QczjCarInfoVO {
    private Integer id;
    private String name;

    public QczjCarInfoVO() {
    }

    public QczjCarInfoVO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
