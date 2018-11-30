package com.lusiwei.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 21:28
 * @Description:
 */
@Setter
@Getter
public class ShippingParam {
    private Integer id;
    @NotNull
    private Integer userId;
    @NotBlank(message = "收件人姓名不能为空")
    private String receiverName;
    @Size(min = 5,max = 15,message = "手机人固定电话长度不符合")
    private String receiverPhone;
    @NotBlank(message = "收件人手机号码不能未空")
    private String receiverMobile;
    @NotBlank(message = "收件地址省份不能为空")
    private String receiverProvince;
    @NotBlank(message = "收件城市不能为空")
    private String receiverCity;
    private String receiverDistrict;
    @NotBlank(message = "收件人详细地址不能为空")
    private String receiverAddress;
    private String receiverZip;


}
