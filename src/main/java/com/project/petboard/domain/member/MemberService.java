package com.project.petboard.domain.member;

import com.project.petboard.infrastructure.jwt.JwtDto;
import com.project.petboard.infrastructure.jwt.JwtTokenUtil;
import com.project.petboard.infrastructure.kakao.KakaoAccount;
import com.project.petboard.infrastructure.kakao.KakaoUtil;
import com.project.petboard.infrastructure.kakao.RequestKakao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class MemberService{

    private final MemberRepository memberRepository;

    private final RoleRepository roleRepository;

    private final KakaoUtil kakaoUtil;

    private final JwtTokenUtil jwtTokenUtil;

    public void deleteMember(Long memberNumber){
        memberRepository.deleteById(memberNumber);
    }

    public JwtDto loginMember(String code){
        RequestKakao requestKakao  = kakaoUtil.getKakaoProfile(code);
        log.info(String.valueOf(requestKakao.getId()));
       Member member = memberRepository.findByMemberSnsId(String.valueOf(requestKakao.getId()))
               .map(entity -> entity.kakaoProfileUpdate(requestKakao.getKakao_account()))
               .orElseGet(()->saveMember(requestKakao));
        return createToken(member);
    }

    public JwtDto createToken(Member member){
       return jwtTokenUtil.createToken(member.getMemberNumber());
    }

    public boolean isCheckOverlapMember(String email){
        return memberRepository.existsByMemberEmail(email);
    }

    public void saveRole(Member member){
        roleRepository.save(new Role(member,"MEMBER"));
    }

    public Member saveMember(RequestKakao requestKakao){
        memberRepository.save(requestKakao.toEntity());
        Member member = memberRepository.findByMemberSnsId(String.valueOf(requestKakao.getId())).get();
        saveRole(member);
        return member;
    }

}
