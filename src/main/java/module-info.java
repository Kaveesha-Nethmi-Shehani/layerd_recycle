module lk.ijse.recycle {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires mysql.connector.j;
    requires java.sql;
    requires jdk.jfr;
    requires java.desktop;
    requires jasperreports;
    requires itext;
    requires java.rmi;

    opens lk.ijse.recycle.controller to javafx.fxml;
    opens lk.ijse.recycle.dto to javafx.base;
    exports lk.ijse.recycle;
    exports lk.ijse.recycle.controller;
}
