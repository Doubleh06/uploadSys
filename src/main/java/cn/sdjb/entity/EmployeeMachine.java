package cn.sdjb.entity;

import lombok.Data;

@Data
public class EmployeeMachine extends BaseEntity {
    private Integer employeeId;
    private Integer machineId;

    public EmployeeMachine(Integer employeeId, Integer machineId) {
        this.employeeId = employeeId;
        this.machineId = machineId;
    }

    public EmployeeMachine() {
    }
}
