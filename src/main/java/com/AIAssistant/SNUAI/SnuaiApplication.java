package com.AIAssistant.SNUAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SnuaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnuaiApplication.class, args);
	}
	@GetMapping("/")
    public String home() {
        return "chat"; // loads chat.html
    }
}
