package com.bookflow.user.service.concrete;

import com.bookflow.user.enums.StatusEnum;
import com.bookflow.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserCleanUpService {

    private final UserRepository userRepository;

    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void deleteExpiredPendingUsers() {

        LocalDateTime expirationTime =
                LocalDateTime.now().minusHours(24);

        userRepository.deleteByStatusAndCreatedAtBefore(
                StatusEnum.PENDING,
                expirationTime
        );
    }
}
