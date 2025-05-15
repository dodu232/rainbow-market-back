package org.example.rm_back.common.config.redis;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.rm_back.global.exception.ApiException;
import org.example.rm_back.global.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TokenListStrategyFactory {

    private final Map<TokenListType, TokenListService> strategies;

    public TokenListStrategyFactory(List<TokenListService> services) {
        this.strategies = services.stream()
            .collect(Collectors.toMap(TokenListService::getType, Function.identity()));
    }

    public TokenListService get(TokenListType type) {
        return strategies.get(type);
    }

    public <T extends TokenListService> T get(TokenListType type, Class<T> clazz) {
        TokenListService svc = strategies.get(type);
        if (svc == null) {
            throw new ApiException("No strategy for type " + type, ErrorType.NO_RESOURCE,
                HttpStatus.BAD_REQUEST);
        }
        return clazz.cast(svc);
    }
}
