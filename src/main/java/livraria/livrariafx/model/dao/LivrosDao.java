package livraria.livrariafx.model.dao;

import senac.senacfx.model.entities.Livros;
import java.util.List;

public interface LivrosDao {

    void insert(senac.senacfx.model.entities.Livros obj);
    void update(Livros obj);
    void deleteById(Integer id);
    Livros findById(Integer id);
    List<Livros> findAll();

}
