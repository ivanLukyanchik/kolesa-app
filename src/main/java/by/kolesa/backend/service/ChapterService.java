package by.kolesa.backend.service;

import by.kolesa.backend.model.Chapter;
import by.kolesa.backend.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {

  private final ChapterRepository chapterRepository;

  public List<Chapter> getAllChapters() {
    List<Chapter> chapters = new ArrayList<>();
    chapterRepository.findAll().forEach(chapters::add);
    return chapters;
  }
}
