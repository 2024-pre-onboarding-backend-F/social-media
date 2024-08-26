package wanted.media.content.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import wanted.media.content.domain.dto.StatParam;
import wanted.media.content.domain.dto.StatResponse;
import wanted.media.content.service.StatService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatController {
	private final StatService statService;

	@GetMapping("/statistics")
	public ResponseEntity<StatResponse> statistics(@ModelAttribute StatParam param) {
		StatResponse response = StatResponse.from(statService.statistics(param));
		return ResponseEntity.ok().body(response);
	}
}
