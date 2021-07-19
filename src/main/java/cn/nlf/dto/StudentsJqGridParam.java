package cn.nlf.dto;


import cn.nlf.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class StudentsJqGridParam extends JqGridParam {

    private String name;
    private String phone;

}
