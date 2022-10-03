package livraria.livrariafx.model.dao.impl;

import livraria.livrariafx.db.DB;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.model.dao.ClienteDao;
import livraria.livrariafx.model.entities.Cliente;
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
            st.setInt(3, obj.getIdade());
            st.setString(4, obj.getCpf());
            st.setInt(5, obj.getEndereco());

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
                    "update Cliente " +
                            "set nome = ?, Email = ?" +
                            "where id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            st.setInt(3, obj.getId());

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
            st = conn.prepareStatement("delete from Cliente where Id = ?");

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
                    "select Cliente.*, livros.Nome as livnome " +
                    "from Cliente inner join livros " +
                    "where Cliente.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Livros liv = instantiateLivros(rs);
                Cliente obj = instantiateCliente(rs, liv);
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
        Livros liv = new Livros();
        liv.setNome(rs.getString("livNome"));
        return liv;
    }

    private Cliente instantiateCliente(ResultSet rs, Livros liv) throws SQLException{
        Cliente obj = new Cliente();
        obj.setId(rs.getInt("Id"));
        obj.setNome((rs.getString("Nome")));
        obj.setEmail(rs.getString("Email"));
        obj.setLivros(liv);
        return obj;
    }
    @Override
    public List<Cliente> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select Cliente.*, livros.Nome as livNome " +
                    "from Cliente inner join livros " +
                    "order by Nome");

            rs = st.executeQuery();

            List<Cliente> list = new ArrayList<>();
            Map<Integer, Livros> map = new HashMap<>();

            while (rs.next()){

 //               Livros livros = map.get(rs.getInt("DepartmentId"));
//
//                if (liv == null){
//                    liv = instantiateLivros(rs);
//                    map.put(rs.getInt("DepartmentId"), dep);
//                }

                Cliente obj = instantiateCliente(rs);
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
    public List<Cliente> findByDepartment(Cliente cliente) {
        return null;
    }

    private Cliente instantiateCliente(ResultSet rs) {
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

//            st.setInt(1, department.getId());

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
