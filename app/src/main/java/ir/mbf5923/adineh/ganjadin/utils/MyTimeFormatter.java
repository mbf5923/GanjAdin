package ir.mbf5923.adineh.ganjadin.utils;

import com.github.bassaer.chatmessageview.util.ITimeFormatter;

import java.util.Calendar;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class MyTimeFormatter implements ITimeFormatter {
    @Override
    public String getFormattedTimeText(Calendar createdAt) {

        // Time difference [second]

        PersianDate pdate = new PersianDate(createdAt.getTimeInMillis());
        PersianDateFormat pdformater1 = new PersianDateFormat("d F H:i");
         pdformater1.format(pdate);
        return ".";
    }


}
