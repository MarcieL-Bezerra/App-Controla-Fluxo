package com.santosstampa.controla_de_fluxo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.santosstampa.controla_de_fluxo.R.id.telaadm_menu;

public class MainActivity extends AppCompatActivity {
    TextView test, clientes;
    EditText editado;
    Button btnentrada, btnsaida, btnDataini, btnDatafim;
    String tipodecliente;
    String data;
    int clientesemloja;
    String dataFormatada;
    String quantidade;
    String classificacao;
    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    ListView listviewclientes;
    Spinner lvOpcoes;
    MenuItem compartilhar;


    String[] todasopcoes = new String[]{"Igreja", "Rede de Crianças", "Estacionamento", "Opção manual"};

    BancoDados db = new BancoDados(this);


    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (TextView) findViewById(R.id.textView2);
        editado= (EditText) findViewById(R.id.TxtEditado);
        clientes = (TextView) findViewById(R.id.lblclientes);
        btnentrada = (Button) findViewById(R.id.btnentrada);
        btnsaida = (Button) findViewById(R.id.btnsaida);
        clientesemloja = 0;
        quantidade = " 1 /";
        listviewclientes = (ListView) findViewById(R.id.listviewclientes);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, todasopcoes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvOpcoes = (Spinner) findViewById(R.id.lvopcoes);
        lvOpcoes.setAdapter(adapter2);
        lvOpcoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String escolhendo = (String) lvOpcoes.getSelectedItem();
                Toast.makeText(MainActivity.this,"Local Selecionado - " + escolhendo, Toast.LENGTH_LONG).show();


                if (escolhendo == "Opção manual") {
                    editado.setText("");
                    editado.setVisibility(View.VISIBLE);
                } else if (escolhendo == "Rede de Crianças") {
                    //classificacao = "Crianças";
                    editado.setText("Crianças");
                    editado.setVisibility(View.INVISIBLE);
                }else if (escolhendo == "Estacionamento") {
                    //classificacao = "Veículo";
                    editado.setText("Veículo");
                    editado.setVisibility(View.INVISIBLE);
                } else {
                    //classificacao = "Adulto";
                    editado.setText("Adulto");
                    editado.setVisibility(View.INVISIBLE);
                }




                }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action, keycode;
        action = event.getAction();
        keycode = event.getKeyCode();

        switch (keycode) {
            case KeyEvent.KEYCODE_VOLUME_UP: {
                if (KeyEvent.ACTION_UP == action) {
                    tipodecliente = "Entrada";
                    Date data = new Date();
                    dataFormatada = formataData.format(data);
                    classificacao = editado.getText().toString();
                    clientesemloja += 1;
                    test.setText(tipodecliente);
                    clientes.setText("QTD Atual:   " + String.valueOf(clientesemloja));
                    db.addCliente(new Cliente(quantidade, tipodecliente, dataFormatada, classificacao));
                    lvOpcoes.setEnabled(false);
                    editado.setEnabled(false);

                }
                break;

            }
            case KeyEvent.KEYCODE_VOLUME_DOWN: {
                if (KeyEvent.ACTION_DOWN == action) {
                    if (clientesemloja <= 0) {


                        lvOpcoes.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Não há pessoas!", Toast.LENGTH_LONG).show();

                    } else {

                        tipodecliente = "Saida";
                        Date data = new Date();
                        dataFormatada = formataData.format(data);
                        //test.setText(tipodecliente);
                        classificacao = editado.getText().toString();
                        clientesemloja -= 1;
                        clientes.setText("QTD Atual:   " + String.valueOf(clientesemloja));
                        db.addCliente(new Cliente(quantidade, tipodecliente, dataFormatada, classificacao));
                        if (clientesemloja == 0) {
                            lvOpcoes.setEnabled(true);
                            editado.setEnabled(true);

                        }
                        //listarclientes();
                        //Toast.makeText(MainActivity.this, "adicionado com sucesso", Toast.LENGTH_LONG).show();

                    }
                }
                break;
            }

                }
            return super.dispatchKeyEvent(event);
            }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(MainActivity.this, "Você clicou", Toast.LENGTH_LONG).show();

        if(item.getItemId() == R.id.compartilha_menu) {
            listarclientes();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, test.getText());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, "Relatorio");
            startActivity(shareIntent);

        }else if (item.getItemId() == R.id.telaadm_menu){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);

        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void botaoclicado(View view) {
        boolean checked = ((Button) view).isClickable();

        switch (view.getId()) {
            case R.id.btnentrada:
                if (checked) {
                    tipodecliente = "Entrada";
                    Date data = new Date();
                    dataFormatada = formataData.format(data);
                    clientesemloja += 1;
                    test.setText(tipodecliente);
                    classificacao = editado.getText().toString();
                    clientes.setText("QTD Atual:   "+String.valueOf(clientesemloja));
                    db.addCliente(new Cliente(quantidade,tipodecliente,dataFormatada,classificacao));
                    lvOpcoes.setEnabled(false);
                    editado.setEnabled(false);
                    //listarclientes();
                    //Toast.makeText(MainActivity.this, "adicionado com sucesso", Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.btnsaida:
                if (checked) {
                    if (clientesemloja <= 0) {


                        lvOpcoes.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Não há pessoas!", Toast.LENGTH_LONG).show();

                    }else {

                        tipodecliente = "Saida";
                        Date data = new Date();
                        dataFormatada = formataData.format(data);
                        //test.setText(tipodecliente);
                        classificacao = editado.getText().toString();
                        clientesemloja -= 1;
                        clientes.setText("QTD Atual:   "+String.valueOf(clientesemloja));
                        db.addCliente(new Cliente(quantidade,tipodecliente,dataFormatada,classificacao));
                        if (clientesemloja == 0){
                            lvOpcoes.setEnabled(true);
                            editado.setEnabled(true);

                        }
                        //listarclientes();
                        //Toast.makeText(MainActivity.this, "adicionado com sucesso", Toast.LENGTH_LONG).show();

                    }
                }
                break;

    }
    }

    public void listarclientes() {
        List<Cliente> clientes = db.listatodosClientes();
        arrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listviewclientes.setAdapter(adapter);


        for (Cliente c : clientes) {

            arrayList.add(c.getCodigo() + "-" + c.getTelefone() + " , " + c.getEmail() + " , " + c.getClassificacao() + " , " + c.getNome());
            adapter.notifyDataSetChanged();
        }
        test.setText(String.valueOf(arrayList));
    }


}