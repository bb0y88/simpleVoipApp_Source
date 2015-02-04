package com.tuenti.voice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class VoiceClientActivity
    extends Activity
    implements View.OnClickListener, VoiceClientEventCallback
{
// ------------------------------ FIELDS ------------------------------

    private static final String TAG = "VoiceClientActivity";

    private static final String MY_XMPP_SERVER = "talk.google.com";

    private static final boolean USE_SSL = true;

    private static Vibrator mVibrator;

    private AudioManager mAudioManager;

    private VoiceClient mClient;
    //test zone
    private SharedPreferences mSettings;
    //AdMob
    private AdView adView;
    
    //UPD
    private AlertDialog alert = null;
    private LayoutInflater inflater = null;

// ------------------------ INTERFACE METHODS ------------------------

// --------------------- Interface OnClickListener ---------------------

    public void onClick( View view )
    {    	
        switch ( view.getId() )
        {
        case R.id.para_btn:
        {  
    
        	//AlertDialog alertWifi = null;
        	if(!isWifi()){
		      	
	        	//LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        		if(null == this.inflater ){
        			this.inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        		}
	        	graphic.alertDialog.Builder nowifi = new graphic.alertDialog.Builder(this, inflater);
	
	        	this.alert = nowifi.getAlert(R.layout.alert_dialog_1);
	        	this.alert.show();
			 	
	        	break;
	        }
        	
        	if(isWifi() && null != this.alert && this.alert.isShowing())
        		this.alert.cancel();
	       
            
	//     // Otteniamo il riferimento alle Preferences
	        if(null == this.mSettings)		//TODO spostare nell init
	        	this.mSettings = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
	     	
	        if(null == this.inflater){		
	        	this.inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        }
	        graphic.alertDialog.Builder login = new graphic.alertDialog.Builder(this, this.inflater);
	        //this.alert = login.getAlert(R.layout.login_alert_dialog); 
	        this.alert = login.getAlertLogin(R.layout.login_alert_dialog, this.mSettings);
	        this.alert.show(); 
        
        }//fine case para_btn
        break;
                
       case R.id.place_call_btn:{ 
	       
    	   // Otteniamo il riferimento alle Preferences
	       if(null == this.mSettings)		//TODO spostare nell init
	    	   this.mSettings = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
	     	
	        if(null == this.inflater){		
	        	this.inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        }
    	   
	        graphic.alertDialog.Builder call = new graphic.alertDialog.Builder(this, this.inflater);
	        this.alert = call.getAlertCall(R.layout.call_alert_dialog, this.mSettings);
	        this.alert.show(); 
                
        }//fine case para_btn
        break;
        
       case R.id.hang_up_btn:
                mClient.endCall();
                changeStatus( "call closed" );
                //quando l'azione proviene dal cell chiamante meglio gestire qua il cambiamento di status
                refresh();
                break;
            case R.id.accept_call_btn:
                mClient.acceptCall();
                break;
            case R.id.decline_call_btn:
                mClient.declineCall();
                changeStatus( "call rejected" );
              //quando l'azione proviene dal cell chiamante meglio gestire qua il cambiamento di status
                refresh();
                break;
        }
    }

// --------------------- Interface VoiceClientEventCallback ---------------------

    @Override
    public void handleCallStateChanged( int state, String remoteJid )
    {
        switch ( state )
        {	//il cambiamento di stato è riconosciuto solo quando l'azione è compiuta dall'altra app.
        	
            case VoiceClient.CALL_ANSWERED:
                mVibrator.cancel();
                changeStatus( "call answered" );                
              //test zone
                Button acceptCallA=(Button)findViewById(R.id.accept_call_btn);
                acceptCallA.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button placeCallA=(Button)findViewById(R.id.place_call_btn);
                placeCallA.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button hangUpA=(Button)findViewById(R.id.hang_up_btn);
            	hangUpA.setVisibility(0); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button declineCallA=(Button)findViewById(R.id.decline_call_btn);
            	declineCallA.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!
            	//end test zone
                break;

            case VoiceClient.CALL_CALLING:
                changeStatus( "calling..." );
                //test zone
                Button acceptCallB=(Button)findViewById(R.id.accept_call_btn);
                acceptCallB.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button placeCallB=(Button)findViewById(R.id.place_call_btn);
                placeCallB.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
              //test zone
                Button hangUpB=(Button)findViewById(R.id.hang_up_btn);
            	hangUpB.setVisibility(0); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button declineCallB=(Button)findViewById(R.id.decline_call_btn);
            	declineCallB.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!
            	//end test zone
                break;

            case VoiceClient.CALL_INCOMING:
                changeStatus( "incoming call from " + remoteJid );
                mVibrator.vibrate( 300 );
                //test zone
                Button acceptCallC=(Button)findViewById(R.id.accept_call_btn);
            	acceptCallC.setVisibility(0); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button placeCallC=(Button)findViewById(R.id.place_call_btn);
                placeCallC.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!
            	 //test zone
                Button hangUpC=(Button)findViewById(R.id.hang_up_btn);
            	hangUpC.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button declineCallC=(Button)findViewById(R.id.decline_call_btn);
            	declineCallC.setVisibility(0); //To set visible(0), to set invisible (4)
            	//!!!
            	//end test zone            	
                break;

            case VoiceClient.CALL_RECIVEDTERMINATE:
                mClient.endCall();
                changeStatus( "Call hung up" );
                //test zone
                Button acceptCallD=(Button)findViewById(R.id.accept_call_btn);
            	acceptCallD.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!
                //test zone
                Button placeCallD=(Button)findViewById(R.id.place_call_btn);
            	placeCallD.setVisibility(0); //To set visible(0), to set invisible (4)
            	//ok working!!!
            	//test zone
                Button hangUpD=(Button)findViewById(R.id.hang_up_btn);
            	hangUpD.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!//test zone
                Button declineCallD=(Button)findViewById(R.id.decline_call_btn);
            	declineCallD.setVisibility(4); //To set visible(0), to set invisible (4)
            	//!!!
            	//end test zone
                break;
        }
    }

    @Override
    public void handleXmppEngineStateChanged( int state, String message )
    {
        changeStatus( message );
    }

    @Override
    public void handleXmppError( int error )
    {
        Log.e( TAG, "error code " + error );
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );
        
        initAudio();
        initVibration();
        initClient();
        
        //test zone->working!
        mClient.init();
        
        Button parametres=(Button)findViewById(R.id.para_btn);		//attiva il pulsante login solo dopo aver avviato il client
        parametres.setVisibility(0); //To set visible				//ok working!!!
        //end test zone
        
        //AdMob
        // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());
        //AdMob
        
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mClient.destroy();
    }
    
    private void changeStatus( String status )
    {
        ( (TextView) findViewById( R.id.status_view ) ).setText( status );
    }

    private void initAudio()
    {
        //If it's playing audio out of the speaker, switch this to get earpiece.
        if ( mAudioManager == null )
        {
            mAudioManager = (AudioManager) getSystemService( Context.AUDIO_SERVICE );
        }

        if ( mAudioManager == null )
        {
            Log.e( TAG, "Could not change audio routing - no audio manager" );
        }
        /*else
        {
            mAudioManager.setMode( AudioManager.MODE_IN_CALL );
        }*/
        else
        {
            mAudioManager.setMode( ( Build.VERSION.SDK_INT <= 16 )
        	 //mAudioManager.setMode( ( Build.VERSION.SDK_INT < 11 )
                                       ? AudioManager.MODE_IN_CALL
                                       : AudioManager.MODE_IN_COMMUNICATION );
            mAudioManager.requestAudioFocus( null, AudioManager.STREAM_VOICE_CALL, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );
        }
    }

    private void initClient()
    {
        VoiceClientEventHandler handler = new VoiceClientEventHandler( this );

        mClient = VoiceClient.getInstance();
        mClient.setHandler( handler );

        findViewById( R.id.para_btn ).setOnClickListener( this );
        findViewById( R.id.place_call_btn ).setOnClickListener( this );
        findViewById( R.id.hang_up_btn ).setOnClickListener( this );
        findViewById( R.id.accept_call_btn ).setOnClickListener( this );
        findViewById( R.id.decline_call_btn ).setOnClickListener( this );
    }

    private void initVibration()
    {
        mVibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
    }
    
    //handler per la distruzione del progress dialog
    public void timerDelayRemoveDialog(long time, final Dialog d){
        Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() {           
            public void run() {                
                d.dismiss();         
            }
        }, time); 
    }
    
    private void refresh()
    {
    	//test zone
        Button acceptCallF=(Button)findViewById(R.id.accept_call_btn);
    	acceptCallF.setVisibility(4); //To set visible(0), to set invisible (4)
    	//!!!
    	//test zone
        Button placeCallF=(Button)findViewById(R.id.place_call_btn);
    	placeCallF.setVisibility(0); //To set visible(0), to set invisible (4)
    	//
    	//test zone
        Button hangUpF=(Button)findViewById(R.id.hang_up_btn);
    	hangUpF.setVisibility(4); //To set visible(0), to set invisible (4)
    	//!!!//test zone
        Button declineCallF=(Button)findViewById(R.id.decline_call_btn);
    	declineCallF.setVisibility(4); //To set visible(0), to set invisible (4)
    	//!!!
    }

	private boolean isWifi(){
		 
		// Prendiamo dal context il ConnectivityManager
		 ConnectivityManager connManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 // Prendiamo le informazioni della connessione mobile
		 NetworkInfo netInfo= connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		 // Prendiamo le informazioni della connessione WiFi
		 NetworkInfo wifiInfo= connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	 
		 if ( netInfo.getState() != NetworkInfo.State.CONNECTED && wifiInfo.getState() != NetworkInfo.State.CONNECTED )
			 {
			 	Log.d(TAG, "Il telefono non è connesso ad internet");
			 	return false;
			 }
			 else if(wifiInfo.getState() != NetworkInfo.State.CONNECTED)
			 {
			     Log.d(TAG, "I dati da scaricare sono molti, vi consigliamo di connettersi tramite WiFi");
			 }
	
		 return true;
	 }
    

	//---------------------------------- PARTE ONCLICK LISTEER---------------------------------// 
	
	public void enableWifi(View view){
		
		//piu completo mostra pure dati mobili
		startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));		
		//TODO WORKING
		//startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
		
	}
	 
	public void cancel(View view){
		
		//System.out.println("test");
//		View viewAlert = findViewById(R.layout.alert_dialog_1);
//		viewAlert.findFocus();
//		removeDialog(R.layout.alert_dialog_1);
//		DialogFragment.this.dismiss();
		
		if(this.alert.isShowing()){
			this.alert.cancel();
		}
		//dismissDialog(DELETE_ALL_DIALOG); // thats what you are looking for
		//EditText edit = (EditText) ((AlertDialog) dialog).findViewById(R.id.the_id_of_view);
		/*
		view.r
		dialog.cancel();
		*/
	}
	
	public void loginUser(View view){
		    
		final EditText input1 = (EditText) this.alert.findViewById(R.id.EditText1);
	    final EditText input2 = (EditText) this.alert.findViewById(R.id.EditText2);
		
	    Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
	    Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
	    final String MY_USER = input1.getText().toString();  	//trasforma l input in String
	    final String MY_PASS = input2.getText().toString();		//trasforma l input in String
	    
	    // Otteniamo il riferimento alle Preferences
		//SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
	    if(null == this.mSettings)
	    	this.mSettings = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
		// Otteniamo il corrispondente Editor
		final SharedPreferences.Editor editor = this.mSettings.edit();
	    
	    // Li salviamo permanentemente nelle Preferences
	 	editor.putString(TEXT_DATA_KEY1, MY_USER);
	 	editor.putString(TEXT_DATA_KEY2, MY_PASS);
	 	//operazione di .commit() necessaria per rendere effettive le modifiche
	 	editor.commit();
	    //fine ShPr
	 	
	 	 mClient.login( MY_USER , MY_PASS, MY_XMPP_SERVER, USE_SSL );
	 	
	    if ( mClient.login( MY_USER , MY_PASS, MY_XMPP_SERVER, USE_SSL ) == true  ){
	    	Button loginB=(Button)findViewById(R.id.para_btn);
	    	loginB.setVisibility(4); //To set visible(0), to set invisible (4)
	    	
	    	Button placeCallB=(Button)findViewById(R.id.place_call_btn);
	    	placeCallB.setVisibility(0); //To set visible(0), to set invisible (4)
	    	//progress dialog to waiting login successly
	    	ProgressDialog waitDialog = ProgressDialog.show(VoiceClientActivity.this, "", 
	                "Logging in, Please wait...", true);
	    	//handler per terminare il progress dialog
	    	timerDelayRemoveDialog(5500, waitDialog);
	    	
	    	if(this.alert.isShowing())
	    		this.alert.cancel();
	    	}
        //end testZone

	}
	 
	public void callUser(View view){
			
		final EditText input3 = (EditText) this.alert.findViewById(R.id.EditText2a);
		
		Log.i("AlertDialog","TextEntry 1 Entered "+input3.getText().toString());
      
		final String TO_USER = input3.getText().toString();  	//trasforma l input in String
		
		 // Otteniamo il riferimento alle Preferences
    	 if(null == this.mSettings)
        	this.mSettings = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    	// Otteniamo il corrispondente Editor
    	final SharedPreferences.Editor editor = this.mSettings.edit();
		
		// Li salviamo permanentemente nelle Preferences
		editor.putString(TEXT_DATA_KEY3, TO_USER);
		//operazione di .commit() necessaria per rendere effettive le modifiche
		editor.commit();
		//fine ShPr
		
		mClient.call( TO_USER );
		
		if (  mClient.call( TO_USER ) == true  ){	
			Button placeCallB=(Button)findViewById(R.id.place_call_btn);
			placeCallB.setVisibility(4); //To set visible(0), to set invisible (4)
			
			if(this.alert.isShowing())
        		this.alert.cancel();
        	
			
		}     //end testZone

	}
	 
	//---------------------------------- PARTE SHARED PREFERENCES---------------------------------//
 
	 //Parte SharedPreferences
	 // Identificatore delle preferenze dell'applicazione
	    private final static String MY_PREFERENCES = "MyPref";
	    // Costanti relative al nome della particolare preferenza
	    private final static String TEXT_DATA_KEY1 = "usernameSP";
	    private final static String TEXT_DATA_KEY2 = "passwordSP";
	    private final static String TEXT_DATA_KEY3 = "tousernameSP";
}//fine classe
