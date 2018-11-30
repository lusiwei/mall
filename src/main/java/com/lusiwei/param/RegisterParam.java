package com.lusiwei.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: lusiwei
 * @Date: 2018/11/24 23:12
 * @Description:
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParam {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotBlank(message = "手机号不能为空")
    private String phone;
    @NotBlank(message = "问题不能为空")
    private String question;
    @NotBlank(message = "答案不能为空")
    private String answer;
}
