package cn.uploadSys.dto;


import cn.uploadSys.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class StudentsJqGridParam extends JqGridParam {


    private Integer cid;
    private String name;
    private String phone;

}
