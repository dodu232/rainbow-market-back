package org.example.rm_back.common.config.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenJtiListService implements TokenListService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.exp}")
    private long tokenExpiry;

    @Override
    public TokenListType getType() {
        return TokenListType.CURRENT_JTI;
    }

    @Override
    public void addTokenToList(String jti, String userJtiKey) {
        redisTemplate.opsForValue().set(userJtiKey, jti, tokenExpiry, TimeUnit.MILLISECONDS);
    }

    public String getJit(String userJtiKey) {
        return (String) redisTemplate.opsForValue().get(userJtiKey);
    }
}
