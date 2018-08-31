package com.br.sp.perez.leandro.sorteionrosbingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txtViewSortear;
    private Button btnSortear;
    private TextView txtViewReset;
    private Button btnReset;
    private TextView txtViewMostraNumerosJaSorteados;


    private ArrayList<Integer> listaNumerosSorteados;
    // Gerador de números randômicos
    private Random geradorNumeroRandomico;
    private static int qtdMaxNumerosParaSorteio = 65;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LOG_TESTE", "onCreate()");
        setContentView(R.layout.activity_main);

        //txtViewSortear = findViewById(R.id.txtViewSortear); //não precisa
        btnSortear = findViewById(R.id.btnSortear);
        btnReset = findViewById(R.id.btnResetarSorteio);
        txtViewMostraNumerosJaSorteados = findViewById(R.id.textViewMostraNumerosJaSorteados);


        if (listaNumerosSorteados == null) {
            listaNumerosSorteados = new ArrayList<Integer>(qtdMaxNumerosParaSorteio); //Cria uma lista de 75 posições para armazenar os números sorteados
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {  //Salva os dados dinâmicos
        super.onSaveInstanceState(outState);
        Log.i("LOG_TESTE", "onSaveInstanceState");
        outState.putIntegerArrayList("LISTA_NUMEROS_SORTEADOS", listaNumerosSorteados);
        outState.putString("TEXTO_NUMEROS_JA_SORTEADOS", txtViewMostraNumerosJaSorteados.getText().toString());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { //Restaura dados dinâmicos
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("LOG_TESTE", "onRestoreInstanceState()");

        if (savedInstanceState != null) {
            listaNumerosSorteados = savedInstanceState.getIntegerArrayList("LISTA_NUMEROS_SORTEADOS");
            String textoNumerosSorteadosRecuperado = savedInstanceState.getString("TEXTO_NUMEROS_JA_SORTEADOS");
            txtViewMostraNumerosJaSorteados.setText(textoNumerosSorteadosRecuperado);

        }
    }


    public void clickSortear(View view) {
        if (view.getId() == R.id.btnSortear) {

            if (listaNumerosSorteados.size() < qtdMaxNumerosParaSorteio) { //só sorteia se existir espaço disponível para o sorteio
                int numeroSorteado = sortearNumero();
                while (listaNumerosSorteados.contains(numeroSorteado)) { //sorteia um número ainda não sorteado
                    numeroSorteado = sortearNumero();
                }
                listaNumerosSorteados.add(numeroSorteado);
                txtViewMostraNumerosJaSorteados.setText((CharSequence) listaNumerosSorteados.toString()); //adiciona na tela os números já sorteados

                Log.i("LOG_TESTE", "Número sorteado: " + numeroSorteado);

            } else {
                Log.i("LOG_TESTE", "Você já sorteou todos os números");
                Toast.makeText(this, "Você já sorteou todos os números", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void clickResetar(View view) {
        if (view.getId() == R.id.btnResetarSorteio) {
            txtViewMostraNumerosJaSorteados.setText("");
            listaNumerosSorteados.clear();
            Toast.makeText(this, "Reset de números efetuado", Toast.LENGTH_SHORT).show();
        }
    }


    private int sortearNumero() {
        geradorNumeroRandomico = new Random(System.currentTimeMillis());
        int numeroSorteado = geradorNumeroRandomico.nextInt(qtdMaxNumerosParaSorteio); //sorteia números de 0 até (qtdMaxNumerosParaSorteio -1)
        return numeroSorteado + 1; //ajusta número sorteado para ir de 1 até qtdMaxNumerosParaSorteio
    }


}