package JNI;

import android.util.Log;

public class Dipsw implements JNI {
	static {
		System.loadLibrary("dipsw");
	}

	public native int Open();

	public native int Close();

	public native int GetValue();

	public boolean m_Start;
	private int NewValue;

	public Dipsw() {
		m_Start = false;
		int res = Open();
		Log.i("hanback", "dipsw" + res);
	}

	@Override
	public int UpdateValue() {
		NewValue = GetValue();
		Log.e("hanback","value got "+NewValue);
		{
			if ((NewValue & 0x1) == 0x1) {
				return 1;// "1";
			}
			if ((NewValue & 0x2) == 0x2) {
				return 2;// "2";
			}
			if ((NewValue & 0x4) == 0x4) {
				return 3;// "3";
			}
			if ((NewValue & 0x8) == 0x8) {
				return 4;// "4";
			}
			if ((NewValue & 0x10) == 0x10) {
				return 5;// "5";
			}
			if ((NewValue & 0x20) == 0x20) {
				return 6;// "6";
			}
			if ((NewValue & 0x40) == 0x40) {
				return 7;// "7";
			}
			if ((NewValue & 0x80) == 0x80) {
				return 8;// "8";
			}
			return 1;
		}
	}

}
