package cn.sdjb.dto;


import cn.sdjb.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class PersonInfoJqGridParam extends JqGridParam {

    private String userId;
    private String name;
    private String email;

}
