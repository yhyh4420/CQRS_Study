package practice.cqrsstudy.query.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "posts")
public class PostDocument {

    @Id
    private String id; // Elasticsearch의 ID는 보통 String을 사용합니다.

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Builder
    public PostDocument(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
