package pl.edu.agh.to.drugstore.controller.clientOrder;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.to.drugstore.command.CommandRegistry;
import pl.edu.agh.to.drugstore.controller.AppController;
import pl.edu.agh.to.drugstore.controller.person.PersonAppController;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;
import pl.edu.agh.to.drugstore.model.dao.MedicationDAO;
import pl.edu.agh.to.drugstore.model.dao.PersonDAO;
import pl.edu.agh.to.drugstore.presenter.StatsPresenter;
import pl.edu.agh.to.drugstore.presenter.editDialog.ClientOrderEditDialogPresenter;

import javax.persistence.EntityManager;
import java.io.IOException;

@Getter
public class ClientOrderAppController {
    private final static Logger logger = LoggerFactory.getLogger(PersonAppController.class);
    private final ClientOrderDAO clientOrderDAO;
    private final PersonDAO personDAO;
    private final MedicationDAO medicationDAO;
    private final Stage primaryStage;

    private final CommandRegistry commandRegistry = new CommandRegistry();
    private final AppController appController;

    public ClientOrderAppController(Stage primaryStage, EntityManager em, AppController appController) {
        this.primaryStage = primaryStage;
        this.personDAO = new PersonDAO(em);
        this.clientOrderDAO = new ClientOrderDAO(em);
        this.medicationDAO = new MedicationDAO(em);
        this.appController = appController;
    }

    public void initRootLayout() throws IOException {
        this.primaryStage.setTitle("Drugstore");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientOrderAppController.class
                .getResource("/view/ClientOrderOverviewPane.fxml"));
        BorderPane rootLayout = loader.load();

        ClientOrderOverviewController controller = loader.getController();
        controller.setAppController(this);
        controller.setData();
        controller.setCommandRegistry(commandRegistry);
        controller.setClientOrderDAO(clientOrderDAO);
        controller.setMedicationDAO(medicationDAO);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showOrdersStatsDialog() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/OrdersStatsDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Orders Stats dialog");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            StatsPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setClientOrderDAO(clientOrderDAO);
            presenter.setData();

            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

    public boolean showClientOrderEditDialog(ClientOrder clientOrder) {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(PersonAppController.class
                    .getResource("/view/ClientOrderEditDialog.fxml"));
            BorderPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit client order");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ClientOrderEditDialogPresenter presenter = loader.getController();
            presenter.setDialogStage(dialogStage);
            presenter.setPersonDAO(personDAO);
            presenter.setMedicationDAO(medicationDAO);
            presenter.updateClientComboBox();
            presenter.setData(clientOrder);

            dialogStage.showAndWait();
            return presenter.isApproved();

        } catch (IOException e) {
            logger.error("An error appeared when loading page.", e);
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AppController getAppController() {
        return appController;
    }
}
