public class Main {

    public static void main(String[] args) {
        SecondTask secondTask = new SecondTask();
        int[] test = {2, 1, 3, 4, 5, 12, 4, 8, 8, 4, 3, 2, 1};
        int[] test2 = {0, 0};
        System.out.println(secondTask.initTask(test2));
        System.out.println(secondTask.initTask(test));
        System.out.println("--------------------------");

        ThirdTask thirdTask = new ThirdTask();
        int[] secondTest = {1, 4, 5, 6, 7};
        int[] thirdTest = {0, 0, 2, 2, 6, 6};
        System.out.println(thirdTask.initTask(secondTest));
        System.out.println(thirdTask.initTask(thirdTest));
    }
}
