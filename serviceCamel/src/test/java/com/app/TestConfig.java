package com.app;

import com.app.service.RestApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Created by andrey on 06.10.16.
 */
@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                // Exclude the default message service
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ServiceCamelApplicationTests.class),
                // Exclude the default boot application or it's
                // @ComponentScan will pull in the default message service
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = RestApi.class)
        }
)
public class TestConfig {

}
