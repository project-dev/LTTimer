package jp.project.dev.lttimer;

import jp.project.dev.dgl2.DefaultDGLActivity;
import jp.project.dev.dgl2.SceneManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LTTimerActivity extends DefaultDGLActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 画面をスリープさせない
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	SceneManager manager = SceneManager.getInstance();
        manager.addScene("TimerMain", SC00TimerMain.class);
	}

	/**
	 * @see jp.project.dev.dgl2.DefaultDGLActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onDestroy();
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SceneManager manager = SceneManager.getInstance();
		manager.setSound(!manager.isSound());
		//return super.onOptionsItemSelected(item);
		return true;
	}

	/**
	 * @see android.app.Activity#openOptionsMenu()
	 */
	@Override
	public void openOptionsMenu() {
		super.openOptionsMenu();
	}

	/**
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		if(SceneManager.getInstance().isSound() == true){
			menu.add(0 , Menu.FIRST , Menu.NONE , "Sound On");
		}else{
			menu.add(0 , Menu.FIRST , Menu.NONE , "Sound Off");
		}
		menu.add(1, Menu.FIRST, Menu.NONE, "Time Setting");
		return super.onPrepareOptionsMenu(menu);
	}
}
