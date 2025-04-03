package se.iths.java24.spring25.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.iths.java24.spring25.PlaygroundService;
import se.iths.java24.spring25.domain.entity.Playground;

@Controller
@RequestMapping("/playgrounds")
public class PlaygroundController {

    private final PlaygroundService playgroundService;

    public PlaygroundController(PlaygroundService playgroundService) {
        this.playgroundService = playgroundService;
    }

    @GetMapping("/view")
    public String viewPlaygrounds(Model model) {
        model.addAttribute("playgrounds", playgroundService.getAllPlaygrounds());
        return "view-playgrounds";
    }

    @GetMapping("/add")
    public String showAddPlaygroundForm(Model model) {
        // Add an empty playground object to the model for the form to bind to
        if (!model.containsAttribute("playground")) {
            model.addAttribute("playground", new Playground());
        }
        return "add-playground";
    }

    @PostMapping("/add")
    public String addPlayground(@ModelAttribute Playground playground, 
                               RedirectAttributes redirectAttributes) {
        playgroundService.addPlayground(playground);
        
        // Add success message and the playground object (in case we need to display it)
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("playground", new Playground());
        
        return "redirect:/playgrounds/add";
    }
}
