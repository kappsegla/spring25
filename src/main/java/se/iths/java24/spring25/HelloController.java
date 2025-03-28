package se.iths.java24.spring25;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.java24.spring25.domain.PlaygroundService;
import se.iths.java24.spring25.domain.entity.Playground;

import java.util.List;

@Controller
public class HelloController {

    private final PlaygroundService playgroundService;

    public HelloController(PlaygroundService playgroundService) {
        this.playgroundService = playgroundService;
    }

    @GetMapping("/hello")
    String hello(Model model){
        model.addAttribute("message", "Hello, World!");
        model.addAttribute("name","Martin");
        return "hello";
    }

    @GetMapping("/api/playgrounds")
    @ResponseBody
    List<Playground> playground(Model model){
        return playgroundService.getAllPlaygrounds();
    }

}
