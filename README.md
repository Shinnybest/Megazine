# 항해 Spring 2-3주차 개인 과제 - 로그인 사용자용 게시판 웹사이트 제작 Project

## BE 개발자 👩‍🦰
### 이름: 김효신

## 프로젝트 사용 기술
사용한 버전: JAVA8 / SDK 1.8

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">


## 주요기능

### 회원가입 / 로그인
### 전체 게시글 및 상세 게시글 조회, 작성, 수정 및 삭제
### 게시글 좋아요 등록 및 취소

## API
| 분류   | 기능     |url|Method|Request| Response                                                                                                                                                                                                                                                                                    |                                                                                                                                                                                                                                                       
|------|--------|---|------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 회원관리 | 회원가입   |/api/register|POST| { "email": "email", <br/>"username": "username", <br/>"password": "password", <br/>"passwordCheck": "passwordCheck" }                                                                                                                                      | { "result": "success / fail", <br/>"msg": "msg" }                                                                                                                                                                                                                                           |
|      | 로그인    |/api/login|POST| { "email": "email", <br/>"password": "password" }                                                                                                                                                                                                         | { "result": "success / fail", <br/>"msg": "msg", <br/>"userData": { "userId": userId, <br/>"username": "username", <br/>"email": "email", <br/>"token": "token" } }                                                                                                                         |
| 게시판  | 전체글 조회 |/api/board|GET|| { "result": "success / fail", <br/>"msg": "msg", <br/>"data": [ { "boardId": boardId, <br/>"creater": "username", <br/>"content": "content", <br/>"imageurl": "url", <br/>"grid": "grid", "likeCount": int, <br/>"createdAt": LocalDateTime, <br/>"likes": [ { "userId": userId } ] } ] } |
|      | 상세글 조회 |/api/board/{boardId}|GET|                                                                                                                                                                                                                                                       | { "result": "success / fail",<br/> "msg": "msg",<br/> "data": { "boardId": boardId,<br/> "creater": "username",<br/> "content": "content",<br/> "imageurl": "url",<br/> "grid": "grid",<br/> "likeCount": int, <br/>"createdAt": LocalDateTime, <br/>"likeList": [ { "userId": userId } ] } } |
|      | 게시글 등록 |/api/board|POST| { "username": “username", <br/>"imageUrl": "url", <br/>"grid": "grid", <br/>"content":"content" }                                                                                                                                                                        | { "result": "success / fail", <br/>"msg": "msg", <br/>"boardId": boardId }                                                                                                                                                                                                                  |
|      | 게시글 수정 |/api/board/{boardId}|PUT| { "username":"username", <br/>"imageUrl": "url", <br/>"grid": "grid", <br/>"content":"content" }                                                                                                                                                                         | { "result": "success / fail", <br/>"msg": "msg" }                                                                                                                                                                                                                                           |
|      | 게시글 삭제 |/api/board/{boardId}|DELETE| { "username":"username" }                                                                                                                                                                                                                                 | { "result": "success / fail", <br/>"msg": "msg }                                                                                                                                                                                                                                            |
| 좋아요  | 좋아요 등록 |/api/board/{boardId}/like|POST| { "userid": userId }                                                                                                                                                                                                                                      | { "result": "success / fail", <br/>"msg": "msg" }                                                                                                                                                                                                                                           |
|      | 좋아요 취소 |/api/board/{boardId}/like|DELETE| { "userid": userId }                                                                                                                                                                                                                                      | { "result": "success / fail", <br/>"msg": "msg" }                                                                                                                                                                                                                                           |


## 프로젝트 중 아쉬웠던 점 + 좀 더 고민해야할 내용
### 01. 연관 매핑 시 발생하는 순환참조 문제 해결 방식
스프링부트는 Object를 JSON으로 변환하여 보내줄 때 HttpMessageConverters 클래스에서 Jackson 라이브러리를 사용해서 직렬화 과정을 거치는데 이 과정에서 두개의 엔티티 객체가 서로를 조회하기 때문에 순환참조의 문제가 발생한다. 그렇기 때문에 역직렬화의 과정을 끊어줄 수 있도록 해결을 해야 하는데 그 방법은 다양하다. JsonIgonore Annotation을 쓸 수도 있고, DTO를 활용해서 DTO 자체를 반환하는 방법도 있다. 이번에 연관 매핑 관련해서 공부를 하면서 DTO를 활용해서 이 관계를 끊어줬어야 하는데, 이전 프로젝트 리팩토링을 하면서 정작 이 프로젝트에서는 여전히 어노테이션 사용으로 끝내버렸다. 시간있을 때 수정해야하는 부분이다.

### 02.  단위 테스트 그리고 Mokito 사용 및 컨트롤러 테코 작성의 실패
테코 짜는 게 처음인데 또 기능구현을 다 해놓고 나서 테스트 코드를 짜는 과제가 주어져서, 단위테스트를 어떤식으로 하는 게 맞는지 감이 잘 오지 않았다. 또 Mock Object를 만들면서 NullPointerException 문제가 있어서, when ~ thenreturn으로 예외처리를 해줬어야 하는데, 해당 부분이 Mokito Framework 사용 없이는 힘든 부분이라 도중에 시간 부족으로 후순위로 미루게 되었다. 이 부분은 나중에 꼭 완성하고싶다.

### 03. 캐싱
캐싱 같은 경우에는 자주 불러오는 데이터의 경우 쿼리에서 select로 조회한 값을 저장하고 있다가 다시 같은 쿼리문을 요청하면 미리 캐싱된 값을 반환하는 기능이다. 이걸 전체 게시물을 get방식으로 가져올 때 쓰니까 확실히 DB 조회 속도가 빨라졌다. ngrinder 통해서 TTFB가 줄어들고 TPS는 높아지는 것도 확인했다. 하지만 이 부분에 캐싱을 쓰는 것이 맞는 전략인지 모르겠다. 왜냐하면 수정하고 삭제할때 Cacheput과 Cacheevict 어노테이션으로 또 캐시 수정과 삭제를 해줘야 하는데, 게시판 조회에 대한 트래픽이 엄청나게 크지 않은 이상, 모든 Controller단의 메소드에서 Cache 어노테이션을 다는 게 맞는 방법인지 모르겠다. 더욱이 여기서는 Cacheput과 CacheEvict을 할 때, 다시 Cacheable 어노테이션 처리해둔 캐시 메모리 값을 지우고 업데이트 하는 방법을 찾지 못해 최종적으로 캐시 구현 완성도 하지 못했지만. 실전에서는 좀 더 공부해서 Redis도 공부하고 제대로 Caching을 사용해 보고싶다.
