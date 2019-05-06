package cn.sdjb.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MachineDto {
    private Integer id;
    @NotEmpty(message = "机床号不能为空")
    private String name;

}
