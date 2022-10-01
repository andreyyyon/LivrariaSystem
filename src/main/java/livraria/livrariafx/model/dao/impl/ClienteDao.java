package livraria.livrariafx.model.dao;

import livraria.livrariafx.model.entities.Cliente;
import livraria.livrariafx.model.entities.Livros;

import java.util.List;

public interface ClienteDao {

    void insert(Cliente obj);
    void update(Cliente obj);


    void deleteById(Integer id);
    Cliente findById(Integer id);
    List<Cliente> findAll();
    List<Cliente> findByDepartment(Cliente cliente);

}