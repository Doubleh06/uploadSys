package cn.nlf.dto;


import cn.nlf.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class DeleteClassJqGridParam extends JqGridParam {

    private Integer id;
    private String className;
    private Integer status;
    private Integer year;
    private Integer month;
    

}
