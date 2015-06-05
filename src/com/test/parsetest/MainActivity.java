package com.test.parsetest;

import java.util.HashMap;

import org.json.JSONObject;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {
	private TextView mText;
	private Button mButton;
	private RatingBar mRatingBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mText = (TextView)findViewById(R.id.textView1);
		mButton = (Button)findViewById(R.id.button1);
		mButton.setOnClickListener(this);
		mRatingBar = (RatingBar)findViewById(R.id.ratingBar1);
		mRatingBar.setOnRatingBarChangeListener(this);
		
		Parse.initialize(this, "Z7zudTla3m0cR0Q9bX5IRkxImzlfn0uWVNIih9ry", "SQbI6c9xcNPgrtYHepHsYWUAedKptLVBxDyT4rVh");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("value", "120");
		ParseCloud.callFunctionInBackground("foo1", params, new FunctionCallback<String>() {
			@Override
			public void done(String result, ParseException e) {
				if (e == null) {
					mText.setText(result);
				} else {
					mText.setText(e.toString());
				}
			}
		});
		
		params.clear();
		ParseCloud.callFunctionInBackground("foo2", params, new FunctionCallback<HashMap<String, String>>() {
			@Override
			public void done(HashMap<String, String> result, ParseException e) {
				if (e == null) {
					mText.setText(result.toString());
				} else {
					mText.setText(e.toString());
				}
			}
		});
		
		/*params.clear();
		params.put("movie", "The Matrix");
		ParseCloud.callFunctionInBackground("averageStars", params, new FunctionCallback<Object>() {
			@Override
			public void done(Object result, ParseException e) {
				if (e == null) {
					mText.setText(result.toString());
				} else {
					mText.setText(e.toString());
				}
			}
		});*/
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		ParseObject gameScore = new ParseObject("Review");
		gameScore.put("movie", "The Matrix");
		gameScore.put("stars", rating);
		gameScore.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null)
					mText.setText("save success");
				else
					mText.setText(e.getMessage());
			}
		});
	}
}
