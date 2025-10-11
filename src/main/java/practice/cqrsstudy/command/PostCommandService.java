package practice.cqrsstudy.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.cqrsstudy.command.event.PostCreatedEvent;
import practice.cqrsstudy.command.event.PostDeletedEvent;
import practice.cqrsstudy.command.event.PostUpdatedEvent;
import practice.cqrsstudy.domain.Post;
import practice.cqrsstudy.domain.PostRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {

    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Long createPost(PostCreateRequestDto requestDto) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent());
        Post savedPost = postRepository.save(post);
        eventPublisher.publishEvent(new PostCreatedEvent(savedPost.getId()));
        return savedPost.getId();
    }

    public void updatePost(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(requestDto.getTitle(), requestDto.getContent());
        eventPublisher.publishEvent(new PostUpdatedEvent(id));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
        eventPublisher.publishEvent(new PostDeletedEvent(id));
    }
}
