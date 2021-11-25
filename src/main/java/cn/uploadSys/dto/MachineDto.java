package cn.uploadSys.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MachineDto {
    private Integer id;
    @NotEmpty(message = "机床号不能为空")
    private String name;

}
