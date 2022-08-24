# innovation_w03
게시판 CRUD API 구현

<br/>

## 📆프로젝트 기간

2022.08.12 ~ 2022.08.18 (총 7일)  
<br/>

## ⚙️**요구사항**

1. 아래의 요구사항을 기반으로 Use Case 그려보기
    - 손으로 그려도 됩니다.
    - cf. [https://narup.tistory.com/70](https://narup.tistory.com/70)
2. 전체 게시글 목록 조회 API
    - 제목, 작성자명, 작성 날짜를 조회하기
    - 작성 날짜 기준으로 내림차순 정렬하기
3. 게시글 작성 API
    - 제목, 작성자명, 비밀번호, 작성 내용을 입력하기!

4. 게시글 조회 API
    - 제목, 작성자명, 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
5. 게시글 비밀번호 확인 API
    - 비밀번호를 입력 받아 해당 게시글의 비밀번호와 일치여부 판단하기
6. 게시글 수정 API
    - 제목, 작성자명, 비밀번호, 작성 내용을 수정되게 하기
7. 게시글 삭제 API
    - 글이 삭제되게 하기
![]()
<br/>

## Use Case

![스크린샷 2022-08-12 오후 3 55 04](https://user-images.githubusercontent.com/48474929/184300772-5a23427f-d73c-4cf5-9ef7-b6dc0cfa2720.png)


<br/>

## 📑API

| 기능              | Method | url               | Request                   | Response             |
|-----------------|--------|-------------------|---------------------------|----------------------|
| 전체 게시글 목록 조회| GET   | /api/posts         |        -                  | 게시글 목록 List<Post>  |
| 게시글 작성        | POST  | /api/posts         |        -                  | -                    |
| 게시글 조회        | GET   | /api/posts/{id}    | id                        | 게시글 정보 Post        |
| 게시글 수정        | PUT   | /api/posts/{id}    | id                        | -                    |
| 게시글 삭제        | DELETE| /api/posts/{id}    | id                        | -                    |
| 게시글 비밀번호 확인 | POST  | /api/posts/password| id, password              |  일치 여부             |
