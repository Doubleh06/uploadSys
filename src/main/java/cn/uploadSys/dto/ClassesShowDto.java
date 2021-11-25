package cn.uploadSys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesShowDto {
    private String className;
    private Integer total;
    private Integer noDeleteClasses;
    private Integer deleteClasses;

}
