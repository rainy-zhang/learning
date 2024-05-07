package org.rainy.minis.web.test.controller;

import org.rainy.minis.beans.annotation.Autowired;
import org.rainy.minis.beans.annotation.Controller;
import org.rainy.minis.web.annotation.RequestMapping;
import org.rainy.minis.web.annotation.RequestParam;
import org.rainy.minis.web.test.service.UserService;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser")
    public Object getUser(@RequestParam("aaa") String name, @RequestParam("age") Integer age) {
        return this.userService.getUser() + ", name: " + name + ",age: " + age;
    }

    @RequestMapping(value = "/getRole")
    public Object getRole(@RequestParam(value = "id", required = false) String id) {
        return this.userService.getRole() + ", id:" + id;
    }

}
