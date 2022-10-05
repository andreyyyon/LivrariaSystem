package livraria.livrariafx.model.services;

import livraria.livrariafx.model.dao.DaoFactory;
import livraria.livrariafx.model.dao.LivrosDao;
import livraria.livrariafx.model.entities.Livros;

import java.util.List;

public class LivrosService {

    //dependencia injetada usando padrao factory
    private final LivrosDao dao = DaoFactory.createLivrosDao();

    public List<Livros> findAll() {
        return dao.findAll();

        //Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Department> list = new ArrayList<>();
//        list.add(new Department(1,"Computadores"));
//        list.add(new Department(2,"Alimentação"));
//        list.add(new Department(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Livros obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
        }
    }

    public void remove(Livros obj){
        dao.deleteById(obj.getId());
    }
}

