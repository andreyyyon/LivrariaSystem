package livraria.livrariafx.model.dao;

import livraria.livrariafx.db.DB;
import livraria.livrariafx.model.dao.impl.ClienteDaoJDBC;
import livraria.livrariafx.model.dao.impl.LivrosDaoJDBC;
import livraria.livrariafx.model.dao.ClienteDao;

public class DaoFactory {

    public static ClienteDao createClienteDao(){
        return new ClienteDaoJDBC(DB.getConnection());
    }

    public static LivrosDao createLivrosDao(){
        return new LivrosDaoJDBC(DB.getConnection());
    }

}
