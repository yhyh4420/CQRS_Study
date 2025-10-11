package practice.cqrsstudy.interfaces.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.cqrsstudy.command.PostCommandService;
import practice.cqrsstudy.command.PostCreateRequestDto;
import practice.cqrsstudy.command.PostUpdateRequestDto;
import practice.cqrsstudy.query.PostQueryService;
import practice.cqrsstudy.query.PostResponseDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    // Command
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequestDto requestDto) {
        Long postId = postCommandService.createPost(requestDto);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto) {
        postCommandService.updatePost(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postCommandService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Query
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long id) {
        PostResponseDto post = postQueryService.findPostById(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAllPosts() {
        List<PostResponseDto> posts = postQueryService.findAllPosts();
        return ResponseEntity.ok(posts);
    }
}
