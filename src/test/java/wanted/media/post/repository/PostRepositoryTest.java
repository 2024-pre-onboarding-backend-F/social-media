package wanted.media.post.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.User;
import wanted.media.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void getPostTest() {

        User user = User.builder()
                .account("sun")
                .email("sun@gmail.com")
                .password("1234")
                .grade(Grade.NORMAL_USER)
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .id("qwer")
                .type(Type.TWITTER)
                .title("제목 입력")
                .user(user)
                .build();

        postRepository.save(post);

        Post result = postRepository.findById(post.getId()).orElseThrow(() -> new IllegalArgumentException("Content not found"));

        assertThat(result.getTitle()).isEqualTo("제목 입력");
        assertThat(result.getUser().getAccount()).isEqualTo("sun");
    }

    @Test
    @Transactional
    void addViewCountTest() {
        User user = User.builder()
                .account("sun")
                .email("sun@gmail.com")
                .password("1234")
                .grade(Grade.NORMAL_USER)
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .id("qwer")
                .type(Type.TWITTER)
                .title("제목 입력")
                .user(user)
                .viewCount(100L)
                .build();

        postRepository.save(post);

        Post getContent = postRepository.findById(post.getId()).orElseThrow(() -> new RuntimeException("Content not found"));


        getContent.incrementViewCount();
        assertThat(getContent.getViewCount()).isEqualTo(101);


        postRepository.save(getContent);

    }
}