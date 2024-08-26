package wanted.media.content.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import wanted.media.content.domain.dto.StatParam;
import wanted.media.content.repository.StatRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {
	private final StatRepository statRepository;

	public Long statistics(StatParam param) {
		return statRepository.statistics(param);
	}
}
