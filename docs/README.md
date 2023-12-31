## 📝 전체 흐름
> 사용자의 올바르지 않은 입력에 의한 예외 발생시 예외메세지를 출력하고 다시 입력을 요청합니다.

1. 로또 구입 금액 입력 안내를 출력합니다.
2. 사용자가 로또 구입 금액을 입력합니다.
   - 입력한 로또 구입 금액이 유효하지 않으면 예외를 발생합니다.
3. 로또 구입 금액에 맞는 로또 개수를 계산합니다.
4. 계산한 개수 만큼의 로또를 발행합니다.
5. 구매한 개수와 발행한 로또 번호를 출력합니다.
6. 당첨 번호 입력 안내를 출력합니다.
7. 사용자에게 당첨 번호 6자리를 입력받습니다.
   - 당첨 번호가 유효하지 않으면 예외를 발생합니다.
8. 당첨 보너스 번호 입력 안내를 출력합니다.
9. 사용자에게 당첨 보너스 번호 1자리를 입력받습니다.
   - 보너스 번호가 유효하지 않으면 예외를 발생합니다.
10. 당첨 로또 번호와 사용자의 로또 번호를 비교하여 몇 등인지 계산합니다.
11. 모든 로또 번호를 비교한 후 통계를 만듭니다.
12. 당첨 통계 안내를 출력합니다.
13. 통계를 출력합니다.


## ✏️ 구현할 기능 목록
> 핵심적인 기능만을 추출해서 기능 목록에 명세하였습니다. 
> <br>
> 클래스간 소통을 위한 public 메서드를 중심으로 기능을 먼저 작성하고, 
> 세부적인 요구사항을 덧붙였습니다.   
### View
****
`UI(입출력)에 해당하는 기능만을 하는 컴포넌트입니다.`

**Input**
- 숫자 입력 기능
- 여러 개의 숫자 입력 기능
  - 숫자 포맷에 맞지 않을시 예외 발생

**Output**
- 안내 메세지 출력 기능
- 에러 메세지 출력 기능
- DTO 출력 기능  

### Controller
****
`사용자의 입력을 받아 Service로 전달하여 로직을 수행하도록 하고 
반환된 결과를 출력할 수 있도록 View로 전달하는 중개자 역할을 합니다.`  

- 로또 게임 실행 기능
  - 로또 구매 기능
  - 당첨 번호 색출 기능
  - 예외 처리 기능


### Service
****
`도메인 로직을 적절히 이용하여 요구사항의 핵심 기능을 수행하는 역할을 합니다.`

- 로또 구매 및 발행 기능
  - 랜덤 숫자 생성 기능
  - 로또 생성 기능
- 당첨 번호 생성 기능 (보너스 번호 제외)
- 당첨 번호 생성 기능 (보너스 번호 포함)
- 당첨 통계 계산 기능
  - 당첨 등수, 걔수 계산 기능
  - 수익률 계산 기능

### Model
****
`애플리케이션에 필요한 데이터를 가지고 있으며 가지고 있는 맞는 순수 로직을 가집니다.
서로 다른 모델ㅎ 데이터에 의존하는 로직을 가지지 않습니다.`

**지불 금액(Payment)**
- 생성자
  - 최소 가격 검증 기능 (1개 가격 이상)
  - 최대 가격 검증 기능 (1,000,000개 가격 이하)
  - 구매 가격 단위 검증 기능
- 로또 구매 개수 계산 기능

**로또(Lotto)**
- 생성자
  - 로또 번호 개수 검증 기능 (6개)
  - 로또 번호 범위 검증 기능 (1~45 범위 안의 숫자)
  - 로또 번호 중복 검증 기능
- 다른 로또 번호와 비교하여 몇 개가 일치하는지 계산하는 기능

**당첨 로또(WinningLotto)**
- 생성자
  - 로또 숫자와 보너스 숫자와의 중복 검증 기능
- 로또와 비교하여 몇 등인지 계산하는 기능

**로또 순위(Rank)**
- 몇 개가 일치 하였는지에 따라 순위를 결정하는 기능
- 당첨인지 아닌지 확인하는 기능

**추첨 결과(LottoResult)**
- 생성자
  - 초기 당첨 개수 설정 기능
- 당첨 횟수를 추가하는 기능
- 수익률을 계산하는 기능