# Simple Payment Service

포인트를 이용한 간단한 결제 승인 시스템

## 디렉토리 구조

---
핵사고날 아키텍처의 Port And Adapter 패턴을 이용하여 구성

![핵사고날 아키텍처](docs/hexagonal.jpg)

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


## 프로세스

---
### 1. 잔액조회

![잔액조회](docs/잔액조회.png)

1. adapter.in.web 패키지의 매핑된 PaymentQueryController.getBalance() 진입
2. port-in을 거치지 않고 거치지 않고 port-out에 바로접근
   1. 사용자 조회 : `getUserInfoPort.existUser(userId)`
      1. 사용자가 아닐경우 `존재하지 사용자 입니다.` 리턴
   2. 지갑 조회 : `getWalletInfoPort.getMyWalletInfo(userId)` 
      1. 지갑이 없을경우 `지갑이 존재하지 않습니다.` 리턴
3. `WalletView.toBuild(wallets)` 에서 데이터 변환 후 `WalletView` 데이터 리턴

### 2. 결제 예상 금액 결과조회

![결제예상결과조회](docs/결제예상결과조회.png)

1. adapter.in.web 패키지의 매핑된 PaymentQueryController.paymentEstimate() 진입
2. port-in을 거치지 않고 거치지 않고 port-out에 바로접근
    1. 상점 조회 : `getMerchantPort.checkMerchantById(request.merchantId())`
       1. 상점이 아닐경우 `존재하지 않는 상점 정보입니다.` 리턴
3. 결제 예상금액 계산 : `PaymentEstimateCommand`를 생성하여 imcomping 포트 `paymentEstimateUseCase.calculatePaymentEstimate(command)`를 수행
   1. 실제 구현부는 `PaymentService.class` 이며 `calculatePaymentEstimate` 메서드에서 수수료와 결제 예상금액을 계산
4. `PaymentEstimateResponse.toBuild(paymentEstimate)` 에서 데이터 변환 후 `PaymentEstimateResponse` 데이터 리턴


### 3. 결제승인요청 

![결제승인요청](docs/결제승인요청.png)

1. adapter.in.web 패키지의 매핑된 PaymentApprovalController.paymentApproval() 진입
2. port-in을 거치지 않고 거치지 않고 port-out에 바로접근 
   1. 사용자 조회 : `getUserInfoPort.existUser(userId)`
      1. 사용자가 아닐경우 `존재하지 사용자 입니다.` 리턴
   2. 상점 조회 : `getMerchantPort.checkMerchantById(request.merchantId())`
      1. 상점이 아닐경우 `존재하지 않는 상점 정보입니다.` 리턴
3. 결제 예상금액 계산 : `PaymentApprovalCommand`를 생성하여 imcomping 포트 `paymentApprovalUseCase.paymentApprovalRequest(command)`를 수행
    1. 실제 구현부는 `PaymentApprovalService.class` 이며 `paymentApprovalRequest` 에서 지갑조회, 고시환율조회를 조회하여 결제정보 데이터 계산 모델 생성
    2. 만약 `paymentMethod`가 `point`일 경우 `checkInsufficientBalanceByPayMethodPoint(calculatePayment.paymentMethod(), calculatePayment.cardAmount())` 메서드를 수행하여 잔액이 부족할 경우 `잔액이 부족합니다.` 메시지 리턴;
    3. 결제정보계산 모델을 가지고 `PaymentApproval` 도메인 생성
    4. `paymentMethod`가 `creditCard`일 경우 포인트 잔액이 부족하여 카드결제를 수해야하는 경우 `sendCreditCardApproval(command.paymentDetail(), paymentApproval)`를 수행
    5. `savePaymentApproval(paymentApproval)`에서 결제 승인 정보 저장
    6. 만약 정상적으로 결제승인이 되면 `withdrawalBalance(calculatePayment)`를 수행하여 포인트 차감
4. `PaymentAppovalResponse.toBuild(approval)` 에서 데이터 변환 후 `PaymentApprovalResponse` 데이터 리턴

#### 고려사항
결제 승인요청시 국가별 승인요청 시간을 기준으로 저장하기 위해 `HttpServletRequest`에서 `ZoneId`를 가져옴
지갑 조회 후 차감 시 Pessimistic Write Lock`@Lock(LockModeType.PESSIMISTIC_WRITE)` 을 추가하여 동시성 보장


## 테이블 명세서

---
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

|     COLUMN     |     TYPE      | KEY |  NULL?   | Default |           COMMENT            |
|:--------------:|:-------------:|:---:|:--------:|:-------:|:----------------------------:|
|   payment_id   |  varchar(60)  | PK  | NOT NULL |         |             승인번호             |
|  merchant_id   |  varchar(60)  |     | NOT NULL |         |             상점ID             |
|    user_id     |  varchar(60)  |     | NOT NULL |         |            사용자ID             |
|  payment_dtm   |   datetime    |     |   NULL   |         |             결제일시             |
| payment_method |  varchar(20)  |     |   NULL   |         |    거래유형(creditCard,point)    |
|    card_num    |  varchar(20)  |     |   NULL   |         |             카드번호             |
|     status     |  varchar(20)  |     |   NULL   |         | 승인상태request,approved,failed) |
|     amount     | decimal(18,2) |     |   NULL   |         |             지불금액             |
|      fee       | decimal(5,2)  |     |   NULL   |         |             수수료              |
| currency_code  |    char(3)    |     |   NULL   |         |             통화코드             |
| exchange_rate  | decimal(18,2) |     |   NULL   |         |             적용환율             |
| wallet_amount  | decimal(18,2) |     |   NULL   |         |         지갑(포인트)지불금액          |
|  card_amount   | decimal(18,2) |     |   NULL   |         |          신용카드 결제금액           |
|   won_amount   |    bigint     |     |   NULL   |         |             원화금액             |
|  amount_total  | decimal(18,2) |     |   NULL   |         |            총지불금액             |
| after_balance  | decimal(18,2) |     |   NULL   |         |            거래후잔액             |



### tb_merchant - 상점 테이블

|    COLUMN     |     TYPE     | KEY |  NULL?   | Default | COMMENT |
|:-------------:|:------------:|:---:|:--------:|:-------:|:-------:|
|  merchant_id  | varchar(60)  | PK  | NOT NULL |         |  상점ID   |
| merchant_name | varchar(200) |     |   NULL   |         |   상점명   |


## API 명세서

---

### 통신방법

- Protocol : HTTP
- Data Format: JSON
- Encoding : UTF-8
- Port : 8080

**테스트 계정**
사용자 ID : user1234
상점 ID : merchantId1234

### Swagger

- 프로젝트 실행 후 아래 링크를 통해 API 명세를 확인할 수 있다.
> http://localhost:8080/api-docs


### 1. Error Response Data Format

#### 1-1. Error Response 공통 데이터 포멧

| Parameter | Description |  
|:---------:|:------------|
|   code    | 서비스 코드      |
|  message  | 메시지         |
|   data    | 오류 데이터      |


#### 1-2. Error data 필드
| Parameter | Description |  
|:---------:|:------------|
|   field   | 파라미터        |
|   value   | 요청 값        |
|  reason   | 메시지         |


#### 1-4. Example

- Http Status 400 Bad Request
```json
{
  "code": "4000",
  "message": "공백일 수 없습니다",
  "data": [
    {
      "field": "userId",
      "value": "",
      "reason": "공백일 수 없습니다"
    }
  ]
}
```


### 2. API

#### 2-1. 잔액조회

현재 보유하고 있는 지갑의 잔액을 조회

- URI : /api/payment/balance/{userId}
- Method : GET

* curl

```curl
curl -X 'GET' \
  'http://localhost:8080/api/payment/balance/user1234' \
  -H 'accept: */*'
```

**[Response]**

| Parameter | Description |
|:---------:|:------------|
|  userId   | 사용자ID       |
|  balance  | 잔액          |
| currency  | 통화코드        |


```json
[
  {
    "userId": "user1234",
    "balance": 1000.25,
    "currency": "USD"
  },
  {
    "userId": "user1234",
    "balance": 10000,
    "currency": "KRW"
  }
]
```


#### 2-2. 결제 예샹 결과 조회

결제금액에 대한 예상 결제 가격 및 결제 수수료 조회

- URI : /api/payment/estimate
- Method : POST


**[Request]**

|   Parameter    | Require | Description |  
|:--------------:|:-------:|:------------|
|     amount     |    Y    | 결제금액        |
|    currency    |    Y    | 통화코드        |
|   merchantId   |    Y    | 상점ID        |
|     userId     |    Y    | 사용자ID       |


```json
{
  "amount": 150.00,
  "currency": "USD",
  "merchantId": "merchantId1234",
  "userId": "user1234"
}
```

* curl

```curl
curl -X 'POST' \
  'http://localhost:8080/api/payment/estimate' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "amount": 150.00,
  "currency": "USD",
  "merchantId": "merchantId1234",
  "userId": "user1234"
}'
```

**[Response]**

|   Parameter    | Description |
|:--------------:|:------------|
| estimatedTotal | 예상결제금액      |
|      fees      | 수수료         |
|    currency    | 통화코드        |


```json
{
  "estimatedTotal": 154.5,
  "fees": 4.5,
  "currency": "USD"
}
```


#### 2-3. 결제 승인 요청

최종 결제 승인 요청 처리
paymentMethod type에 따른 결제 처리
creditCard : 지갑 잔액이 충분할 경우 포인트로 결제하고, 지갑 잔액이 부족할 경우 차액만큼 신용카드로 결제
point : 지갑잔액이 충분할 경우 포인트로 결제하고, 지갑 잔액이 부족할 경우 지갑 잔액 부족 메시지 결과 표시

- URI : /api/payment/approval
- Method : POST


**[Request]**

|   Parameter   | Require | Description                  |  
|:-------------:|:-------:|:-----------------------------|
|    userId     |    Y    | 사용자ID                        |
|    amount     |    Y    | 결제금액                         |
|   currency    |    Y    | 통화코드                         |
|  merchantId   |    Y    | 상점ID                         |
| paymentMethod |    Y    | 결제 Method(creditCard, point) |
| paymentDetail |    N    | 카드결제 상세                      |

**paymentDetail**

| Parameter  | Require | Description |  
|:----------:|:-------:|:------------|
| cardNumber |    Y    | 카드번호        |
| expiryDate |    Y    | 만료기간        |
|    cvv     |    Y    | cvv         |

```json
{
  "userId": "user1234",
  "amount": 150,
  "currency": "USD",
  "merchantId": "merchantId1234",
  "paymentMethod": "creditCard",
  "paymentDetail": {
    "cardNumber": "1234-5678-9012-3456",
    "expiryDate": "12/24",
    "cvv": "335"
  }
}
```

* curl

```curl
curl -X 'POST' \
  'http://localhost:8080/api/payment/approval' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "user1234",
  "amount": 150,
  "currency": "USD",
  "merchantId": "merchantId1234",
  "paymentMethod": "creditCard",
  "paymentDetail": {
    "cardNumber": "1234-5678-9012-3456",
    "expiryDate": "12/24",
    "cvv": "335"
  }
}'
```

**[Response]**

|  Parameter  | Description |
|:-----------:|:------------|
|  paymentId  | 승인ID        |
|   status    | 상태          |
| amountTotal | 결제금액        |
|  currency   | 통화코드        |
|  timestamp  | 결제 승인 일시    |


```json
{
  "paymentId": "0e885f2d7e86417ba9f1c2b2aecfb87d",
  "status": "approved",
  "amountTotal": 154.5,
  "currency": "USD",
  "timestamp": "2024-06-03T23:24:36Z"
}
```

## 빌드 결과물
---

## [Download](https://github.com/pscheol/simple-payment-server/releases/download/simple-payment-server0.0.1/simple-payment-server-0.0.1.jar)

**[실행방법]**

- `java -jar simple-payment-server-0.0.1.jar`