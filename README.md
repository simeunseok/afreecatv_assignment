# afreecatv_assignment

아프리카티비 과제입니다.

# 사용 라이브러리
- ViewModel
- Coroutine
- Retrofit2
- Glide
- Hilt

# 사용 기술
- MVVM 패턴
- DataBinding
- RecyclerView & DiffUtil
- Dbbounce, Throttle
- BindingAdapter

# 구현
### MVVM
#### Model
- Retrofit 통신으로 받아올 Response 객체에 대한 비즈니스 로직 작성
- RecyclerView에서 사용할 모델 구현

#### View
- Activity로 구현
- ViewModel에서 값의 변경을 수신하고 UI에 데이터 처리
- 데이터 갱신 및 이벤트 처리를 DataBinding을 통해 간소화

#### ViewModel
- StateFlow와 SharedFlow를 사용하여 Activity에 보여줄 데이터를 소유
- 이벤트에 따른 데이터 fetch를 수행하고 데이터 갱신

### 입력
- 앱 바에 EditText를 추가하여 검색어를 입력할 수 있다.
- 양방향 데이터바인딩을 통해 키워드를 전달
- afterTextChanged 이벤트 콜백으로 깃 주소를 가져오는 함수 호출
- Debounce를 적용하여 추가 입력이 없으면 자동으로 검색
- 검색 버튼에는 throttle을 적용하여 같은 입력이 연속해서 들어오는 것을 일정시간 막음으로써 함수 중복 실행을 방지

### 목록 확인
- RecyclerView를 통해 데이터를 보여주며, 데이터 변경이 있을 시 DiffUtil로 감지 후 UI 갱신
- Glide를 사용하여 ImageView에 이미지 전달
- BindingAdapter로 Glide 사용 간소화

### 무한 스크롤
- 화면에서 스크롤이 더이상 아래로 이동할 수 없을 때 다음 페이지 fetch 시도

### 통신
- Retrofit과 Hilt를 사용하여 HTTP 통신을 위한 보일러 플레이트 코드 제거
- runCatching을 통해 통신 오류나 반환값에 문제가 있을 때 예외처리 및 스낵바로 사용자에게 피드백
- fetch 중일때 사용자에게 시각적 피드백을 위해 프로그래스바 보여줌

### 기타
- EditText 밖 영역을 클릭하면 키보드 창을 내림

# 개발환경
- 언어 : Kotlin
- minSdkVersion : 23
- compileSDKVersion: 31
