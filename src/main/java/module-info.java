module codsoft.sujit.numberguessgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens coderscave.sujit.numberguessgame to javafx.fxml;
    exports coderscave.sujit.numberguessgame;
}