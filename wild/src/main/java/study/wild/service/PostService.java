package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Post;
import study.wild.dto.CategoryDto;
import study.wild.dto.PostDto;
import study.wild.repository.CategoryRepository;
import study.wild.repository.CommentRepository;
import study.wild.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public PostDto createPost(PostDto postDto) {
        CategoryDto categoryDto = categoryService.findByPost(postDto);

        Post savedPost = postRepository.save(postDto.toEntity(categoryDto.toEntity()));
        return PostDto.from(savedPost);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public PostDto editPost(Long postId, PostDto postDto) {
        return postRepository.findPostByIdAndIsDeleted(postId, false)
                .map(post -> {
                    post.setTitle(postDto.title());
                    post.setContent(postDto.content());
                    return PostDto.from(postRepository.save(post));
                })
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
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
     * 특정 게시글 조회 (삭제 여부 조건에 필터링한 게시글)
     *
     * @param isDeleted 게시글 삭제 여부
     */
    public PostDto viewPostDetail(Long postId, boolean isDeleted) {
        Post post = postRepository.findPostByIdAndIsDeleted(postId, isDeleted)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        countUpView(post);
        return PostDto.from(post);
    }

    /**
     * 게시글 삭제 (soft delete)
     */
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
        commentRepository.deleteByPostId(postId);
    }

    /**
     * 조회수 증가
     */
    @Transactional
    public void countUpView(Post post) {
        post.increaseView();
    }
}
