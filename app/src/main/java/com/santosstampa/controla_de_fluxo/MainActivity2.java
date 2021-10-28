package com.santosstampa.controla_de_fluxo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.text.format.DateFormat;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity2 extends AppCompatActivity {

    ImageView dataPickInicial, dataPickFinal;
    static EditText editTextDataini, editTextDatafim;
    ArrayList<String> arrayList2, arrayList3;
    int contei;
    BancoDados db = new BancoDados(this);
    ListView listviewclientes2;
    Date dataInicial, dataFinal, novadata;
    String dataInicial1, dataFinal1,novadata1;
    SimpleDateFormat formataData2 = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dataPickInicial = findViewById(R.id.dataPickInicial);
        dataPickFinal = findViewById(R.id.dataPickFinal);
        editTextDataini = findViewById(R.id.editTextDataini);
        editTextDatafim = findViewById(R.id.editTextDatafim);
        listviewclientes2 = (ListView) findViewById(R.id.listviewclientes2);

        Calendar calendario = Calendar.getInstance();
        Integer dia = calendario.get(Calendar.DAY_OF_MONTH);
        Integer mes = calendario.get(Calendar.MONTH);
        Integer ano = calendario.get(Calendar.YEAR);
        editTextDataini.setText(dia + "/"+ (mes+1) + "/" + ano);
        editTextDatafim.setText(dia + "/"+ (mes+1) + "/" + ano);

        dataPickInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePicker();
                dialogFragment.show(getSupportFragmentManager(),"DataInicial");
            }

        });
        dataPickFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePicker();
                dialogFragment.show(getSupportFragmentManager(),"DataFinal");
            }

        });



 }

    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle saveInstanceState){
            Calendar calendario = Calendar.getInstance();
            Integer dia = calendario.get(Calendar.DAY_OF_MONTH);
            Integer mes = calendario.get(Calendar.MONTH);
            Integer ano = calendario.get(Calendar.YEAR);

            DatePickerDialog formatoMeu = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, this, ano, mes,dia);


            return formatoMeu;
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int ano, int mes, int dia) {

            String tagClicada = getTag();

            if (tagClicada.equals("DataInicial")){
                editTextDataini.setText(dia + "/" + (mes+1) + "/" + ano);
            }else {
                editTextDatafim.setText(dia + "/" + (mes+1) + "/" + ano);
            }


        }
    }
    public void contarclientes(View view) {
        List<Cliente> clientes = db.listatodosClientes();
        arrayList2 = new ArrayList<String>();
        arrayList3 = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity2.this, android.R.layout.simple_list_item_1, arrayList3);
        listviewclientes2.setAdapter(adapter);
        dataInicial1 = String.valueOf(editTextDataini.getText());
        dataInicial1=dataInicial1.replaceAll("/","-");
        dataFinal1 = String.valueOf(editTextDatafim.getText());
        dataFinal1=dataFinal1.replaceAll("/","-");


        for (Cliente c : clientes) {
            //novadata = novadata.parse(c.getEmail());
            novadata1 =c.getEmail().substring(0, 10);
            try {
                dataInicial = formataData2.parse(dataInicial1);
                dataFinal = formataData2.parse(dataFinal1);
                novadata = formataData2.parse(novadata1);
                //novadata1= formataData2.format(novadata);
            } catch (ParseException e) {
                //novadata="";
            }
            if (dataInicial.compareTo(novadata)<1 && novadata.compareTo(dataFinal)<1) {
                /*arrayList.add(c.getCodigo() + "-" + c.getTelefone() + " , " + c.getEmail() + " , " + c.getClassificacao() + " , " + c.getNome());
                novadata1= formataData2.format(novadata);
                dataInicial1= formataData2.format(dataInicial);
                dataFinal1= formataData2.format(dataFinal);*/
                arrayList2.add(c.getClassificacao());

                adapter.notifyDataSetChanged();
            }
        }
        Set<String> arrayList4 = new HashSet<String>(arrayList2);
        for (String x: arrayList4){
            contei = Collections.frequency(arrayList2,x)/2;
            arrayList3.add(x + " = " + String.valueOf(contei));
        }
        Collections.sort(arrayList3);
        adapter.notifyDataSetChanged();
        Toast.makeText(MainActivity2.this,"Finalizado", Toast.LENGTH_LONG).show();
        //test.setText(String.valueOf(arrayList3));
    }

}