package java8.chapter10;

import java.util.Optional;

/**
 * Created by choi on 22/01/2019.
 */
public class Chapter10 {

    public static void main(String[] args) {
        Optional<Person> person = Optional.of(new Person());

        /**
         * 아래와 같은 코드는 동작하지 않음
         * map은 Optional<T>를 반환한다. 첫 person.map(Person::getCat)의 리턴 값은 Optional<Optional<Car>> 이다.
         */
//        Optional<String> name = person.map(Person::getCar)
//                .map(Car::getInsurance)
//                .map(Insurance::getName);

        Optional<String> nameOpt2 = person.map(Person::getCar)
                .map(car -> car.get().getInsurance())
                .map(insurance -> insurance.get().getName());

        System.out.println(nameOpt2);

        String name2 = person.map(Person::getCar)
                .map(car -> car.get().getInsurance())
                .map(insurance -> insurance.get().getName())
                .orElse("Unknown name2");

        System.out.println(name2);

        /**
         * .flatMap()을 활용하여 중첩되는 스트림을 일차원적인 스트림으로 풀어주어 해결할 수 있다.
         */
        String name = person.map(Person::getCar)
                .flatMap(car -> car.get().getInsurance())
                .map(Insurance::getName)
                .orElse("Unknown name");

        System.out.println(name);

        Optional<Car> car = person.flatMap(Person::getCar);
        Optional<Insurance> insurance = car.flatMap(Car::getInsurance);
        Optional<String> nameee = insurance.map(Insurance::getName);

        /**
         * 근데 getCar가 null인 경우 NullPointerException이 발생하는데?
         */
        Optional<String> nameOpt = person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName);

        System.out.println(nameOpt);
    }
}

class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }
}

class Car {
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}

class Insurance {
    private String name;

    public String getName() {
        return name;
    }
}