package com.happyheng.cloud;

import com.happyheng.cloud.api.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * Created by happyheng on 16/11/26.
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ApiApplication {

    @Autowired
    RestTemplate client;

    /**
     * LoadBalanced 注解表明restTemplate使用LoadBalancerClient执行请求
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;
    }

    @RequestMapping("/")
    public String helloWorld() {
        return client.getForObject("http://simple/", String.class);
    }

    @RequestMapping("/register")
    public String register(String username,Integer age){

        System.out.println("api中的username为" + username);
        return client.getForObject("http://simple/save?name="+username + "&age=" + age, String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


}
