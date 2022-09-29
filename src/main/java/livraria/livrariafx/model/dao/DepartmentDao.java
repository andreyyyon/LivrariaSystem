package livraria.livrariafx.model.dao;

<<<<<<< HEAD:src/main/java/livraria/livrariafx/model/dao/DepartmentDao.java
import livraria.livrariafx.model.entities.Department;

=======
>>>>>>> f6ec33715531d84e34d823125c3f6194ab8eddb6:src/main/java/senac/senacfx/model/dao/DepartmentDao.java
import java.util.List;

public interface DepartmentDao {

    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();

}
