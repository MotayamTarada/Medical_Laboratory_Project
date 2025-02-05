import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class InactivityMonitor {

    private final Timer timer = new Timer(true);
    private TimerTask inactivityTask;
    private final int timeoutMillis;

    public InactivityMonitor(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public void monitorScene(javafx.scene.Scene scene) {
        // Listen for any mouse activity to reset the timer
        scene.setOnMouseMoved(event -> resetTimer());
        scene.setOnMouseClicked(event -> resetTimer());
        resetTimer();
    }

    private void resetTimer() {
        if (inactivityTask != null) {
            inactivityTask.cancel();
        }
        inactivityTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        // Exit the JavaFX platform, closing all stages and terminating the app
                        Platform.exit();
                        System.exit(0); // Force termination of JVM to ensure all threads stop
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        timer.schedule(inactivityTask, timeoutMillis);
    }



    public void stopMonitoring() {
        if (inactivityTask != null) {
            inactivityTask.cancel();
        }
        timer.cancel();
    }
}
