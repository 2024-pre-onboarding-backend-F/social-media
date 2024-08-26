package wanted.media.post.dto;

import lombok.Builder;
import wanted.media.post.domain.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PostDetailResponse(
        String postId,
        Type type,
        String title,
        String content,
        String hashtags,
        Long likeCount,
        Long viewCount,
        Long shareCount,
        LocalDateTime updatedAt,
        LocalDateTime createdAt,
        UUID userId,
        String account,
        String email
) {
}
