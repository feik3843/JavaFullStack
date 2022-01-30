package com.company;
import java.util.Calendar;
import java.util.Date;

public class Person {
    public String FIO;
    public Date Birthday;
    public String Post;

    Person(String FIO,Date Birthday,String Post){
        this.FIO = FIO;
        this.Birthday = Birthday;
        this.Post = Post;
    }

    public void PrintTable(){
        System.out.printf("│%30.30s│%02d.%02d│%10.10s│\n",FIO,Birthday.getDate(),Birthday.getMonth()+1,Post);
        System.out.println("├──────────────────────────────┼─────┼──────────┤");
    }

    public long getDayCount(){
        Date d0 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(d0.getYear(),d0.getMonth(),d0.getDate());
        if ((Birthday.getMonth()<d0.getMonth())||((Birthday.getMonth() == d0.getMonth())&&(Birthday.getDate()<d0.getDate()))) calendar2.set(d0.getYear()+1,Birthday.getMonth(),Birthday.getDate());
        else calendar2.set(d0.getYear(),Birthday.getMonth(),Birthday.getDate());
        return ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis())/86400000);
    }

}