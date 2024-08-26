package wanted.media.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wanted.media.post.domain.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DetailResponse {
    private String postId;
    private Type type;
    private String title;
    private String post;
    private String hashtags;
    private Long likeCount;
    private Long viewCount;
    private Long shareCount;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private UUID userId;
    private String account;
    private String email;
}
