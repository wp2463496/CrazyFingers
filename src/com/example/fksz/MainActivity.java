package com.example.fksz;

//�����ָ

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
			moemy_ci.setText("��ʷ��ߵ÷֣�" + k + "��");
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
				text_ci.setText(i + "��");
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
					text_time.setText(recLen + 1 + " ��");
					if (recLen < 0) {// ����ʱ��������¼�
						timer.cancel();
						text_dwon.setClickable(false);// ����Ϊ���ɵ�
						dialog();// ��Ϸ������ʾ

						// ��ס��߷�
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

	// ��Ϸ������ʾ
	protected void dialog() {

		AlertDialog.Builder builder = new Builder(MainActivity.this);

		builder.setMessage("���ֵ÷�:" + i + "��");

		builder.setTitle("��Ϸ����");

		builder.setPositiveButton("�˳�",

		new android.content.DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}

		});

		builder.setNegativeButton("����",

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
