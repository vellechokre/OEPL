package com.oepl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration
public class OeplServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(OeplServiceApplication.class, args);
    }

    @Bean
    public Filter corsFilter() {

        return new Filter() {

            @Override
            public void init(final FilterConfig filterConfig) throws ServletException {

            }

            @Override
            public void doFilter(final ServletRequest req, final ServletResponse res,
                                 final FilterChain chain) throws IOException, ServletException {

                final HttpServletResponse response = (HttpServletResponse) res;
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods",
                        "POST, GET, OPTIONS, DELETE, PUT,PATCH");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers",
                        "Content-Type, Accept, X-Requested-With, remember-me, Authorization,x_tenant");
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                // Get request object.
                final HttpServletRequest request = (HttpServletRequest) req;
                chain.doFilter(req, res);
            }

            @Override
            public void destroy() {

            }
        };
    }
}
