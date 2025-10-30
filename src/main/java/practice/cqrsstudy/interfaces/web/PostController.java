package practice.cqrsstudy.interfaces.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.cqrsstudy.command.PostCommandService;
import practice.cqrsstudy.command.PostCreateRequestDto;
import practice.cqrsstudy.command.PostUpdateRequestDto;
import practice.cqrsstudy.query.PostQueryService;
import practice.cqrsstudy.query.PostResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    // Command
    @PostMapping
    public Mono<ResponseEntity<Void>> createPost(@RequestBody Mono<PostCreateRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(postCommandService::createPost)
                .map(postId -> ResponseEntity.created(URI.create("/posts/" + postId)).build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Void>> updatePost(@PathVariable Long id, @RequestBody Mono<PostUpdateRequestDto> requestDtoMono) {
        return requestDtoMono
                .map(requestDto -> {
                    postCommandService.updatePost(id, requestDto);
                    return ResponseEntity.ok().build();
                });
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePost(@PathVariable Long id) {
        return Mono.fromRunnable(() -> postCommandService.deletePost(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    // Query
    @GetMapping("/{id}")
    public Mono<ResponseEntity<PostResponseDto>> findPostById(@PathVariable Long id) {
        return Mono.justOrEmpty(postQueryService.findPostById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<PostResponseDto> findAllPosts() {
        return Flux.fromIterable(postQueryService.findAllPosts());
    }

    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> getPostCount() {
        return Mono.just(postQueryService.getPostCount())
                .map(ResponseEntity::ok);
    }
}