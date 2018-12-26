package java8.chapter5;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by choi on 18/12/2018.
 */
public class Chapter5 {
    public static void main(String[] args) {
//        mapFunction();
        flatMapFunction();
        reduceFunction();

        List<Apple> apples = new ArrayList<>();
        apples.add(new Apple("green", 150));
        apples.add(new Apple("green", 120));
        apples.add(new Apple("red", 120));
        apples.add(new Apple("green", 140));
        apples.add(new Apple("blue", 100));
        apples.add(new Apple("red", 125));

        anyMatchFunction(apples);
        findXXX(apples);

        allTotal(apples);
    }

    // 고유의 문자를 찾지 못함
    private static void mapFunction() {
        String[] words = {"Goodbye", "World"};
        List<Stream<String>> list = Arrays.stream(words)
                .map(word -> word.split(""))
                .map(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        list.forEach(stringStream -> stringStream.forEach(System.out::println));
    }

    private static void flatMapFunction() {
        String[] words = {"Goodbye", "World"};
        List<String> list = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(list);
    }

    private static void reduceFunction() {
        int[] nums = {1, 1, 2, 4, 5, 1};

        int sum3 = 0;
        for (int x : nums) {
            sum3 += x;
        }
        System.out.println(sum3);

        int sum1 = Arrays.stream(nums).sum();
        System.out.println(sum1);
        //reduce의 첫번째 인자로 초깃값을 추가할 수 있다.
        int sum2 = Arrays.stream(nums).reduce(1, (a, b) -> a + b);
        System.out.println(sum2);
        int sum4 = Arrays.stream(nums).reduce((a, b) -> a * b).getAsInt();
        System.out.println(sum4);

        OptionalInt sum5 = Arrays.stream(nums).reduce((a, b) -> a * b);
        System.out.println(sum5.orElse(1));
    }

    private static void anyMatchFunction(List<Apple> apples) {
        if (apples.stream().anyMatch(a -> "green".equals(a.getColor()))) {
            System.out.println("any match!");
            return ;
        }
        System.out.println("not any match!");
    }

    private static void findXXX(List<Apple> apples) {
        apples.parallelStream()
                .filter(a -> "green".equals(a.getColor()))
                .findAny()
                .ifPresent(a -> System.out.println(a.toString()));

        apples.stream()
                .filter(a -> "green".equals(a.getColor()))
                .findFirst()
                .ifPresent(a -> System.out.println(a.toString()));
    }

    private static void allTotal(List<Apple> apples) {
        apples.stream()
                .map(Apple::getWeight)
                .reduce((a1, a2) -> a1 + a2)
                .ifPresent(System.out::println);

        apples.parallelStream()
                .map(Apple::getWeight)
                .reduce((a1, a2) -> a1 + a2)
                .ifPresent(System.out::println);
    }
}

@Data
@AllArgsConstructor
class Apple {
    private String color;
    private int weight;
}