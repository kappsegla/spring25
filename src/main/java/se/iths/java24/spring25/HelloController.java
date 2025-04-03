package se.iths.java24.spring25;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
