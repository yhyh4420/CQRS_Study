package practice.cqrsstudy.interfaces.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.cqrsstudy.command.PostCommandService;
import practice.cqrsstudy.command.PostCreateRequestDto;
import practice.cqrsstudy.command.PostUpdateRequestDto;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostCommandController {

    private final PostCommandService postCommandService;

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
}
