package practice.cqrsstudy.command.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDeletedEvent {
    private final Long postId;
}
