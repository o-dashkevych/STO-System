package util;

import javafx.scene.control.ProgressIndicator;

/**
 * Created by Oleg Dashkevych on 04.05.2015.
 */
public class ProgressControl extends Thread {
    ProgressIndicator progressIndicator;
    public void setIndicator(ProgressIndicator progressIndicator){
        this.progressIndicator = progressIndicator;
    }
    @Override
    public void run() {
        progressIndicator.setVisible(true);
    }
}
