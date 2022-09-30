package livraria.livrariafx.model.dao;

import livraria.livrariafx.model.entities.Livros;
import java.util.List;

public interface LivrosDao {

    void insert(Livros obj);
    void update(Livros obj);
    void deleteById(Integer id);
    Livros findById(Integer id);
    List<Livros> findAll();

}
