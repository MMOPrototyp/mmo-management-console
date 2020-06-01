package com.jukusoft.mmo.rest.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerController {

    @GetMapping("/swagger")
    public String swaggerUI() {
        return "redirect:/swagger-ui.html";
    }

}
