package sjtu.se.metrolauncher;


import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem.OnMenuItemClickListener;


public class ListAppsLongClickMenu implements OnCreateContextMenuListener
{
	MainActivity MA;
	AppInfo appInfo;
	
	public ListAppsLongClickMenu(MainActivity ma, AppInfo theInfo)
	{	
		MA = ma;
		appInfo = theInfo;
	}
	
	@Override
	public void onCreateContextMenu
		(ContextMenu menu, View v,ContextMenuInfo menuInfo)
	{
		final AppInfoStorageCenter AISC = MA.getLauncherApplication().AISC;
		
		menu.setHeaderTitle("应用操作：");
		menu.add("添加到桌面")
			.setOnMenuItemClickListener(new OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				AISC.deskTopApps.addAppInfo(appInfo.packageName);
				// changing exists in the list, thus write file
				AISC.writeIntoFiles();
				
				MA.deskTop.update();
				return true;
			}
		});
		menu.add("隐藏该程序")
			.setOnMenuItemClickListener(new OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				String thePackageName = appInfo.packageName;
				
				AISC.allApps.findAppInfo(thePackageName).visible = false;
				AISC.listApps.delAppInfo(thePackageName);
				// changing exists in the list, thus write file
				AISC.writeIntoFiles();
				
				MA.listApps.update();
				return true;
			}
		});
		menu.add("卸载")
			.setOnMenuItemClickListener(new OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				String packageName = appInfo.packageName;
				Uri uri = Uri.parse("package:" + packageName);//获取删除包名的URI
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_DELETE);//设置我们要执行的卸载动作
				intent.setData(uri);//设置获取到的URI
				MA.startActivity(intent);
				
				AISC.updateOnChange(packageName, AppInfoStorageCenter.REMOVE_APP);
				return true;
			}
		});
		menu.add("详情")
			.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem item)
			{
				Intent intent = 
						new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				Uri uri = Uri.fromParts("package", appInfo.packageName, null);  
                intent.setData(uri);
                MA.startActivity(intent);
				return true;
			}
		});
	}  
}