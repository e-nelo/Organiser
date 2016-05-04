
package organiser;

/**
 *
 * @author nkem
 */
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.*;

/*
get year
check leap year 
return number of days for each month
get weekdays
*/
public class DateMaker 
{  
    public static int id = 0;
    public static String m;
    static int l;
      
     
      GregorianCalendar date = new GregorianCalendar();
        
      
       
       int month ;       
       int year= Calendar.YEAR;     
       int day;       
       boolean check_leap;
       
        
   DateMaker()
   {
      
      if(month ==  1)
      {
         if(year%4==0)
         {     
              check_leap = true;
              if(year % 100==0 && year % 400 != 0)
              {
                  check_leap = true;
                  
              }else check_leap = false;
              
         }else check_leap = false;
      }
   }
   
   String getMonth()
   {
       Date date = new Date();
       SimpleDateFormat sdf = new SimpleDateFormat("M");
       m = sdf.format(date);
       return m;         
   }
   
   
   int Jan()
   {    
       int daysInMonth = 31;
       return daysInMonth;
   }
   
   int feb()
   {    int dim=0;
       if(check_leap==true)
       {
           dim = 29; return dim;
       }
       if(check_leap== false)
       {
           dim = 28 ;  return dim;
       }
       
       return dim;
   }
   
   int mar()
   {
       int dim = 31;
       return dim;
   }
   
   int apr()
   {
       int dim = 30;
       return dim;
   }
   
   int may()
   {
       int dim = 31;
       return dim;
   }
   
   int jun()
   {
       int dim = 30;
       return dim;
   }
   
   int jul()
   {
       int dim = 31;
       return dim;
   }
   
   int aug()
   {
       int dim = 31;
       return dim;
   }
   
   int sep()
   {
       int dim = 30;
       return dim;
   }

   int oct()
   {
       int dim = 31;
       return dim;
   }
   
   int nov()
   {
       int dim = 30;
       return dim;
   }
   
   int dec()
   {
       int dim = 31;
       return dim ;
   }
   
   int monthLength()
   {    
       switch(m)
       {
             case "1":
                l= Jan();              
                break;
              
             case "2":
                l = feb();
                break;
           
             case "3":
                l = mar();
                break;
             case "4":
                l = apr();
               break;
             case "5":
                l = may();
                break;     
             case "6":
                l = jun();
                 break;     
             case "7":
                l= jul();
                 break;         
             case "8":
                l= aug();
                break;
             case "9":
                l = sep();
                 break;
             case "10":
                l = oct();
                 break;
                 
             case "11":
                l = nov();
                break;
                
             case "12":
                l= dec();
                 break;  
                 
               
               
       }
       return l;
   }
   
   
   
   public String getDay(String m)
   {
       Calendar c =Calendar.getInstance();
       c.set(Calendar.DATE, 1);
       c.set(Calendar.DAY_OF_MONTH, 1);
       c.set(Calendar.YEAR, year);
       SimpleDateFormat sdf = new SimpleDateFormat("EEE");
       String firstDay = sdf.format(c.getTime());
       return firstDay;
       
   }
   
   
   
   
   
   
   
   //check to see if 
    String time()
    {       
        Date date = new Date();
        String datee = date.toString();
        SimpleDateFormat sdf  = new SimpleDateFormat("hh:mm:ss");
        String time  = sdf.format(date);
        return time;
    }
}

           
   
   

