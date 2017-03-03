package com.ehu.controller;

import com.ehu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-02 15:59.
 */
@RestController
@Api(tags = "用户相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ApiOperation(value="用户列表",notes="用户列表",consumes="application/json",produces="application/json")
    public Object getUserList() {
        return userService.getUserList();
    }
}
