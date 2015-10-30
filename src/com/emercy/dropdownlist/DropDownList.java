package com.emercy.dropdownlist;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 下拉框控件。一个简化的spinner，用于讲字符串数组的数据生成到下拉菜单，比spinner更轻量，更简洁
 * 
 * @author M C
 * 
 */
public class DropDownList extends LinearLayout
{
	ClickToDropDown clickToDropDown;
	Context context;
	TextView textView;
	String title;
	OnDropListClickListener listener;
	String[] items;
	int currentIndex;

	/**
	 * 获取下拉列表的文本框
	 * 
	 * @return 返回文本框，如果不存在，则返回null
	 */
	public TextView getTextView()
	{
		if (textView != null)
		{
			return textView;
		}
		return null;
	}
	/**
	 * 默认属性的下拉列表框。可以与一个字符串数组相关联从而随时设置下拉框的内容
	 * 
	 * @param context
	 *            当前界面的context变量
	 */
	public DropDownList(Context context)
	{
		this(context, null);
	}

	/**
	 * 带属性的下拉框。可以与一个字符串数组相关联从而随时设置下拉框的内容
	 * 
	 * @param context
	 *            当前界面的context变量
	 * @param attrs
	 *            下拉框的属性
	 */
	public DropDownList(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;

		init_widget();
	}

	private void init_widget()
	{
		textView = new TextView(context);
		textView.setTextSize(20F);
		textView.setOnClickListener(new ClickToDropDown());

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.BOTTOM;
		params.topMargin = 2;

		addView(textView, params);
	}

	/**
	 * 设置下拉框的显示内容
	 * 
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getItem()
	{
		return textView.getText().toString();
	}

	/**
	 * 设置选项框的标题。当前及下拉框时，会弹出根据相关数组所生产的选项框
	 * 
	 * @param str
	 *            要设置的标题
	 */
	public void setItem(String[] str)
	{
		items = str;
		setSelection(0);
	}

	public void setItem(int resourceId)
	{
		setItem(getResources().getStringArray(resourceId));
	}

	public int getCurrentIndex()
	{
		return currentIndex;
	}
	/**
	 * 设置下拉菜单的当前选项
	 * 
	 * @param which
	 *            当前是第几个选项。
	 */
	public void setSelection(int which)
	{
		if (which < 0)
		{
			which = 0;
		}
		else if (which >= items.length)
		{
			which = items.length - 1;
		}

		currentIndex = which;
		textView.setText(items[which]);
	}

	/**
	 * 设置下拉框的点击事件
	 * 
	 * @param dropListClick
	 *            点击事件监听器
	 */
	public void setOnListClickListener(OnDropListClickListener dropListClick)
	{
		listener = dropListClick;
	}

	class ClickToDropDown implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			AlertDialog dialog = new AlertDialog.Builder(context)
					.setTitle(title).setItems(items, new onItemClick())
					.create();
			dialog.show();
		}
	}

	class onItemClick implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			currentIndex = which;
			if (textView != null)
			{
				textView.setText(items[which]);
			}
			if (listener != null)
			{
				listener.onListItemClick(DropDownList.this, which);
			}
			dialog.dismiss();
		}
	}

	/**
	 * 下拉框点击事件接口
	 * 
	 * @author M C
	 * 
	 */
	public interface OnDropListClickListener
	{
		public void onListItemClick(DropDownList dropDownList, int which);
	}
}
