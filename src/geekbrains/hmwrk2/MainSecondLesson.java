package geekbrains.hmwrk2;

import geekbrains.hmwrk2.lesson2.MyArrayDataException;
import geekbrains.hmwrk2.lesson2.MyArraySizeException;

public class MainSecondLesson {
    public static String[][] checkedStrings;
    public static void main(String[] args) {
//        Первое задание
        try{
            initStringMassive(4, 4);
        } catch (MyArraySizeException exceptionSize){
            exceptionSize.printStackTrace();
        }
//        Второе задание

//        Здесь метод на выбор: fillingWorkingStrings или fillingNotWorkingStrings
//        В случае, если нужно исключение - использовать второй метод
        fillingWorkingStrings(checkedStrings);
        try {
            parsedArrayInit(checkedStrings);
        } catch (MyArrayDataException exceptionData){
            exceptionData.printStackTrace();
        }
//        Третье задание
        soutOfSummOfArrayInt();
        System.out.println("Program close");
    }

    private static void soutOfSummOfArrayInt() {
        int summOfArrayInt = 0;
        for (int i = 0; i < checkedStrings.length; i++) {
            for (int j = 0; j < checkedStrings[i].length; j++) {
                if (isInteger(checkedStrings[i][j]) == true){
                    summOfArrayInt = summOfArrayInt + Integer.parseInt(checkedStrings[i][j]);
                } else {
                    System.out.println("soutOfSummOfArrayInt failed at [" + i + "][" + j + "]");
                }
            }
        }
        System.out.println("Summary of all Int in array is " + summOfArrayInt);
    }

    //    Инициализация массива и проброс исключения (Первое задание)
    private static void initStringMassive(int first, int second) throws MyArraySizeException {
        checkedStrings = new String[first][second];
        if (first != 4 || second != 4){
            throw new MyArraySizeException();
        }
        System.out.println("Right massive size");

    }
//    Метод для инициализации замены String элементов на Int с пробросом исключения при невозможности
//    данного действия (Второго задание)
    private static void parsedArrayInit(String[][] checkedStrings) throws MyArrayDataException {
        for (int i = 0; i < checkedStrings.length; i++) {
            for (int j = 0; j < checkedStrings[i].length; j++) {
                if (isInteger(checkedStrings[i][j]) == true){
                Integer.parseInt(checkedStrings[i][j]);
                } else {
                    System.out.println("isInteger failed at [" + i + "][" + j + "]");
                    throw new MyArrayDataException();
                }
            }
        }
        System.out.println("parsedArrayInit done");
    }
//    Boolean-метод для проверки, можно ли перевести String в Int. (для второго задания)
    private static boolean isInteger(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //    Метод для получения исключения MyArrayDataException (для второго задания)
    private static void fillingNotWorkingStrings(String[][] checkedStrings) {
        for (int i = 0; i < checkedStrings.length; i++) {
            for (int j = 0; j < checkedStrings[i].length; j++) {
                checkedStrings[i][j] = "x";
            }
        }
    }

//    Метод для обхода исключения MyArrayDataException (для второго задания)
    private static void fillingWorkingStrings(String[][] checkedStrings) {
        for (int i = 0; i < checkedStrings.length; i++) {
            for (int j = 0; j < checkedStrings[i].length; j++) {
                checkedStrings[i][j] = "1";
            }
        }
    }

}
