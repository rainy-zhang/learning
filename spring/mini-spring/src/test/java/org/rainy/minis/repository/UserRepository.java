package org.rainy.minis.repository;

import com.google.common.collect.Maps;
import lombok.Setter;
import org.rainy.minis.service.UserService;
import org.rainy.minis.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class UserRepository {

    private UserService userService;


    public List<Map<String, Object>> getUser() {
        List<Map<String, Object>> result = new ArrayList<>();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("id", 1);
        map.put("name", "rainy");
        map.put("age", 18);
        result.add(map);
        return result;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

}
