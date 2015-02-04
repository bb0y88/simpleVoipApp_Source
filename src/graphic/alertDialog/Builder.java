package graphic.alertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tuenti.voice.R;

public class Builder extends AlertDialog.Builder{

	private Context context = null;
	private LayoutInflater inflater = null;

	public Builder(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public Builder(Context context, LayoutInflater inflater) {
		super(context);
		this.context = context;
		this.inflater = inflater;
	}
	
	public AlertDialog getAlert(int res_id){
		
		//View newAlert = inflater.inflate(R.layout.alert_dialog_1, null);
		View newAlert = inflater.inflate(res_id, null);
    	this.setView(newAlert);
    	return this.create();

	}

	 //Parte SharedPreferences
	 // Identificatore delle preferenze dell'applicazione
	    private final static String MY_PREFERENCES = "MyPref";
	    // Costanti relative al nome della particolare preferenza
	    private final static String TEXT_DATA_KEY1 = "usernameSP";
	    private final static String TEXT_DATA_KEY2 = "passwordSP";
	    private final static String TEXT_DATA_KEY3 = "tousernameSP";
	
	public AlertDialog getAlertLogin(int res_id, SharedPreferences prefs){
		View newAlert = inflater.inflate(res_id, null);
		
		final SharedPreferences.Editor editor = prefs.edit();	

        // Leggiamo le informazioni eventualmente salvate...
        String textData1 = prefs.getString(TEXT_DATA_KEY1, "usernameSP");
        String textData2 = prefs.getString(TEXT_DATA_KEY2, "passwordSP");
        ////final View textEntryView = findViewById(R.layout.login_alert_dialog);
		
        final EditText input1 = (EditText) newAlert.findViewById(R.id.EditText1);
        final EditText input2 = (EditText) newAlert.findViewById(R.id.EditText2);
       
     // ...le impostiamo nelle EditText
    	input1.setText(textData1,  TextView.BufferType.EDITABLE);
    	input2.setText(textData2,  TextView.BufferType.EDITABLE);
    	//end SharedPreferences
        //trasforma in ***** la linea password
        input2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        /** UPD */
		
		this.setView(newAlert);
    	 	
    	return this.create();
	}
	
	public AlertDialog getAlertCall(int res_id, SharedPreferences prefs){
		View newAlert = inflater.inflate(res_id, null);

    	 // Leggiamo le informazioni eventualmente salvate...
        String textData3 = prefs.getString(TEXT_DATA_KEY3, "tousernameSP");
         
        final EditText input3 = (EditText) newAlert.findViewById(R.id.EditText2a);
                
        // ...le impostiamo nelle EditText
        input3.setText(textData3,  TextView.BufferType.EDITABLE);
        //end SharedPreferences
    	
		this.setView(newAlert);
    	
    	return this.create();
	}
	
	
	public AlertDialog getAlert(Context context, int res_id){
//		nowifi.setIcon(R.drawable.icon);
//	 	nowifi.setTitle("Connection status");
//	 	nowifi.setMessage("Nessuna connessione internet disponibile...");
		//LayoutInflater inflater = (LayoutInflater)context.getSystemService
	    // (Context.LAYOUT_INFLATER_SERVICE);
		
		/*
		 AlertDialog.Builder nowifi = new Builder(this);
    	LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	//ViewGroup root = (ViewGroup) getLayoutInflater().inflate(R.layout.main, null);
    	//View newAlert = inflater.inflate(R.layout.alert_dialog_1, root);
    	View newAlert = inflater.inflate(R.layout.alert_dialog_1, null);
    	nowifi.setView(newAlert);
    	AlertDialog alert = nowifi.create();
    	alert.show();
		 */
		
		//View newAlert = inflater.inflate(R.layout.alert_dialog_1, null);
		View newAlert = inflater.inflate(res_id, null);
    	this.setView(newAlert);
    	return this.create();
//    	AlertDialog alert = this.create();
//    	alert.show();

//		this.inflater.inflate(res_id, null);
//
//		return this;
	}

	
	//TODO TBD
//	//(ViewGroup)getApplicationContext().findViewById(String.valueOf(R.layout.main))
	public Builder setAlert(Context context, int res_id, ViewGroup root_id){
		
		this.inflater.inflate(res_id, root_id);

		return this;
	}
	
}
