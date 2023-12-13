package study.wild.service;

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
        return PostDto.from(post); // 변환된 PostDto 반환
    }

    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId);
        post.setTitle(postDto.title());
        post.setContent(postDto.content());
        return PostDto.from(post);
    }

    /**
     * 전체 게시글 조회
     */
    public List<PostDto> findPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 특정 게시글 조회
     */
    public PostDto findPost(Long postId) {
        return PostDto.from(postRepository.findById(postId));
    }

}
