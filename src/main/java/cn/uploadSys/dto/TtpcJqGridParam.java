package cn.uploadSys.dto;


import cn.uploadSys.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class TtpcJqGridParam extends JqGridParam {

    private String mobile;
    private String startDate;
    private String endDate;
    private Integer status;
    private Integer invite;
    private Integer detection;
    private Integer auction;
    private Integer deal;


}
