package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {FreeMarkerAutoConfiguration.class})
@Controller
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage() {
		ModelAndView modelAndView = new ModelAndView("homePage");
		return modelAndView;
	}
}
