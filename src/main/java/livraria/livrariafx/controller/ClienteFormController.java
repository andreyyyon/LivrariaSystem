package livraria.livrariafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.gui.listeners.DataChangeListener;
import livraria.livrariafx.gui.util.Alerts;
import livraria.livrariafx.gui.util.Constraints;
import livraria.livrariafx.gui.util.Utils;
import livraria.livrariafx.model.entities.Cliente;
import livraria.livrariafx.model.entities.Livros;
import livraria.livrariafx.model.exceptions.ValidationException;
import livraria.livrariafx.model.services.ClienteService;
import livraria.livrariafx.model.services.LivrosService;
import java.net.URL;
import java.util.*;

public class ClienteFormController implements Initializable {
    private Cliente entity;

    private ClienteService service;

    private LivrosService LivrosService;

    private final List<DataChangeListener> dataChangeListeners = new ArrayList<>();

//    @FXML
//    private TextField txtId;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtCpf;

    @FXML
    public TextField txtEndereco;

//    @FXML
//    private ComboBox<Livros> comboBoxLivros;
    @FXML
    private Label labelErrorNome;
    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorIdade;

    @FXML
    private Label labelErrorCpf;
    @FXML
    private Label labelErrorEndereco;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    private ObservableList<Livros> obsList;

//    public ClienteFormController() {
//    }

    //Contolador agora tem uma instancia do departamento
    public void setCliente(Cliente entity){
        this.entity = entity;
    }

    public void setServices(ClienteService service, LivrosService livrosService){
        this.service = service;
        this.LivrosService = livrosService;
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

    private Cliente getFormData() {
        Cliente obj = new Cliente();

        ValidationException exception = new ValidationException("Erro na validacao");

//        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtNome.getText() == null || txtNome.getText().trim().equals("")){
            exception.addError("name", "campo nao pode ser vazio");
        }
        obj.setNome(txtNome.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addError("email", "campo nao pode ser vazio");
        }
        obj.setEmail(txtEmail.getText());

        if (txtIdade.getText() == null){
            exception.addError("idade", "idade nao selecionada");
        }
        obj.setIdade(Integer.valueOf(txtIdade.getText()));

        if (txtCpf.getText() == null){
            exception.addError("cpf", "campo nao pode ser vazio");
        }
        obj.setCpf(String.valueOf(txtCpf.getText()));

        if (txtEndereco.getText() == null){
            exception.addError("endereco", "campo nao pode ser vazio");
            obj.setEndereco(String.valueOf(txtEndereco.getText()));
        }

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
//        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtNome, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Constraints.setTextFieldMaxLength(txtIdade, 3);
        Constraints.setTextFieldMaxLength(txtCpf, 20);
        Constraints.setTextFieldMaxLength(txtEndereco, 70);


//        initializeComboBoxLivros();

    }

    public void updateFormData(){

        if (entity == null){
            throw new IllegalStateException("Entidade nula");
        }

//        txtId.setText(String.valueOf(entity.getId()));
        txtNome.setText(entity.getNome());
        txtEmail.setText(entity.getEmail());
        txtIdade.setText(String.valueOf(entity.getIdade()));
        txtCpf.setText(entity.getCpf());
        txtEndereco.setText(entity.getEndereco());


        Locale.setDefault(Locale.US);

    }

//    public void loadAssociatedObjects(){
//
//        if (LivrosService == null){
//            throw new IllegalStateException("LivrosService was null");
//        }
//
//        List<Livros> list = LivrosService.findAll();
//        obsList = FXCollections.observableArrayList(list);
//        comboBoxLivros.setItems(obsList);
//    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        labelErrorNome.setText((fields.contains("nome") ? errors.get("nome") : ""));
        labelErrorEmail.setText((fields.contains("email") ? errors.get("email") : ""));
        labelErrorIdade.setText((fields.contains("idade") ? errors.get("idade") : ""));
        labelErrorCpf.setText((fields.contains("cpf") ? errors.get("cpf") : ""));
        labelErrorEndereco.setText((fields.contains("endereco") ? errors.get("endereco") : ""));
        labelErrorNome.getStyleClass().add("button");

    }

//    private void initializeComboBoxLivros() {
//        Callback<ListView<Livros>, ListCell<Livros>> factory = lv -> new ListCell<Livros>() {
//            @Override
//            protected void updateItem(Livros item, boolean empty) {
//                super.updateItem(item, empty);
//                setText(empty ? "" : item.getNome());
//            }
//        };
//        ComboBox<Livros> comboBoxLivros = null;
//        assert comboBoxLivros != null;
//        comboBoxLivros.setCellFactory(factory);
//        comboBoxLivros.setButtonCell(factory.call(null));
//    }

}
