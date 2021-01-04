# 카카오페이 보험사업 사전과제 (결제시스템)

## 목차
1. [개발환경](#1.-개발환경)
2. [URL](#2.-URL)
3. [기능정의](#3.-기능정의)
4. [테이블 설계](#4.-테이블-설계)
5. [빌드 및 실행방법](#5.-빌드-및-실행방법)
6. [API 메시지](#6.-API-메시지)
7. [테스트](#7.-테스트)
8. [문제해결 전략](#8.-문제해결-전략)
9. [마치며](#9.-마치며)


## 1. 개발환경 <a name="1.-개발환경"></a>
- STS4
- Java
- Spring Boot
  - gradle
  - swagger-ui
  - H2 Database
  - MyBatis
  - Lombok
  - jackson


## 2. URL <a name="2.-URL"></a>
  - 테스트 주소
    - 기본 URL : http://uridev.iptime.org:8080
    - swagger-ui : [http://uridev.iptime.org:8080/swagger-ui.html](http://uridev.iptime.org:8080/swagger-ui.html)
    - h2-console : [http://uridev.iptime.org:8080/h2-console](http://uridev.iptime.org:8080/h2-console)
  - 로컬 주소
    - 기본 URL : http&#58;//localhost:8080
    - swagger-ui : http&#58;//localhost:8080/swagger-ui.html
    - h2-console : http&#58;//localhost:8080/h2-console
  - H2-Console에 로그인 하려면 접속 정보를 확인해주세요.
    - JDBC URL : jdbc&#58;h2&#58;mem&#58;homework
    - User Name : sa
    - Password :


## 3. 기능정의 <a name="3.-기능정의"></a>

### 결제 승인
  - 결제 요청은 카드번호와 유효기간, cvc, 할부개월수, 결제금액, 부가가치세(선택)를 보냅니다.
  - 응답은 Json 타입으로 제공되며, 승인이 정상적으로 이루어지면 관리번호와 카드사로 전송한 전문내용(450byte)을 반환 합니다
  - 서버로 보낸 데이터가 정상적이지 않거나 오류가 있는경우 오류 메시지를 반환 합니다.
#### request
```
POST /api/payment/approved?cardNo=123456789012345&cvc=123&expiryDate=1221&monthIns=0&price=20000&vat=1000
```
#### response
```json
{
  "tradeId": "ABwfofnPicsS2wVQAX0r",
  "sendData": " 446PAYMENT   ABwfofnPicsS2wVQAX0r123456789012345     001221123     200000000001000                    2NnC0EDLwtQAnIXfGSfddpvm5ELJuaEs45DzUb53PiQ+8D6taEjp/g==                                                                                                                                                                                                                                                                                                   "
}
```

### 결제 취소
  - 취소 요청은 결제시 받은 원거래의 관리번호와 취소금액, 부가가치세(선택)를 보냅니다.
  - 응답은 Json 타입으로 제공되며, 취소가 정상적으로 이루어지면 관리번호와 카드사로 전송한 전문내용(450byte)을 반환 합니다
  - 서버로 보낸 데이터가 정상적이지 않거나 오류가 있는경우 오류 메시지를 반환 합니다.
#### request
```
POST /api/payment/cancel?price=10000&tradeId=ABwfofnPicsS2wVQAX0r&vat=500
```
#### response
```json
{
  "tradeId": "l4qiN9DTIqh45VQFwywA",
  "sendData": " 446CANCEL    l4qiN9DTIqh45VQFwywA123456789012345     001221123     100000000000500ABwfofnPicsS2wVQAX0rFBoxXpGj+n/hjqEIo6NjXB8Vc6HYizOw6jGEegDQiQE0/nJ7wgzJ/A==                                                                                                                                                                                                                                                                                                   "
}
```

### 결제 조회
  - 조회하려는 결제 승인/취소의 관리번호로 내역을 단건 및 목록으로 조회합니다,
  - 단건은 승인 또는 취소된 1건의 내용을 제공합니다.
  - 목록은 결제시 받은 원거래의 관리번호로 결제, 취소 내역을 제공합니다.
#### request
```
GET /api/payment/search?tradeId=ABwfofnPicsS2wVQAX0r
```
#### response
##### [단건]
```json
{
  "tradeId": "ABwfofnPicsS2wVQAX0r",
  "payGb": "PAYMENT",
  "cardNo": "123456******345",
  "expiryDate": "1221",
  "cvc": "123",
  "monthIns": "0",
  "price": 20000,
  "vat": 1000,
  "orgTradeId": "",
  "regDt": "2021-01-03 20:32:58",
  "status": "OK"
}
```
##### [목록]
```json
[
  {
    "tradeId": "ABwfofnPicsS2wVQAX0r",
    "payGb": "PAYMENT",
    "cardNo": "123456******345",
    "expiryDate": "1221",
    "cvc": "123",
    "monthIns": "0",
    "price": 20000,
    "vat": 1000,
    "orgTradeId": "",
    "regDt": "2021-01-03 20:32:58",
    "status": "OK"
  },
  {
    "tradeId": "l4qiN9DTIqh45VQFwywA",
    "payGb": "CANCEL",
    "cardNo": "123456******345",
    "expiryDate": "1221",
    "cvc": "123",
    "monthIns": "00",
    "price": 10000,
    "vat": 500,
    "orgTradeId": "ABwfofnPicsS2wVQAX0r",
    "regDt": "2021-01-03 20:34:27",
    "status": "OK"
  }
]
```


## 4. 테이블 설계 <a name="4.-테이블-설계"></a>
본 프로그램에서 사용하는 테이블 레이아웃은 src/main/resources/schema.sql에 위치 합니다.
(서버가 올라갈때 자동 생성 됩니다. 또한 서버 종료시 기존 등록된 데이터는 사라지게 됩니다.)

### 결제/취소 정보(tb_payment_info)
| 컬럼 | 이름 | 속성 | 키 | 비고 |
| ---- | ---- | ---- | -- | ---- |
| trade_id | 관리번호 | varchar(20) | V |  |
| pay_gb | 거래 구분 | varchar(10) |  | PAYMENT=승인, CANCEL=취소 |
| card_info | 카드정보 | varchar(300) |  | 카드번호&#124;유효기간&#124;cvc, 암호화저장 |
| month_ins | 할부개월 | varchar(2) |  | 결제=0,1~12, 취소=00 |
| price | 거래금액 | bigint |  | 원 |
| vat | 부가가치세 | bigint |  | 원 |
| org_trade_id | 원거래 관리번호 | varchar(20) |  | 결제취소시에만 저장 |
| reg_dt | 등록일자 | datetime |  | &nbsp; |

### 결제/취소가 진행중인 중인 정보(tb_payment_card)
| 컬럼 | 이름 | 속성 | 키 | 비고 |
| ---- | ---- | ---- | -- | ---- |
| pay_gb | 거래 구분 | varchar(2) | V | PAYMENT=승인, CANCEL=취소 |
| private_key | 확인 고유값 | varchar(100) | V | &nbsp; |

### 카드사 전송 데이터(tb_card_send)
| 컬럼 | 이름 | 속성 | 키 | 비고 |
| ---- | ---- | ---- | -- | ---- |
| trade_id | 관리번호 | varchar(20) | V |  |
| send_data | 전송 데이터 | varchar(450) |  | 450바이트, 헤더=34, 바디=416(공백=47)|
| status | 전송결과 | varchar(2) |  | OK=성공|
| reg_dt | 등록일자 | datetime |  | &nbsp; |


## 5. 빌드 및 실행방법 <a name="5.-빌드-및-실행방법"></a>
  - **STS4 Import**
    1. Package Explorer > Import
	2. Select > Gradle > Existing Gradle Project > Next > Next
	3. Import Gradle Project > Project root directory > 프로젝트 폴더 선택 > Finish
  - **STS4 빌드**
    1. 프로젝트 선택
    2. Gradle > Refresh Gradle Project
    3. 프로젝트 빌드
        - (프로젝트 인코딩 타입이 UTF-8 인지 확인해 주세요.)
  - **STS4 실행**
    1. /kakaopay/src/main/java/com/kakao/card 패키지에 위치한 KakaopayApplication.java
    2. Run As > Spring Boot App (or Java Application)
  - **Command 실행**
    1. cmd를 이용하여 프로젝트 폴더 이동
	2. gradlew bootRun 명령어 실행
	    - ex ) D:\sts4\workspace\kakaopay>gradlew bootRun


## 6. API 메시지 <a name="6.-API-메시지"></a>
### 공통 메시지
| 구분 | 항목 | 코드 | 메시지 |
| ---- | ---- | ---- | ------ |
| 공통 | SUCCESS | 0 | 정상 |
|  | FAIL | 1 | 오류가 발생 하였습니다. |
| 파라메터 | PARAMETER_ERROR | 20 | 파라메터 오류가 발생하였습니다. |
| 조회 | NOT_FOUND_TRADE_ID | 30 | 존재하지 않는 관리번호 입니다. |
| 결제/취소 진행중 | LOCK_APPROVED | 40 | 결제가 진행중인 카드정보 입니다. |
|  | LOCK_CANCEL | 41 | 결제취소가 진행중인 거래번호 입니다. |
| 결제취소 | CANCEL_CARD | 50 | 결제취소가 진행중인 카드정보 입니다. |
|  | CANCEL_PRICE | 51 | 입력하신 금액으로는 취소가 불가능 합니다. |
|  | CANCEL_VAT | 52 | 취소가능한 부가가치세 금액이 부족합니다. |
|  | CANCEL_MORE_VAT | 53 | 결제금액 취소 후 부가가치세가 남아있어 취소가 불가능합니다. |

### 파라메터 오류 메시지 정의
| 이름 | 파라메터 | 체크내용 | 메시지 |
| ---- | -------- | -------- | ------ |
| 관리번호 | tradeId | NotNull | 관리번호를 입력해주세요. |
|  |  | Size(20) | 관리번호를 20자리로 입력해주세요. |
| 카드번호 | cardNo | NotNull | 카드번호를 입력해주세요. |
|  |  | Pattern | 카드번호는 숫자로 입력해주세요. |
|  |  | Size(10~16) | 카드번호는 10~16자리로 입력해주세요. |
| 유효기간 | expiryDate | NotNull | 유효기간을 입력해주세요. |
|  |  | ExpireDateFormat | 카드의 유효기간은 MMyy로 입력바랍니다. |
|  |  | ExpireDatePeriod | 유효기간이 지난 카드는 사용이 불가능 합니다. |
| cvc | cvc | NotNull	 | cvc번호를 입력해주세요. |
|  |  | Pattern | cvc번호는 숫자로 입력해주세요. |
|  |  | Size(3) | cvc번호를 3자리로 입력해주세요. |
| 할부개월 | monthIns | NotNull | 할부개월를 입력해주세요. |
|  |  | Min(0) | 할부개월은 0~12로 입력해주세요. |
|  |  | Max(12) | 할부개월은 0~12로 입력해주세요. |
| 결제/취소 금액 | price | NotNull | 결제/취소 금액을 입력해주세요. |
|  |  | Min(100) | 결제/취소 금액은 100원 이상으로 입력이 가능합니다. |
|  |  | Max(1000000000) | 결제/취소 금액은 1000000000원 이하로 입력이 가능합니다. |
| 부가가치세 | vat | vat > price | 부가가치세는 결제금액보다 작게 입력해주세요. |


## 7. 테스트 <a name="7.-테스트"></a>
  - 공통위치 : /kakaopay/src/test/java/com/kakao/card
  - 프로그램 설명
    - TestCommon.java : 공통 파일
      - 테스트를 위한 junit 설정 및 결제, 결제취소, 조회 등 url을 호출하는 기능이 개발된 공통 파일 입니다.
      - 테스트 클래스에서 공통 파일을 상속받아 주요 로직을 수행하는데 사용됩니다.
    - PaymentTest.java : 결제, 결제취소 테스트
	  - 결제, 결제취소를 각각 성공, 실패 테스트 케이스를 확인 할 수 있습니다.
    - SearchTest.java : 조회 테스트
	  - 결제승인 후 조회, 결제취소 후 조회 기능을 제공합니다.
    - IntegratedCaseTest.java : Test Case 테스트
      - 제공해주신 테스트 케이스 3가지를 확인 할 수 있습니다.
	- MultiThreadTest.java : Multi Thread 환경 테스트
	  - 동시 결제승인, 동시 결제취소 기능에 대해서 테스트 할 수 있습니다.


## 8. 문제해결 전략 <a name="8.-문제해결-전략"></a>
  - 요구사항에 맞는 응답 API 설계.
  - 결제 승인/취소 데이터를 저장하고, 조회하기 위한 테이블 설계.
  - Multi Thread 환경에서 동시에 결제, 전체/부분 취소가 불가능 하도록 테이블 설계 및 로직 개발.
    - 결제 승인/취소 요청이 들어와 로직을 수행중인 데이터의 키값이 있는지 DB를 조회합니다.
    - 이 메서드는 synchronized 이며, 저장된 키값이 없을경우 키값을 insert 하고 로직을 수행할수있도록 true값을 리턴 합니다.
    - 결제 승인/취소 로직이 종료되면 저장된 키값을 delete하여 다음 처리가 가능하도록 합니다.


## 9. 마치며 <a name="9.-마치며"></a>
그동안 Spring MVC로 프로젝트 셋팅과 개발, 유지보수를 해봤고, Spring Boot를 이용한 프로젝트를 많이 접해보지 않습니다.

이번 과제를 진행하면서 처음으로 스프링 부트 프로젝트를 생성하여 개발하게 되었습니다.

여기저기 검색을 해가며 셋팅중인 프로젝트를 삭제하고 다시 처음부터 시작하는 것을 반복했고 최종적으로 이 아이가 만들어지게 되었습니다.

부족한게 많지만 우리 아이 이쁘게 봐주세요!