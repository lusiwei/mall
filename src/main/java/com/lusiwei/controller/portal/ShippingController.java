package com.lusiwei.controller.portal;

import com.lusiwei.common.Constant;
import com.lusiwei.common.ResponseResult;
import com.lusiwei.dto.UserDto;
import com.lusiwei.param.ShippingParam;
import com.lusiwei.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: lusiwei
 * @Date: 2018/11/29 21:24
 * @Description:
 */
@RestController
@RequestMapping("/shipping")
public class ShippingController {
    private final ShippingService shippingService;

    @Autowired
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    /**
     * 添加地址
     */
    @RequestMapping("add.do")
    public ResponseResult add(ShippingParam shippingParam, HttpSession session){
        UserDto userDto= (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录，请先登录");
        }
        Integer userId=userDto.getId();
        if (!userId.equals(shippingParam.getUserId())) {
            return ResponseResult.createSuccessResponse("用户id不一致，没有权限，非法操作");
        }
        return shippingService.addOrUpdate(shippingParam,userId);
    }

    /**
     * 删除地址
     * @param session
     * @return
     */
    @RequestMapping("del.do")
    public ResponseResult del(Integer shippingId, HttpSession session){
        UserDto userDto= (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录，请先登录");
        }
        Integer userId=userDto.getId();
        return shippingService.delete(shippingId,userId);
    }

    /**
     * 登录状态更新地址
     * @param shippingParam
     * @param session
     * @return
     */
    @RequestMapping("update.do")
    public ResponseResult update(ShippingParam shippingParam, HttpSession session){
        UserDto userDto= (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录，请先登录");
        }
        Integer userId=userDto.getId();
        return shippingService.addOrUpdate(shippingParam,userId);
    }

    /**
     * 选中查看具体的地址
     * @param session
     * @return
     */
    @RequestMapping("select.do")
    public ResponseResult select(Integer shippingId, HttpSession session){
        UserDto userDto= (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录，请先登录");
        }
        int userId=userDto.getId();
        return shippingService.select(shippingId,userId);
    }

    /**
     * 地址列表
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    public ResponseResult list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "3") int pageSize, HttpSession session){
        System.out.println("pageSize="+pageSize);
        UserDto userDto= (UserDto) session.getAttribute(Constant.User.CURRENT_USER);
        if (userDto == null) {
            return ResponseResult.createFailResponse("用户未登录，请先登录");
        }
        int userId=userDto.getId();
        return shippingService.list(pageNum,pageSize,userId);
    }
}
