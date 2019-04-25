package cn.sdjb.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EmailDto {
    private Integer id;
    @NotEmpty(message = "smtpAuth不能为空")
    private String smtpAuth;
    @NotEmpty(message = "transportProrocol不能为空")
    private String transportProrocol;
    @NotEmpty(message = "sendCharset不能为空")
    private String sendCharset;
    @NotEmpty(message = "smtpPort不能为空")
    private String smtpPort;
    @NotEmpty(message = "isSsl不能为空")
    private String isSsl;
    @NotEmpty(message = "host不能为空")
    private String host;
    @NotEmpty(message = "authName不能为空")
    private String authName;
    @NotEmpty(message = "authPassword不能为空")
    private String authPassword;
    @NotNull(message = "smtpTimeout不能为空")
    private Integer smtpTimeout;
    private String address;
    @NotEmpty(message = "ehsEmail不能为空")
    private String ehsEmail;
}
