package practice.cqrsstudy.interfaces.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import practice.cqrsstudy.command.PostCreateRequestDto;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostApiIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateAndQueryPost() {
        // 1. 생성 (Command)
        PostCreateRequestDto createDto = new PostCreateRequestDto("테스트 제목", "테스트 내용");

        String createdLocation = webTestClient.post().uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists("Location")
                .returnResult(Void.class)
                .getResponseHeaders().getLocation().getPath();

        String[] parts = createdLocation.split("/");
        String postId = parts[parts.length - 1];

        // 2. 대기 (Eventual Consistency) - 실제 환경에서는 메시지 큐 등을 통해 처리됩니다.
        // 테스트에서는 필요에 따라 짧은 지연을 줄 수 있습니다.
        // try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // 3. 조회 (Query)
        webTestClient.get().uri("/posts/" + postId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(postId)
                .jsonPath("$.title").isEqualTo("테스트 제목")
                .jsonPath("$.content").isEqualTo("테스트 내용");
    }
}