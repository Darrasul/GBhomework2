package geekbrains.hmwrk2;

public class MainFifthLesson {
    private static final int FULL_ARRAY_SIZE = 10_000_000;
    private static final int HALF_ARRAY_SIZE = FULL_ARRAY_SIZE / 2;

    public static void main(String[] args) {
        fullArrayCount();
        System.out.println("--------------------------------------");
        dividedArrayCount();
    }

    public static void arrayRendering(float[] floatArray) {
        for (int i = 0; i < floatArray.length; i++) {
            floatArray[i] = (float) (floatArray[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void fullArrayCount() {
        float[] floatArray = new float[FULL_ARRAY_SIZE];
        for (int i = 0; i < floatArray.length; i++) {
            floatArray[i] = 1.0f;
        }

        long startTime = System.currentTimeMillis();

        arrayRendering(floatArray);
        System.out.println("One thread time is " + (System.currentTimeMillis() - startTime) + " ms");
    }


    private static void dividedArrayCount() {
        float[] dividedArray = new float[FULL_ARRAY_SIZE];
        for (int i = 0; i < dividedArray.length; i++) {
            dividedArray[i] = 1.0f;
        }

        long startTime = System.currentTimeMillis();

        float[] firstHalfOfArray = new float[HALF_ARRAY_SIZE];
        float[] secondHalfOfArray = new float[HALF_ARRAY_SIZE];
        float[] mergedArray = new float[FULL_ARRAY_SIZE];

        System.arraycopy(dividedArray, 0, firstHalfOfArray, 0, HALF_ARRAY_SIZE);
        System.arraycopy(dividedArray, HALF_ARRAY_SIZE, secondHalfOfArray, 0, HALF_ARRAY_SIZE);
        long divideTime = System.currentTimeMillis();
        System.out.println("Divide time is " + (divideTime - startTime) + " ms");

        Thread thread1 = new Thread(() -> {
            arrayRendering(firstHalfOfArray);
        });
        Thread thread2 = new Thread(() -> {
            arrayRendering(secondHalfOfArray);
        });
        thread1.start();
        thread2.start();
        long renderTime = System.currentTimeMillis();
        System.out.println("Render time is " + (renderTime - divideTime));

        System.arraycopy(firstHalfOfArray, 0, mergedArray, 0, HALF_ARRAY_SIZE);
        System.arraycopy(secondHalfOfArray, 0, mergedArray, HALF_ARRAY_SIZE, HALF_ARRAY_SIZE);
        System.out.println("Merge time is " + (System.currentTimeMillis() - renderTime));
        System.out.println("Two thread time is " + (System.currentTimeMillis() - startTime) + " ms");
    }
}


