import java.util.Arrays;

public class SecondTask implements SecondTasksInterface {

    private int numberOfSymbols = 0;
    private boolean isThereAFour = false;
    private int requiredNumber = 4;

    public int getNumberOfSymbols() {
        return numberOfSymbols;
    }

    public boolean isThereAFour() {
        return isThereAFour;
    }

    public int getRequiredNumber() {
        return requiredNumber;
    }

    public int[] initTask(int[] initNumberArray){
        comparisonMessage(initNumberArray);

        calculateResult(initNumberArray);
        return convertResultIfTrue(initNumberArray);
    }

    private void comparisonMessage(int[] numbersArray) {
        for (int i : numbersArray) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private void calculateResult(int[] numbersArray) {
        for (int i = numbersArray.length - 1; i >= 0; i--) {
            if (numbersArray[i] != requiredNumber){
                numberOfSymbols++;
            } else if (numbersArray[i] == 4){
                isThereAFour = true;
                numberOfSymbols++;
                break;
            }
        }
    }

    private int[] convertResultIfTrue(int[] numbersArray) {
        if (isThereAFour){
            int[] requestArray = Arrays.copyOfRange(numbersArray, numbersArray.length - (numberOfSymbols - 1), numbersArray.length);
            for (int i : requestArray) {
                System.out.print(i + " ");
            }
            return requestArray;
        }
        return null;
    }
}
