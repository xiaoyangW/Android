package com.example.arvin.myhttpweb;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.arvin.myhttpweb.adapter.MyAdapter;

public class MyVollyActivity extends Activity {
	ListView listView;
	String[] images = new String[] {
			"http://pic.qiantucdn.com/58pic/18/88/87/565bfb9f9688f_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/63/18/77R58PICHKi_1024.jpg",
			"http://pic.qiantucdn.com/58pic/16/84/56/94I58PICHmM_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/64/73/25S58PICF6Q_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/86/18/21u58PICgYn_1024.jpg",
			"http://pic.qiantucdn.com/58pic/19/19/63/5684470f3a9df_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/63/82/42w58PICHIk_1024.jpg",
			"http://pic.qiantucdn.com/58pic/19/19/63/5684477b5f649_1024.jpg",
			"http://pic.qiantucdn.com/58pic/17/11/81/91t58PICwN4_1024.jpg",
			"http://pic.qiantucdn.com/58pic/19/67/46/55u58PICyPX_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/69/68/78458PICCNs_1024.jpg",
			"http://pic.qiantucdn.com/58pic/16/92/70/88H58PICg7A_1024.jpg",
			"http://pic.qiantucdn.com/58pic/19/19/64/568447c55be93_1024.jpg",
			"http://pic.qiantucdn.com/58pic/14/98/39/58Y58PICWEc_1024.jpg",
			"http://pic.qiantucdn.com/58pic/17/90/28/55a7d67695717_1024.jpg",
			"http://pic.qiantucdn.com/58pic/18/84/97/5655f95ef090a_1024.jpg",
			"http://pic.qiantucdn.com/58pic/17/90/28/55a7d65fef2ab_1024.jpg",
			"http://pic.qiantucdn.com/58pic/19/19/66/56844990d849b_1024.jpg",
			"http://pic.qiantucdn.com/58pic/17/96/17/55b4ba7887f8b_1024.jpg",
			"http://pic.qiantucdn.com/58pic/18/84/97/5655f95972d7a_1024.jpg",
			"http://pic.qiantucdn.com/58pic/11/56/32/50k58PICbjE.JPG"
			 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		listView = (ListView) findViewById(R.id.lv_xml);
		MyAdapter adapter = new MyAdapter(this, images);
		listView.setAdapter(adapter);
	}
}
