package cn.uploadSys.dto;


import cn.uploadSys.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class SignUpJqGridParam extends JqGridParam {

    private String name;
    private Integer year;
    private Integer month;

}
