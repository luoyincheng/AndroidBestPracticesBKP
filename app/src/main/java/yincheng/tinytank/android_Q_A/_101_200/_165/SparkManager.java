package yincheng.tinytank.android_Q_A._101_200._165;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * @author Dean Guo
 **/
class SparkManager {

	// 火花半径
	private static final float SPARK_RADIUS = 4.0F;
	// 火花外侧阴影大小
	private static final float BLUR_SIZE = 10.0F;
	// 每帧速度
	private static final float PER_SPEED_SEC = 1.0F;
	// 是否是激活状态
	boolean isActive = false;
	// 画笔对象
	private Paint mSparkPaint;
	// 花火半径
	private float radius = 0;
	// 火花喷射距离
	private float mDistance = 0;
	// 当前喷射距离
	private float mCurDistance = 0;
	private Random mRandom = new Random();
	// 火花的起始点，终点，塞贝儿曲线拐点1，塞贝儿曲线拐点2
	private Point start, end, c1, c2;

	SparkManager() {
		// 初始化画笔
		setSparkPaint();
	}

	int[] drawSpark(Canvas canvas, int x, int y, int[] store) {

		// 当前触摸位置
		this.mCurDistance = store[0];
		this.mDistance = store[1];

		// 初始化火花
		if (mCurDistance == mDistance && isActive) {
			mDistance = mRandom.nextInt(30) + 1;
			mCurDistance = 0;

			start = new Point(x, y);
			end = getRandomPoint(start.x, start.y, (int) mDistance);
			c1 = getRandomPoint(start.x, start.y, mRandom.nextInt(SparkView.WIDTH / 16));
			c2 = getRandomPoint(end.x, end.y, mRandom.nextInt(SparkView.WIDTH / 16));
		}
		// 恢复火花路径
		else {
			start.set(store[2], store[3]);
			end.set(store[4], store[5]);
			c1.set(store[6], store[7]);
			c2.set(store[8], store[9]);
		}

		// 更新火花路径
		updateSparkPath();
		// 计算塞贝儿曲线的当前点
		Point bezierPoint = CalculateBezierPoint(mCurDistance / mDistance, start, c1, c2, end);
		// 设置随机颜色
		mSparkPaint.setColor(Color.argb(255, mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128, mRandom.nextInt(128) + 128));
		// 画花火
		canvas.drawCircle(bezierPoint.x, bezierPoint.y, radius, mSparkPaint);


		// 重置火花状态
		if (mCurDistance == mDistance) {
			store[0] = 0;
			store[1] = 0;
		}
		// 保持花火的状态
		else {
			store[0] = (int) mCurDistance;
			store[1] = (int) mDistance;
			store[2] = start.x;
			store[3] = start.y;
			store[4] = end.x;
			store[5] = end.y;
			store[6] = c1.x;
			store[7] = c1.y;
			store[8] = c2.x;
			store[9] = c2.y;
		}

		return store;
	}

	/**
	 * 更新火花路径
	 */
	private void updateSparkPath() {
		mCurDistance += PER_SPEED_SEC;
		// 前半段
		if (mCurDistance < (mDistance / 2) && (mCurDistance != 0)) {
			radius = SPARK_RADIUS * (mCurDistance / (mDistance / 2));
		}
		// 后半段
		else if (mCurDistance > (mDistance / 2) && (mCurDistance < mDistance)) {
			radius = SPARK_RADIUS - SPARK_RADIUS * ((mCurDistance / (mDistance / 2)) - 1);
		}
		// 完成
		else if (mCurDistance >= mDistance) {
			mCurDistance = 0;
			mDistance = 0;
			radius = 0;
		}
	}

	/**
	 * 根据基准点获取指定范围为半径的随机点
	 */
	private Point getRandomPoint(int baseX, int baseY, int r) {
		if (r <= 0) {
			r = 1;
		}
		int x = mRandom.nextInt(r);
		int y = (int) Math.sqrt(r * r - x * x);

		x = baseX + getRandomPNValue(x);
		y = baseY + getRandomPNValue(y);

		return new Point(x, y);
	}

	/**
	 * 获取随机正负数
	 */
	private int getRandomPNValue(int value) {
		return mRandom.nextBoolean() ? value : 0 - value;
	}

	/**
	 * 计算塞贝儿曲线
	 *
	 * @param t  时间，范围0-1
	 * @param s  起始点
	 * @param c1 拐点1
	 * @param c2 拐点2
	 * @param e  终点
	 * @return 塞贝儿曲线在当前时间下的点
	 */
	private Point CalculateBezierPoint(float t, Point s, Point c1, Point c2, Point e) {
		float u = 1 - t;
		float tt = t * t;
		float uu = u * u;
		float uuu = uu * u;
		float ttt = tt * t;

		Point p = new Point((int) (s.x * uuu), (int) (s.y * uuu));
		p.x += 3 * uu * t * c1.x;
		p.y += 3 * uu * t * c1.y;
		p.x += 3 * u * tt * c2.x;
		p.y += 3 * u * tt * c2.y;
		p.x += ttt * e.x;
		p.y += ttt * e.y;

		return p;
	}

	/**
	 * 设置画笔
	 */
	private void setSparkPaint() {
		this.mSparkPaint = new Paint();
		// 打开抗锯齿
		this.mSparkPaint.setAntiAlias(true);
		/*
		 * 设置画笔样式为填充 Paint.Style.STROKE：描边 Paint.Style.FILL_AND_STROKE：描边并填充
		 * Paint.Style.FILL：填充
		 */
		this.mSparkPaint.setDither(true);
		this.mSparkPaint.setStyle(Paint.Style.FILL);
		// 设置外围模糊效果
		this.mSparkPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
	}
}