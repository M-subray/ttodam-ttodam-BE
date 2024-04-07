---
name: Issue Template
about: 이슈 템플릿입니다.
title: 'Feat:#5-회원탈퇴 기능 구현'
labels: '기능'
assignees: 'M-subray'
---
## Description
-  회원탈퇴 기능 구현
   - 메일 존재여부 확인
   - 현재 로그인 된 계정과 요구하는 계정의 일치 확인

## Estimated Duration
4/1(월) ~ 4/7 (목) [7일]

## Classification
- [x] 기능
- [ ] 버그
- [ ] 기타

## TO-DO

## ETC
- AuthenticationUtil 설명
  - SecurityContextHolder.getContext().getAuthentication() 을 통해 현재 로그인 된 계정의 정보를 담아옮
  - 그렇게 담아온 authentication 객체의 .principal를 instanceof UserDetails로 로그인이 돼 있는지 확인
    - 로그인 돼 있지 않거나 토큰이 만료 됐다면 커스텀 익셉션인 SIGNIN_TIME_OUT 발생
