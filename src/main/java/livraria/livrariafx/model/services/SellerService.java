package livraria.livrariafx.model.services;

<<<<<<< HEAD:src/main/java/livraria/livrariafx/model/services/SellerService.java
import livraria.livrariafx.model.dao.DaoFactory;
import livraria.livrariafx.model.entities.Seller;
import livraria.livrariafx.model.dao.SellerDao;
=======
import senac.senacfx.model.dao.DaoFactory;
import senac.senacfx.model.dao.SellerDao;
>>>>>>> f6ec33715531d84e34d823125c3f6194ab8eddb6:src/main/java/senac/senacfx/model/services/SellerService.java

import java.util.List;

public class SellerService {

    //dependencia injetada usando padrao factory
    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();

        //Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Seller> list = new ArrayList<>();
//        list.add(new Seller(1,"Computadores"));
//        list.add(new Seller(2,"Alimentação"));
//        list.add(new Seller(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Seller obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
            }
        }

        public void remove(Seller obj){
            dao.deleteById(obj.getId());
        }
    }

