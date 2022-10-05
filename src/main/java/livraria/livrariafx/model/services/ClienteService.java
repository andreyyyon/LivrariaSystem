package livraria.livrariafx.model.services;

import livraria.livrariafx.model.dao.DaoFactory;
import livraria.livrariafx.model.dao.ClienteDao;
import livraria.livrariafx.model.entities.Cliente;

import java.util.List;

public class ClienteService {

    //dependencia injetada usando padrao factory
    private final ClienteDao dao = DaoFactory.createClienteDao();

    public List<Cliente> findAll() {
        return dao.findAll();

//        Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Seller> list = new ArrayList<>();
//        list.add(new Cliente(1,"Computadores"));
//        list.add(new Cliente(2,"Alimentação"));
//        list.add(new Cliente(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Cliente obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Cliente obj){
        dao.deleteById(obj.getId());
    }
}

