package wanted.media.content.domain.dto;

public record StatResponse(Long count) {

    public static StatResponse from(Long count) {
        return new StatResponse(count);
    }
}
