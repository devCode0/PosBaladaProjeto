package br.uniararas.mobile.baladanights.fragments;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	EditText edtData;
	
	public DatePickerFragment(EditText edtData){
		this.edtData =  edtData;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Pega da sistema e seta com default
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Cria uma nova instancia para retorno
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		
		//Evitar erro do websrvice de datas somente com um numero
		String dia = String.valueOf(day);
		
		//Evitar o rerro do android que sempre pega um mes a menos
		String mes = String.valueOf(month+1);
		
		if(String.valueOf(day).length() == 1){
			dia = "0"+dia; 
		}
		if(String.valueOf(month).length() == 1){
			mes = "0"+mes; 
		}
		
		
		String resultado = mes+"/"+dia+"/"+String.valueOf(year);
		edtData.setText(resultado);
	}
}
