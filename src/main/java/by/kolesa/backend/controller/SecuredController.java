package by.kolesa.backend.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/secured")
public class SecuredController {

    @ApiOperation(value = "This is only for developing purpose", hidden = true)
    @GetMapping
    public String getSecuredInfo() {
        return "This is super secret  info!";
    }

}
