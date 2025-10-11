package practice.cqrsstudy.query;

import lombok.Getter;
import practice.cqrsstudy.domain.Post;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
