package practice.cqrsstudy.query;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import practice.cqrsstudy.query.document.PostDocument;

public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, String> {
}
