package com.example.demo.member.service;

import com.example.demo.common.response.CustomApiResponse;
import com.example.demo.common.response.ResponseCode;
import com.example.demo.member.dto.JoinRequestDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public CustomApiResponse joinProcess(JoinRequestDto joinRequestDto) {
        if (memberRepository.existsByMemberId(joinRequestDto.getMemberId())) {
            return CustomApiResponse.fail(ResponseCode.MEMBER_CREATE_FAIL, null);
        } else {
            memberRepository.save(
                    Member.builder()
                            .memberId(joinRequestDto.getMemberId())
                            .memberName(joinRequestDto.getMemberName())
                            .password(bCryptPasswordEncoder.encode(joinRequestDto.getPassword()))
                            .role(joinRequestDto.getRole())
                            .build()
            );
            return CustomApiResponse.success(null, ResponseCode.MEMBER_CREATE_SUCCESS.getMessage());
        }
    };

}
