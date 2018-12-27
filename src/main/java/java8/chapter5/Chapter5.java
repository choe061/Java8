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

        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        solution1(transactions);
        solution2(transactions);
        solution3(transactions);
        solution5(transactions);
        solution6(transactions);
        solution7(transactions);
        solution8(transactions);
    }

    //2011년의 트랜잭션을 오름차순 정렬
    private static void solution1(List<Transaction> transactions) {
        List<Transaction> sortedTransactions = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        System.out.println(sortedTransactions);
    }

    //도시를 중복없이 나열
    private static void solution2(List<Transaction> transactions) {
        List<String> cities = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(cities);

        Set<String> cities2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toSet());

        System.out.println(cities2);
    }

    //Cambridge에서 근무하는 모든 거래자를 찾아 이름순으로 나열
    private static void solution3(List<Transaction> transactions) {
        List<String> names = transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        System.out.println(names);

        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        System.out.println(traders);
    }

    //모든 거래자의 이름을 알파벳순으로 정렬
    private static void solution4(List<Transaction> transactions) {

    }

    //밀라노에 거래자가 있는가
    private static void solution5(List<Transaction> transactions) {
        if (transactions.stream().anyMatch(transaction -> "Milan".equals(transaction.getTrader().getCity()))) {
            System.out.println("Yes");
            return ;
        }
        System.out.println("No");
    }

    //케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력
    private static void solution6(List<Transaction> transactions) {
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);
    }

    //트랜잭션의 최대값
    private static void solution7(List<Transaction> transactions) {
        transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max)
                .ifPresent(System.out::println);
    }

    //트랜잭션의 최소값
    private static void solution8(List<Transaction> transactions) {
        transactions.stream()
                .map(Transaction::getValue)
                .reduce((t1, t2) -> t1 > t2 ? t2 : t1)
                .ifPresent(System.out::println);
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
        int[] nums = {0, 1, 2, 4, 5, 1};

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

        nums = new int[0];
        OptionalInt sum5 = Arrays.stream(nums).reduce((a, b) -> a * b);
        System.out.println("reduce : " + sum5.orElse(1));
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

@Data
@AllArgsConstructor
class Trader {
    private final String name;
    private final String city;
}

@Data
@AllArgsConstructor
class Transaction {
    private final Trader trader;
    private final int year;
    private final int value;
}