# shoppingWiki

위키와 쇼핑이 어울러진 토이 프로젝트입니다.  
항상 쇼핑을 할 때 쇼핑을 하기 위해서 이것 저것 많이 알아보고 사야하는 번거로움이 있었습니다.  
그런면에서 정보를 보면 쇼핑을 하면 조금 더 좋은 제품을 살 수 있지 않을까 싶어 만들게 되었습니다.  

## 기술스택 
  
- FrontEnd : React, Next.js
- BackEnd : SpringBoot, JPA, QueryDsl, SpringCloud, Mysql, Redis, Kafka

## Shopping Wiki DB ERD
![쇼핑위키 ER 다이어 그램 drawio](https://github.com/kibongcoders/shoppingWiki/assets/54662349/8e635663-dff5-4cb5-9d29-d340cc84e470)
 
## Shopping Wiki Jwt 시퀀스 다이어그램
![JWT 시퀀스 다이어그램 drawio](https://github.com/kibongcoders/shoppingWiki/assets/54662349/a26ef4c3-a5e3-481b-b99f-b46b6b7c51c9)

### How to Run
1. 로그인 및 회원가입을 통해 AccessToken과 RefreshToken을 받습니다.
2. 이 토큰들을 LocalStorage에 넣고 요청이 필요할 때마다 토큰을 이용해서 요청합니다.
3. AccessToken의 만료가 지난 경우 RefreshToken을 통해 AccessToken을 요청합니다.
4. RefreshToken의 정보 및 권한이 올바른 경우 AccessToken 반환합니다.

## Shopping Wiki Wiki 정보 가져오기 시퀀스 다이어그램
![기본적인 시퀀스 다이어 그램 drawio](https://github.com/kibongcoders/shoppingWiki/assets/54662349/e741348e-3e00-4640-b45e-594ff6542d5e)

### How to Run
1. 클라이언트가 서버에 위키의 컨텐츠를 검색합니다.
2. Redis에 먼저 위키 컨텐츠가 존재하는지 조회합니다.
3. 있는 경우 해당 컨텐츠를 클라이언트에게 반환해줍니다.
4. 없는 경우 DB에 해당 컨텐츠가 있는지 조회합니다.
5. DB에 해당 컨텐츠가 있을 경우 Kafka에 데이터를 보내 클라이언트에게 반환 후 Redis에 해당 컨텐츠를 저장합니다. (WriteThrough)
6. 없는 경우 Kafka에 데이터를 보내 ChatGpt 통해 위키 정보를 저장할 수 있도록합니다.

## ShoppingWiki 웹 이벤트 수집 및 서버 지표 파이프라인 아키텍처
![카프카 파이프 라인 drawio](https://github.com/kibongcoders/shoppingWiki/assets/54662349/ba1ac925-0d3e-4458-ac98-e58f69afe9b4)

### 웹 이벤트 수집 파이프라인

- Redis에 데이터를 저장해야하는 경우
프로듀서 APP에서 데이터를 보내 컨슈머 APP에서 해당 데이터를 저장할 수 있도록한다.
- 컨텐츠 데이터가 없어 ChatGpt를 사용해서 데이터를 저장해야하는 경우
프로듀서 APP에서 데이터를 보내 컨슈머 APP에서 해당 데이터를 ChatGpt에 보내 데이터 반환 받은 후 Redis 및 DB에 저장한다.

### 서버 지표 파이프라인

- MetricBeat를 이용해서 서버 지표 수집
수집된 정보를 카프카 스트림즈 APP에 보내 CPU는 metric.cpu 토픽으로 Memory는 metric.memory 토픽으로 데이터를 Kafka 클러스터에 다시 보낸다.  
만약, CPU 사용량이 50% 이상일 경우 metric.cpu.alert 토픽으로 데이터를 보낸다.
이 데이터들을 컨슈머 APP에서 받아서 DB에 저장한다.
