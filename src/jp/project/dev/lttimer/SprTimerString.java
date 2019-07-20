package jp.project.dev.lttimer;

import java.util.Calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import jp.project.dev.dgl2.SceneManager;
import jp.project.dev.dgl2.SceneManager.GestureType;
import jp.project.dev.dgl2.SpriteObject;
import jp.project.dev.tone.TONE;
import jp.project.dev.tone.ToneGenerator;

/**
 * 
 * @author TAKA@はままつ
 * 
 *
 */
public class SprTimerString extends SpriteObject {

	private STATUS step = STATUS.READY;
	private Calendar startTime = null;
	private double boundsCnt = 0;
	private long prevSec = 0;
	
	public SprTimerString(String spriteName) {
		super(spriteName, 0, 0);
		y = 0;
	}

	/**
	 * 
	 * @see jp.project.dev.dgl2.AbstractDGLObject#main(android.graphics.Canvas)
	 */
	@Override
	public void main(Canvas canvas) {
		Paint paint = new Paint();
		SceneManager manager = SceneManager.getInstance();
		SC00TimerMain scene = (SC00TimerMain)manager.getCurrentScene();
		ToneGenerator tone = scene.getToneGenerator();
		switch(step){
		case READY:
			paint.setColor(Color.WHITE);
			int posY = (int)(Math.sin(boundsCnt) * 5);
			boundsCnt += 0.5;
			if(boundsCnt >= 180.0){
				boundsCnt = 0.0;
			}
			double size = canvas.getHeight() / 10;
			paint.setTextSize((float)size);
			drawText(canvas, "Screen Tap Start", 20, canvas.getHeight() / 2 + posY, paint);
			// タップでスタートの文字を表示
			if(SceneManager.getInstance().getGestureType() == GestureType.SINGLE_TAP_UP){
				step = STATUS.START_COUNT_DOWN;
				startTime = Calendar.getInstance();
			}
			break;
		case START_COUNT_DOWN:
			{
				paint.setColor(Color.WHITE);
				Calendar currentTime = Calendar.getInstance();
				long span = currentTime.getTimeInMillis() - startTime.getTimeInMillis();
				long time = (3 * 1000) - span;
				long sec = time / 1000;
				long msec = time % 1000;
				if(time <= 0){
					time = 0;
					step = STATUS.TIMER;
					startTime = Calendar.getInstance();
					tone.toneOff();
				}else{
					if(manager.isSound()){
						if(sec != prevSec){
							tone.toneOn(TONE.A4, 100);
						}else if(msec >= 50){
							tone.toneOff();
						}
					}
				}
				prevSec = sec;
				double fontSize = canvas.getHeight() / 3;
				paint.setTextSize((float)fontSize);
				float textWidth = paint.measureText("0");
				int centerX = (int)((canvas.getWidth() - (int)textWidth) / 2);
				FontMetrics fontMetrics = paint.getFontMetrics();
				drawText(canvas, Long.toString(sec + 1), centerX, (int)(canvas.getHeight() / 2 + fontMetrics.descent), paint);
			}
			break;
		case TIMER:
			{
				paint.setColor(Color.WHITE);
				Calendar currentTime = Calendar.getInstance();
				long span = currentTime.getTimeInMillis() - startTime.getTimeInMillis();
				long time = (5 * 60 * 1000) - span;
				if(time < 0){
					time = 0;
					step = STATUS.END;
				}
				long minute = time / (60 * 1000);
				long sec = (time % (60 * 1000)) / 1000;
				long msec = time % 1000;
				double fontSize = canvas.getHeight() / 3;
				paint.setTextSize((float)fontSize);
				float textWidth = paint.measureText("0:00");
				int centerX = (int)((canvas.getWidth() - (int)textWidth) / 2);
				FontMetrics fontMetrics = paint.getFontMetrics();
				drawText(canvas, String.format("%1d:%2$02d", minute, sec), centerX, (int)(canvas.getHeight() / 2 + fontMetrics.descent), paint);

				if(manager.isSound() && minute == 0 && sec <= 5){
					if(sec != prevSec){
						if(sec == 0){
							tone.toneOn(TONE.C4, 100);
						}else{
							tone.toneOn(TONE.C3, 100);
						}
					}else if(msec > 50){
						tone.toneOff();
					}
				}
				prevSec = sec;
			
			}
			break;
		case END:
			paint.setColor(Color.WHITE);

//			double fontSize = canvas.getHeight() / 3.5;
//			paint.setTextSize((float)fontSize);
//			float textWidth = paint.measureText("Time Over!");
//			int centerX = (int)((canvas.getWidth() - (int)textWidth) / 2);
//			FontMetrics fontMetrics = paint.getFontMetrics();
//			drawText(canvas, "Time Over!", centerX, (int)(canvas.getHeight() / 2 + fontMetrics.descent), paint);

			x = canvas.getWidth() / 2;
			y = canvas.getHeight() / 2;
			putSprite(canvas, paint);
			
			// タップでタイマー停止
			if(SceneManager.getInstance().getGestureType() == GestureType.SINGLE_TAP_UP){
				manager.sceneChange("TimerMain");
			}
			break;
		}
	}
	
	enum STATUS{
		READY,
		START_COUNT_DOWN,
		TIMER,
		END
	}
}
