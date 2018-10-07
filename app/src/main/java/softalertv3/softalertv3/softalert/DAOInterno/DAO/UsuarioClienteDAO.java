package softalertv3.softalertv3.softalert.DAOInterno.DAO;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

import softalertv3.softalertv3.softalert.DAOInterno.DadosOpenHelper;
import softalertv3.softalertv3.softalert.Model.UsuarioCliente;
import softalertv3.softalertv3.softalert.Uteis.Geral;

public abstract  class UsuarioClienteDAO {

    public static String getTabelaUsuarioCliente() {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE IF NOT EXISTS UsuarioCliente ( ");
        sql.append("       id               INTEGER PRIMARY KEY, ");
        sql.append("       nome             VARCHAR(50) NOT NULL, ");
        sql.append("       cpf              VARCHAR(15) NOT NULL, ");
        sql.append("       dataNascimento   DATETIME NOT NULL) ");

        return sql.toString();
    }

    public static void inserir(UsuarioCliente uc) throws Exception{
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", uc.getId());
        contentValues.put("nome", uc.getNome());
        contentValues.put("cpf", uc.getCpf());
        contentValues.put("dataNascimento", uc.getDataNascimento().toString());

        DadosOpenHelper.getConexao().insertOrThrow("UsuarioCliente",null,contentValues);
    }

    public static void alterar(UsuarioCliente uc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", uc.getId());
        contentValues.put("nome", uc.getNome());
        contentValues.put("cpf", uc.getCpf());
        contentValues.put("dataNascimento", uc.getDataNascimento().toString());

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(uc.getId());

        DadosOpenHelper.getConexao().update("UsuarioCliente", contentValues, "id = ?", parametros);
    }

    public static UsuarioCliente buscarCliente(){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id, nome,cpf, dataNascimento FROM UsuarioCliente");

        Cursor resultado = DadosOpenHelper.getConexao().rawQuery(sql.toString(), null);

        if(resultado.getCount() <= 0) {
            return null;
        }

        resultado.moveToFirst();

        UsuarioCliente usuarioCliente = new UsuarioCliente();

        usuarioCliente.setId(resultado.getInt(resultado.getColumnIndexOrThrow("id")));
        usuarioCliente.setCpf(resultado.getString(resultado.getColumnIndexOrThrow("cpf")));
        usuarioCliente.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));

        Date dataNascimento = Geral.geraData("DD/MM/YYYY",resultado.getString(resultado.getColumnIndexOrThrow("dataNascimento")));
        usuarioCliente.setDataNascimento(dataNascimento);

        return usuarioCliente;
    }
}