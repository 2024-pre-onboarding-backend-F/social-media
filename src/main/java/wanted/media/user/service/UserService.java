package wanted.media.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wanted.media.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
