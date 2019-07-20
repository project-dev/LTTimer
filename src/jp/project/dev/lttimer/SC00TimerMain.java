package jp.project.dev.lttimer;

import android.graphics.Color;
import jp.project.dev.dgl2.AbstractScene;
import jp.project.dev.dgl2.SpriteData;
import jp.project.dev.tone.ToneGenerator;

public class SC00TimerMain extends AbstractScene {
	private ToneGenerator tone;
	
	@Override
	protected void onSceneInitalize() {
		tone = new ToneGenerator();
		
		// スプライトデータの登録
		SpriteData sprData = null;
		sprData = new SpriteData(R.drawable.logo);
		
		sprData.setSprData(0, 0, 0, 0, 200, 156, 100, 78);
		registSprData("SPRITE", sprData);
		sprData.init();

		setBackgroundColor(Color.BLACK);
		// スプライトの登録
		addDGLObject("TimerString", new SprTimerString("SPRITE"));
	}

	@Override
	protected void onSceneEnd() {
		tone.release();
	}

	protected ToneGenerator getToneGenerator(){
		return tone;
	}
}
