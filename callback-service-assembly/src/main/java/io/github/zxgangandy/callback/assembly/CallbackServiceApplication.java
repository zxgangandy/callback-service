package io.github.zxgangandy.callback.assembly;


import io.github.zxgangandy.eureka.manager.EnableEurekaManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ComponentScan(basePackages = {"io.github.zxgangandy.callback", "io.jingwei.base"})
//@EnableFeignClients(basePackages = {"io.jingwei.otc"})
@EnableEurekaManage
@SpringBootApplication
@Slf4j
public class CallbackServiceApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(CallbackServiceApplication.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application PaymentApplication is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + "/\n\t" +
                "swagger-ui: http://" + ip + ":" + port + "/swagger-ui.html\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
