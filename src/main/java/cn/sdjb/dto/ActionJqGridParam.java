package cn.sdjb.dto;


import cn.sdjb.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class ActionJqGridParam extends JqGridParam {
    private Integer ehsId;
    private String address;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector ;
    private String startDate;
    private String endDate;
    private String status1;
    private String status2;

//    public ActionJqGridParam(Integer ehsId, String address, String responsibleMan, String responsibleDept, String responsibleDirector, String startDate, String endDate, String status1, String status2) {
//        this.ehsId = ehsId;
//        this.address = address;
//        this.responsibleMan = responsibleMan;
//        this.responsibleDept = responsibleDept;
//        this.responsibleDirector = responsibleDirector;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status1 = status1;
//        this.status2 = status2;
//    }
}
