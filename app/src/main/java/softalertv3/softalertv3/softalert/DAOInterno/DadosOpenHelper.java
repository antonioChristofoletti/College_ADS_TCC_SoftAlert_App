package softalertv3.softalertv3.softalert.DAOInterno;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import softalertv3.softalertv3.softalert.DAOInterno.DAO.UsuarioClienteDAO;

public class DadosOpenHelper extends SQLiteOpenHelper{

    private static SQLiteDatabase conexao;

    private static DadosOpenHelper dadosOpenHelper;

    public static void criarconexao(Context context) {

        if(dadosOpenHelper != null) {
            conexao.close();
            dadosOpenHelper.close();
        }

        dadosOpenHelper = new DadosOpenHelper(context);

        conexao = dadosOpenHelper.getWritableDatabase();
    }

    public DadosOpenHelper(Context context){
        super(context,"SoftAlert",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioClienteDAO.getTabelaUsuarioCliente());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static SQLiteDatabase getConexao() {
        return conexao;
    }

    public static DadosOpenHelper getDadosOpenHelper() {
        return dadosOpenHelper;
    }
}
