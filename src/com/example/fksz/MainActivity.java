package com.example.fksz;

//疯狂手指

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView text_dwon, text_time, text_ci, text_play, moemy_ci;
	int recLen = 9, i = 0, j = 0;
	String k = null;
	Timer timer = new Timer();
	SharedPreferences sp;
	Context ctx;
	ImageButton text_topBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ctx = MainActivity.this;
		sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
		text_dwon = (TextView) findViewById(R.id.text_dwon);
		text_time = (TextView) findViewById(R.id.text_time);
		text_ci = (TextView) findViewById(R.id.text_ci);
		text_play = (TextView) findViewById(R.id.text_play);
		moemy_ci = (TextView) findViewById(R.id.moemy_ci);
		text_topBack  = (ImageButton) findViewById(R.id.text_topBack);
		if (!sp.getString("user","").equals("")) {
			k = sp.getString("user", "");
			j = Integer.valueOf(k).intValue();
			moemy_ci.setText("历史最高得分：" + k + "次");
		}
		text_time.setVisibility(View.GONE);
		text_dwon.setVisibility(View.GONE);
		text_ci.setVisibility(View.GONE);
		moemy_ci.setVisibility(View.GONE);

		text_topBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				i += 1;
				text_ci.setText(i + "次");
			}
		});

		text_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				timer.schedule(task, 1000, 1000); // timeTask
				text_play.setVisibility(View.GONE);
				text_time.setVisibility(View.VISIBLE);
				text_dwon.setVisibility(View.VISIBLE);
				text_ci.setVisibility(View.VISIBLE);
				moemy_ci.setVisibility(View.VISIBLE);
			}
		});

	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					recLen--;
					text_time.setText(recLen + 1 + " 秒");
					if (recLen < 0) {// 倒计时结束后的事件
						timer.cancel();
						text_dwon.setClickable(false);// 设置为不可点
						dialog();// 游戏结束提示

						// 记住最高分
						if (i > j) {
							SharedPreferences.Editor editor = sp.edit();
							editor.putString("user", String.valueOf(i));
							editor.commit();

						}
					}
				}
			});
		}
	};

	// 游戏结束提示
	protected void dialog() {

		AlertDialog.Builder builder = new Builder(MainActivity.this);

		builder.setMessage("本轮得分:" + i + "次");

		builder.setTitle("游戏结束");

		builder.setPositiveButton("退出",

		new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}

		});

		builder.setNegativeButton("重玩",

		new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent i = new Intent(MainActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}

		});

		builder.create().show();

	}

}
