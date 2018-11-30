package com.lusiwei.vo;

import com.lusiwei.common.Constant;
import com.lusiwei.pojo.Cart;
import com.lusiwei.pojo.Product;
import com.lusiwei.util.BigDecimalUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 13:35
 * @Description:
 */
@Setter
@Getter
public class CartProductVo {
    /**
     * "id": 1,
     * "userId": 13,
     * "productId": 1,
     * "quantity": 1,
     * "productName": "iphone7",
     * "productSubtitle": "双十一促销",
     * "productMainImage": "mainimage.jpg",
     * "productPrice": 7199.22,
     * "productStatus": 1,
     * "productTotalPrice": 7199.22,
     * "productStock": 86,
     * "productChecked": 1,
     * "limitQuantity": "LIMIT_NUM_SUCCESS"
     */
    private Integer id;
    private Integer userId;
    private Integer productId;
    /**商品数量 */
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    /**单价 */
    private BigDecimal productPrice;
    /**商品状态,1-在售 2-下架 3-删除*/
    private Integer productStatus;
    /**总价*/
    private BigDecimal productTotalPrice;
    /**商品库存*/
    private Integer productStock;
    /**商品是否被选中*/
    private Integer productChecked;
    /**限制数量*/
    private String limitQuantity;
    private String imageHost = "http://www.image.com/upload/product/";

    public static CartProductVo getCartProductVo(Product product, Cart cart) {
        CartProductVo cartProductVo = new CartProductVo();
        cartProductVo.setId(cart.getId());
        cartProductVo.setUserId(cart.getUserId());
        cartProductVo.setProductId(cart.getProductId());
        cartProductVo.setQuantity(cart.getQuantity());
        cartProductVo.setProductName(product.getName());
        cartProductVo.setProductSubtitle(product.getSubtitle());
        cartProductVo.setProductMainImage(product.getMainImage());
        cartProductVo.setProductPrice(product.getPrice());
        cartProductVo.setProductStatus(product.getStatus());
        cartProductVo.setProductTotalPrice(BigDecimalUtil.multiple(product.getPrice(),cart.getQuantity()));
        cartProductVo.setProductStock(product.getStock());
        cartProductVo.setProductChecked(cart.getChecked());
        if (cart.getQuantity() > product.getStock()) {
            cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_FAIL);
        }else {
            cartProductVo.setLimitQuantity(Constant.Cart.LIMIT_NUM_SUCCESS);
        }
        return cartProductVo;
    }
}
