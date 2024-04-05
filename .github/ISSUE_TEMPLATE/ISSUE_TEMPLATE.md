---
name: Issue Template
about: 이슈 템플릿입니다.
title: 'Feat:#4-로그인 기능 구현'
labels: '기능'
assignees: 'M-subray'
---
## Description
-  로그인 기능 구현
  - 메일 존재여부 확인
  - 비밀번호 일치 확인

## Estimated Duration
4/1(월) ~ 4/7 (목) [7일]

## Classification
- [x] 기능
- [ ] 버그
- [ ] 기타

## TO-DO

## ETC
- 로그인시 UnKnownClassException 발생
  - implementation 'io.jsonwebtoken:jjwt-api:0.11.2' 기존 jwt 의존성을 	
    - implementation group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.2'
      runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.2'
      runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.2' 이와 같이 변경 후 처리 완료
