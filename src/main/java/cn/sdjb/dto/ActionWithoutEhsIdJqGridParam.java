package cn.sdjb.dto;


import cn.sdjb.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class ActionWithoutEhsIdJqGridParam extends JqGridParam {
    private String address;
    private String responsibleMan;
    private String responsibleDept;
    private String responsibleDirector ;
    private String startDate;
    private String endDate;
    private String status1;
    private String status2;

}
