package com.inteliense.bashcut;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BashcutApplication extends Application {

    private static BashcutController controller = null;
    private static Stage s = null;
    private static boolean launched = false;

    @Override
    public void start(Stage stage) throws IOException {
        s = stage;
        Parameters p = getParameters();
        List<String> params = p.getUnnamed();
        FXMLLoader fxmlLoader = new FXMLLoader(BashcutApplication.class.getResource("bashcut.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        controller = (BashcutController) fxmlLoader.getController();
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        stage.setTitle(params.get(0));
        stage.setY(44);
        stage.setX(bounds.getMaxX() - 644);
        stage.setScene(scene);
        controller.setCmd(params.get(0));
        s.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void println(String output) {
        System.out.println(output);
        Platform.runLater(() -> {
            controller.println(output);
        });
    }

    public static boolean hasLaunched() {
        return launched;
    }

    public static void display(String[] args) {
        main(args);
    }

    public static void display() {
        Platform.runLater(() -> {
            s.show();
        });
    }

    public static void exit() {
        Platform.setImplicitExit(false);
    }

}