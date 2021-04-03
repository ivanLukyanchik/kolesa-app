package by.kolesa.backend.service;

import by.kolesa.backend.dto.ChapterDto;
import by.kolesa.backend.entity.Chapter;
import by.kolesa.backend.mapper.ChapterMapper;
import by.kolesa.backend.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService {

  private final ChapterRepository chapterRepository;
  private final ChapterMapper chapterMapper;

  @Transactional(readOnly = true)
  public List<ChapterDto> getAllChapters() {
    List<Chapter> chapters = new ArrayList<>();
    chapterRepository.findAll().forEach(chapters::add);
    return chapterMapper.toChapterDtos(chapters);
  }
}
