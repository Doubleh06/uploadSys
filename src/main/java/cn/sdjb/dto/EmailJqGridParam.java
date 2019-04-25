package cn.sdjb.dto;


import cn.sdjb.core.jqGrid.JqGridParam;
import lombok.Data;

/**
 * @author fonlin
 * @date 2018/4/24
 */
@Data
public class EmailJqGridParam extends JqGridParam {

    private String authName;
    private String address;

}
