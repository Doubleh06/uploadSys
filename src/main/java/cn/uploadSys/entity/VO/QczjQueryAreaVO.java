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
public class QczjQueryAreaVO {
    private Integer pid;
    private String pname;
    private Integer cid;
    private String cname;
    private Integer countyid;
    private String countyname;

    public QczjQueryAreaVO() {
    }

    public QczjQueryAreaVO(Integer pid, String pname, Integer cid, String cname, Integer countyid, String countyname) {
        this.pid = pid;
        this.pname = pname;
        this.cid = cid;
        this.cname = cname;
        this.countyid = countyid;
        this.countyname = countyname;
    }
}
