package com.guolala.zxx.controller.app;

import com.guolala.zxx.entity.UserInfo;
import com.guolala.zxx.entity.model.ReceiverAddress;
import com.guolala.zxx.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Author: pei.nie
 * @Date:2019/4/22
 * @Description:收货地址管理
 */

@RestController
@RequestMapping("/app/address")
@Api(value = "【app】 收货地址相关接口",tags = {"AddressController"})
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * @param userInfo
     * @param address
     */
    @PostMapping("/v1/saveAddress")
    @ApiOperation(value = "保存收货地址", httpMethod = "POST", notes = "新增或者更新使用")
    public void saveAddress(@ApiIgnore UserInfo userInfo, @RequestBody ReceiverAddress address) {
        addressService.saveAddress(userInfo.getId(), address);
    }

    /**
     * @param userInfo
     * @param addressId
     */
    @ApiOperation(value = "删除收货地址", httpMethod = "GET", notes = "")
    @GetMapping("/v1/deleteAddress")
    public void deleteAddress(@ApiIgnore UserInfo userInfo, @ApiParam("收货地址id") @RequestParam("addressId") Integer addressId) {
        addressService.deleteAddress(userInfo.getId(), addressId);
    }

    /**
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "查询收货地址", httpMethod = "GET", notes = "", response = ReceiverAddress.class)
    @GetMapping("/v1/listAddress")
    public List<ReceiverAddress> listAddress(@ApiIgnore UserInfo userInfo) {
        return addressService.listAddress(userInfo.getId());
    }
}

