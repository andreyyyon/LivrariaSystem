package livraria.livrariafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
<<<<<<< HEAD:src/main/java/livraria/livrariafx/controller/LivrosFormController.java
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.gui.listeners.DataChangeListener;
import livraria.livrariafx.gui.util.Alerts;
import livraria.livrariafx.gui.util.Constraints;
import livraria.livrariafx.gui.util.Utils;
import livraria.livrariafx.model.entities.Department;
import livraria.livrariafx.model.exceptions.ValidationException;
import livraria.livrariafx.model.services.DepartmentService;
=======
import senac.senacfx.db.DbException;
import senac.senacfx.gui.listeners.DataChangeListener;
import senac.senacfx.gui.util.Alerts;
import senac.senacfx.gui.util.Constraints;
import senac.senacfx.gui.util.Utils;
import senac.senacfx.model.exceptions.ValidationException;
import senac.senacfx.model.services.DepartmentService;
>>>>>>> f6ec33715531d84e34d823125c3f6194ab8eddb6:src/main/java/senac/senacfx/controller/DepartmentFormController.java

import java.net.URL;
import java.util.*;

public class LivrosFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private Label labelErrorNome;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    //Contolador agora tem uma instancia do departamento
    public void setDepartment(Department entity){
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService service){
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

    private Department getFormData() {
        Department obj = new Department();

        ValidationException exception = new ValidationException("Erro na validacao");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")){
            exception.addError("nome", "campo nao pode ser vazio");
        }
        obj.setName(txtNome.getText());

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

    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entidade nula");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getName());
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        if (fields.contains("nome")){
            labelErrorNome.setText(errors.get("nome"));
        }
    }

}
