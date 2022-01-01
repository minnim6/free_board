package com.project.petboard.service;

import com.project.petboard.domain.comment.*;
import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberRepository;
import com.project.petboard.domain.post.Post;
import com.project.petboard.domain.post.PostRepository;
import com.project.petboard.domain.recomment.ReocommentResponseDto;
import com.project.petboard.infrastructure.exception.CrudErrorCode;
import com.project.petboard.infrastructure.exception.CustomErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentServiceTest {

    CommentRepository commentRepository = mock(CommentRepository.class);

    MemberRepository memberRepository = mock(MemberRepository.class);

    PostRepository postRepository = mock(PostRepository.class);

    CommentService commentService = new CommentService(commentRepository,memberRepository,postRepository);

    private CommentRequestDto  commentRequestDto;

    private Comment comment;

    @Before
    public void setup(){
        commentRequestDto = new CommentRequestDto(
                1L,1L,"contents"
        );
        comment = commentRequestDto.toEntity(new Post(),new Member());
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    public void createCommentTestShouldBeSuccess(){
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.save(any())).willReturn(comment);
        //when
        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
        //then
        assertThat(commentResponseDto).isNotNull();
    }
    @Test
    @DisplayName("댓글 생성 실패 테스트")
    public void createCommentTestShouldBeFail(){
        //given
        given(memberRepository.findByMemberNumber(1L)).willReturn(new Member());
        given(postRepository.findByPostNumber(1L)).willReturn(new Post());
        given(commentRepository.save(any())).willReturn(null);
        try {
            //when
            CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto);
        }catch (CustomErrorException e) {
            //then
            assertThat(e.getErrorCode()).isEqualTo(CrudErrorCode.NULL_EXCEPTION);
        }
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void deleteCommentTestShouldBeFail(){
        //when
        commentRepository.deleteById(1L);
        //then
        verify(commentRepository).deleteById(1L);
    }
}