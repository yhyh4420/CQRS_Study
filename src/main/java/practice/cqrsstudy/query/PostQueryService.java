package practice.cqrsstudy.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.cqrsstudy.query.document.PostDocument;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostQueryService {

    private final PostSearchRepository postSearchRepository; // Read-Model Repository

    public PostResponseDto findPostById(Long id) {
        PostDocument postDocument = postSearchRepository.findById(id.toString())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostResponseDto(postDocument);
    }

    public List<PostResponseDto> findAllPosts() {
        Iterable<PostDocument> postDocuments = postSearchRepository.findAll();
        return StreamSupport.stream(postDocuments.spliterator(), false)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
