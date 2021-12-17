package geekbrains.hmwrk2.lesson1;

public class Wall {
    //    высота стены
    private int high;
    private String nameNumber;
    private boolean CatJumpBool = false;
    private boolean HumanJumpBool = false;
    private boolean RobotJumpBool = false;
//    конструктор

    public Wall(int high, String nameNumber) {
        this.high = high;
        this.nameNumber = nameNumber;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setCatJumpBool(boolean catJumpBool) {
        CatJumpBool = catJumpBool;
    }

    public void setHumanJumpBool(boolean humanJumpBool) {
        HumanJumpBool = humanJumpBool;
    }

    public void setRobotJumpBool(boolean robotJumpBool) {
        RobotJumpBool = robotJumpBool;
    }

    public boolean isCatJumpBool() {
        return CatJumpBool;
    }

    public boolean isHumanJumpBool() {
        return HumanJumpBool;
    }

    public boolean isRobotJumpBool() {
        return RobotJumpBool;
    }

    //    По задаче
    public void compareCat(Cat compCat){
        if (high <= compCat.getJumpMaxHigh()){
            System.out.println("Cat jump over!");
            setCatJumpBool(true);
        } else {
            System.out.println("Cat could not do it");
            setCatJumpBool(false);
        }
    }
    public void compareHuman(Human compHuman){
        if (high <= compHuman.getJumpMaxHigh()){
            System.out.println("Human jump over!");
            setHumanJumpBool(true);
        } else {
            System.out.println("Human could not do it");
            setHumanJumpBool(false);
        }
    }
    public void compareRobot(Robot compRobot){
        if (high <= compRobot.getJumpMaxHigh()){
            System.out.println("Robot jump over!");
            setRobotJumpBool(true);
        } else {
            System.out.println("Robot could not do it");
            setRobotJumpBool(false);
        }
    }
}
