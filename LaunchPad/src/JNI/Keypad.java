package JNI;

import android.util.Log;


public class Keypad implements JNI{
	static {
		System.loadLibrary("keypad");
	}

	public native int Open();

	public native int Close();

	public native int GetValue();

	private int NewValue;


	public Keypad() {
		int res=Open();
		Log.i("hanback","keypad"+res);
	}
	
	@Override
	public int UpdateValue(){
		return 0;
	}

}
