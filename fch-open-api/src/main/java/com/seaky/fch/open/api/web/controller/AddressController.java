package com.seaky.fch.open.api.web.controller;
/**
 * 开放平台接口
 * 1 查询uxto列表
 * 2 查询交易
 * 3 发送交易
 * 4 地址转换
 * 5 签名解码
 * 6 在线签名
 * fch特有
 * 1 查询cid
 * 2 查询
 */

import com.seaky.fch.open.api.web.view.BaseView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//all api
@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "交易")
public class AddressController {


    //transaction api

    @GetMapping("/fch/address/uxto")
    @ApiOperation(value ="查询指定地址的uxto")
    public BaseView getTransaction(@ApiParam(value = "地址", required = true) @RequestParam String address,String appId,String sig) {

        return new BaseView();
    }


}
