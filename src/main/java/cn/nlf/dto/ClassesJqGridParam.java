package cn.nlf.dto;


import cn.nlf.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class ClassesJqGridParam extends JqGridParam {

    private String name;
    private String teacher;
    private Integer week;

}
