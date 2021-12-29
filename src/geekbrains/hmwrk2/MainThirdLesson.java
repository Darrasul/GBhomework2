package geekbrains.hmwrk2;

import geekbrains.hmwrk2.lesson3.Phonebook;

import java.util.*;

public class MainThirdLesson {
    public static void main(String[] args) {
        String[] firstTaskArray = new String[15];
        fillingTaskArray(firstTaskArray);

        Set<String> firstSet = new HashSet<>();
        for (int i = 0; i < firstTaskArray.length; i++) {
            firstSet.add(firstTaskArray[i]);
        }
        System.out.println(firstSet);

        Map<String, String> map = new HashMap<>();
        Phonebook first = new Phonebook("SurnameA", "8(111)111-11-11");
        Phonebook second = new Phonebook("SurnameB", "8(222)222-22-22");
        Phonebook third = new Phonebook("SurnameC", "8(333)333-33-33");
        Phonebook fourth = new Phonebook("SurnameA", "8(444)444-44-44");
        Phonebook fifth = new Phonebook("SurnameD", "8(555)555-55-55");
        map.put(first.getPhoneNumber(), first.getSurname());
        map.put(second.getPhoneNumber(), second.getSurname());
        map.put(third.getPhoneNumber(), third.getSurname());
        map.put(fourth.getPhoneNumber(), fourth.getSurname());
        map.put(fifth.getPhoneNumber(), fifth.getSurname());
        for (Map.Entry<String, String> iterTry : map.entrySet()) {
            if (iterTry.getValue() == "SurnameA") System.out.println("Number of " + iterTry.getValue() +
                                                                        " is " + iterTry.getKey());
        }
    }

    private static void fillingTaskArray(String[] firstTaskArray) {
        for (int i = 0; i < firstTaskArray.length; i++) {
            int randomInt = (int)(Math.random() * 4);
            if (randomInt == 0) firstTaskArray[i] = "WordA";
            else if (randomInt == 1) firstTaskArray[i] = "WordB";
            else if (randomInt == 2) firstTaskArray[i] = "WordC";
            else firstTaskArray[i] = "WordD";
        }
    }
}
