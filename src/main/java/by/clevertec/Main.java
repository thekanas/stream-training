package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
//        task15();
//        task16();
//        task17();
//        task18();
//        task19();
        task20();
//        task21();
//        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(2 * 7)
                .limit(7)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getOrigin().equals("Japanese"))
                .peek(a ->
                {
                    if (a.getGender().equals("Female")) {
                        a.setBread(a.getBread().toUpperCase());
                    }
                })
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(o -> o.startsWith("A"))
                .forEach(System.out::println);

    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        Long n = animals.stream()
                .filter(a -> a.getGender().equals("Female"))
                .count();
        System.out.println(n);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        boolean thereIsAnAnimalFromHungarian = animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> a.getOrigin().equals("Hungarian"));
        System.out.println("Есть ли животные из Венгрии возрастом 20 - 30 лет: " + thereIsAnAnimalFromHungarian);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> !a.getGender().equals("Male") && !a.getGender().equals("Female"))
                .forEach(System.out::println);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        boolean thereIsAnAnimalFromOceania = animals.stream()
                .anyMatch(a -> a.getOrigin().equals("Oceania"));
        System.out.println("Есть ли животные из Oceania: " + thereIsAnAnimalFromOceania);
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        int age = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .orElseThrow();
        System.out.println(age);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        char[] minBreed = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(c -> c.length))
                .orElseThrow();
        System.out.println("Длинна самого короткого массива символов составленного из названия породы: " + minBreed.length + ", строковое представление массива: " + Arrays.toString(minBreed));
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        int count = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println("Суммарный возраст всех животных: " + count);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        double avg = animals.stream()
                .filter(a -> "Indonesian".equals(a.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .orElseThrow();
        System.out.println("Средний возвраст всех животных: " + avg);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(g -> "Male".equals(g.getGender()))
                .filter(a -> LocalDate.now().isAfter(a.getDateOfBirth().plus(18, ChronoUnit.YEARS)) &&
                        LocalDate.now().isBefore(a.getDateOfBirth().plus(27, ChronoUnit.YEARS)))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();

        houses.stream()
                .flatMap(h -> h.getPersonList().stream())
                .forEach(System.out::println);

    }

    public static void task14() {
        List<Car> cars = Util.getCars();
//        cars.stream() Продолжить ...
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        double total = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(
                                Comparator.comparing(Flower::getPrice)
                                        .thenComparing(
                                                Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed())))
                .filter(f -> f.getCommonName().matches("[C-S].*"))
                .filter(Flower::isShadePreferred)
                .filter(f -> f.getFlowerVaseMaterial().contains("Glass") || f.getFlowerVaseMaterial().contains("Steel") || f.getFlowerVaseMaterial().contains("Aluminum"))
                .mapToDouble(f -> f.getPrice() + f.getWaterConsumptionPerDay() * 365 * 5 * 1.39 * 0.001)
                .sum();

        System.out.println("Общая стоимость и расходы на обслуживание за 5 лет выбранных растений составят: " + total);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(s -> s.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(System.out::println);

    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.toMap(
                        s -> s.getFaculty(),
                        s -> new int[]{s.getAge(), 1},
                        (int[] a, int[] b) -> {
                            a[0] = a[0] + b[0];
                            a[1] = a[1] + b[1];
                            return a;
                        }
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        s -> s.getKey(),
                        s -> Arrays.stream(s.getValue()).asDoubleStream().reduce((a, b) -> a / b).orElse(0)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
//        students.stream() Продолжить ...
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
//        students.stream()
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.toMap(
                        Student::getGroup,
                        s -> 1,
                        Integer::sum
                ))
                .entrySet()
                .forEach(System.out::println);
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.toMap(
                        Student::getFaculty,
                        Student::getAge,
                        (a, b) -> {
                            if(a < b) return a;
                            else return b;
                        }
                ))
                .entrySet()
                .forEach(System.out::println);
    }
}
