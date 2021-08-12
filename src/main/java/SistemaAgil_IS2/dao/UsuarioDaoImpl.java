package SistemaAgil_IS2.dao;

import SistemaAgil_IS2.model.Usuario;
import SistemaAgil_IS2.service.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioDaoImpl implements UsuarioDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource datasource;

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    private static final String OBTENER_USUARIO = "SELECT * FROM usuario WHERE nombreUsuario = ? AND passwrd = ?"; //se cambiaron el nombre de la tabla y sus columnas a recuparar, de acuerdo a tu configuracion interna cambiarla acordemente

    @Override
    public Usuario validarIngreso(Usuario usuario) throws Exception {
        List<Usuario> user = jdbcTemplate.query(OBTENER_USUARIO, new UsuarioRowMapper(), usuario.getNombreUsuario(), usuario.getPasswrd());
        return user.size() > 0 ? user.get(0) : null;
    }

    private class UsuarioRowMapper implements RowMapper<Usuario> {

        @Override
        public Usuario mapRow(ResultSet rs, int i) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario")); //se cambio el nombre de la columna, cambiarla de acuerdo a tu configuracion local
            usuario.setNombre(rs.getString("nombre"));
            usuario.setNombreUsuario(rs.getString("nombreUsuario"));//se cambio el nombre de la columna, cambiarla de acuerdo a tu configuracion local
            usuario.setPasswrd(rs.getString("passwrd"));
            return usuario;
        }
    }

    //metodos de prueba CRUD
    @Override
    public List listar() {
        List<Usuario> list = new ArrayList<>();
        String sql = "select * from usuario";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNombreUsuario(rs.getString("nombreUsuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setPasswrd(rs.getString("passwrd"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    @Override
    public Usuario list(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean add(Usuario user) {
        String sql="insert into usuario(nombreUsuario,nombre,apellido,passwrd) values ('"+user.getNombreUsuario()+"','"+user.getNombre()+"','"+user.getApellido()+"','"+user.getPasswrd()+"')";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public Boolean edit(Usuario user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
