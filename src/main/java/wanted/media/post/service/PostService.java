package wanted.media.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.exception.ErrorCode;
import wanted.media.exception.NotFoundException;
import wanted.media.post.domain.Post;
import wanted.media.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post getPost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        post.incrementViewCount();
        return post;
    }
}
