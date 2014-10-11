package com.coo.m.vote;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 很不错的Button，支持图片圆角，圆形，支持Icon和字的组合排版
 * 
 * @author boqing.shen
 * 
 */
public class FancyButton extends LinearLayout {

	private Context mContext;

	// # Background Attributs
	private int mDefaultBackgroundColor = Color.BLACK;
	private int mFocusBackgroundColor = 0;

	// # Text Attributs
	private int mDefaultTextColor = Color.WHITE;

	private int mDefaultTextSize = 15;
	private String mText = null;

	// # Icon Attributs
	private Drawable mIconResource = null;
	private int mFontIconSize = 15;
	private String mFontIcon = null;
	private int mIconPosition = 1;

	// 边框
	private int mBorderColor = Color.TRANSPARENT;
	private int mBorderWidth = 0;

	// 边角的半径，如果要要圆的，长=宽=2*mRadius
	private int mRadius = 0;

	// 字体样式
	private Typeface mTextTypeFace = Typeface.defaultFromStyle(1);
	private Typeface mIconTypeFace = Typeface.defaultFromStyle(1);

	/**
	 * Tags to identify the position of the icon
	 */
	public static final int POSITION_LEFT = 1;
	public static final int POSITION_RIGHT = 2;
	public static final int POSITION_TOP = 3;
	public static final int POSITION_BOTTOM = 4;

	/**
	 * 内部封装的三个View：仅图标
	 */
	private ImageView mIconView;
	/**
	 * 内部封装的三个View：字体图标和字
	 */
	private TextView mFontIconView;
	private TextView mTextView;

	/**
	 * 构造函数,API构造
	 */
	public FancyButton(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	/**
	 * 构造函数,XML构造
	 */
	public FancyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		initContainer();
		mTextView = setupTextView();
		mIconView = setupIconView();
		mFontIconView = setupFontIconView();

		if (mIconView == null && mFontIconView == null
				&& mTextView == null) {
			Button tempTextView = new Button(mContext);
			tempTextView.setText("");
			this.addView(tempTextView);
		} else {
			this.removeAllViews();
			setupBackground();

			ArrayList<View> views = new ArrayList<View>();

			if (mIconPosition == POSITION_LEFT
					|| mIconPosition == POSITION_TOP) {
				if (mIconView != null) {
					views.add(mIconView);
				}
				if (mFontIconView != null) {
					views.add(mFontIconView);
				}
				if (mTextView != null) {
					views.add(mTextView);
				}

			} else {
				if (mTextView != null) {
					views.add(mTextView);
				}
				if (mIconView != null) {
					views.add(mIconView);
				}
				if (mFontIconView != null) {
					views.add(mFontIconView);
				}
			}

			for (View view : views) {
				this.addView(view);
			}
		}
	}

	@SuppressLint("NewApi")
	private TextView setupTextView() {
		if (mText != null) {
			TextView textView = new TextView(mContext);
			textView.setText(mText);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(mDefaultTextColor);
			textView.setTextSize(mDefaultTextSize);
			// 设置布局
			textView.setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 1f));
			return textView;
		}
		return null;
	}

	@SuppressLint("NewApi")
	private TextView setupFontIconView() {
		if (mFontIcon != null) {
			TextView fontIconView = new TextView(mContext);
			fontIconView.setTextColor(mDefaultTextColor);

			LayoutParams iconTextViewParams = new LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 1f);

			if (mTextView != null) {
				iconTextViewParams.rightMargin = 10;
				iconTextViewParams.leftMargin = 10;
				if (mIconPosition == POSITION_TOP
						|| mIconPosition == POSITION_BOTTOM) {
					iconTextViewParams.gravity = Gravity.CENTER;
					fontIconView.setGravity(Gravity.CENTER);
				} else {
					fontIconView.setGravity(Gravity.CENTER_VERTICAL);
					iconTextViewParams.gravity = Gravity.CENTER_VERTICAL;
				}
			} else {
				iconTextViewParams.gravity = Gravity.CENTER;
				fontIconView.setGravity(Gravity.CENTER_VERTICAL);
			}

			fontIconView.setLayoutParams(iconTextViewParams);
			if (!isInEditMode()) {
				fontIconView.setTextSize(mFontIconSize);
				fontIconView.setText(mFontIcon);
				fontIconView.setTypeface(mIconTypeFace);
			} else {
				fontIconView.setText("O");
			}
			return fontIconView;
		}
		return null;
	}

	@SuppressLint("NewApi")
	private ImageView setupIconView() {
		if (mIconResource != null) {
			ImageView iconView = new ImageView(mContext);
			iconView.setImageDrawable(mIconResource);

			LayoutParams iconViewParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if (mTextView != null) {
				if (mIconPosition == POSITION_TOP
						|| mIconPosition == POSITION_BOTTOM)
					iconViewParams.gravity = Gravity.CENTER;
				else
					iconViewParams.gravity = Gravity.LEFT;

				iconViewParams.rightMargin = 10;
				iconViewParams.leftMargin = 10;
			} else {
				iconViewParams.gravity = Gravity.CENTER_VERTICAL;
			}
			iconView.setLayoutParams(iconViewParams);

			return iconView;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void setupBackground() {

		// Default Drawable
		GradientDrawable drawable = new GradientDrawable();
		drawable.setCornerRadius(mRadius);
		drawable.setColor(mDefaultBackgroundColor);
		if (mBorderColor != 0) {
			drawable.setStroke(mBorderWidth, mBorderColor);
		}

		// Focus/Pressed Drawable
		GradientDrawable drawable2 = new GradientDrawable();
		drawable2.setCornerRadius(mRadius);
		drawable2.setColor(mFocusBackgroundColor);
		if (mBorderColor != 0) {
			drawable2.setStroke(mBorderWidth, mBorderColor);
		}

		StateListDrawable states = new StateListDrawable();

		if (mFocusBackgroundColor != 0) {
			states.addState(new int[] { android.R.attr.state_pressed },
					drawable2);
			states.addState(new int[] { android.R.attr.state_focused },
					drawable2);
		}
		states.addState(new int[] {}, drawable);

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			this.setBackgroundDrawable(states);
		} else {
			this.setBackground(states);
		}
	}

	private void initContainer() {
		if (mIconPosition == POSITION_TOP
				|| mIconPosition == POSITION_BOTTOM) {
			this.setOrientation(LinearLayout.VERTICAL);
		} else {
			this.setOrientation(LinearLayout.HORIZONTAL);
		}
		LayoutParams containerParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(containerParams);
		this.setGravity(Gravity.CENTER_VERTICAL);
		this.setClickable(true);
		this.setFocusable(true);
		if (mIconResource == null && mFontIcon == null
				&& getPaddingLeft() == 0
				&& getPaddingRight() == 0
				&& getPaddingTop() == 0
				&& getPaddingBottom() == 0) {
			this.setPadding(20, 20, 20, 20);
		}
	}

	public void setText(String text) {
		this.mText = text;
		if (mTextView == null) {
			init();
		} else {
			mTextView.setText(text);
		}
	}

	public void setTextColor(int color) {
		this.mDefaultTextColor = color;
		if (mTextView == null) {
			init();
		} else {
			mTextView.setTextColor(color);
		}
	}

	public void setBackgroundColor(int color) {
		this.mDefaultBackgroundColor = color;
		if (mIconView != null || mFontIconView != null
				|| mTextView != null) {
			this.setupBackground();
		}
	}

	public void setFocusBackgroundColor(int color) {
		this.mFocusBackgroundColor = color;
		if (mIconView != null || mFontIconView != null
				|| mTextView != null)
			this.setupBackground();
	}

	public void setTextSize(int textSize) {
		this.mDefaultTextSize = textSize;
		if (mTextView != null)
			mTextView.setTextSize(textSize);
	}

	public void setIconResource(int drawable) {
		this.mIconResource = mContext.getResources().getDrawable(
				drawable);

		// TODO 需要考慮圖片大小和佈局的大小关系
		int height = this.getLayoutParams().height;
		int width = this.getLayoutParams().width;
		Toast.makeText(mContext, "布局大小:" + height + "-" + width,
				Toast.LENGTH_LONG).show();
		// mIconResource.getBounds().height()
		Toast.makeText(mContext,
				"图片大小:"
						+ mIconResource.getIntrinsicHeight()
						+ "-"
						+ mIconResource.getIntrinsicWidth(),
				Toast.LENGTH_LONG).show();

		if (mIconView == null || mFontIconView != null) {
			mFontIconView = null;
			init();
		} else {
			mIconView.setImageDrawable(mIconResource);
		}
	}

	public void setIconResource(String icon) {
		this.mFontIcon = icon;
		if (mFontIconView == null) {
			mIconView = null;
			init();
		} else
			mFontIconView.setText(icon);
	}

	public void setFontIconSize(int iconSize) {
		this.mFontIconSize = iconSize;
		if (mFontIconView != null)
			mFontIconView.setTextSize(iconSize);
	}

	public void setIconPosition(int position) {
		if (position > 0 && position < 5)
			mIconPosition = position;
		else
			mIconPosition = POSITION_LEFT;
		this.init();
	}

	public void setBorderColor(int color) {
		this.mBorderColor = color;
		if (mIconView != null || mFontIconView != null
				|| mTextView != null) {
			this.setupBackground();
		}
	}

	public void setBorderWidth(int width) {
		this.mBorderWidth = width;
		if (mIconView != null || mFontIconView != null
				|| mTextView != null) {
			this.setupBackground();
		}
	}

	public void setRadius(int radius) {
		this.mRadius = radius;
		if (mIconView != null || mFontIconView != null
				|| mTextView != null) {
			this.setupBackground();
		}
	}

	public void setCustomTextFont(String fontName) {
		if (mTextView == null) {
			init();
		} else {
			mTextView.setTypeface(mTextTypeFace);
		}
	}

	public void setCustomIconFont(String fontName) {
		if (mFontIconView == null) {
			init();
		} else {
			mFontIconView.setTypeface(mIconTypeFace);
		}
	}

}
