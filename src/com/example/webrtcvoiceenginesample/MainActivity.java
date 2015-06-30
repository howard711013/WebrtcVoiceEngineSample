package com.example.webrtcvoiceenginesample;

import com.tutk.webrtc.VoE;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	///////////////////////
	private static final String DESTINATION_IP = "127.0.0.1";
	
   
    private int mChannel = -1;
    private boolean mSettingSet = true;
    private int mSettingMenu = 0;
    
    private int mMaxVolume = 0; // Android max level (commonly 5)
 // VoE level (0-255), corresponds to level 4 out of 5
    private int mVolumeLevel = 204;
    
    private int mCodecIndex = 0;
    private int mEcIndex = 0;
    private int mNsIndex = 0;
    private int mAgcIndex = 0;
    private int mVadIndex = 0;
    private int mAudioIndex = 3;

    private EditText mDestinationIp;
	private Button mStart;
	private Button mStop;
	
	private Spinner mSpinner1;
	private Spinner mSpinner2;
	
	private VoE mVoE = new VoE();
	public void LOGD(String str)
	{
		Log.d("VoE",str);
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.TextView01);
        tv.setText("");

        mDestinationIp = (EditText) findViewById(R.id.EditText01);
        mDestinationIp.setText(DESTINATION_IP);

        mStart = (Button) findViewById(R.id.Button01);
        mStart.setText("Start Call");
        mStart.setOnClickListener(this);


        mStop = (Button) findViewById(R.id.Button02);
        mStop.setText("Close app");
        mStop.setOnClickListener(this);

        mSpinner1 = (Spinner) findViewById(R.id.Spinner01);
        String settings[] = { "Audio", "Codec", "Echo Control", "Noise Suppression","Automatic Gain Control","Voice Activity Detection"};
        ArrayAdapter<String> adapterSettings1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,settings);
        mSpinner1.setAdapter(adapterSettings1);
        mSpinner1.setOnItemSelectedListener(this);

        // Setup VoiceEngine
        SetupVoE();

        // Suggest to use the voice call audio stream for hardware volume
        // controls
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        // Get max Android volume and adjust default volume to map exactly to an
        // Android level
        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
        if (mMaxVolume <= 0) {
        	LOGD("Could not get max volume!");
        } else {
            int androidVolumeLevel = (mVolumeLevel * mMaxVolume) / 255;
            mVolumeLevel = (androidVolumeLevel * 255) / mMaxVolume;
        }
        LOGD("Started Webrtc Android Test");

    }
    
    private void SetupVoE() {
        // Create VoiceEngine
    	mVoE.Create(); // Error logging is done in native API wrapper

        // Initialize
        if (0 != mVoE.Init())
        {
        	LOGD("VoE init failed");
        }

        // Create channel
        mChannel = mVoE.CreateChannel();
        if (0 != mChannel) {
        	LOGD("VoE create channel failed : " + mChannel);
        }
    }
    
	@Override
	public void onClick(View v) {
		int id = v.getId();
		
		switch(id)
		{
			case R.id.Button01:
			{
				if (mStart.getText().equals("Stop Call")) 
				{
/*
                        if (stopCall() != -1) {
                            _isCallActive = false;
                            buttonStart.setText("Start Call");
                        }
*/                        
                } else {
/*
                        _destinationIP = ed.getText().toString();
                        if (startCall() != -1) {
                            _isCallActive = true;
                            buttonStart.setText("Stop Call");
                        }
*/                        
                }
			}
			break;
			case R.id.Button02:
			{
/*
                if (!_runAutotest) {
                    ShutdownVoE();
                }
*/                
                finish();
			}
			break;
		}
		
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId())
		{
			case R.id.Spinner01:
			{

                mSettingMenu = position;
                mSettingSet = false;
                
                if (position == 0) {
                    setAudioProperties(2);
                }
                if (position == 1) {
                    if (0 != mVoE.SetSendCodec(mChannel, VoE.CODEC_ISAC)) {
                    	LOGD("VoE set send codec failed");
                    	return;
                    }
                    LOGD("VoE set send codec success");
                }
                if (position == 2) {
                    if (0 != mVoE.SetECStatus(true, VoE.EC_MODE_AEC)) {
                        LOGD("VoE set EC status failed");
                        return;
                    }
                    LOGD("VoE set EC status success");
                }
                if (position == 3) {
                    if (0 != mVoE.SetNSStatus(true, VoE.NS_MODE_LOW_SUPPRESSION)) {
                        LOGD("VoE set NS status failed");
                        return;
                    }
                    LOGD("VoE set NS status success");
                }
                if (position == 4) {

                    if (0 != mVoE.SetAGCStatus(true, VoE.AGC_MODE_ADAPTIVE_DIGITAL)) {
                        LOGD("VoE set AGC status failed");
                        return;
                    }
                    LOGD("VoE set AGC status success");
                }
                if (position == 5) {
                    if (0 != mVoE.SetVADStatus(mChannel, true, VoE.VAD_MODE_HIGH)) 
                    {
                        LOGD("VoE set VAD status failed");
                        return;
                    }
                    LOGD("VoE set VAD status success");

                }
            
			}
			break;
		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		switch(parent.getId())
		{
			case R.id.Spinner01:
			{
				LOGD("No setting1 selected");
			}
			break;
			case R.id.Spinner02:
			{
				
			}
			break;			
		}
		
	}

	
    private int setAudioProperties(int val) {

        if (val == 0) {
            // _streamVolume =
            int androidVolumeLevel = (mVolumeLevel * mMaxVolume) / 255;
            LOGD("androidVolumeLevel : " + androidVolumeLevel);
            if (androidVolumeLevel < mMaxVolume) {
            	mVolumeLevel = ((androidVolumeLevel + 1) * 255) / mMaxVolume;
                if (0 != mVoE.SetSpeakerVolume(mVolumeLevel)) {
                    LOGD("VoE set speaker volume failed");
                }
            }
        } else if (val == 1) {
            // _streamVolume =
            int androidVolumeLevel = (mVolumeLevel * mMaxVolume) / 255;
            LOGD("androidVolumeLevel : " + androidVolumeLevel);
            if (androidVolumeLevel > 0) {
            	mVolumeLevel = ((androidVolumeLevel - 1) * 255) / mMaxVolume;
                if (0 != mVoE.SetSpeakerVolume(mVolumeLevel)) {
                    LOGD("VoE set speaker volume failed");
                }
            }
        } else if (val == 2) {
            // route audio to back speaker
            if (0 != mVoE.SetLoudspeakerStatus(true)) {
                LOGD("VoE set loudspeaker status failed");
            }
            mAudioIndex = 2;
        } else if (val == 3) {
            // route audio to earpiece
            if (0 != mVoE.SetLoudspeakerStatus(false)) {
                LOGD("VoE set loudspeaker status failed");
            }
            mAudioIndex = 3;
        }

        return 0;
    }
}
