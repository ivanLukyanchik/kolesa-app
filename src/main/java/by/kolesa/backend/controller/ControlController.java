package by.kolesa.backend.controller;

import by.kolesa.backend.dto.ControlDto;
import by.kolesa.backend.service.ControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controls")
@RequiredArgsConstructor
public class ControlController {

    private final ControlService controlService;

    @GetMapping("/topic/{id}")
    public ControlDto getControlByTopic(@PathVariable Long id) {
        return controlService.getControlByTopic(id);
    }

    @GetMapping("/random")
    public ControlDto getRandomControl() {
        return controlService.getRandomControl();
    }

}
