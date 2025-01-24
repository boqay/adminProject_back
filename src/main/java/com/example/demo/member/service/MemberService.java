package com.example.demo.member.service;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 생성
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // 회원 목록 조회
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 회원 상세 조회 (by ID)
    @Transactional(readOnly = true)
    public Optional<Member> getMemberById(Long memberSeq) {
        return memberRepository.findById(memberSeq);
    }

    // 회원 수정
    public Member updateMember(Long memberSeq, Member updatedMember) {
        return memberRepository.findById(memberSeq)
                .map(member -> {
                    member = Member.builder()
                            .memberSeq(memberSeq)
                            .memberId(updatedMember.getMemberId())
                            .memberName(updatedMember.getMemberName())
                            .password(updatedMember.getPassword())
                            .role(updatedMember.getRole())
                            .phone(updatedMember.getPhone())
                            .address(updatedMember.getAddress())
                            .birth(updatedMember.getBirth())
                            .gender(updatedMember.getGender())
                            .build();
                    return memberRepository.save(member);
                })
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberSeq));
    }

    // 회원 삭제
    public void deleteMember(Long memberSeq) {
        memberRepository.deleteById(memberSeq);
    }
}
