package livraria.livrariafx.model.entities;

import java.io.Serializable;

public class Livros implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nome;
    private String genero;
    private String editora;
    private String autor;
    private Object Livros;

    public Livros() {
    }

    public Livros(Integer id, String nome, String genero, String editora, String autor) {
        this.id = id;
        this.nome = nome;
    }

    public void setLivros(String Livros){
        this.Livros = Livros;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editoria) {
        this.editora = editoria;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Livros other = (Livros) o;
        if (id == null){
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Livros{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", genero='" + genero+ '\'' +
                ", editora=" + editora +
                ", autor=" + autor +
                '}';

    }
}
