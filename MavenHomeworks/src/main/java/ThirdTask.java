public class ThirdTask implements ThirdTaskInterface {

    private static int firstRequiredNumber = 1;
    private static int secondRequiredNumber = 4;

    public boolean initTask(int[] initNumberArray){
        return compareToRequest(initNumberArray);
    }

    private static boolean compareToRequest (int[] array) {
        boolean isThereFirstNumber = false;
        boolean isThereSecondNumber = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == firstRequiredNumber){
                isThereFirstNumber = true;
            } else if (array[i] == secondRequiredNumber){
                isThereSecondNumber = true;
            }
        }
        if (isThereFirstNumber && isThereSecondNumber){
            return true;
        } else {
            return false;
        }
    }
}
