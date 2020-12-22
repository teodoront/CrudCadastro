package com.example.crudcadastro.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudcadastro.DadosPessoaisActivity;
import com.example.crudcadastro.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<String> nomes = new ArrayList<String>();
    String[] idades;
    String[] sexos;
    View viewOnCreate;
    ViewHolder viewHolderLocal;



    public RecyclerViewAdapter(Context contextRecebido, String[] nomesRecebidos, String[] idadesRecebidos, String[]sexosRecebidos){
        context = contextRecebido;
        nomes.addAll(Arrays.asList(nomesRecebidos));
        idades = idadesRecebidos;
        sexos = sexosRecebidos;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textNome;
        public TextView textIdade;
        public ImageView icone;

        public ViewHolder(View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.textNome);
            textIdade = itemView.findViewById(R.id.textIdade);
            icone = itemView.findViewById(R.id.icone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Teste", "click");
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewOnCreate = LayoutInflater.from(context).inflate(R.layout.recyclerview_itens, parent, false);
        viewHolderLocal = new ViewHolder(viewOnCreate);

        return viewHolderLocal;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textNome.setText(nomes.get(position));
        holder.textIdade.setText(idades[position]);

        if (sexos[position].equals("F")){
            holder.icone.setImageResource(R.drawable.ic_woman);
        }else {
            holder.icone.setImageResource(R.drawable.ic_man);
        }


        viewOnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DadosPessoaisActivity.class);
                intent.putExtra("nome", nomes.get(position));
                intent.putExtra("idade", idades[position]);
                intent.putExtra("sexo", sexos[position]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivities(new Intent[]{intent});

                /**
                DAO dao = new DAO(context);
                dao.apagaPessoa(nomes.get(position));
                nomes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, nomes.size());
                 **/
            }
        });

    }

    @Override
    public int getItemCount() {
        return nomes.size();
    }
}
