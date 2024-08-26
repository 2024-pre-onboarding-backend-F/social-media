package wanted.media.post.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.post.domain.Post;
import wanted.media.post.domain.Type;
import wanted.media.post.repository.PostRepository;
import wanted.media.user.domain.Grade;
import wanted.media.user.domain.User;
import wanted.media.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    void getPostTest() {
        // given
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
                .post("내용 입력")
                .user(user)
                .viewCount(100L)
                .build();

        postRepository.save(post);

        // when
        Post getData = postService.getPost(post.getId());

        // then
        assertThat(getData.getTitle()).isEqualTo("제목 입력");
        assertThat(getData.getPost()).isEqualTo("내용 입력");
        assertThat(getData.getViewCount()).isEqualTo(101);
        assertThat(getData.getUser().getAccount()).isEqualTo("sun");
        assertThat(getData.getUser().getEmail()).isEqualTo("sun@gmail.com");
    }
}