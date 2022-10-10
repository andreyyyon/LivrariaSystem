package livraria.livrariafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.gui.listeners.DataChangeListener;
import livraria.livrariafx.gui.util.Alerts;
import livraria.livrariafx.gui.util.Constraints;
import livraria.livrariafx.gui.util.Utils;
import livraria.livrariafx.model.entities.Livros;
import livraria.livrariafx.model.exceptions.ValidationException;
import livraria.livrariafx.model.services.LivrosService;

import java.net.URL;
import java.util.*;

public class LivrosFormController implements Initializable {

    private Livros entity;

    private LivrosService service;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextField txtEditora;
    @FXML
    private TextField txtAutor;
    @FXML
    private Label labelErrorId;
    @FXML
    private Label labelErrorNome;
    @FXML
    private Label labelErrorGenero;
    @FXML
    private Label labelErrorEditora;
    @FXML
    private Label labelErrorAutor;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;


    //Contolador agora tem uma instancia do Livros
    public void setLivros(Livros entity){
        this.entity = entity;
    }

    public void setLivrosService(LivrosService service){
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        //validacao manual pois nao esta sendo usado framework para injetar dependencia
        if (entity == null){
            throw new IllegalStateException("Entidade nula");
        }
        if (service == null){
            throw new IllegalStateException("Servico nulo");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e){
            Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Livros getFormData() {
        Livros obj = new Livros();

        ValidationException exception = new ValidationException("Erro na validacao");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")){
            exception.addError("nome", "campo nao pode ser vazio");
        }
        obj.setNome(txtNome.getText());

        if (txtGenero.getText() == null || txtGenero.getText().trim().equals("")){
            exception.addError("genero", "campo nao pode ser vazio");
        }
        obj.setGenero(txtGenero.getText());

        if (txtEditora.getText() == null || txtEditora.getText().trim().equals("")){
            exception.addError("editora", "campo nao pode ser vazio");
        }
        obj.setEditora(txtEditora.getText());

        if (txtAutor.getText() == null || txtAutor.getText().trim().equals("")){
            exception.addError("autor", "campo nao pode ser vazio");
        }
        obj.setAutor(txtAutor.getText());

        if (exception.getErrors().size() > 0){
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 30);
        Constraints.setTextFieldMaxLength(txtGenero, 30);
        Constraints.setTextFieldMaxLength(txtEditora, 30);
        Constraints.setTextFieldMaxLength(txtAutor, 30);


    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entidade nula");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtGenero.setText(entity.getGenero());
        txtEditora.setText(entity.getEditora());
        txtAutor.setText(entity.getAutor());
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("nome")) {
            labelErrorNome.setText(errors.get("nome"));

            if (fields.contains("genero")) {
                labelErrorGenero.setText(errors.get("genero"));

                if (fields.contains("editora")) {
                    labelErrorEditora.setText(errors.get("editora"));

                    if (fields.contains("autor")) {
                        labelErrorAutor.setText(errors.get("autor"));
                    }
                }
            }
        }
    }
}