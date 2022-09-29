package livraria.livrariafx.model.dao;

import livraria.livrariafx.model.entities.Seller;
import livraria.livrariafx.model.entities.Department;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);
    void update(Seller obj);


    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);

}
