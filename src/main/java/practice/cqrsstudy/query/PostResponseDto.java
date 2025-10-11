package practice.cqrsstudy.query;

import lombok.Getter;
import practice.cqrsstudy.domain.Post;
import practice.cqrsstudy.query.document.PostDocument;

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

    public PostResponseDto(PostDocument postDocument) {
        this.id = Long.parseLong(postDocument.getId());
        this.title = postDocument.getTitle();
        this.content = postDocument.getContent();
    }
}
