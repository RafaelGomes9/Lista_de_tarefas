package com.cursoandroid.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.cursoandroid.listadetarefas.R;
import com.cursoandroid.listadetarefas.adapter.TarefaAdapter;
import com.cursoandroid.listadetarefas.helper.DbHelper;
import com.cursoandroid.listadetarefas.helper.RecyclerItemClickListener;
import com.cursoandroid.listadetarefas.helper.TarefaDAO;
import com.cursoandroid.listadetarefas.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
   private TarefaAdapter tarefaAdapter;
   private List<Tarefa> listaTarefas= new ArrayList<>();
   private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView= findViewById(R.id.recyclerView);








        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


              Tarefa tarefaSelecionada= listaTarefas.get(position);
              Intent intent= new Intent(MainActivity.this,AdicionarTarefaActivity.class);
              intent.putExtra("tarefaSelecionada",tarefaSelecionada);
              startActivity(intent);




            }

            @Override
            public void onLongItemClick(View view, int position) {


                tarefaSelecionada= listaTarefas.get(position);



                AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);

                dialog.setTitle("Confirmar exclus??o");
                dialog.setMessage("Deseja excluir a tarefa: "+tarefaSelecionada.getNomeTarefa()+"?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        TarefaDAO tarefaDAO= new TarefaDAO(getApplicationContext());
                        if(tarefaDAO.deletar(tarefaSelecionada))
                        {

                            carregarListaTarefas();
                            Toast.makeText(getApplicationContext(),"Sucesso ao excluir tarefa!",Toast.LENGTH_SHORT).show();



                        }

                       else {



                            Toast.makeText(getApplicationContext(),"Erro ao excluir tarefa!",Toast.LENGTH_SHORT).show();




                        }






                    }
                });


                dialog.setNegativeButton("N??o",null);
                dialog.create();
                dialog.show();









            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent= new Intent(getApplicationContext(),AdicionarTarefaActivity.class);
                startActivity(intent);




            }
        });
    }


    public void carregarListaTarefas()
    {


        TarefaDAO tarefaDAO= new TarefaDAO(getApplicationContext());
        listaTarefas= tarefaDAO.listar();




        tarefaAdapter= new TarefaAdapter(listaTarefas);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(tarefaAdapter);







    }


    @Override
    protected void onStart() {
        super.onStart();

        carregarListaTarefas();




    }




}