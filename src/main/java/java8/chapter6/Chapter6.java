package java8.chapter6;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by choi on 28/12/2018.
 */
public class Chapter6 {
    public static void main(String[] args) {
        List<Dish> dishes = Arrays.asList(
                new Dish("Coffee", "디저트", 150),
                new Dish("쌀국수", "면음식", 700),
                new Dish("라면", "면음식", 1000),
                new Dish("케익", "디저트", 1500),
                new Dish("닭갈비", "한식", 800),
                new Dish("사과", "디저트", 300),
                new Dish("찌개", "한식", 1000)
        );

        maxByCalories(dishes);
        totalCalories(dishes);
        joiningMenu(dishes);

        grouping1(dishes);
        grouping2(dishes);
    }

    private static void maxByCalories(List<Dish> dishes) {
        Optional<Dish> dish = dishes.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));

        System.out.println(dish.orElse(new Dish("Nothing", 0)));

        Optional<Dish> dish2 = dishes.stream().min(Comparator.comparingInt(Dish::getCalories));

        System.out.println(dish2.orElse(new Dish("Nothing", 0)));
    }

    private static void totalCalories(List<Dish> dishes) {
        int totalCalories = dishes.stream()
                .collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);

        int totalCalories2 = dishes.stream().mapToInt(Dish::getCalories).sum();
        System.out.println(totalCalories2);
    }

    private static void joiningMenu(List<Dish> dishes) {
        String menuNames = dishes.stream().map(Dish::getMenu).collect(Collectors.joining(", "));
        System.out.println(menuNames);

//        String menu = dishes.stream().collect(Collectors.joining());
//        System.out.println(menu);
    }

    private static void grouping1(List<Dish> dishes) {
        Map<CaloricLevel, List<Dish>> map = new HashMap<>();
        for (CaloricLevel level : CaloricLevel.values()) {
            map.put(level, new ArrayList<>());
        }
        System.out.println(map);

        map = dishes.stream().collect(Collectors.groupingBy(dish -> calcCaloricLevel(dish)));
        for (CaloricLevel level : CaloricLevel.values()) {
            map.put(level, map.getOrDefault(level, new ArrayList<>()));
        }
        System.out.println(map);

        Map<String, Map<CaloricLevel, List<Dish>>> dishedByTypeCaloricLevel = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(Chapter6::calcCaloricLevel)));

        System.out.println(dishedByTypeCaloricLevel);
    }

    private static void grouping2(List<Dish> dishes) {
        Map<String, Dish> dishByType = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
        System.out.println(dishByType);

        Map<String, Set<CaloricLevel>> dishByType2 = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> calcCaloricLevel(dish), Collectors.toSet())));
        System.out.println(dishByType2);

        Map<String, Set<CaloricLevel>> dishByType3 = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> calcCaloricLevel(dish), Collectors.toCollection(HashSet::new))));
        System.out.println(dishByType3);
    }

    private static void grouping3(List<Dish> dishes) {
        Map<CaloricLevel, List<Dish>> dishesMap = dishes.stream().collect(Collectors.groupingBy(Chapter6::calcCaloricLevel));
        System.out.println(dishesMap);
    }

    private static CaloricLevel calcCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (dish.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }
}

@Data
@ToString
@AllArgsConstructor
class Dish {
    private String menu;
    private String type;
    private int calories;

    public Dish(String menu, int calories) {
        this.menu = menu;
        this.calories = calories;
    }
}

enum CaloricLevel {
    DIET,
    NORMAL,
    FAT,
    SPECIAL
}