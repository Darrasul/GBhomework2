package ru.buzas.server.chat;

import java.util.Date;
import java.util.TimerTask;

public class DisconnectTimer extends TimerTask {

    private boolean isInteracted = false;

    @Override
    public void run() {
        System.out.println("Disconnect timer online at" + new Date() + " need authorization");
        completeTask();
        System.out.println("Unknown user disconnected at " + new Date());
    }

    public void completeTask(){
        try {
            Thread.sleep(120000);
            isInteracted = true;
        } catch (InterruptedException e) {
            isInteracted = false;
            System.err.println("Failed to wait with timer");
            e.printStackTrace();
        }
    }

    public boolean isInteracted() {
        return isInteracted;
    }

    public void setInteracted(boolean interacted) {
        isInteracted = interacted;
    }
}
