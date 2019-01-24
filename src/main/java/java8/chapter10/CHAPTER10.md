## Optional
* 자바에서 null은 NullPointerException을 발생시키는 원인이다. 그 대안으로 자바 8에서 Optional객체가 나왔다.
* 기존에는 NPE를 피하기 위해 `if (obj != null)` 처럼 null 확인 코드를 모든 부분에 추가해야한다.
* 문제점
    1. 가독성 저하 : if문이 추가됨으로써 블록(들여쓰기) 수준이 증가한다. 블록이 많아진다는 것은 가독성이 저하되는 것이다.
    2. 의미 없음 : null 레퍼런스는 의미가 없는 로직이다.
    3. 자바 철학에 위배 : 포인터가 없는 자바에서 예외적으로 null 레퍼런스(포인터)만 존재하는 서로 반대되는 개념이 공존하게 된다.
* 해결방법
    * 각 언어마다 null과 같은 상황을 해결하는 방법이 있다. 자바 8의 Optional은 `선택형 값(optional value)` 개념의 영향을 받았다. 참고로 스칼라의 Option[T]도 선택형 값을 저장하는 구조이다.
    * 선택형 값이란 ? `값을 갖거나 아무 값도 갖지 않을 수 있는 구조`
    
#### java.util.Optional\<T>
* __선택형 값을 캡슐화하는 클래스__
* Optional.empty()
    * 싱글톤 인스턴스를 반환하는 정적 팩토리 메소드
    * VS null : null은 참조시 NPE가 발생하지만, Optional.empty()는 싱글톤 인스턴스(, 객체)를 반환한다. 그렇기 때문에 예외가 발생하지 않는다.
* Optional 객체로 감싸는 것 자체가 값이 있을 수도 없을 수도 있음을 의미
    * 이로써 의미를 더욱 정확하게 전달할 수 있다

#### Optional 메소드
* map은 stream의 map과 동일하다
    * Chapter10.java 처럼 Optional 객체 내부에 또 다른 Optional이 있는 경우 유의하여 사용해야 한다.
    * 그 경우 flatMap을 사용
* flatMap은 함수를 인수로 받아서 다른 스트림을 반환하는 메소드

#### 주의할 점
* 값이 반드시 있어야 하는 모델에 Optional을 사용하게 되면, 값이 없는 경우를 근본적으로 해결하는 것이 아닌 문제나 버그를 숨기는 꼴이 된다.
    * 선택형 값인지 여부를 구별여 사용하자