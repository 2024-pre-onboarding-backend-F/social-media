package wanted.media.content.repository;

import static com.querydsl.core.types.ExpressionUtils.*;
import static wanted.media.content.domain.QPost.*;
import static wanted.media.user.domain.QUser.*;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import wanted.media.content.domain.dto.StatParam;

@Repository
@RequiredArgsConstructor
public class StatRepository {
	private final JPAQueryFactory queryFactory;

	/**
	 * ex.
	 * SELECT SUM(view_count)
	 * FROM post p
	 *          LEFT JOIN members m ON p.user_id = m.user_id
	 * where p.created_at between '2024-08-18 00:00:00' and '2024-08-25 23:59:59'
	 *   and m.account = 'user1';
	 */
	public Long statistics(StatParam param) {
		var selectQuery = switch (param.value()) {
			case COUNT -> queryFactory.select(count(post.id));
			case LIKE_COUNT -> queryFactory.select(post.likeCount.sum());
			case VIEW_COUNT -> queryFactory.select(post.viewCount.sum());
			case SHARE_COUNT -> queryFactory.select(post.shareCount.sum());
		};

		return selectQuery.from(post)
			.leftJoin(user).on(post.user.userId.eq(user.userId))
			.where(
				post.createdAt.between(param.start(), param.end()),
				post.user.account.eq(param.hashtag())
			)
			.fetchFirst();
	}
}
