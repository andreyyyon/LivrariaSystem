package livraria.livrariafx.model.services;

<<<<<<< HEAD:src/main/java/livraria/livrariafx/model/services/DepartmentService.java
import livraria.livrariafx.model.dao.DaoFactory;
import livraria.livrariafx.model.dao.DepartmentDao;
import livraria.livrariafx.model.entities.Department;
=======
import senac.senacfx.model.dao.DaoFactory;
import senac.senacfx.model.dao.DepartmentDao;
>>>>>>> f6ec33715531d84e34d823125c3f6194ab8eddb6:src/main/java/senac/senacfx/model/services/DepartmentService.java

import java.util.List;

public class DepartmentService {

    //dependencia injetada usando padrao factory
    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();

        //Dados MOCK (fake) so para testar, sem puxar do banco por hora
//        List<Department> list = new ArrayList<>();
//        list.add(new Department(1,"Computadores"));
//        list.add(new Department(2,"Alimentação"));
//        list.add(new Department(3,"Financeiro"));
//        return list;

    }
    public void saveOrUpdate(Department obj){
        if (obj.getId() == null){
            dao.insert(obj);
        } else {
            dao.update(obj);
            }
        }

        public void remove(Department obj){
            dao.deleteById(obj.getId());
        }
    }

