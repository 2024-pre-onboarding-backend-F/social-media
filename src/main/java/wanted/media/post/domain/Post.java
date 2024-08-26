package wanted.media.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wanted.media.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @Column(name = "post_id", nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Size(max = 150)
    @Column(nullable = false)
    private String title;

    private String post;
    private String hashtags;

    @ColumnDefault("0")
    private Long likeCount;

    @ColumnDefault("0")
    private Long viewCount;

    @ColumnDefault("0")
    private Long shareCount;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public void incrementViewCount() {
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
        this.viewCount += 1;
    }
    
}
