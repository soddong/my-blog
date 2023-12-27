package study.wild.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.wild.dto.CommentDto;
import study.wild.service.CommentService;
import study.wild.service.PostCommentService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostCommentService postCommentService;

    @Test
    @DisplayName("댓글을 등록한다")
    void saveCommentTest() throws Exception {
        // given
        long postId = 1L;
        CommentDto comment = new CommentDto(null, "댓글 작성");
        CommentDto returnedComment = new CommentDto(1L, "댓글 작성");
        given(postCommentService.createCommentWithPost(eq(postId), any(CommentDto.class))).willReturn(returnedComment);

        String commentJson = objectMapper.writeValueAsString(comment);

        // when & then
        mockMvc.perform(post("/posts/" + postId + "/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("댓글 작성"));
    }

    @Test
    @DisplayName("댓글을 수정한다")
    void updateCommentTest() throws Exception {
        // given
        long postId = 1L;
        long commentId = 1L;
        CommentDto comment = new CommentDto(null, "댓글 수정");
        CommentDto returnedComment = new CommentDto(commentId, "댓글 수정");
        given(commentService.editComment(eq(commentId), any(CommentDto.class))).willReturn(returnedComment);

        String commentJson = objectMapper.writeValueAsString(comment);

        // when & then
        mockMvc.perform(put("/comments/" + commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.content").value("댓글 수정"));
    }

    @Test
    @DisplayName("특정 게시글의 댓글들을 조회한다")
    void getCommentsByPostTest() throws Exception {
        // given
        long postId = 1L;
        List<CommentDto> comments = Arrays.asList(
                new CommentDto(1L, "댓글 1"),
                new CommentDto(2L, "댓글 2")
        );
        given(commentService.getCommentsByPost(postId)).willReturn(comments);

        // when & then
        mockMvc.perform(get("/posts/" + postId + "/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].content").value("댓글 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].content").value("댓글 2"));
    }

    @Test
    @DisplayName("댓글을 삭제한다")
    void deleteCommentTest() throws Exception {
        // given
        long commentId = 1L;
        willDoNothing().given(commentService).deleteComment(commentId);

        // when & then
        mockMvc.perform(delete("/comments/" + commentId))
                .andExpect(status().isOk());

        verify(commentService).deleteComment(commentId);
    }
}
