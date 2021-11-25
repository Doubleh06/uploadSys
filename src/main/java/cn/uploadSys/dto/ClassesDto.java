package cn.uploadSys.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ClassesDto {
    private Integer id;
    @NotEmpty(message = "课程名不能为空")
    private String name;
    private String startTime;
    private String endTime;
    @NotNull(message = "上课星期不能为空")
    private Integer week;
    private String teacher;
    @NotNull(message = "总课程数不能为空")
    private Integer total;
    private BigDecimal fee;

}
