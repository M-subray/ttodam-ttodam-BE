---
name: Issue Template
about: 이슈 템플릿입니다.
title: ''
labels: ''
assignees: ''
---
## Description
- 회원가입 기능 생성
  - 메일 인증을 통한 회원가입 구현
    - 구글 SMTP 이용해 메일 전송
  - 랜덤함 6자리 숫자 받아 기입하는 형식
    - Redis에 인증 메일이 key가 되어 value로 번호 5분간 저장 후 자동삭제
  - 인증 완료 후
    - Redis에 인증 완료 된 메일이 key가 되어 value로 'CONFIRMED' 저장 (인증번호가 value로 들어가 있다면 덧씌워짐)
      - 해당 키는 60분간 존재하며 자동삭제
      - 최종 회원가입 버튼을 누를 때 Redis에 해당 메일의 value가 'CONFIRMED' 가 아니라면 가입불가

## Estimated Duration
4/1(월) ~ 4/7 (목) [7일]

## Classification
- [x] 기능
- [ ] 버그
- [ ] 기타

## TO-DO
- [ ] SNS 로그인
  - 카카오 로그인 구현
  - 구글 로그인 구현
## ETC
- Spring boot 2.6 ~ 7 버전대 사용하다가 처음 3.1을 써보게 되어 초기 세팅에 크게 변경 됐던점이 있습니다.
  - spring security 5.6에서 6.1 버전 적용 됨
    - SecurityConfig 설정에서 WebSecurityConfigurerAdapter 를 상속 받던 것이 SecurityFilterChain 을 Bean으로 만들어 사용해야합니다.
    - .csrf().disable() 또는 .and() 같은 메서드 방식이 deprecated 되고 현재 프로젝트의 SecurityConfig 에서 볼 수 있듯 .csrf(CsrfConfigurer::disable) 이러한 람다표현식으로 사용법이 변경 됐습니다.
### 아래는 Spring 공식 문서상의 변경 이유 입니다.


- Lamda DSL 구성을 사용하는 이유

  - 이전 사용방식에서는 반환 유형을 정확히 모르며, 어떤 개체가 구성되어있는지 명확하게 확인 할 수 없다.
  중첩이 깊어질 수록 가독성이 떨어지고 많은 코드베이스가 구성을 이해하기 어렵게 하였고, 잘못된 구성으로 이어지는 상황이 발생했다.


- Lamda DSL의 목표

  - and() 메서드를 통해 옵션을 연결할 필요가 없음
  자동 들여쓰기를 위해 구성을 더 읽기 쉽게 만듦.
