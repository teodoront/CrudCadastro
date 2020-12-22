package com.example.agendatelefonica.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.agendatelefonica.objetos.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {
        super(context, "banco", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE pessoa(nome TEXT UNIQUE, sexo TEXT, idade Integer);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa;";
        db.execSQL(sql);
        onCreate(db);
    }

    public List<Pessoa> buscaPessoa() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM pessoa;";

        Cursor c = db.rawQuery(sql, null);

        List<Pessoa> pessoas = new ArrayList<Pessoa>();

        while (c.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(c.getString(c.getColumnIndex("nome")));
            pessoa.setSexo(c.getString(c.getColumnIndex("sexo")));
            pessoa.setIdade(Integer.parseInt(c.getString(c.getColumnIndex("idade"))));
            pessoas.add(pessoa);
        }
        return pessoas;
    }

    public void inserePessoa(Pessoa pessoa, String pessoaParaAtualizar) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();

        dados.put("idade", pessoa.getIdade());
        dados.put("sexo", pessoa.getSexo());

        if(pessoaParaAtualizar != null){
            dados.put("nome", pessoaParaAtualizar);
        }else {
            dados.put("nome", pessoa.getNome());
        }

       // Log.d("recebeu", pessoaParaAtualizar);
        //Log.d("NomeNoObjeto", pessoa.getNome());
        try {
            db.insertOrThrow("pessoa", null, dados);
         //   Log.d("Executou", "o try");
        } catch (SQLiteConstraintException e) {
            dados.put("nome", pessoa.getNome());
            db.update("pessoa", dados, "nome = ?", new String[]{pessoaParaAtualizar});
         //   Log.d("Executou", "o catch");
        }


    }

    public void apagaPessoa(String nome) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM pessoa WHERE nome = " + "'" + nome + "'";
        db.execSQL(sql);

    }


}
