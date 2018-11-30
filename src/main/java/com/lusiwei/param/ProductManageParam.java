package com.lusiwei.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: lusiwei
 * @Date: 2018/11/28 10:30
 * @Description:
 */
@Getter
@Setter
public class ProductManageParam {

    private Integer id;

    @NotNull(message = "分类id不能未空")
    private Integer categoryId;

    @NotBlank(message = "产品名称不能未空")
    private String name;

    @NotBlank(message = "子标题不能未空")
    @Size(min = 1, max = 30)
    private String subtitle;

    @NotBlank(message = "主图片不能为空")
    private String mainImage;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @NotBlank
    private String subImages;

    @NotBlank
    private String detail;

    @NotNull(message = "库存不能为空")
    private Integer stock;

    @NotNull(message = "状态不能为空")
    private Integer status;

}
