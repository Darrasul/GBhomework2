package geekbrains.hmwrk2;


import geekbrains.hmwrk2.lesson1.*;

public class MainFirstLesson {
    public static void main(String[] args) {
        Cat cat = new Cat("Alice");
        Robot robot = new Robot("AI");
        Human human = new Human("Albert");
        Wall[] walls = new Wall[3];
        walls[0] = new Wall(2, "first");
        walls[1] = new Wall(3,"second");
        walls[2] = new Wall(5,"third");
        Track[] tracks = new Track[3];
        tracks[0] = new Track(50, "first");
        tracks[1] = new Track(75, "second");
        tracks[2] = new Track(100, "third");
        allJumpTest(cat, robot, human, walls);
        allRunTest(cat, robot, human, tracks);
    }

//    Общий метод для запуска всех тестов на прыжки в высоту

    private static void allJumpTest(Cat cat, Robot robot, Human human, Wall[] walls) {
        System.out.println("--------Cat jump now---------");
        catJumping(walls, cat);
        System.out.println("--------Human jump now-------");
        humanJumping(walls, human);
        System.out.println("--------Robot jump now-------");
        robotJumping(walls, robot);
        System.out.println();
    }


    //    Общий метод для запуска всех тестов на бег в длину

    private static void allRunTest(Cat cat, Robot robot, Human human, Track[] tracks) {
        System.out.println("--------Cat run now---------");
        catRunning(tracks, cat);
        System.out.println("--------Human run now-------");
        humanRunning(tracks, human);
        System.out.println("--------Robot run now-------");
        robotRunning(tracks, robot);
        System.out.println();
    }

    //    Метод для прыжков кота

    private static void catJumping(Wall[] walls, Cat cat){
        for (int i = 0; i < walls.length; i++) {
            walls[i].compareCat(cat);
            if (walls[i].isCatJumpBool() != true) break;
        }
    }

    //    Метод для прыжков человека

    private static void humanJumping(Wall[] walls, Human human){
        for (int i = 0; i < walls.length; i++) {
            walls[i].compareHuman(human);
            if (walls[i].isHumanJumpBool() != true) break;
        }
    }

    //    Метод для прыжков робота

    private static void robotJumping(Wall[] walls, Robot robot){
        for (int i = 0; i < walls.length; i++) {
            walls[i].compareRobot(robot);
            if (walls[i].isRobotJumpBool() != true) break;
        }
    }

    //    Метод для бега кота

    private static void catRunning(Track[] tracks, Cat cat){
        for (int i = 0; i < tracks.length; i++) {
            tracks[i].compareCat(cat);
            if (tracks[i].isCatRunBool() != true) break;
        }
    }

    //    Метод для бега человека

    private static void humanRunning(Track[] tracks, Human human){
        for (int i = 0; i < tracks.length; i++) {
            tracks[i].compareHuman(human);
            if (tracks[i].isHumanRunBool() != true) break;
        }
    }

    //    Метод для бега робота

    private static void robotRunning(Track[] tracks, Robot robot){
        for (int i = 0; i < tracks.length; i++) {
            tracks[i].compareRobot(robot);
            if (tracks[i].isRobotRunBool() != true) break;
        }
    }

}
