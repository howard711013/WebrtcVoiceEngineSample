package com.tutk.webrtc;

import android.util.Log;

public class VoE {
	
	static
	{
		try{
		System.loadLibrary("WebRtc");
		}catch(UnsatisfiedLinkError e)
		{
			Log.e("VoE", e.getMessage());
		}
	}
	
	private native int nativeInit();
	private native boolean nativeCreate();
	private native int nativeCreateChannel();
	private native int nativeSetVADStatus(int channel, boolean enable, int mode);
	private native int nativeSetAGCStatus(boolean enable, int mode);
	private native int nativeSetNSStatus(boolean enable, int mode);
	private native int nativeSetECStatus(boolean enable, int mode);
	private native int nativeSetSendCodec(int channel , int codecIndex);
	private native int nativeSetSpeakerVolume(int leavl);
	private native int nativeSetLoudspeakerStatus(boolean enable);
	
	public static final int VAD_MODE_ALREADY_SET = 0;
	public static final int VAD_MODE_LOW = 1;
	public static final int VAD_MODE_MID = 2;
	public static final int VAD_MODE_HIGH = 3;
	
	public static final int AGC_MODE_UNCHANGED = 0;
	public static final int AGC_MODE_ALERADY_SET = 1;
	public static final int AGC_MODE_ADAPTIVE_ANALOG = 2;
	public static final int AGC_MODE_ADAPTIVE_DIGITAL = 3;
	public static final int AGC_MODE_FIXED_DIGOTAL = 4;
	
	public static final int NS_MODE_UNCHANGED = 0;
	public static final int NS_MODE_ALREADY_SET = 1;
	public static final int NS_MODE_CONFERENCE = 2;
	public static final int NS_MODE_LOW_SUPPRESSION = 3;
	public static final int NS_MODE_MODERATE_SUPPRESSION = 4;
	public static final int NS_MODE_HIGH_SUPPRESSION = 5;
	public static final int NS_MODE_VERY_HIGH_SUPPRESSION = 6;
	
	
	public static final int EC_MODE_DEFAULT = 0;
	public static final int EC_MODE_ALREADY_SET = 1;
	public static final int EC_MODE_CONFERENCE = 2;
	public static final int EC_MODE_AEC = 3;
	public static final int EC_MODE_AECM =4;
	
	public static final int CODEC_ISAC = 0;
	public static final int CODEC_PCMU = 1;
	public static final int CODEC_PCMA = 2;
	public static final int CODEC_ILBC = 3;
	public static final int CODEC_G729 = 4;
	

	public boolean Create()
	{
		return nativeCreate();
	}
	
	public int Init()
	{
		return nativeInit();
	}
	
	public int CreateChannel()
	{
		return nativeCreateChannel();
	}
	
	public int SetLoudspeakerStatus(boolean enable)
	{
		return nativeSetLoudspeakerStatus(enable);
	}
	public int SetSpeakerVolume(int leavl)
	{
		return nativeSetSpeakerVolume(leavl);
	}
	public int SetSendCodec(int channel , int codecIndex)
	{
		return nativeSetSendCodec(channel , codecIndex);
	}
	public int SetECStatus(boolean enable, int mode)
	{
		return nativeSetECStatus(enable,mode);
	}
	
	public int SetNSStatus(boolean enable, int mode)
	{
		return nativeSetNSStatus(enable,mode);
	}
	
	public int SetAGCStatus(boolean enable, int mode)
	{
		return nativeSetAGCStatus(enable,mode);
	}

	public int SetVADStatus(int channel , boolean enable, int mode)
	{
		return nativeSetVADStatus(channel, enable, mode);
	}
}
