package geekbrains.hmwrk2.lesson1;

    public class Robot {
        //    константы для последнего задания
        private int jumpMaxHigh = 1;
        private int runMaxLength = 150;
        //    параметры при создании
        private String name;
        //    конструктор
        public Robot(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        //    методы из задания и геттеры для констант
        public void run() {
            System.out.println(getName() + " run about " + runMaxLength + " meters");
        }
        public void jump() {
            System.out.println(getName() + " jump about " + jumpMaxHigh + " meters high");
        }

        public int getJumpMaxHigh() {
            return jumpMaxHigh;
        }

        public int getRunMaxLength() {
            return runMaxLength;
        }
}
