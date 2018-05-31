package com.example.accounting.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.accounting.R;

import java.util.Random;

/**
 * @Author jelly
 * @TIME 2018/3/4
 * @DES ${TODO}
 */

public class FlexibleView extends LinearLayout {

    private Scroller mScroller;
    private int mLastY;
    private ScrollListener mScrollListener;
    private int mDownY;

    public void setScrollListener(ScrollListener scrollListener) {
        mScrollListener = scrollListener;
    }

    public FlexibleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                mDownY = (int)event.getRawY();
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE :
                int dy = y - mLastY;
                mLastY = y;
                scrollBy(0,-dy/2);

                int rawY = (int) event.getRawY();
                if(mScrollListener != null){
                    mScrollListener.scrolling((rawY-mDownY)/2);
                }
                return true;
            case MotionEvent.ACTION_UP :
//                mScrollListener.scrollFinish();
//                randomChangeBg();
                mScroller.startScroll(0,getScrollY(),0,-getScrollY(),500);
                invalidate();
                return true;
        }
        return true;
    }

    private void randomChangeBg() {
        int[] drawables = {R.drawable.heimu,R.drawable.bali,R.drawable.huoshan};
        Random random = new Random();
        int id = random.nextInt(3);
        setBackgroundResource(drawables[id]);
    }


    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    public interface ScrollListener{
        void scrollFinish();
        void scrolling(int scrollDistance);
    }
}
