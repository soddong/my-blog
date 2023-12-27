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
import study.wild.dto.PostDto;
import study.wild.service.PostCategoryService;
import study.wild.service.PostCommentService;
import study.wild.service.PostService;

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

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private PostService postService;

    @MockBean
    private PostCommentService postCommentService;

    @MockBean
    private PostCategoryService postCategoryService;

    @Test
    @DisplayName("게시글을 등록한다")
    void saveTest() throws Exception {
        // given
        PostDto postDto = new PostDto(null, null, "제목", "내용");
        PostDto returnedPostDto = new PostDto(1L, 1L, "제목", "내용");
        given(postCategoryService.createPostWithCategory(any(PostDto.class))).willReturn(returnedPostDto);

        String postJson = objectMapper.writeValueAsString(postDto);

        // when & then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    @DisplayName("게시글을 수정한다")
    void updatePostTest() throws Exception {
        // given
        long postId = 1L;
        PostDto returnedPostDto = new PostDto(1L, 1L, "제목 수정", "내용 수정");
        given(postService.editPost(eq(postId), any(PostDto.class))).willReturn(returnedPostDto);

        String postJson = objectMapper.writeValueAsString(returnedPostDto);

        // when & then
        mockMvc.perform(put("/posts/" + postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("제목 수정"))
                .andExpect(jsonPath("$.content").value("내용 수정"));
    }

    @Test
    @DisplayName("게시글 목록을 조회한다")
    void getAllPost() throws Exception {
        // given
        List<PostDto> posts = Arrays.asList(
                new PostDto(1L, 1L, "제목 1", "내용 1"),
                new PostDto(2L, 1L, "제목 2", "내용 3")
        );
        given(postService.viewPosts(false)).willReturn(posts);

        // when & then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("제목 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("제목 2"));
    }

    @Test
    @DisplayName("특정 카테고리의 게시글들을 조회하다")
    void getPostByCategoryTest() throws Exception {
        // given
        long categoryId = 1L;
        List<PostDto> posts = Arrays.asList(
                new PostDto(1L, 1L, "제목 1", "내용 1"),
                new PostDto(2L, 1L, "제목 2", "내용 3")
        );
        given(postService.getPostsByCategory(categoryId, false)).willReturn(posts);

        // when & then
        mockMvc.perform(get("/categories/" + categoryId + "/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("제목 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("제목 2"));
    }

    @Test
    @DisplayName("게시글을 조회한다")
    void getPostTest() throws Exception {
        // given
        long postId = 1L;
        PostDto postDto = new PostDto(postId, 1L, "제목 1", "내용 1");
        given(postService.viewPostDetail(eq(postId))).willReturn(postDto);

        // when & then
        mockMvc.perform(get("/posts/" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목 1"))
                .andExpect(jsonPath("$.content").value("내용 1"));
    }

    @Test
    @DisplayName("게시글을 삭제한다")
    void deletePost() throws Exception {
        // given
        long postId = 1L;
        willDoNothing().given(postCommentService).deletePostWithComment(postId);

        // when & then
        mockMvc.perform(delete("/posts/" + postId))
                .andExpect(status().isOk());

        verify(postCommentService).deletePostWithComment(postId);
    }
}