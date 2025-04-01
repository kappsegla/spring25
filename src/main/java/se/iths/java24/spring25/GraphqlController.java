package se.iths.java24.spring25;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class GraphqlController {

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    UserDto user(){
        return new UserDto("Martin");
    }
}

record UserDto(String name){}
