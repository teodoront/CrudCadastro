package com.example.crudcadastro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.crudcadastro.DAO.DAO;
import com.example.crudcadastro.objetos.Pessoa;

public class DadosPessoaisActivity extends AppCompatActivity {

    ImageView dadosPessoaisIconeRecebidos;
    EditText dadosPessoaisEditTxtNomeRecebidos, dadosPessoaisEditTxtIdadeRecebidas;
    Spinner spinnerDadosPessoaisSexo;
    Button btnSalvar, btnDeletar;
    Context context;

    String activity_dados_nome_recebido;
    String activity_dados_sexo_recebidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        context = getApplicationContext();
        dadosPessoaisIconeRecebidos = findViewById(R.id.dados_pessoais_icone);
        dadosPessoaisEditTxtNomeRecebidos = findViewById(R.id.dados_pessoais_nome);
        dadosPessoaisEditTxtIdadeRecebidas = findViewById(R.id.dados_pessoais_idade);
        spinnerDadosPessoaisSexo = findViewById(R.id.dados_pessoais_spinner_sexo);
        btnSalvar = findViewById(R.id.btnSalvarAlter);
        btnDeletar = findViewById(R.id.btnDeletar);

        Intent intent = getIntent();


        if (intent.getStringExtra("sexo").equals("F")) {
            dadosPessoaisIconeRecebidos.setImageResource(R.drawable.ic_woman);
            spinnerDadosPessoaisSexo.setSelection(1);
            activity_dados_sexo_recebidos = "F";
        } else {
            dadosPessoaisIconeRecebidos.setImageResource(R.drawable.ic_man);
            spinnerDadosPessoaisSexo.setSelection(0);
            activity_dados_sexo_recebidos = "M";
        }
        spinnerDadosPessoaisSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerDadosPessoaisSexo.getSelectedItem().equals("Masculino")) {
                    activity_dados_sexo_recebidos = "M";
                } else {
                    activity_dados_sexo_recebidos = "F";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activity_dados_nome_recebido = intent.getStringExtra("nome");
        dadosPessoaisEditTxtNomeRecebidos.setText(intent.getStringExtra("nome"));
        dadosPessoaisEditTxtIdadeRecebidas.setText(intent.getStringExtra("idade"));
        //spinnerDadosPessoaisSexo.set(intent.getStringExtra("sexo"));

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirma = new AlertDialog.Builder(DadosPessoaisActivity.this);
                confirma.setTitle("Alerta!!!");
                confirma.setMessage("Tem certeza que deseja excluir o cadastro " + activity_dados_nome_recebido + "?");
                confirma.setCancelable(false);
                confirma.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletarPessoas();
                    }
                });
                confirma.setNegativeButton("Não", null);
                confirma.show();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizaPessoa();
            }
        });

    }

    private void atualizaPessoa() {
        DAO dao = new DAO(getApplicationContext());
        Pessoa pessoaParaAtualizar = new Pessoa();
        pessoaParaAtualizar.setNome(dadosPessoaisEditTxtNomeRecebidos.getText().toString());
        pessoaParaAtualizar.setIdade(Integer.parseInt(dadosPessoaisEditTxtIdadeRecebidas.getText().toString()));
        pessoaParaAtualizar.setSexo(activity_dados_sexo_recebidos);
        dao.inserePessoa(pessoaParaAtualizar, activity_dados_nome_recebido);
        dao.close();
        finish();
    }

    private void deletarPessoas() {


        DAO dao = new DAO(context);
        dao.apagaPessoa(activity_dados_nome_recebido);
        finish();
    }
}