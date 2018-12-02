package java8.chapter2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choi on 01/12/2018 3:59 PM.
 */
public class Chapter2 {
    public static void main(String[] args) {
        List<Apple> appleList = new ArrayList<>();
        appleList.add(new Apple("green", 150));
        appleList.add(new Apple("red", 120));
        appleList.add(new Apple("green", 140));
        appleList.add(new Apple("blue", 100));
        appleList.add(new Apple("red", 125));

        System.out.println(filterGreenApples(appleList));

        System.out.println(filterApplesByColor(appleList, "green"));
        System.out.println(filterApplesByColor(appleList, "red"));

        System.out.println(filterApplesByWeight(appleList, 120));
        System.out.println(filterApplesByWeight(appleList, 130));

        System.out.println(filterApples(appleList, "green", 120, true));

        System.out.println(filterApples(appleList, new AppleGreenColorPredicate()));
        System.out.println(filterApples(appleList, new AppleHeavyWeightPredicate()));

        System.out.println(filterApples(appleList, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "green".equals(apple.getColor());
            }
        }));

        System.out.println(filterApples(appleList, apple -> "green".equals(apple.getColor())));

        System.out.println(filter(appleList, apple -> "green".equals(apple.getColor())));
    }

    private static List<Apple> filterGreenApples(List<Apple> appleList) {
        List<Apple> resultAppleList = new ArrayList<>();

        for (Apple apple : appleList) {
            if ("green".equals(apple.getColor())) {
                resultAppleList.add(apple);
            }
        }

        return resultAppleList;
    }

    private static List<Apple> filterApplesByColor(List<Apple> appleList, String color) {
        List<Apple> resultAppleList = new ArrayList<>();

        for (Apple apple : appleList) {
            if (color.equals(apple.getColor())) {
                resultAppleList.add(apple);
            }
        }

        return resultAppleList;
    }

    private static List<Apple> filterApplesByWeight(List<Apple> appleList, int weight) {
        List<Apple> resultAppleList = new ArrayList<>();

        for (Apple apple : appleList) {
            if (apple.getWeight() >= weight) {
                resultAppleList.add(apple);
            }
        }

        return resultAppleList;
    }

    /**
     * flag를 사용한 필터링은 매우 좋지 않은, 절대 사용하지 말아야할 방법
     * boolean에 대한 의미를 쉽게 파악할 수 없을 뿐더러 상황에 따라 color와 weight는 필요없는 매개변수가 되기도 한다.
     *
     * 이 코드는 color와 wieght에 모두 대응할 수 있지만, 굉장히 유연하지 못한 코드
     *  => 다른 조건이 추가된다면 true, false 로 대응할 수 없기 때문이다.
     */
    private static List<Apple> filterApples(List<Apple> appleList, String color, int weight, boolean flag) {
        List<Apple> resultAppleList = new ArrayList<>();

        for (Apple apple : appleList) {
            if ((flag && apple.getColor().equals(color)) || (flag && apple.getWeight() >= weight)) {
                resultAppleList.add(apple);
            }
        }

        return resultAppleList;
    }

    private static List<Apple> filterApples(List<Apple> appleList, ApplePredicate predicate) {
        List<Apple> resultAppleList = new ArrayList<>();

        for (Apple apple : appleList) {
            if (predicate.test(apple)) {
                resultAppleList.add(apple);
            }
        }

        return resultAppleList;
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> resultList = new ArrayList<>();

        for (T t : list) {
            if (predicate.test(t)) {
                resultList.add(t);
            }
        }

        return resultList;
    }

}

@Data
@AllArgsConstructor
class Apple {
    private String color;
    private int weight;
}

interface ApplePredicate {
    boolean test(Apple apple);
}

class AppleHeavyWeightPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getWeight() >= 120;
    }
}

class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return "green".equals(apple.getColor());
    }
}

interface Predicate<T> {
    boolean test(T t);
}
