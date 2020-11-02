package cn.tedu.sp06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
public class Sp06RibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sp06RibbonApplication.class, args);
	}

	/**
	 * 可以放在启动类，或者放在自定义自动配置类
	 * @return
	 */
	@Bean
	@LoadBalanced //ribbon注解，对restTemplate进行增强
	public RestTemplate restTemplate(){
		SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
		f.setConnectTimeout(1000);//连接超时 -- 1秒
		f.setReadTimeout(1000);//等待响应超时 -- 1秒
		return new RestTemplate(f);
	}
}
