package livraria.livrariafx.model.dao.impl;

import livraria.livrariafx.db.DB;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.model.dao.ClienteDao;
import livraria.livrariafx.model.entities.Livros;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteDaoJDBC implements ClienteDao {
    private Connection conn;

    public ClienteDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Cliente obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "insert into cliente " +
                            "(nome, email, idade, cpf, endereco) " +
                            "values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Error! No rows affected!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Cliente obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "update seller " +
                            "set nome = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " +
                            "where id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setDate(3, new Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("delete from seller where Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Vendedor inexistente!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Cliente findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select seller.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Department dep = instantiateLivros(rs);
                Cliente obj = instantiateCliente(rs, dep);
                return obj;

            }
            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Livros instantiateLivros(ResultSet rs) throws SQLException {
        Livros dep = new Livros();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Cliente instantiateSeller(ResultSet rs, Livros dep) throws SQLException{
        Cliente obj = new Cliente();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));
        obj.setLivros(dep);
        return obj;
    }
    @Override
    public List<Cliente> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select cliente.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "order by Name");

            rs = st.executeQuery();

            List<Cliente> list = new ArrayList<>();
            Map<Integer, Livros> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateLivros(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Cliente obj = instantiateCliente(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Cliente> findByLivros(Livros livros) {
        return null;
    }

    @Override
    public List<Cliente> findByDepartment(Livros livros) {
        return null;
    }

    @Override
    public List<Cliente> findByDepartment(Livros livros) {
        return null;
    }

    @Override
    public List<Cliente> findByLivros(Livros livros) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select seller.*, department.Name as DepName " +
                    "from seller inner join department " +
                    "on seller.DepartmentId = department.Id " +
                    "where DepartmentId = ? " +
                    "order by Name");

            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Cliente> list = new ArrayList<>();
            Map<Integer, Livros> map = new HashMap<>();

            while (rs.next()){

                Livros dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateLivros(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Cliente obj = instantiateCliente(rs, dep);
                list.add(obj);
            }
            return list;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
