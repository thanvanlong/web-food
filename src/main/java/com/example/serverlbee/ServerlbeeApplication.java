package com.example.serverlbee;

import com.example.serverlbee.entity.Table;
import com.example.serverlbee.service.table.TableServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;

@SpringBootApplication
public class ServerlbeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerlbeeApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(TableServiceImpl tableService){
        return args -> {
            List<Table> tables = tableService.findTableByStatus(true);
            tables.stream().forEach(table -> tableService.updateTableStatus(table, false));
        };
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public WebApplicationInitializer webApplicationInitializer(){
//        return new WebApplicationInitializer() {
//            @Override
//            public void onStartup(ServletContext servletContext) throws ServletException {
//                servletContext.getSessionCookieConfig().setDomain("http://localhost:3000/");
//                servletContext.getSessionCookieConfig().setHttpOnly(true);
//            }
//        };
//    }
}
