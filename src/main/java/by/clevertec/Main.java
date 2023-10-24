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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.minBy;

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
        task15();
//        task16();
//        task17();
//        task18();
//        task19();
//        task20();
//        task21();
//        task22();
    }

    public static void task1() {
        int countAnimal = 7;
        int skipZoo = 2;
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() < 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(skipZoo * countAnimal)
                .limit(countAnimal)
                .forEach(System.out::println);
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .filter(animal -> animal.getGender().equals("Female"))
                .map(Animal::getBread)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(origin -> origin.startsWith("A"))
                .forEach(System.out::println);

    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        long n = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();
        System.out.println("Колличество животных пола Female: " + n);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        boolean thereIsAnAnimalFromHungarian = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        System.out.println("Есть ли животные из Венгрии возрастом 20 - 30 лет: " + thereIsAnAnimalFromHungarian);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> !animal.getGender().equals("Male") && !animal.getGender().equals("Female"))
                .forEach(System.out::println);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        boolean thereIsAnAnimalFromOceania = animals.stream()
                .anyMatch(animal -> animal.getOrigin().equals("Oceania"));
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
        System.out.println("Возвраст самого старого животного из выбранных: " + age);
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
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToInt(Animal::getAge)
                .average()
                .orElseThrow();
        System.out.println("Средний возвраст всех животных: " + avg);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(person -> "Male".equals(person.getGender()))
                .filter(person -> LocalDate.now().isAfter(person.getDateOfBirth().plus(18, ChronoUnit.YEARS)) &&
                        LocalDate.now().isBefore(person.getDateOfBirth().plus(27, ChronoUnit.YEARS)))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();

        houses.stream()
                .flatMap(hh -> Stream.of(
                                        houses.stream()
                                                .filter(house -> house.getBuildingType().equals("Hospital"))
                                                .map(House::getPersonList)
                                                .flatMap(Collection::stream),

                                        houses.stream()
                                                .filter(house -> house.getBuildingType().equals("Civil building"))
                                                .map(House::getPersonList)
                                                .flatMap(Collection::stream)
                                                .filter(p -> p.getDateOfBirth().isAfter(LocalDate.now().minus(18, ChronoUnit.YEARS)) ||
                                                        p.getDateOfBirth().isBefore(LocalDate.now().minus(60, ChronoUnit.YEARS))),

                                        houses.stream()
                                                .map(House::getPersonList)
                                                .flatMap(Collection::stream)

                                )
                                .flatMap(stream -> stream)
                )
                .distinct()
                .limit(500)
                .forEach(System.out::println);

    }

    public static void task14() {
        List<Car> cars = Util.getCars();
//        cars.stream() Продолжить ...
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();

        int year = 5;
        double costCubicMeterWater = 1.39;
        Comparator<Flower> compareByOriginReversed = Comparator.comparing(Flower::getOrigin).reversed();
        Comparator<Flower> compareByPrice = Comparator.comparing(Flower::getPrice);
        Comparator<Flower> compareByWaterConsumptionPerDayReversed = Comparator.comparing(Flower::getWaterConsumptionPerDay).reversed();
        List<String> materials = new ArrayList<>(){{add("Glass"); add("Steel"); add("Aluminum");}};

        double total = flowers.stream()
                .sorted(compareByOriginReversed.thenComparing(compareByPrice.thenComparing(compareByWaterConsumptionPerDayReversed)))
                .filter(flower -> flower.getCommonName().matches("[C-S].*"))
                .filter(Flower::isShadePreferred)
                .filter(flower ->  materials.stream().anyMatch(material -> flower.getFlowerVaseMaterial().contains(material)))
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * year * costCubicMeterWater * 0.001)
                .sum();

        System.out.println("Общая стоимость и расходы на обслуживание за " + year + "лет выбранных растений составят: " + total);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 18)
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
                .collect(groupingBy(
                        Student::getFaculty,
                        averagingDouble(Student::getAge)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        String group = "M-1";

        students.stream()
                .filter(student -> student.getGroup().equals(group))
                .filter(student -> examinations.stream()
                        .filter(examination -> examination.getStudentId() == student.getId())
                        .anyMatch(examination -> examination.getExam3() > 4)
                )
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        Map.Entry<String, Double> facultyWithHighestAverageGradeOnFirstExam = students.stream()
                .map(student -> new Object[]{student.getFaculty(), examinations.stream()
                                                                    .filter(examination -> examination.getStudentId() == student.getId())
                                                                    .map(Examination::getExam1)
                                                                    .findFirst()
                                                                    .orElse(-1)
                                            })
                .filter(array -> (int) array[1] > 0)
                .collect(groupingBy(
                        array -> (String) array[0],
                        averagingDouble(array -> (int) array[1])
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).orElseThrow();

        System.out.println("Факультет " + facultyWithHighestAverageGradeOnFirstExam.getKey() +
                " имеет максимальную среднюю оценку по первому экзамену, которая составляет: "
                + facultyWithHighestAverageGradeOnFirstExam.getValue());
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
                .collect(groupingBy(Student::getFaculty,
                        collectingAndThen(minBy(Comparator.comparingInt(Student::getAge)),
                                student -> student.orElseThrow().getAge())
                ))
                .entrySet()
                .forEach(System.out::println);
    }
}
