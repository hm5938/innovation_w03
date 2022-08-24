# innovation_w04
게시판 CRUD API 구현

<br/>

## 📆프로젝트 기간

2022.08.19 ~ 2022.08.25 (총 7일)  
<br/>

## ⚙️**요구사항**

1. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 날짜를 조회하기
    - 작성 날짜 기준으로 내림차순 정렬하기
    - AccessToken이 없어도 조회 가능하게 하기
2. 게시글 작성 API
    - AccessToken이 있고, 유효한 Token일 때(== 로그인 상태일 때)만 작성 가능하게 하기
    - 제목 작성 내용을 입력하기
3. 게시글 조회 API
    - 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기
    - AccessToken이 없어도 조회 가능하게 하기
4. 게시글 수정 API
    - AccessToken이 있고, 유효한 Token이면서 해당 게시글 작성자만 수정 가능하게 하기
    - 제목, 작성자명, 작성 내용을 수정되게 하기
5. 게시글 삭제 API
    - AccessToken이 있고, 유효한 Token이면서 해당 게시글 작성자만 삭제 가능하게 하기
    - 글과 댓글이 함께 삭제되게 하기

<aside>
✌️ **새로운 요구사항을 구현해 보세요!**

</aside>

1. 아래 요구사항에 맞는 API 명세서와 ERD 설계
**ERD 설계 →** [https://www.erdcloud.com/](https://www.erdcloud.com/)
    
    **API 명세서 작성 툴 →** [https://learnote-dev.com/java/Spring-A-문서-작성하기/](https://learnote-dev.com/java/Spring-A-%EB%AC%B8%EC%84%9C-%EC%9E%91%EC%84%B1%ED%95%98%EA%B8%B0/)
    
2. 회원 가입 API
    - 닉네임, 비밀번호, 비밀번호 확인을 request에서 전달받기
    - 닉네임은 `최소 4자 이상, 12자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
    - 비밀번호는 `최소 4자 이상이며, 32자 이하 알파벳 소문자(a~z), 숫자(0~9)` 로 구성하기
    - 비밀번호 확인은 비밀번호와 정확하게 일치하기
3. 로그인 API
    - 닉네임, 비밀번호를 request에서 전달받기
    - 로그인 버튼을 누른 경우 닉네임과 비밀번호가 데이터베이스에 등록됐는지 확인하기
    - 로그인 성공 시, JWT를 활용하여 AccessToken을 발급하고, 
    발급한 AccessToken은 Header의 Access-Token에 담아서 반환하기
    - 로그인 성공 시, JWT를 활용하여 RefreshToken을 발급하고,
    발급한 RefreshToken은 Header의 Refresh-Token에 담아서 반환하기
    - **참고 자료**
        1. [https://www.youtube.com/watch?v=ewslpCROKXY&t=440s](https://www.youtube.com/watch?v=ewslpCROKXY&t=440s)
        2. [https://www.inflearn.com/course/스프링부트-jwt](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt)
        3. [https://bcp0109.tistory.com/301](https://bcp0109.tistory.com/301)
4. 로그인 검사
    - `아래 API를 제외하고` 모두 AccessToken, RefreshToken을 전달한 경우만 정상 response를 전달받을 수 있도록 하기
        - 회원가입 API
        - 로그인 API
        - 게시글 목록 조회 API
        - 게시글 조회 API
        - 댓글 목록 조회 API
    - cf. Authorization에 담긴 AccessToken으로 사용자 판단
5.  댓글 목록 조회 API
    - AccessToken이 없어도 댓글 목록 조회가 가능하도록 하기
    - 조회하는 게시글에 작성된 모든 댓글을 response에 포함하기
6. 댓글 작성 API
    - AccessToken이 있고, 유효한 Token일 때만 댓글 작성이 가능하도록 하기
7. 댓글 수정 API
    - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 댓글만 수정 가능하도록 하기
8. 댓글 삭제 API
    - AccessToken이 있고, 유효한 Token이면서 해당  사용자가 작성한 댓글만 삭제 가능하도록 하기
9. 예외 처리
    - Refresh Token을 전달하지 않거나 정상 토큰이 아닐 때는 "Token이 유효하지 않습니다." 라는 에러 메세지를 response에 포함하기
    - 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 response에 포함하기
    - 비밀번호와 비밀번호 확인 값이 일치하지 않을 때 “비밀번호와 비밀번호 확인이 일치하지 않습니다.” 라는 에러 메세지를 resonse에 포함하기
    - 로그인 시, 전달된 닉네임과 비밀번호 중 맞지 않는 정보가 있다면 "사용자를 찾을 수 없습니다."라는 에러 메세지를 response에 포함하기
    - AccessToken이 있고, 유효한 Token이면서 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제할 수 있습니다.”라는 에러 메세지를 response에 포함하기
<br/>

## ERD
![innovation04 (3)](https://user-images.githubusercontent.com/48474929/186540674-4667b38c-f926-4e91-a582-6f03869b5e45.png)


<br/>

## 📑API

| 기능          | Method | url                      | Request                                                                                   | Response                                                                                                                                                                                                                                                                                                                                                                           |
|-------------|--------|--------------------------|-------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 회원가입        | POST   | /api/users/signup        | {<br/>"username": "hyemin1234",<br/>"password": "aaaa",<br/>"passwordAgain": "aaaa"<br/>} | {"success":true,"data":{"id":10,"username":"hyemin1234","password":"$2a$10$Gd9jvRDRTnpcWzsD/K4hWeuZT44iEE8xMgMnGoMmVGmpYXQebLCYi"},"error":null}                                                                                                                                                                                                                                   |
| 로그인         | POST   | /api/users/login         | {"username": "hyemin123","password": "aaaa"}                                              | {"success":true,"data":{"grantType":"","accessToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoeWVtaW4xMjMiLCJleHAiOjE2NjEzNzU3MDV9.QbcKAovMsRSgF-QloVZ3BuKx2nrWgOPBh8cuqCsjNps","refreshToken":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVU0VSIjo5LCJleHAiOjE2NjE5ODAyMDV9.vlDqm6afPffA15jz6FKJi2u4DWPXtusAKRQ3UDdQGtI","accessTokenExpriesIn":1661375705263},"error":null} |
| 게시글 작성      | POST   | /api/posts               | {"title": "title","password" : "password","content" : "content"}                          | {"success":true,"data":{"createdAt":"2022-08-25T07:44:09.033758","modifiedAt":"2022-08-25T07:44:09.033758","id":18,"title":"title","name":"hyemin1234","password":"password","content":"content"},"error":null}                                                                                                                                                                    |
| 게시글 조회      | GET    | /api/posts/{id}          | -                                                                                         | {"success": true,"data": {"createdAt": "2022-08-17T13:47:48.137268","modifiedAt": "2022-08-17T13:47:48.137268","id": 1,"title": "title","name": "이혜민","password": "1234","content": "안녕 난 내용이다"},"error": null}                                                                                                                                                                    |
| 게시글 수정      | PUT    | /api/posts/{id}          | {"title": "title","password" : "1234","content" : "content"}                              | {"success": true,"data": {"createdAt": "2022-08-25T06:40:32.773423","modifiedAt": "2022-08-25T07:47:27.661536","id": 14,"title": "title","name": "hyemin1234","password": "1234","content": "content"},"error": null}                                                                                                                                                              |
| 게시글 삭제      | DELETE | /api/posts/{id}          | -                                                                                         | {"success": true,"data": true,"error": null}                                                                                                                                                                                                                                                                                                                                       |
| 게시글 비밀번호 확인 | POST   | /api/posts/password/{id} | password                                                                                  | {"success": true,"data": true,"error": null}                                                                                                                                                                                                                                                                                                                                       |
| 게시글 목록 조회   | GET    | /api/posts               | -                                                                                         | {"success": true,data": [{"createdAt": "2022-08-25T06:40:32.773423","modifiedAt": "2022-08-25T07:47:27.661536","id": 14,"title": "title","name": "hyemin1234","password": "1234","content": "content"}],"error": null}                                                                                                                                                             |
| 댓글 작성       | POST   | /api/reviews             | {"post": 14,"content" : "좋은 글이에요"}                                                        | {"success": true,"data": {"createdAt": "2022-08-25T07:17:03.341004","modifiedAt": "2022-08-25T07:17:03.341004","id": 16,"post": 14,"username": "hyemin1234","content": "좋은 글이에요"},"error": null}                                                                                                                                                                                   |
| 댓글 수정       | PUT    | /api/reviews/{id}        | {"post": 14,"content" : "좋은 글이에요3"}                                                       | {"success": true,"data": {"createdAt": "2022-08-25T07:17:03.341004","modifiedAt": "2022-08-25T07:17:03.341004","id": 16,"post": 14,"username": "hyemin1234","content": "좋은 글이에요3"},"error": null}                                                                                                                                                                                  |
| 댓글 목록 조회    | GET    | /api/reviews/{post}      | -                                                                                         |{"success": true,"data": [{"createdAt": "2022-08-25T07:21:44.099303","modifiedAt": "2022-08-25T07:21:44.099303","id": 17,"post": 14,"username": "hyemin123","content": "좋은 글이에요"},{"createdAt": "2022-08-25T07:17:03.341004","modifiedAt": "2022-08-25T07:20:30.230266","id": 16,"post": 14,"username": "hyemin1234","content": "좋은 글이에요3"}],"error": null}|
| 댓글 삭제       | DELETE | /api/reviews/{id}        | -                                                                                         |{"success": true,"data": true,"error": null}|

