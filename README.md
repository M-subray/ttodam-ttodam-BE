<div align="center"><img width="621" alt="또담또담 배너" src="https://github.com/ttodam-ttodam/ttodam-ttodam-BE/assets/38836598/a7525b97-3b05-42fc-9d17-9ef676205437"></div>

<div align="center"><h3>또담또담, 우리 동네 공동구매 플랫폼</h3></div>

</br>

## 프로젝트 설명
1인 가구 증가와 물가가 치솟고 있는 상황에 합리적 소비를 원하는 사람들이 늘고 있습니다.

1인 가구가 혼자 구매하기에 양이 많거나 부담비가 부담되는 경우들이 있습니다.

또담또담은 이럴 때 가까운 거리에 살고 있는 동네 주민들끼리 함께 물건을 구매해 만남 장소를 결정 및

소분까지 보다 편하게 할 수 있도록 도와주는 커뮤니티 앱입니다.

</br>

## 사용 기술
|<strong> 분야 </strong>|<strong> 사용한 기술 이름 </strong>|
|:--|:--|
|<strong> Language </strong>| Java 17 |
|<strong> Build </strong>| Gradle |
|<strong> Framework </strong>| Spring, SpringBoot 3.1.10 |
|<strong> Core Skills </strong>| Spring JPA, Spring Security, OAuth, SSE, STOMP, WebSocket |
|<strong> Database </strong>| MySQL,  Redis |
|<strong> Deploy </strong>| AWS EC2, AWS RDS, Amazon S3 |

</br>

## 🤼 팀원 소개 🤼

|<strong> 문병학 </strong>|<strong> 양예린 </strong>|<strong> 양지은 </strong>|
|:----:|:-----:|:----:|
|<img src="https://avatars.githubusercontent.com/u/100525337?v=4" width="200" height="200"/>|<img src="https://avatars.githubusercontent.com/u/68904755?v=4" width="200" height="200"/>|<img src="https://github.com/ttodam-ttodam/ttodam-ttodam-BE/assets/38836598/efd7413d-e06d-4955-a942-8ad55c129fd6" width="200" height="200"/>|
|<strong> 유저, 프로필, 알림  </strong>|<strong> 게시글, 검색, 배포 </strong>|<strong> 채팅, 알림, 테스트 </strong>|
|<strong> [M_subray](https://github.com/M-subray) </strong>|<strong> [e_elin](https://github.com/yell2023) </strong>|<strong> [yje9802](https://github.com/yje9802) </strong>|

<br/>

## 시스템 아키텍처
![또담또담_아키텍처](https://github.com/ttodam-ttodam/ttodam-ttodam-BE/assets/38836598/5aa1bb7b-86b0-419e-b634-9f948f312e3f)

</br>

## ERD
![ttodamERD](https://github.com/ttodam-ttodam/ttodam-ttodam-BE/assets/144686741/17512f4d-f9c5-4986-b8a5-5b2f1d0feba3)

## 프로젝트 기능
### 회원 기능
- [x] 회원가입
  - 이메일 및 비밀번호로 회원가입 진행
    - 실제 이메일을 id로 사용하기 위해 랜덤한 6자릿의 수를 본인의 메일로 받아 인증을 통해 가입
- [x] 로그인
  - 일반 로그인
    - 이메일과 비밀번호를 통해 로그인 진행
  - 소셜 로그인 (구글, 카카오)
    - 기존 가입 이메일이라면 바로 로그인 진행
    - 가입 돼 있지 않은 이메일이라면 이메일로 가입 진행된 후 로그인 진행 됨
  - 로그인과 동시에 JWT 발급하여 권한이 필요한 모든 페이지에서 인증에 사용 됨
  - 로그인과 동시에 알림기능을 위한 SSE 생성
- [x] 로그아웃
  - 로그아웃과 동시에 해당 유저의 JWT를 Redis에 JWT의 남은 시간을 TTL로 넣어 저장
    - Redis에 저장 된 JWT는 블랙리스트로 지정되어 권한이 필요한 페이지에 접근을 불가하게 만듦
  - 로그아웃과 동시에 알림기능에 사용 된 SSE 삭제
- [x] 회원탈퇴
  - 회원탈퇴와 동시에 알림기능에 사용 된 SSE 삭제
  - DB에서 해당 유저의 정보 삭제
- [x] 프로필 수정
  - 프로필 사진, 닉네임, 휴대폰번호, 주소 기입 및 기존 비밀번호 변경 역시 가능
  - 첫 프로필 수정 진입 시 위의 정보 중 하나라도 미기입하면 수정 불가
  - 첫 프로필 수정이 이뤄져야 또담또담의 기능 정상적으로 사용 가능
- [x] 키워드
  - 알림을 받기 위해 키워드 등록
    - 공동구매 게시글이 작성될 때 프로덕트의 이름이 곧 키워드가 되어 해당 프로덕트를 키워드로 등록한 유저에게 알림이 오게 된다.
- [x] 북마크
  - 게시판의 글을 북마크로 등록 가능
  - 북마크를 조회해 찜해놓은 글을 모아서 확인 가능
- [x] 매너점수
  - 유저의 매너점수 조회 가능
  - 거래가 끝나면 공동구매에 함께 참여한 사람들의 매너점수를 평가하게 된다.
### 게시판 기능
- [x] 공동구매 게시글 CRUD
  - [x] 작성
    - 제목, 내용, 카테고리, 모일 장소(주소), 모집 인원, 모집 기간
      - 모일 주소를 위도, 경도로 변환하여 게시글의 내부의 지도API를 통해 표기되게 됨
    - 프로덕트 (구매 품목), 가격, 품목 사진, 구매 링크
      - 게시판(Post) 테이블과 별개로 Product 테이블로 관리
    - 위의 내용을 토대로 글을 작성하면 기본적으로 글의 상태는 '진행중' 으로 생성 됨
  - [x] 수정
    - 작성에서의 모든 요소 수정 및 프로덕트 추가 가능
      - 프로덕트 사진의 수정은, 수정과 동시에 S3에 저장 된 기존의 사진은 삭제 됨
- [x] 참여 요청
  - 원하는 프로덕트가 존재하는 모집글에 참여 요청을 진행
    - 게시글의 상태가 '진행중' 인 경우 요청이 가능하다.
    - 게시글 작성자는 요청자의 매너점수를 확인해 최종 수락 가능
      - 수락과 동시에 게시글의 요청인원이 카운트 된다.
      - 게시글의 모집인원이 모두 모이게 되면 게시글의 상태가 '완료'가 되며 구매 일정 조율을 위한 단체 채팅방이 생성된다.
  - 요청 전 문의사항이 있다면 작성자에게 1:1 채팅을 통해 문의 가능
### 채팅 기능
- [x] 1:1 문의 채팅방 생성
  - 참여자가 글 작성자에게 문의사항이 있을 경우 생성할 수 있습니다.
- [x] 단체 채팅방
  - 공동구매 게시글의 참여희망 인원이 모두 모이게 되면 자동으로 생성됩니다.
- [x] WebSocket과 STOMP를 활용하여 채팅 기능을 구현했습니다.
  - STOMP를 도입하여 세션 관리와 메시지 타입 관리에 대한 부담을 줄였습니다.
- [x] 30일 지난 채팅 메시지 자동 삭제
  - Spring Scheduler 기능을 활용하여 서버로 수신된 지 30일 지난 메시지는 자동으로 삭제되게 하여 DB에 과도한 메시지가 쌓이는 것을 방지했습니다.
