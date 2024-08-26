package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.domain.Post;
import wanted.media.post.dto.PostDetailResponse;
import wanted.media.post.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService posetService;

    /**
     * @param postId
     * @return PostDetailResponse
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable String postId) {
        Post post = posetService.getPost(postId);
        PostDetailResponse result = PostDetailResponse.builder()
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
        return ResponseEntity.ok(result);
    }
}
