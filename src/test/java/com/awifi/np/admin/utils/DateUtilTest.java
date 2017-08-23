package com.awifi.np.admin.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {
    
   @Test
   public void test(){
	   String a = DateUtil.getYear() + DateUtil.getDay() + DateUtil.getDays() + DateUtil.getTime();
	   DateUtil.compareDate("2017-01-09","2017-01-09");

	   DateUtil.getDateDiff(1, new Date(), new Date());
	   DateUtil.dateAdd(1, new Date(), 30);
	   
	   
	   Calendar c=Calendar.getInstance();
	   c.setTime(new Date());
	   c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE)+1);
	   DateUtil.getDateBetween(1, new Date(),c.getTime());
	   DateUtil.getDateBetween(2, new Date(),c.getTime());
	   DateUtil.getDateBetween(3, new Date(),c.getTime());
	   DateUtil.getDateBetween(4, new Date(),c.getTime());
	   DateUtil.getDateBetween(5, new Date(),c.getTime());
	   
	   DateUtil.dateAdd(2, new Date(), 3);
	   DateUtil.dateAdd(3, new Date(), 3);
	   DateUtil.dateAdd(4, new Date(), 3);
	   DateUtil.dateAdd(5, new Date(), 3);
	   DateUtil.dateAdd(6, new Date(), 3);
	   DateUtil.dateAdd(7, new Date(), 3);
	   
	   
	   DateUtil.getDateDiff(1, new Date(), new Date());
	   DateUtil.getDateDiff(2, new Date(), new Date());
	   DateUtil.getDateDiff(3, new Date(), new Date());
	   DateUtil.getDateDiff(4, new Date(), new Date());
	   DateUtil.getDateDiff(5, new Date(), new Date());
	   DateUtil.getDateDiff(6, new Date(), new Date());
	   DateUtil.getDateDiff(7, new Date(), new Date());
	   
	   DateUtil.dateFormat(new Date(), "yyyy-MM-dd");
	   DateUtil.longDateFormat(1L, "yyyy-MM-dd");
//	   dateFormat(null, null);
	   DateUtil.getWeekOfDate(new Date());
	   DateUtil.getNowStringDate();
	   DateUtil.getNowDate();
	   DateUtil.getDaySub("2017-01-09", "2017-01-09");
	   DateUtil.getDiffYear("2017-01-09", "2017-01-09");
	   DateUtil.isValidDate("2017-01-09");
	   
	   DateUtil.isValidDate("2017-01-09xxx");
	   DateUtil.fomatDate("2017-01-09");
	   
	   DateUtil.fomatDate("2017-01-09 xxxx");
	   DateUtil.compareDate("2017-01-09", "2017-01-09");
	   DateUtil.isToday(new Date());
	   System.out.println(DateUtil.getNowWeek()+DateUtil.getIntNowTime()+DateUtil.getTodayBegin()+DateUtil.getTodayEnd());
	   
   }

}