package pl.edu.agh.to.drugstore.runner;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.to.drugstore.controller.AppController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class Runner extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        EntityManager em = emf.createEntityManager();

        primaryStage.setTitle("DrugStore");

//        PersonDAO dao = new PersonDAO(em);
//        dao.

        AppController appController = new AppController(primaryStage, em);
        appController.initRootLayout();
    }
}
