package livraria.livrariafx.model.dao;

import livraria.livrariafx.db.DB;
import livraria.livrariafx.model.dao.impl.DepartmentDaoJDBC;
import livraria.livrariafx.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}