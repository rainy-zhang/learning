package org.rainy.minis.service;

import lombok.Setter;
import org.rainy.minis.beans.annotation.Autowired;
import org.rainy.minis.repository.RoleRepository;
import org.rainy.minis.repository.UserRepository;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Setter
public class UserServiceImpl implements UserService {

    private String name;

    @Autowired
    private RoleRepository roleRepository;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void hello() {
        System.out.println("hello " + this.name);
    }

    @Override
    public Object getUser() {
        return this.userRepository.getUser();
    }

    @Override
    public Object getRole() {
        return roleRepository.getRole();
    }


}
