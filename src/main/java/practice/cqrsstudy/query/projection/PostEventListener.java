package practice.cqrsstudy.query.projection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import practice.cqrsstudy.command.event.PostCreatedEvent;
import practice.cqrsstudy.command.event.PostDeletedEvent;
import practice.cqrsstudy.command.event.PostUpdatedEvent;
import practice.cqrsstudy.domain.Post;
import practice.cqrsstudy.domain.PostRepository;
import practice.cqrsstudy.query.PostCountRepository;
import practice.cqrsstudy.query.PostSearchRepository;
import practice.cqrsstudy.query.document.PostCount;
import practice.cqrsstudy.query.document.PostDocument;

@Component
@RequiredArgsConstructor
public class PostEventListener {

    private final PostRepository postRepository; // Write-Model Repository
    private final PostSearchRepository postSearchRepository; // Read-Model Repository
    private final PostCountRepository postCountRepository;

    private static final String POST_COUNT_ID = "POST_COUNT";

    @TransactionalEventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        Post post = postRepository.findById(event.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        postSearchRepository.save(PostDocument.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .content(post.getContent())
                .build());

        PostCount postCount = postCountRepository.findById(POST_COUNT_ID)
                .orElse(PostCount.builder().id(POST_COUNT_ID).count(0L).build());
        postCount.increase();
        postCountRepository.save(postCount);
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

        PostCount postCount = postCountRepository.findById(POST_COUNT_ID)
                .orElse(PostCount.builder().id(POST_COUNT_ID).count(0L).build());
        postCount.decrease();
        postCountRepository.save(postCount);
    }
}
