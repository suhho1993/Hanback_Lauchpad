package com.Hanback.Launchpad;

import core.Controller;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class LauchpadActivity extends Activity {
	private Context mContext;
	MediaPlayer bgm;
	Controller ctr;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = getApplicationContext();
		ctr= new Controller(mContext);
		bgm = MediaPlayer.create(mContext, R.raw.bgm1);

	}

	public void onClickBGM1(View v) {
		if (bgm.isPlaying() == true) {
			bgm.stop();
		}
		bgm = MediaPlayer.create(mContext, R.raw.bgm1);
		bgm.start();
		bgm.setLooping(true);
	}

	public void onClickBGM2(View v) {
		if (bgm.isPlaying() == true) {
			bgm.stop();
		}
		bgm = MediaPlayer.create(mContext, R.raw.bgm2);
		bgm.start();
		bgm.setLooping(true);

	}

	public void onClickBGM3(View v) {
		if (bgm.isPlaying() == true) {
			bgm.stop();
		}
		bgm = MediaPlayer.create(mContext, R.raw.bgm3);
		bgm.start();
		bgm.setLooping(true);

	}

	public void onClickBGM4(View v) {
		if (bgm.isPlaying() == true) {
			bgm.stop();
		}
		bgm = MediaPlayer.create(mContext, R.raw.dump_2);
		bgm.start();
		bgm.setLooping(true);

	}

	public void onClickStop(View v) {
		bgm.stop();
	}

	@Override
	protected void onDestroy() {
		ctr.destroy();
		bgm.stop();
		bgm.release();
		super.onDestroy();
	}
}