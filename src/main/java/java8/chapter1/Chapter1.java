package java8.chapter1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by choi on 29/11/2018 9:21 PM.
 */
public class Chapter1 {

    public static void main(String[] args) {
        List<SimpleData> simpleDataList = new ArrayList<>();
        simpleDataList.add(new SimpleData(10));
        simpleDataList.add(new SimpleData(5));
        simpleDataList.add(new SimpleData(8));
        simpleDataList.add(new SimpleData(1));
        simpleDataList.add(new SimpleData(15));

        s1(simpleDataList);
        System.out.println("No Stream result : " + noStreamMethod(simpleDataList));
        System.out.println("Stream result : " + streamMethod(simpleDataList));
    }

    private static void s1(List<SimpleData> simpleDataList) {
        List<SimpleData> newSimpleDataList = simpleDataList.stream()
                .sorted(Comparator.comparing(SimpleData::getId))
                .collect(Collectors.toList());

        System.out.println(newSimpleDataList.toString());
    }

    private static List<SimpleData> noStreamMethod(List<SimpleData> simpleDataList) {
        List<SimpleData> result = new ArrayList<>();
        for (SimpleData data : simpleDataList) {
            if (data.getId() >= 10) {
                result.add(data);
            }
        }
        return result;
    }

    private static List<SimpleData> streamMethod(List<SimpleData> simpleDataList) {
        return simpleDataList.stream()
                .filter(data -> data.getId() >= 10)
                .collect(Collectors.toList());
    }

}

class SimpleData {
    private int id;

    public SimpleData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SimpleData{" +
                "id=" + id +
                '}';
    }
}