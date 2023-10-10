package com.snce.lightcom.Utils;

import com.snce.lightcom.DTO.CommandeItemDTO;
import com.snce.lightcom.DTO.FactureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

public class UtilsDate {
    public static Date incDateByDays(Date date, int pas){

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+pas);
        return cal.getTime();
    }
    public static int getYear(Date date ){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
    public static Date max(Date date1, Date date2){
        if (date1 == null)
            return date2;

        if (date2 == null)
            return date1;

        if (date1.after(date2))
            return date1;
        return date2;
    }

}
