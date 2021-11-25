package cn.uploadSys.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StudentsDto {
    private Integer id;
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @NotEmpty(message = "联系方式不能为空")
    private String phone;
    private String school;
    private String parentsName;
    @NotNull(message = "费用不能为空")
    private BigDecimal fee;

}
