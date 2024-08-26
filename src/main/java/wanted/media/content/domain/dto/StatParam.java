package wanted.media.content.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import wanted.media.content.domain.CountValueType;
import wanted.media.content.domain.StatDateType;
import wanted.media.exception.BadRequestException;
import wanted.media.exception.ErrorCode;

/**
 *
 * @param hashtag 계정, defaultValue = 본인 계정
 * @param type DATE or HOUR, 필수 값
 * @param start 검색 시작 기간, defaultValue = 오늘로 부터 7일 전
 * @param end 검색 끝 기간, defaultValue = 오늘
 * @param value COUNT or VIEW_COUNT, LIKE_COUNT, SHARE_COUNT, defaultValue = count
 */
public record StatParam(
    String hashtag,
    StatDateType type,
    LocalDateTime start,
    LocalDateTime end,
    CountValueType value) {

    public StatParam(String hashtag, StatDateType type, LocalDateTime start, LocalDateTime end, CountValueType value) {
        // JWT 구현시 default 값 변경 예정
        this.hashtag = (hashtag == null) ? "me" : hashtag;

        // Type(DATE, HOUR)에 맞춰 기본 값 설정
        this.start = switch (type) {
            case DATE -> (start == null) ? LocalDateTime.of(LocalDate.now().minusDays(7), LocalTime.MIN) : start;
            case HOUR -> (start == null) ? LocalDateTime.now().minusDays(7) : start;
        };
        this.end = switch (type) {
            case DATE -> (end == null) ? LocalDateTime.of(LocalDate.now(), LocalTime.MAX) : end;
            case HOUR -> (end == null) ? LocalDateTime.now() : end;
        };
        validateDateRange(start, end);

        // Default 값 설정
        this.value = (value == null) ? CountValueType.COUNT : value;

        // Default 값 설정
        this.type = Optional.ofNullable(type)
            .orElseThrow(() -> new BadRequestException(ErrorCode.INVALID_PARAMETER));
    }

    /**
     * start 일자가 end 일자보다 앞인지 검증
     */
    private void validateDateRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER);
        }
    }
}
