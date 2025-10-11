package practice.cqrsstudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.cqrsstudy.domain.Post;
import practice.cqrsstudy.domain.PostRepository;
import practice.cqrsstudy.dto.PostRequestDto;
import practice.cqrsstudy.dto.PostResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto.getTitle(), requestDto.getContent());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    public void updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        post.update(requestDto.getTitle(), requestDto.getContent());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts() {
        return postRepository.findAll().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }
}
