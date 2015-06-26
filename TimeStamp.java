

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp 
{
	public static String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss");
		Date date = new Date();		
		return dateFormat.format(date);
	}
	public static String giveUniqueName()
	{
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy, HH:mm:ss");
		Date date = new Date();		
		return date.getMonth()+""+date.getDate()+""+date.getHours()+""+date.getMinutes()+""+date.getSeconds();
		
	}
}
