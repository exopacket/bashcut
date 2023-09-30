module com.inteliense.bashcut {
    requires javafx.controls;
    requires javafx.fxml;
    requires jnativehook;


    opens com.inteliense.bashcut to javafx.fxml;
    exports com.inteliense.bashcut;
}