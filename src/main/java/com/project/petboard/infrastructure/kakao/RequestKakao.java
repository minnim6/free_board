package com.project.petboard.infrastructure.kakao;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.member.MemberSignupCategory;
import com.project.petboard.domain.member.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class RequestKakao {
    private Long id;
    private KakaoAccount kakao_account;

    public Member toEntity(){
        return Member.builder()
                .memberNickname(kakao_account.getProfile().getNickname())
                .memberEmail(kakao_account.getEmail())
                .memberSignupCategory(MemberSignupCategory.KAKAO)
                .memberSnsId(String.valueOf(id))
                .build();
    }
}
