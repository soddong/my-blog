package study.wild.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.wild.dto.PostDto;
import study.wild.service.PostService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto save(@RequestBody PostDto postDto) {
        return postService.savePost(postDto);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
        return postService.updatePost(postId, postDto);
    }

    @GetMapping
    public List<PostDto> getPosts() {
        return postService.findPosts(false);
    }

    @GetMapping("/{postId}")
    public PostDto getPost(@PathVariable Long postId) {
        return postService.findPost(postId, false);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}
