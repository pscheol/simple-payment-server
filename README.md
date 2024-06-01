# Simple Payment Service

포인트를 이용한 간단한 결제 승인 시스템

## 디렉토리 구조

핵사고날 아키텍처의 Port And Adapter 패턴을 이용하여 구성

![](docs/hexagonal.jpg)

디렉토리

```tree
└── payment
    ├── SimplePaymentServerApplication.java
    ├── adapter
    │   ├── in
    │   │   └── web
    │   └── out
    │       └── persistence
    │           ├── exchangerate
    │           ├── merchant
    │           ├── payment
    │           └── user
    ├── application
    │   └── port
    │       ├── in
    │       └── out
    ├── config
    ├── domain
    │   ├── exchangerate
    │   ├── merchant
    │   ├── payment
    │   └── user
    └── exception
```

* adapter
    * in : [외부에서 들어온 요청 처리 adapter]
        * web : 잔액조회, 결제 예상결과, 결제승인요청에 대한 Controller
    * out : [외부로 모듈과 통신 및 연결 adapter]
        * persistence
            * exchangerate : 환율 Entity, Repository
            * merchant : 상점 Entity, Repository
            * payment : 결제 Entity, Repository
            * user : 사용자 Entity, Repository
    * application [비지니스 로직 어플리케이션]
        * port : [어플리케이션 계층의 인커밍 포트를 호출하여 내부접근하는 인터페이스와 아웃고잉 포트에 대한 구현을 아웃고잉 어댑터를 가지고 있는 인터페이스]
          * in : adpater.in에서 내부 비지니스 서비스를 연결 해주는 인터페이스
          * out : 비지니스 처리 시 외부 adapter.out을 연결 해주는 인터페이스
* application
    * service : 비지니스 서비스 처리 구현부
* domain : [도메인 모델 구성]
    * exchangerate : 환율관련 도메인
    * merchant : 상점 도메인
    * payment : 결제 도메인
    * user : 사용자 도메인



## 테이블 명세서
![](docs/erd.png)

### tb_user - 사용자 테이블

|      COLUMN      |     TYPE     | KEY |  NULL?   |              Default              | COMMENT |
|:----------------:|:------------:|:---:|:--------:|:---------------------------------:|:-------:|
|     user_id      | varchar(60)  | PK  | NOT NULL |                                   |  사용자ID  |
|       name       | varchar(100) |     |   NULL   |                                   |   이름    |
|    create_dtm    |   datetime   |     |   NULL   |         current_timestamp         |   등록일   |

### tb_wallet - 지갑 테이블

|    COLUMN     |     TYPE      | KEY |  NULL?   | Default | COMMENT |
|:-------------:|:-------------:|:---:|:--------:|:-------:|:-------:|
|   wallet_id   |    bigint     | PK  | NOT NULL |         |  지갑ID   |
|    user_id    |  varchar(60)  | FK  | NOT NULL |         |  사용자ID  |
| currency_code |    char(3)    | FK  | NOT NULL |         |  통화코드   |
|    balance    | decimal(18,2) |     |   NULL   |  0.00   |   잔액    |

### tb_currency - 통화 테이블

|    COLUMN     |    TYPE     | KEY |  NULL?   |      Default      | COMMENT |
|:-------------:|:-----------:|:---:|:--------:|:-----------------:|:-------:|
| currency_code |   char(3)   | PK  | NOT NULL |                   |  통화코드   |
| currency_name | varchar(30) |     |   NULL   |                   |   통화명   |


### tb_daily_exchange_rate - 일별환율 테이블

|    COLUMN     |     TYPE      | KEY |  NULL?   | Default | COMMENT |
|:-------------:|:-------------:|:---:|:--------:|:-------:|:-------:|
|   daily_id    |    bigint     | PK  | NOT NULL |         |  일별SEQ  |
| currency_code |    char(3)    | FK  | NOT NULL |         |  통화코드   |
| exchange_rate | decimal(18,2) |     |   NULL   |         |  현재환율   |
|  current_dtm  |   datetime    |     |   NULL   |         |  공시날짜   |



### tb_payment_approval - 결제승인 테이블

|     COLUMN     |     TYPE      | KEY |  NULL?   |      Default      |        COMMENT         |
|:--------------:|:-------------:|:---:|:--------:|:-----------------:|:----------------------:|
|   payment_id   |  varchar(60)  | PK  | NOT NULL |                   |          승인번호          |
|  merchant_id   |  varchar(60)  |     | NOT NULL |                   |          상점ID          |
|    user_id     |  varchar(60)  |     | NOT NULL |                   |         사용자ID          |
|  payment_dtm   |   datetime    |     |   NULL   | current_timestamp |          결제일시          |
| payment_method |  varchar(20)  |     |   NULL   |                   | 거래유형(creditCard,point) |
|    card_num    |  varchar(20)  |     |   NULL   |                   |          카드번호          |
|     status     |  varchar(20)  |     |   NULL   |                   | 승인상태(approved,failed)  |
|     amount     | decimal(18,2) |     |   NULL   |                   |          지불금액          |
|      fee       | decimal(5,2)  |     |   NULL   |                   |          수수료           |
| currency_code  |    char(3)    |     |   NULL   |                   |          통화코드          |
| exchange_rate  | decimal(18,2) |     |   NULL   |                   |          적용환율          |
| wallet_amount  | decimal(18,2) |     |   NULL   |                   |      지갑(포인트)지불금액       |
|  card_amount   | decimal(18,2) |     |   NULL   |                   |       신용카드 결제금액        |
|   won_amount   |    bigint     |     |   NULL   |                   |          원화금액          |
|  amount_total  | decimal(18,2) |     |   NULL   |                   |         총지불금액          |
| after_balance  | decimal(18,2) |     |   NULL   |                   |         거래후잔액          |



### tb_merchant - 상점 테이블

|    COLUMN     |     TYPE     | KEY |  NULL?   | Default | COMMENT |
|:-------------:|:------------:|:---:|:--------:|:-------:|:-------:|
|  merchant_id  | varchar(60)  | PK  | NOT NULL |         |  상점ID   |
| merchant_name | varchar(200) |     |   NULL   |         |   상점명   |
