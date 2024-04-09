package org.rainy.learning;

import org.rainy.learning.listener.EhcacheListenerDemo;
import org.rainy.learning.simple.EhcacheSimpleDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@SpringBootApplication
public class EhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhcacheApplication.class, args);
    }

    @PostConstruct
    public void init() {
        EhcacheSimpleDemo.createCache();
        EhcacheSimpleDemo.getCache();
        EhcacheSimpleDemo.removeCache();
        EhcacheSimpleDemo.close();

        EhcacheListenerDemo.test();
    }

}
