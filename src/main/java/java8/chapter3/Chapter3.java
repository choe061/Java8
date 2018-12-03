package java8.chapter3;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
}

@Data
@AllArgsConstructor
class Apple {
    private String color;
    private int weight;
}

@FunctionalInterface
interface LambdaTest {
    void method();
}