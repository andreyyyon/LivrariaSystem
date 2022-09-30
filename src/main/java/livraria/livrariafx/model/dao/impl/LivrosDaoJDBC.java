package livraria.livrariafx.model.dao.impl;

import livraria.livrariafx.db.DB;
import livraria.livrariafx.db.DbException;
import livraria.livrariafx.model.dao.LivrosDao;
import livraria.livrariafx.model.entities.Livros;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivrosDaoJDBC implements LivrosDao {
    private Connection conn;

    public LivrosDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Livros obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into livros " +
                            "(nome) " +
                            "values (?) ",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

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
    public void update(Livros obj) {

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("update Livros " +
                    "set Name = ? " +
                    "where Id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Error! No rows affected!");
            }

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
            st = conn.prepareStatement("delete from Livros where Id = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0){
                throw new DbException("Livros inexistente!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Livros findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select * from Livros " +
                    "where Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()){
                Livros liv = instantiateLivros(rs);
                return liv;

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
        liv.setId(rs.getInt("Id"));
        liv.setNome(rs.getString("Name"));
        return liv;
    }

    @Override
    public List<Livros> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("" +
                    "select * from department "+
                    "order by Name");

            rs = st.executeQuery();

            List<Livros> list = new ArrayList<>();
            Map<Integer, Livros> map = new HashMap<>();

            while (rs.next()){

                Livros dep = map.get(rs.getInt("Id"));

                if (dep == null){
                    dep = instantiateLivros(rs);
                    map.put(rs.getInt("Id"), dep);
                }

                list.add(dep);

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
