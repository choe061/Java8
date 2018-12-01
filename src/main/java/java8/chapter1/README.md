## Java 8
#### Java 8의 핵심
* Stream API
* 메소드에 코드를 전달하는 기법
* interface의 default 메소드

#### Stream API
* parallelStream
    * 병렬 연산 지원
    * 비용이 비싼 synchronized를 사용하지 않아도 병렬 연산을 최적화
        > 멀티코어 CPU의 각 코어는 별도의 캐시를 가진다. synchronized(락)을 사용하면 캐시에 대한 동기화가 이루어져야 하므로 속도가 느려진다. 하지만 자바 8에서는 멀티코어 병렬성이 강화되었다.
* Java 8의 Stream은 최적의 저수준 실행 방법을 선택하는 방식으로 동작

#### 메소드에 코드를 전달하는 기법
* Lambda(람다 표현식)
    * 자바 8 이전에는 익명 클래스를 통해 함수의 동작을 전달하는 기능을 유사하게 흉내내었지만, 자바 8에서 람다 표현식을 통해 가독성을 높였다.
    * Stream API와 람다 표현식을 사용하여 Java 8에서도 함수형 프로그래밍의 개념을 도입할 수 있다.
* Method Reference

#### default method
* interface에 디폴트 메소드를 정의하여 해당 인터페이스를 상속 구현 시 디폴트 메소드를 사용할 수 있음.

#### 스트림 처리
* Stream Processing
    * 리눅스나 유닉스에서 여러 명령어를 파이프(|)로 연결시켜 하나의 파이프라인을 만들 수 있다. 동일하게 스트림은 여러 명령어를 하나의 파이프라인으로 만드는 것이다.
* 동작 파라미터화
    * 동작 파라미터화는 쉽게 말하면 메소드에 코드를 전달하는 것이다.
    * 메소드를 다른 메소드의 인수로 넘겨주는 기능 (`일급 객체` 특징 중 하나와 관련)
    * ex) 아래와 같이 동작(메소드)를 또 다른 메소드의 파라미터로 넘겨주는 방식
    ```
    list.stream()
        .sorted(Comparator.comparing(SimpleData::getId))
        .collect(Collectors.toList());
    ```
    * 람다
        * 자바 8에서는 메소드를 일급 값으로 취급할 뿐 아니라 람다를 포함하여 함수도 값으로 취급할 수 있다.
    * Predicate
        * 수학에서는 인수를 받아 true나 false를 반환하는 함수를 predicate라고 한다.
        * Java 8의 Function<ParameterType, ReturnType>을 사용하여 Function<T, Boolean> 으로 구현할 수 있지만, 리턴 타입이 Boolean인 경우 predicate를 사용하는 것이 더 표준적인 방식이다.

#### 멀티코어
* 최근 CPU는 대부분 멀티코어로 구성되어 있다. 하지만 그 멀티코어를 활용하기 위해 멀티스레딩을 통해 병렬성을 구현하는 것은 쉽지 않다.
* 자바 8의 Stream API로 멀티코어 활용의 어려움을 해결하고, 기존의 컬렉션에서 데이터를 처리할 때 반복되는 패턴(데이터베이스 질의와 비슷한)을 서버 애플리케이션 코드에서 처리할 수 있도록 기능을 지원해준다.
    * filtering
    * extracting
    * grouping