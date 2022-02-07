package geekbrains.hmwrk3.lesson4;


public class ABCMethod {

    private final Object monitor = new Object();
    private volatile char currentLetter = 'A';
    private final int iterationNumber = 5;

    public static void main(String[] args) {
        ABCMethod waitNotifyObj = new ABCMethod();
        Thread threadA = new Thread(waitNotifyObj::printA);
        Thread threadB = new Thread(waitNotifyObj::printB);
        Thread threadC = new Thread(waitNotifyObj::printC);
        threadA.start();
        threadB.start();
        threadC.start();
    }

    private void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < iterationNumber; i++) {
                    while (currentLetter != 'A') {
                        monitor.wait();
                    }
                    System.out.print('A');
                    currentLetter = 'B';
                    monitor.notifyAll();
                }

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < iterationNumber; i++) {
                    while (currentLetter != 'B') {
                        monitor.wait();
                    }
                    System.out.print('B');
                    currentLetter = 'C';
                    monitor.notifyAll();
                }

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < iterationNumber; i++) {
                    while (currentLetter != 'C') {
                        monitor.wait();
                    }
                    System.out.print('C');
                    currentLetter = 'A';
                    monitor.notifyAll();
                }

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
