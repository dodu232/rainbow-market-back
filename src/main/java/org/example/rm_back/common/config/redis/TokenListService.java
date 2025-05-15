package org.example.rm_back.common.config.redis;

public interface TokenListService {

    TokenListType getType();

    public void addTokenToList(String jti, String userJtiKey);
}
