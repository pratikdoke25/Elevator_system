package org.example.elevators.endpointsControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {
    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }
}
