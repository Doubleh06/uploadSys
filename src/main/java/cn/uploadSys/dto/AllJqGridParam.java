package cn.uploadSys.dto;


import cn.uploadSys.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class AllJqGridParam extends JqGridParam {

    private String phone;
    private String startDate;
    private String endDate;
    private Integer status;
    private String appid;
    private Integer appealStatus;
    private Integer distributeStatus;
    private Integer filterateStatus;
    private Integer checkStatus;

}
