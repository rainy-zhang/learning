package org.rainy.minis;

import org.rainy.minis.context.ClassPathXmlApplicationContext;
import org.rainy.minis.exception.BeanException;
import org.rainy.minis.service.UserService;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class Test {

    public static void main(String[] args) throws BeanException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.hello();
        System.out.println(userService.getUser());
        System.out.println(userService.getRole());

    }

}
