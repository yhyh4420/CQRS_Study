package practice.cqrsstudy.query.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "post-counts")
@Getter
public class PostCount {

    @Id
    private String id;

    private long count;

    @Builder
    public PostCount(String id, long count) {
        this.id = id;
        this.count = count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        this.count--;
    }
}
