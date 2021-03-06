package com.nino.voicerecognition;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class VoiceRecognitionDemo extends Activity
{
 
    private static final int REQUEST_CODE = 1234;
    private ListView wordsList;
 
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recog);
 
        Button speakButton = (Button) findViewById(R.id.speakButton);
 
        wordsList = (ListView) findViewById(R.id.list);
 
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }
 
    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
 
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice recognition Demo...");
        startActivityForResult(intent, REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
        /*    wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                    matches));*/
            
            for (int i=0;i<matches.size();i++)
            {
            	if(matches.get(i).contains("open"))
            	{
            		String url = "http://www.google.com";
            		/*Intent intent;
            		 intent = new Intent(Intent.ACTION_VIEW);
            		intent.setData(Uri.parse(url));*/
            	////	startActivity(intent);
            		String packageName = "com.android.browser"; 
            		String className = "com.android.browser.BrowserActivity"; 
            		Intent internetIntent = new Intent(Intent.ACTION_VIEW);
            		internetIntent.addCategory(Intent.CATEGORY_LAUNCHER); 
            		internetIntent.setClassName(packageName, className);
            		internetIntent.setData(Uri.parse(url));
            		startActivity(internetIntent);
            	}
            	
            
            }
            
            for(int i=0;i<matches.size();i++)
            	
            	if (matches.get(i).contains("open")==false)
            	{
            		 		wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                                matches));
                	
            	}
            	
            
            
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}