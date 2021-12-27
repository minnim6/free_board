package com.project.petboard.domain.questionboard;

import com.project.petboard.infrastructure.exception.CustomErrorException;
import com.project.petboard.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionBoardService {

    private final QuestionBoardRepository questionBoardRepository;

    @Transactional(readOnly = true)
    public QuestionBoardDto fetchQuestionBoard(Long questionNumber) {
        try {
            return new QuestionBoardDto(questionBoardRepository.findByQuestionBoardNumber(questionNumber));
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), ErrorCode.NOT_FOUND);
        }
    }

    public void deleteQuestionBoard(Long questionNumber) {
        questionBoardRepository.deleteById(questionNumber);
    }

    public void createQuestionBoard(QuestionBoardDto questionBoardDto) {
        questionBoardRepository.save(questionBoardDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Page<QuestionBoard> requestPage(Pageable pageable) {
        try {
            return questionBoardRepository.findAll(pageable);
        } catch (Exception e) {
            throw new CustomErrorException(e.getMessage(), ErrorCode.NOT_FOUND);
        }
    }
}
