package by.kolesa.backend.controller;

import by.kolesa.backend.dto.ChapterDto;
import by.kolesa.backend.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chapters")
@RequiredArgsConstructor
public class ChapterController {

  private final ChapterService chapterService;

  @GetMapping
  public List<ChapterDto> getAllChapters() {
    return chapterService.getAllChapters();
  }
}
