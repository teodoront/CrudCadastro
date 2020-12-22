package com.example.agendatelefonica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.agendatelefonica.DAO.DAO;
import com.example.agendatelefonica.adapter.RecyclerViewAdapter;
import com.example.agendatelefonica.objetos.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome, editTextIdade;
    Switch swichSexo;
    Button buttonSalvar;

    Context context;
    RecyclerView recyclerView;
    LinearLayout linearLayout;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        editTextNome = findViewById(R.id.editTextNome);
        editTextIdade = findViewById(R.id.editTextIdade);
        swichSexo = findViewById(R.id.switchSexo);
        buttonSalvar = findViewById(R.id.btnSalvar);
        recyclerView = findViewById(R.id.recyclerView);

       buscaBanco();

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaPessoa();
            }
        });
    }

    private void salvaPessoa() {
        if (editTextNome.getText().toString().isEmpty() || editTextNome.getText().toString().equals("")
                || editTextIdade.getText().toString().isEmpty() || editTextIdade.getText().toString().equals("")) {
            Toast.makeText(MainActivity.this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show();
        } else {
            String sexo;
            if (swichSexo.isChecked()) {
                sexo = "F";
            } else {
                sexo = "M";
            }

            DAO dao = new DAO(getApplicationContext());
            Pessoa pessoaInserir = new Pessoa();
            pessoaInserir.setNome(editTextNome.getText().toString());
            pessoaInserir.setSexo(sexo);
            pessoaInserir.setIdade(Integer.parseInt(editTextIdade.getText().toString()));
            dao.inserePessoa(pessoaInserir, null);
            dao.close();
            Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_LONG).show();

            limpaFormulario();

            buscaBanco();

        }
    }

    private void buscaBanco() {
        DAO dao1 = new DAO(MainActivity.this);

        List<Pessoa> pessoas = dao1.buscaPessoa();

        List<String> nomes = new ArrayList<String>();
        List<String> idades = new ArrayList<String>();
        List<String> sexos = new ArrayList<>();

        String[] dados_nomes = new String[]{};
        String[] dados_idade = new String[]{};
        String[] dados_sexo = new String[]{};

        for (Pessoa pessoaBuscado : pessoas){
          nomes.add(pessoaBuscado.getNome());
          idades.add(String.valueOf(pessoaBuscado.getIdade()));
          sexos.add(pessoaBuscado.getSexo());

        }
        dados_nomes = nomes.toArray(new String[0]);
        dados_idade = idades.toArray(new String[0]);
        dados_sexo = sexos.toArray(new String[0]);



        recyclerViewLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(context, dados_nomes, dados_idade, dados_sexo);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void limpaFormulario() {
        editTextNome.setText("");
        editTextNome.requestFocus();
        editTextIdade.setText("");
        swichSexo.setChecked(false);
    }

    @Override
    public void onResume(){
        super.onResume();
        buscaBanco();
    }
}