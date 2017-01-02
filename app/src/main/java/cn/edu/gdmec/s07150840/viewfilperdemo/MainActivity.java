package cn.edu.gdmec.s07150840.viewfilperdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private int[] images = {R.drawable.icon1, R.drawable.icon2,
            R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5};
    private GestureDetector gestureDetector = null;
    private ViewFlipper viewFlipper = null;
    private static final int FLING_MIN_DISTANCE = 100;
    private static final int FLING_MIN_VELOCITY = 200;
    private Activity mActivity = null;
    private Animation rInAnim,rOutAnim,lInAnim,lOutAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity =this;
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFilepper);
        gestureDetector = new GestureDetector(this, this);
        //animationUtils类用于定义常用的处理动画功能
        //loadAnimation(Coutext context, int id):从资源文件中加载一个Animation对象。
        //向右滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
        rInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_in);
        //向右滑动右侧滑出的渐变效果（alpha 1.0 -> 0.1）
        rOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_right_out);
        //左右滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
        lInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);
        //向左滑动右侧滑出的渐变效果（alpha 1.0 -> 0.1）
        lOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_out);
        for (int i = 0; i < images.length; i++){    //添加图片源
            ImageView iv = new ImageView(this);
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(iv,i,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        viewFlipper.setAutoStart(true);     //设置自动播放功能（点击事件前，自动播放）
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping();     //点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        //调用GestureDetector的onTouchEvent()方法
        //将捕捉到的MotionEvent交给GestureDetector来分析是否有合适的callback函数来处理用户手势
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //从左向右滑动（左进右出），X轴的坐标位移大于FLING_MIN_DISTANCE,且移动速度大于FLING_MIN_VELOCITY个像素/秒
        if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            viewFlipper.setInAnimation(lInAnim);
            viewFlipper.setOutAnimation(rOutAnim);
            viewFlipper.showPrevious();
            setTitle("相片编码：" + viewFlipper.getDisplayedChild());
            return true;
        }
        return true;
    }
}

