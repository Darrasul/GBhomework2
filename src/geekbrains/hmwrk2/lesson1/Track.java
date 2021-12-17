package geekbrains.hmwrk2.lesson1;

public class Track {
    //    длина беговой дорожки
    private int length;
    private String nameNumber;
    private boolean CatRunBool = false;
    private boolean HumanRunBool = false;
    private boolean RobotRunBool = false;
//    конструктор


    public Track(int length, String nameNumber) {
        this.length = length;
        this.nameNumber = nameNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getNameNumber() {
        return nameNumber;
    }

    public void setNameNumber(String nameNumber) {
        this.nameNumber = nameNumber;
    }

    public boolean isCatRunBool() {
        return CatRunBool;
    }

    public void setCatRunBool(boolean catRunBool) {
        CatRunBool = catRunBool;
    }

    public boolean isHumanRunBool() {
        return HumanRunBool;
    }

    public void setHumanRunBool(boolean humanRunBool) {
        HumanRunBool = humanRunBool;
    }

    public boolean isRobotRunBool() {
        return RobotRunBool;
    }

    public void setRobotRunBool(boolean robotRunBool) {
        RobotRunBool = robotRunBool;
    }

    //    По задаче
    public void compareCat(Cat compCat){
        if (length <= compCat.getRunMaxLength()){
            System.out.println("Cat run all the way!");
            setCatRunBool(true);
        } else {
            System.out.println("Cat could not do it against " + getNameNumber() + " track!");
            setCatRunBool(false);
        }
    }
    public void compareHuman(Human compHuman) {
        if (length <= compHuman.getRunMaxLength()) {
            System.out.println("Human run all the way!");
            setHumanRunBool(true);
        } else{
            System.out.println("Human could not do it against " + getNameNumber() + " track!");
            setHumanRunBool(false);
        }
    }
    public void compareRobot(Robot compRobot){
        if (length <= compRobot.getRunMaxLength()){
            System.out.println("Robot run all the way!");
            setRobotRunBool(true);
        } else {
            System.out.println("Robot could not do it against " + getNameNumber() + " track!");
            setRobotRunBool(false);
        }
    }
}
