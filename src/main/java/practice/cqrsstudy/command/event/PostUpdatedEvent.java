package practice.cqrsstudy.command.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdatedEvent {
    private final Long postId;
}
