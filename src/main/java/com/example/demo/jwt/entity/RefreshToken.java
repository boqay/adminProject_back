package com.example.demo.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60)
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String memberId;
    private String refreshToken;
}
