package se.iths.java24.spring25;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

    @GetMapping("/info")
    public String info(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return switch (auth) {
            case OAuth2AuthenticationToken token ->
                    "User: " + token.getName() + " with roles: " + token.getAuthorities();
            case UsernamePasswordAuthenticationToken token ->
                    "User: " + token.getName() + " with roles: " + token.getAuthorities();
            case null, default -> "User: " + SecurityContextHolder.getContext().getAuthentication().getName();
        };
    }
}
