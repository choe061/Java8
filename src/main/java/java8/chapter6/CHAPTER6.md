## 데이터 수집
#### 요약 연산
* IntSummaryStatistics (LongSummaryStatistics, DoubleSummaryStatistics)
    * IntSummaryStatistics 클래스는 모든 정보를 수집
    * {count, sum, min, max, average}
    ```
    IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    ```
    
#### 문자열 연결
* Collectors.joining() 팩토리 메소드
    * 스트림의 각 객체에 ___toString()___ 를 호출하여 추출한 문자열을 하나의 문자열로 연결
        * toString 메소드 구현해도 map() 없으면 안되는???
    * 내부적으로 StringBuilder 사용
* 외부 반복 시 번거로움을 해결해줌
    * 문자열을 join할 때 구분자를 넣어주는 경우 마지막 인덱스를 확인하여 붙이지 않는 로직을 작성해야한다. 하지만 Collectors.joining(", ")을 사용하면 간단하게 해결해준다.

#### 범용 리듀싱 요약 연산
* Collectors.reducing() 팩토리 메소드로 직접 요약 연산 기능을 정의할 수도 있다.
    * counting(), minBy(), maxBy()
    
#### 그룹화(분류 함수, classification function)
* Collectors.groupingBy(Function)
    * Collectors.groupingBy(Function, Collectors.toList())
* 간단하게 하나의 기준으로 그룹화할 수 있지만, 두 가지 기준으로 동시에 그룹화할 수도 있다.
    * `다수준 그룹화`
    * Collectors.groupingBy(기준1, Collectors.groupingBy(기준2))
        * 그룹화 결과 : Map<기준1, Map<기준2, List<데이터>>>
* 결과를 다른 형식으로 변경하기
    * Collectors.collectingAndThen()
        * 첫번째 인자로 Collectors 인터페이스의 함수를 받음
        * 그룹핑 결과에 또 다시 서브? 연산을 할 수 있음
* 결과를 다른 형식으로 누적하기
    * Collectors.mapping()
        * 첫번째 인자로 Function<T, R> 함수를 받음
        * groupingBy의 결과를 T(인자)로 넣어 R(다른 형식)으로 누적시킬 수 있음
        
#### 분할(파티셔닝)
* 그룹화는 key가 두 개 이상이지만, 분할은 Key가 Boolean으로 두 개의 그룹으로 분류된다.
* Collectors.partitioningBy(Predicate)
* Collectors.partitioningBy(Predicate, Collectors.groupingBy())

#### Collector 인터페이스
* 컬렉터 인터페이스의 메소드
    * T : 수집될 스트림의 제네릭
    * A : 누적자, 중간 결과를 누적하는 객체의 형식 (리스트, 맵 등등)
    * R : 수집 연산 결과 객체의 형식
    1. Supplier\<A> supplier()
        * 함수형 인터페이스 Supplier는 새로운 객체를 생성하여 리턴한다.
        * supplier 메소드도 무인자이며 빈 결과로 이루어진 컨테이너를 생성한다.
    2. BiConsumer<A, T> accumulator() 
        * 함수형 인터페이스 Consumer는 인자를 받아 수행하고 void를 리턴한다.
        * 인자가 A, T 두 개이고 결과 컨테이너에 요소를 추가한다.
            * A : T = list : item
            * A.add(T)
    3. Function<A, R> finisher()
        * 최종 변환 값을 결과 컨테이너로 적용
        * A : R = list : list
    4. BinaryOperator\<A> combiner()
        * 두 결과 컨테이너를 병합
        * 나누어져 병렬로 수행한 결과를 병합하는데 사용한다.
    5. Set\<Characteristics> characteristics()
        * Characteristics는 스트림을 병렬로 리듀스할지, 어떤 최적화를 선택할지 제공한다.
* 리턴 타입을 확인하면 Chapter3의 함수형 인터페이스를 사용하는 메소드가 있다. 각 함수형 인터페이스의 용도에 맞게 Collector 인터페이스의 메소드도 같은 기능을 하는 것 같다.