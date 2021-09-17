module me.retran.skijaexample.javafxskija {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.skija.windows;
    requires org.jetbrains.skija.shared;

    opens me.retran.skijaexample.javafxskija to javafx.fxml;
    exports me.retran.skijaexample.javafxskija;
}