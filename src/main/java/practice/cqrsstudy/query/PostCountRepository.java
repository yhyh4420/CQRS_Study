package practice.cqrsstudy.query;

import org.springframework.data.repository.CrudRepository;
import practice.cqrsstudy.query.document.PostCount;

public interface PostCountRepository extends CrudRepository<PostCount, String> {
}
