package br.uniararas.mobile.baladanights.fragments;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	EditText edtHora;
	
	public TimePickerFragment(EditText edtHora){
		this.edtHora =  edtHora;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// // Pega da sistema e seta com default
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		

		// Cria uma nova instancia para retorno
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}
	
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		//Evitar erro do websrvice de hora e minuto somente com um numero
		String hora = String.valueOf(hourOfDay);
		String minuto = String.valueOf(minute);

		if(String.valueOf(hourOfDay).length() == 1){
			hora = "0"+hora; 
		}
		if(String.valueOf(minute).length() == 1){
			minuto = "0"+minuto; 
		}
		
		
		String resultado = hora+":"+minuto;
		edtHora.setText(resultado);
	}
}
