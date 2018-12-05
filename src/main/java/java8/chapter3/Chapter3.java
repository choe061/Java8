package java8.chapter3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by choi on 02/12/2018 10:22 PM.
 */
public class Chapter3 {
    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();
        appleList.add(new Apple("green", 150));
        appleList.add(new Apple("red", 120));
        appleList.add(new Apple("green", 140));
        appleList.add(new Apple("blue", 100));
        appleList.add(new Apple("red", 125));

        System.out.println(sortApple(appleList));
        System.out.println(sortAppleByLambda(appleList));
        System.out.println(sortAppleByLambda2(appleList));
        System.out.println(sortAppleByMethodReference(appleList));

        Supplier<Apple> appleSupplier = () -> new Apple();
        Apple apple1 = appleSupplier.get();
        System.out.println(apple1.toString());

        Supplier<Apple> appleSupplier2 = Apple::new;
        Apple apple2 = appleSupplier2.get();
        System.out.println(apple2.toString());

        Function<Integer, Apple> appleFunction = Apple::new;
        Apple apple = appleFunction.apply(10);
        System.out.println(apple.toString());

        BiFunction<String, Integer, Apple> appleFunction2 = Apple::new;
        Apple apple3 = appleFunction2.apply("red", 100);
        System.out.println(apple3.toString());

        System.out.println(extractFruit("apple", 33));
    }

    private static List<Apple> sortApple(List<Apple> appleList) {

        appleList.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        return appleList;
    }

    private static List<Apple> sortAppleByLambda(List<Apple> appleList) {

        appleList.sort((o1, o2) -> o1.getWeight() - o2.getWeight());

        return appleList;
    }

    private static List<Apple> sortAppleByLambda2(List<Apple> appleList) {

        appleList.sort((o1, o2) -> {
            return o1.getWeight() - o2.getWeight();
        });

        return appleList;
    }

    private static List<Apple> sortAppleByMethodReference(List<Apple> appleList) {

        appleList.sort(Comparator.comparingInt(Apple::getWeight));

        return appleList;
    }

    // void를 리턴하는 람다, 대표적으로 쓰레드의 run()
    private static LambdaTest lambdaTest() {
        return () -> {};
    }

    /**
     * 하지만 현실적으로 생성자 시그니처와 일치하는 함수형 인터페이스를 찾기 힘들다. 그때는 직접 함수형 인터페이스를 만들면된다.
     */
    private static Map<String, Function<Integer, Fruit>> getFruitMap() {
        Map<String, Function<Integer, Fruit>> fruitMap = new HashMap<>();
        fruitMap.put("apple", Apple::new);
        fruitMap.put("orange", Orange::new);
        return fruitMap;
    }

    private static Fruit extractFruit(String name, int weight) {
        return getFruitMap().get(name).apply(weight);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Apple implements Fruit {
    private String color;
    private int weight;

    public Apple(int weight) {
        this.weight = weight;
    }
}

@Data
class Orange implements Fruit {
    private String color;
    private int weight;

    public Orange(int weight) {
        this.weight = weight;
    }
}

interface Fruit {

}

@FunctionalInterface
interface LambdaTest {
    void method();
}

/**
 *
 * @param <T> 파라미터1
 * @param <U> 파라미터2
 * @param <V> 파라미터3
 * @param <R> 리턴 타입
 */
@FunctionalInterface
interface FruitFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}