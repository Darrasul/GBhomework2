package geekbrains.hmwrk2;


import geekbrains.hmwrk2.lesson1.*;

public class MainFirstLesson {
    public static void main(String[] args) {
        Cat cat = new Cat("Alice");
        Robot robot = new Robot("AI");
        Human human = new Human("Albert");
        Wall shortWall = new Wall(1,"first");
        Wall mediumWall = new Wall(3,"second");
        Wall highWall = new Wall(5,"third");
        Track shortTrack = new Track(50,"first");
        Track mediumTrack = new Track(75,"second");
        Track longTrack = new Track(100, "third");
        System.out.println("Lets jump!");
        allJumpTest(cat, human, robot, shortWall, mediumWall, highWall);
        System.out.println();
        System.out.println("Now we run!");
        allRunTest(cat, human, robot, shortTrack, mediumTrack, longTrack);
    }
    //    Общий метод для запуска всех тестов на прыжки в высоту
    private static void allJumpTest (Cat testCat, Human testHuman, Robot testRobot,
                                     Wall testShortWall, Wall testMediumWall, Wall testHighWall){
        System.out.println("------Cat first-------");
        allCatJumps(testCat, testShortWall, testMediumWall, testHighWall);
        System.out.println("------Human second----");
        allHumanJumps(testHuman, testShortWall, testMediumWall, testHighWall);
        System.out.println("------Robot third-----");
        allRobotJumps(testRobot, testShortWall, testMediumWall, testHighWall);
    }
    //    Общий метод для запуска всех тестов на бег в длину
    private static void allRunTest (Cat testCat, Human testHuman, Robot testRobot,
                                    Track testShortTrack, Track testMediumTrack, Track testLongTrack){
        System.out.println("------Cat first-------");
        allCatRuns(testCat, testShortTrack, testMediumTrack, testLongTrack);
        System.out.println("------Human second----");
        allHumanRuns(testHuman, testShortTrack, testMediumTrack, testLongTrack);
        System.out.println("------Robot third-----");
        allRobotRuns(testRobot, testShortTrack, testMediumTrack, testLongTrack);
    }


    //    Метод для прыжков кота
    private static void catJumpTest(Cat testCat, Wall testWall) {
        testWall.compareCat(testCat);
    }

    private static void allCatJumps(Cat testCat,
                                    Wall testShortWall, Wall testMediumWall, Wall testHighWall){
        catJumpTest(testCat,testShortWall);
        if (testShortWall.isCatJumpBool() == true){
            catJumpTest(testCat,testMediumWall);
            if (testMediumWall.isCatJumpBool() == true){
                catJumpTest(testCat,testHighWall);
            }
        }

    }

    //    Метод для прыжков человека
    private static void humanJumpTest(Human testHuman, Wall testWall) {
        testWall.compareHuman(testHuman);
    }

    private static void allHumanJumps(Human testHuman,
                                      Wall testShortWall, Wall testMediumWall, Wall testHighWall){
        humanJumpTest(testHuman,testShortWall);
        if (testShortWall.isHumanJumpBool() == true){
            humanJumpTest(testHuman,testMediumWall);
            if (testMediumWall.isHumanJumpBool() == true){
                humanJumpTest(testHuman,testHighWall);
            }
        }
    }

    //    Метод для прыжков робота
    private static void robotJumpTest(Robot testRobot, Wall testWall) {
        testWall.compareRobot(testRobot);
    }

    private static void allRobotJumps(Robot testRobot,
                                      Wall testShortWall, Wall testMediumWall, Wall testHighWall){
        robotJumpTest(testRobot,testShortWall);
        if (testShortWall.isHumanJumpBool() == true){
            robotJumpTest(testRobot,testMediumWall);
            if (testMediumWall.isHumanJumpBool() == true){
                robotJumpTest(testRobot,testHighWall);
            }
        }
    }

    //    Метод для бега кота
    private static void allCatRuns(Cat testCat,
                                   Track testShortTrack, Track testMediumTrack, Track testLongTrack) {
        catRunTest(testCat, testShortTrack);
        if (testShortTrack.isCatRunBool() == true){
            catRunTest(testCat, testMediumTrack);
            if (testMediumTrack.isCatRunBool() == true){
                catRunTest(testCat, testLongTrack);
            }
        }
    }

    private static void catRunTest(Cat testCat, Track testTrack){
        testTrack.compareCat(testCat);
    }

    //    Метод для бега человека
    private static void allHumanRuns(Human testHuman,
                                     Track testShortTrack, Track testMediumTrack, Track testLongTrack) {
        humanRunTest(testHuman, testShortTrack);
        if (testShortTrack.isHumanRunBool() == true){
            humanRunTest(testHuman, testMediumTrack);
            if (testMediumTrack.isHumanRunBool() == true){
                humanRunTest(testHuman, testLongTrack);
            }
        }
    }

    private static void humanRunTest(Human testHuman, Track testTrack){
        testTrack.compareHuman(testHuman);
    }

    //    Метод для бега робота
    private static void allRobotRuns(Robot testRobot,
                                     Track testShortTrack, Track testMediumTrack, Track testLongTrack) {
        robotRunTest(testRobot, testShortTrack);
        if (testShortTrack.isRobotRunBool() == true){
            robotRunTest(testRobot, testMediumTrack);
            if (testMediumTrack.isRobotRunBool() == true){
                robotRunTest(testRobot, testLongTrack);
            }
        }
    }

    private static void robotRunTest(Robot testRobot, Track testTrack){
        testTrack.compareRobot(testRobot);
    }
}
