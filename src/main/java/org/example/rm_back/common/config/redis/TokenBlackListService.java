package org.example.rm_back.common.config.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlackListService implements TokenListService{
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public TokenListType getType(){
        return TokenListType.BLACKLIST;
    }

    @Override
    public void addTokenToList(String jti, String userJtiKey){
        Long remainingTtl = redisTemplate.getExpire(userJtiKey, TimeUnit.MILLISECONDS);
        if(remainingTtl > 0){
            String blackKey = "blackList:jti:" + jti;
            redisTemplate.opsForValue().set(blackKey, "", remainingTtl, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isTokenContain(String jti){
        String key = "blackList:jti:" + jti;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
