package study.wild.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.common.exception.PostNotFoundException;
import study.wild.domain.entity.Post;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;
import study.wild.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    public PostDto createPost(PostDto postDto, CategoryDto categoryDto) {
        log.info("Creating post with title: {}", postDto.title());
        log.info("Category ID: {}", categoryDto.id());
        log.info("Post view: {}", postDto.view());
        try {
            Post savedPost = postRepository.save(postDto.toEntity(categoryDto.toEntity()));
            log.info("Post saved with ID: {}", savedPost.getId());
            return PostDto.from(savedPost);
        } catch (Exception e) {
            log.error("Error creating post: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public PostDto editPost(Long postId, PostDto postDto) {
        Post findPost = postRepository.findPostByIdAndIsDeleted(postId, false)
                .orElseThrow(PostNotFoundException::new);
        findPost.setTitle(postDto.title());
        findPost.setContent(postDto.content());
        return PostDto.from(findPost);
    }

    /**
     * 전체 게시글 조회 (삭제 여부 조건에 필터링한 게시글)
     *
     * @param isDeleted 게시글 삭제 여부
     */
    public List<PostDto> viewPosts(boolean isDeleted) {
        return postRepository.findAllByAndDeleted(isDeleted)
                .stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글을 조회하고, 조회수 증가
     */
    @Transactional
    public PostDto viewPostDetail(Long postId) {
        Post post = getPost(postId, false);
        increasePostView(post);
        return PostDto.from(post);
    }

    /**
     * 특정 게시글 조회 (삭제 여부 조건에 필터링한 게시글)
     *
     * @param isDeleted 게시글 삭제 여부
     */
    public Post getPost(Long postId, boolean isDeleted) {
        return postRepository.findPostByIdAndIsDeleted(postId, isDeleted)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 특정 카테고리 내 게시물 조회
     */
    public List<PostDto> getPostsByCategory(Long categoryId, boolean isDeleted) {
        return postRepository.findPostByCategoryIdAndDeleted(categoryId, isDeleted)
                .stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 조회수 증가
     */
    public void increasePostView(Post post) {
        post.increaseView();
    }

    /**
     * 게시글 삭제 (soft delete)
     */
    @Transactional
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    public boolean hasPostInCategory(Long categoryId) {
        return !postRepository.findPostByCategoryIdAndDeleted(categoryId, false).isEmpty();
    }
}
