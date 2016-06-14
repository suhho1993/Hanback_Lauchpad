package core;

import com.Hanback.Launchpad.R;

import JNI.Dipsw;
import JNI.Keypad;
import JNI.TextLcd;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class Controller {
	private Dipsw dipSw;
	private Keypad keyPad;
	private TextLcd tLcd;

	private soundThread mThread;

	private SoundPool m_soundPool;

	private Context mContext;

	private int[] soundKey;
	private int[] soundKey1;
	private int[] soundKey2;
	private int[] soundKey3;
	private int[] soundKey4;

	private static int[] soundBank = { R.raw.dump_1, R.raw.mod1_1,
			R.raw.mod1_2, R.raw.mod1_3, R.raw.mod1_4, R.raw.mod1_5,
			R.raw.mod1_6, R.raw.mod1_7, R.raw.mod1_8, R.raw.mod1_9,
			R.raw.mod1_10, R.raw.mod1_11, R.raw.mod1_12, R.raw.mod1_13,
			R.raw.mod1_14, R.raw.mod1_15, R.raw.mod1_1, R.raw.mod2_1,
			R.raw.mod2_2, R.raw.mod2_3, R.raw.mod2_4, R.raw.mod2_5,
			R.raw.mod2_6, R.raw.mod2_7, R.raw.mod2_8, R.raw.mod2_9,
			R.raw.mod2_10, R.raw.mod2_11, R.raw.mod2_12, R.raw.dump_1,
			R.raw.dump_2, R.raw.dump_3, R.raw.mod3_1, R.raw.mod3_1,
			R.raw.mod3_2, R.raw.mod3_3, R.raw.mod3_4, R.raw.mod3_5,
			R.raw.mod3_6, R.raw.mod3_7, R.raw.mod3_8, R.raw.mod3_9,
			R.raw.mod3_10, R.raw.mod3_11, R.raw.mod3_12, R.raw.mod3_13,
			R.raw.mod3_14, R.raw.mod3_15, 
			R.raw.dumb_4, R.raw.dumb_5,
			R.raw.dumb_6, R.raw.dumb_7, R.raw.dumb_8, R.raw.dumb_9,
			R.raw.dumb_10, R.raw.dumb_11, R.raw.dumb_12, R.raw.dumb_13,
			R.raw.dump_1, R.raw.airhorn_1, R.raw.start_1, R.raw.mod3_10,
			R.raw.mod3_11, R.raw.mod3_12 };

	public Controller(Context context) {

		m_soundPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 0);

		soundKey1 = new int[16];
		soundKey2 = new int[16];
		soundKey3 = new int[16];
		soundKey4 = new int[16];

		mContext = context;
		mThread = new soundThread();

		loadSound();

		dipSw = new Dipsw();
		keyPad = new Keypad();
		tLcd = new TextLcd();

		mThread.start();
		mThread.setPriority(10);

	}
	
	public void destroy(){
		dipSw.Close();
		keyPad.Close();
	}

	public boolean playSound() {// activated when btn is pressed
		int keyVal = keyPad.GetValue();
		if (keyVal == -1) {
			return true;
		}
		m_soundPool.play(soundKey[keyVal - 1], 1, 1, 0, 0, 1);
		return true;
	}

	private void loadSound() {
		int val = 0;
		for (int mod1 = 0; mod1 < 16; mod1++) {
			soundKey1[val] = m_soundPool.load(mContext, soundBank[mod1], 1);
			val++;
		}
		val = 0;
		for (int mod2 = 16; mod2 < 32; mod2++) {
			soundKey2[val] = m_soundPool.load(mContext, soundBank[mod2], 1);

			val++;
		}
		val = 0;
		for (int mod3 = 32; mod3 < 48; mod3++) {
			soundKey3[val] = m_soundPool.load(mContext, soundBank[mod3], 1);
			val++;
		}

		val = 0;
		for (int mod3 = 48; mod3 < 64; mod3++) {
			soundKey4[val] = m_soundPool.load(mContext, soundBank[mod3], 1);
			val++;
		}
	}

	public void setSound() {
		int mod = dipSw.UpdateValue();
		Log.e("hanback", "setSound " + mod);
		switch (mod % 4) {
		case 0:
			soundKey = soundKey1;
			tLcd.getStr("MOD 0");
			tLcd.UpdateValue();
			break;
		case 1:
			soundKey = soundKey2;
			tLcd.getStr("MOD 1");
			tLcd.UpdateValue();
			break;
		case 2:
			soundKey = soundKey3;
			tLcd.getStr("MOD 2");
			tLcd.UpdateValue();
			break;
		case 3:
			soundKey = soundKey4;
			tLcd.getStr("DUBSTEPPPP");
			tLcd.UpdateValue();
		default:
			Log.i("hanback", "Error");
			return;
		}
	}

	class soundThread extends Thread {
		public void run() {
			try {
				while (true) {
					setSound();
					playSound();
					// Thread.sleep(100);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
