package livraria.livrariafx.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.application.Main;
import livraria.livrariafx.gui.listeners.DataChangeListener;
import livraria.livrariafx.gui.util.Alerts;
import livraria.livrariafx.gui.util.Utils;
import livraria.livrariafx.model.entities.Livros;
import livraria.livrariafx.model.services.LivrosService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LivrosListController implements Initializable, DataChangeListener {
    //ao inves de implementar um service = new DepartmentService(), ficaria acoplamento forte
    //e seria obrigado a instanciar a classe
    private LivrosService service;

    @FXML
    private TableView<Livros> tableViewLivros;

    @FXML
    private TableColumn<Livros, Integer> tableColumnId;

    @FXML
    private TableColumn<Livros, String> tableColumnNome;

    @FXML
    private TableColumn<Livros, Livros> tableColumnEDIT;

    @FXML
    private TableColumn<Livros, Livros> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Livros> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Livros obj = new Livros();
        createDialogForm(obj, parentStage);
    }

    //feito isso usando um set, para injetar dependencia, boa pratica
    //injecao de dependendencia manual, sem framework pra isso
    public void setLivrosService(LivrosService service){
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();

    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewLivros.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView(){
        if (service == null){
            throw new IllegalStateException("Service is null!");
        }
        List<Livros> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewLivros.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Livros obj, Stage parentStage){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LivrosForm.fxml"));
            Pane pane = loader.load();

            LivrosFormController controller = loader.getController();
            controller.setLivros(obj);
            controller.setLivrosService(new LivrosService());
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Livros data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e){
            e.printStackTrace();
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Livros, Livros>() {
            private final Button button = new Button("Editar");
            @Override
            protected void updateItem(Livros obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Livros, Livros>() {
            private final Button button = new Button("Remover");

            @Override
            protected void updateItem(Livros obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Livros obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmar", "Confirma que quer deletar?");

        if (result.get() == ButtonType.OK){
            if (service == null){
                throw new IllegalStateException("Service estava null");
            }
            try {
                service.remove(obj);
                updateTableView();
            } catch (DbException e){
                Alerts.showAlert("Error removing object", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
