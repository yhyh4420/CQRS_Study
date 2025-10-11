package practice.cqrsstudy.query.projection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import practice.cqrsstudy.command.event.PostCreatedEvent;
import practice.cqrsstudy.command.event.PostDeletedEvent;
import practice.cqrsstudy.command.event.PostUpdatedEvent;
import practice.cqrsstudy.domain.Post;
import practice.cqrsstudy.domain.PostRepository;
import practice.cqrsstudy.query.PostSearchRepository;
import practice.cqrsstudy.query.document.PostDocument;

@Component
@RequiredArgsConstructor
public class PostEventListener {

    private final PostRepository postRepository; // Write-Model Repository
    private final PostSearchRepository postSearchRepository; // Read-Model Repository

    @TransactionalEventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        Post post = postRepository.findById(event.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        postSearchRepository.save(PostDocument.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .build());
    }

    @TransactionalEventListener
    public void handlePostUpdatedEvent(PostUpdatedEvent event) {
        Post post = postRepository.findById(event.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        postSearchRepository.save(PostDocument.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .build());
    }

    @TransactionalEventListener
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        postSearchRepository.deleteById(event.getPostId().toString());
    }
}
