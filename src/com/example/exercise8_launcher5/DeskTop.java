package com.example.exercise8_launcher5;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class DeskTop
{
	LinearLayout mainLayout;
	GridView gridView;	// the content of the layout
	MainActivity MA;
	
	public DeskTop(MainActivity ma) 
	{
		MA = ma;
		LayoutInflater li = (LayoutInflater)
				MA.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mainLayout = (LinearLayout) li.inflate(R.layout.fragment_grid, null);
		gridView = (GridView)mainLayout.findViewById(R.id.gridview);
		gridView.setAdapter(new DeskTop.iGridAdapter(MA));
	}
	
	public void update()
	{	
		gridView.setAdapter(new DeskTop.iGridAdapter(MA));
	}
	
	
	
	
	class iGridAdapter extends BaseAdapter
	{	
		// width and height in the grid icon, different from that in the list
		int iconWidth;
		int iconHeight;
		AppInfoList deskTopAppList;
		MainActivity MA;
		
		@SuppressWarnings("deprecation")
		public iGridAdapter(MainActivity ma)
		{	
			MA = ma;
			
			WindowManager wm = (WindowManager)MA.getSystemService(Context.WINDOW_SERVICE);
			int screenWidth=wm.getDefaultDisplay().getWidth();	//手机屏幕的宽度
			@SuppressWarnings("unused")
			int screenHeight=wm.getDefaultDisplay().getHeight();	//手机屏幕的高度
			iconWidth = iconHeight = (int)screenWidth / 4;
			
			deskTopAppList = MA.AISC.deskTopApps;
		}
		
		public int getCount() 
		{	return deskTopAppList.size();	}

		public Object getItem(int position) 
		{	return deskTopAppList.get(position);	}

		public long getItemId(int position) 
		{	return position;	}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			final AppInfo appInfo = deskTopAppList.get(position);
			ImageView appIcon;
			if (convertView == null)
				appIcon = new ImageView(MA);
			else
				appIcon = (ImageView)convertView;
			appIcon.setImageDrawable(appInfo.appIcon);
			
			// if the app is the 1st, 10th, 11th... use the big icon
			//if ((position + 1) % 10 == 0 || (position + 1) % 10 == 1)
			//{	appIcon.setLayoutParams
			//		(new GridView.LayoutParams(2 * width, 2 * height));
			//} 
			//else
			appIcon.setLayoutParams(new GridView.LayoutParams(iconWidth, iconHeight));
			// to realize the function of launching
			appIcon.setOnClickListener(new OnClickListener() 
			{	
				@Override
				public void onClick(View v) 
				{	
					String packageName = appInfo.packageName;
					PackageManager pm = MA.getPackageManager(); 
					Intent intent = pm.getLaunchIntentForPackage(packageName);
					MA.startActivity(intent); 
				}
			});
			return appIcon;
		}
	}

}

