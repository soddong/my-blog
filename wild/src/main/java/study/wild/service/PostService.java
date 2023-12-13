package study.wild.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.wild.domain.Post;
import study.wild.dto.PostDto;
import study.wild.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    @Transactional
    public PostDto savePost(PostDto postDto) {
        Post post = postRepository.save(postDto.toEntity());
        return PostDto.from(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postDto.title());
                    post.setContent(postDto.content());
                    return PostDto.from(postRepository.save(post));
                })
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
    }

    /**
     * 전체 게시글 조회
     */
    public List<PostDto> findPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회
     */
    public PostDto findPost(Long postId) {
        return postRepository.findById(postId)
                .map(PostDto::from)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다"));
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
