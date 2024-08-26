package wanted.media.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanted.media.post.dto.DetailResponse;
import wanted.media.post.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService contentService;

    /**
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    public ResponseEntity<DetailResponse> getPost(@PathVariable String postId) {
        return ResponseEntity.ok(contentService.getPost(postId));
    }

}
