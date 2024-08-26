package wanted.media.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wanted.media.exception.ErrorCode;
import wanted.media.exception.PostNotFoundException;
import wanted.media.post.domain.Post;
import wanted.media.post.dto.DetailResponse;
import wanted.media.post.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public DetailResponse getPost(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.ENTITY_NOT_FOUND));

        post.incrementViewCount();
        postRepository.save(post);

        DetailResponse result = DetailResponse.builder()
                .postId(post.getId())
                .likeCount(post.getLikeCount())
                .type(post.getType())
                .title(post.getTitle())
                .post(post.getPost())
                .hashtags(post.getHashtags())
                .viewCount(post.getViewCount())
                .shareCount(post.getShareCount())
                .updatedAt(post.getUpdatedAt())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getUserId())
                .account(post.getUser().getAccount())
                .email(post.getUser().getEmail())
                .build();
        return result;
    }

}
