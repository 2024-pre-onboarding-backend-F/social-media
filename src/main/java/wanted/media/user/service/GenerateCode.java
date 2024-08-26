package wanted.media.user.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateCode {
    private int codeLength = 6; //6자리 코드

    public String codeGenerate() {
        // 1. UUID 생성 후, "-" 제거, 대문자 변환
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        // 2. UUID에서 필요한 길이만큼 자르기
        return uuid.substring(0, codeLength);
    }
}
