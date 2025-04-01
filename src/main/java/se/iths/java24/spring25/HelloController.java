package se.iths.java24.spring25;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.iths.java24.spring25.domain.PlaygroundService;
import se.iths.java24.spring25.domain.entity.Playground;

import java.awt.print.Book;
import java.util.List;

@RestController
public class HelloController {

    private final PlaygroundService playgroundService;
    private final UUIDService uuidService;

    public HelloController(PlaygroundService playgroundService, UUIDService uuidService) {
        this.playgroundService = playgroundService;
        this.uuidService = uuidService;
    }

    @GetMapping("/user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns current user",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid current user",
                    content = @Content)})
    @Operation(summary = "Get current user")
    String user(){
        return "User: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }



    @GetMapping("/hello")
    public String hello(){
        return uuidService.getUUID().uuid();
    }

}
