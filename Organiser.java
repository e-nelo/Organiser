
package organiser;

/**
 * @author nkem
 */
import java.awt.SystemTray;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import java.util.Scanner;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
public class Organiser {

    /**
     * @param args the command line arguments
     */
    public static String savedEntry = "";
    public static int copyCounter;
    public static String connnt;
    public static int counter;
    public static String filename;


    public static void main(String[] args)
    {
          
      //date manager
      DateMaker Date = new DateMaker();
      Date d = new Date();
      SimpleDateFormat sd = new SimpleDateFormat("H:M:S");
      String title = sd.format(d);
      SimpleDateFormat sdf = new SimpleDateFormat("E yyy:MM:dd");
      String fullDate = sdf.format(d);
      SimpleDateFormat df  = new SimpleDateFormat("d");
      String display = df.format(d);
      
      //borders and main frame
      Border empty = BorderFactory.createEmptyBorder();
      Border white  = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
      Border blackline  = BorderFactory.createEtchedBorder();
      JFrame frame = new JFrame();
      Container content;
      JPanel mainPanel = new JPanel();
      BorderLayout main  = new BorderLayout(4, 4);
      main.setHgap(0);
      main.setVgap(0);
      mainPanel.setLayout(main);

      JPanel headerPanel = new JPanel();
      Font font = new Font("Serif", Font.PLAIN,13);
      BorderLayout b = new BorderLayout();
      headerPanel.setLayout(new GridLayout());
      headerPanel.setBackground(Color.BLACK);
        
      JPanel bPanel  = new JPanel();
      bPanel.setLayout(new GridLayout(5, 5, 0,0));
      bPanel.setBackground(Color.BLACK);
      bPanel.setBorder(empty);  
      headerPanel.setBorder(empty);
      
      //set date buttons
      String m = Date.getMonth();
      int mlength = Date.monthLength();
      
      //convert  firstday of month to an integer.
      int fday =  getDayCount(Date.getDay(m));
       
      //keep track of how many blank days must be shown before the month starts 
      int daysFromLastMonth = 0;
  
       //keep track of whether  these empty days are passed
      boolean flag = false;
      for(int i=1; i<=mlength; i++)
      {
          String Label = Integer.toString(i-daysFromLastMonth);
          final JButton B = new JButton();
          B.setEnabled(false);
          
          //if blank days are passed, write day to screen
          if(i >= fday){
          B.setText(Label); 
          B.setEnabled(true);

          //if  empty days are passed, add ommitted days to bottom 

          if(!flag)
          {

              //change limit of i inside for loop/

              mlength+=daysFromLastMonth;
              //mark this so this if clause executes only once
              flag = true;
          }
          }
          else{

          //increment daysFromLastMonth because this is a blank button
          B.setText(" ");
          daysFromLastMonth++;
          }
          B.setBackground(Color.BLACK);
          B.setForeground(Color.WHITE);  
          
          
            //set windowfocus on date            
            if(B.getActionCommand().equals(display)) 
            {
            frame.addWindowFocusListener(new WindowAdapter()
            {
            public void windowGainedFocus(WindowEvent e)
            {   
                B.requestFocus();
            }
            });
            }
            
          //initialise new window for task list when particular button is clicked
          B.addActionListener(new ActionListener()
          {
              @Override
              public void actionPerformed(ActionEvent e)
              {
                  
                  //new window
                  JFrame f = new JFrame();
                  Container c = f.getContentPane();
                  JTextArea area = new JTextArea();
                  
                  BorderLayout b = new BorderLayout();
                  
                  filename = e.getActionCommand();
                   
                  
                  String openingLine = "\t            TASKS\n- ";
                  //align day of week with day - 
                  String dayy = Date.getDay(m);
                  
                  area.setFont(new Font("Comic Sans ms", Font.PLAIN, 17));
                  area.setMargin(new Insets(10, 10, 10, 10));
                  area.setCaretColor(Color.WHITE);
                  area.setBackground(Color.BLACK);
                  area.setForeground(Color.WHITE);
                  area.setLineWrap(true);
                  
                 
                  //set position on screen
                  GraphicsEnvironment windowPosition = GraphicsEnvironment.getLocalGraphicsEnvironment();
                  GraphicsDevice defaultScreen = windowPosition.getDefaultScreenDevice();
                  Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
                  int x = (int) rect.getMaxX() -frame.getWidth();
                  int y =320;

                  //add new line every time user presses 'enter'
                  Action addNewLine = new AbstractAction(){
                  
                  public void actionPerformed(ActionEvent e)
                    {
                        area.append("\n- ");
                    }

                }; 
                area.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"addNewLine");
                area.getActionMap().put("addNewLine", addNewLine);
                
                //open relevant file ifexists when particular date is clicked
                f.addWindowListener(new WindowAdapter(){
                    public void windowOpened(WindowEvent e)
                    {
                        File fileRead  = new File(filename+".txt");
                        if(fileRead.exists())
                        {
                        try{
                            BufferedReader br = new BufferedReader(new FileReader(fileRead));
                            while((savedEntry = br.readLine())!=null)
                            {
                                area.append(savedEntry);
                                area.append("\n");
                                        
                            }area.append("- ");
                            br.close();
                        }
                          catch(Exception e1)
                          {
                              System.out.println("Filereading error is "+e1);
                          }
                        
                    
                    }else{ area.setText(openingLine); }
                    }
                });

                //save whatever user types, and if user doesnt type, do not save or append to file
                f.addWindowListener(new WindowAdapter(){
                    public void windowClosing(WindowEvent e)
                    {
                        if(!(area.getText().equals(openingLine)||(area.getText().isEmpty())))
                        {
                        try
                          {
                        
                              File file = (new File(filename+".txt"));
                          if(!file.exists())
                           {
                               
                           file.createNewFile();
                           FileWriter ff = new FileWriter(file,true);                                  
                           area.write(ff);
                           ff.flush();
                           ff.close();
                          }
                          else
                          {                              
                              Scanner scanner = new Scanner(new File(filename+".txt"));
                              String text = scanner.useDelimiter("\\A").next();
                              scanner.close();
                              if(text.compareToIgnoreCase(area.getText())!=0)
                              {
                                  File fil = new File(filename + ".txt");
                                  FileWriter ff = new FileWriter(file);                                  
                                  area.write(ff);
                                  ff.flush();
                                  ff.close();
                              }
                          }
                          
                          
                          
                }
                catch(IOException ex)
                {
                    System.out.print("exceptiing this - "+ex);
                }
                    }}
                });
                
                  ImageIcon icon = new ImageIcon("C:\\Users\\Nelo\\Documents\\NetBeansProjects\\Organiser\\src\\organiser\\download.jpg");
                  f.setIconImage(icon.getImage());
                  JPanel panel  = new JPanel();
                  panel.setLayout(b);
                  panel.add(area, BorderLayout.CENTER);
                  c.add(panel);
                  f.setTitle(fullDate);
                  f.setLocation(x, y);
                  f.setPreferredSize(new Dimension(500, 300));
                  f.pack();
                  f.setVisible(true);
                  f.setResizable(false);
                  
                }
                  
          });
          
          bPanel.add(B);
      }
      
      int g = 0;
      String weekdd = "SunMonTueWedThuFriSat".trim();
      //label generator
       for(int i  = 0; i<weekdd.length()-1; i++)
       {
         
         if(i<18&&g<18)
         {

          String weekday = weekdd.substring(g, g+3);
          JLabel weekdayButton = new JLabel("   "+weekday);
          weekdayButton.setHorizontalTextPosition(SwingConstants.CENTER);
          weekdayButton.setForeground(Color.white);
          weekdayButton.setBackground(Color.black);
          weekdayButton.setPreferredSize(new Dimension(30, 30));
          weekdayButton.setFont(font);
          weekdayButton.setBorder(white);
          headerPanel.add(weekdayButton);
          g+=3;

         }

         if(i>=18)
         {
             String weekday = weekdd.substring(g, g+3);
             JLabel weekdayButton = new JLabel("       "+weekday);
             weekdayButton.setForeground(Color.white);
             weekdayButton.setBackground(Color.black);
             weekdayButton.setPreferredSize(new Dimension(30, 30));
             weekdayButton.setFont(font);
             weekdayButton.setBorder(white);
             headerPanel.add(weekdayButton);
             break;
         }
         
     }
     
     // real time clock ticking on window title
      ActionListener updateClockAction = new ActionListener()
      {
          public void actionPerformed(ActionEvent e)
          {
              String time  = Date.time();
              frame.setTitle(time+"                           Organiser");
          }
      };
         
      //update time per second
      Timer t = new Timer(1000, updateClockAction);
      t.start();
      
      //update frame icon
      ImageIcon img = new ImageIcon("C:\\Users\\Nelo\\Documents\\NetBeansProjects\\Organiser\\src\\organiser\\download.jpg");
      frame.setIconImage(img.getImage());
      mainPanel.add(headerPanel, BorderLayout.NORTH);
      mainPanel.add(bPanel, BorderLayout.CENTER);
      mainPanel.setForeground(Color.white);
      mainPanel.setBackground(Color.black);
      content = frame.getContentPane();
      content.setBackground(Color.BLACK);
      content.add(mainPanel);
      
      frame.setPreferredSize(new Dimension(500, 300));
      frame.setVisible(true);
      frame.setLocationRelativeTo(null);
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      //set window position
      GraphicsEnvironment windowPosition = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice defaultScreen = windowPosition.getDefaultScreenDevice();
      Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
      int x = (int) rect.getMaxX() -frame.getWidth();
      int y =0;
      frame.setLocation(x, y);
      frame.setResizable(false);
      frame.setVisible(true);
      //system tray icon
      if(SystemTray.isSupported())
      {
         // new TrayIcon(createImage("C:\\Users\\Nelo\\Documents\\NetBeansProjects\\Organiser\\src\\organiser\\"));
            final SystemTray tray = SystemTray.getSystemTray();
            
      }
    }
    
   
    
    
    
    public static int getDayCount(String day){
        
        switch(day){
            case "Sun":
                return 1;
            case "Mon":
                return 2;
            case "Tue":
                return 3;
            case "Wed":
                return 4;
            case "Thur":
                return 5;
            case "Fri":
                return 6;
            default:
                return 7;
                        
                        
        }
    
    }
    
   
    
    public static void fileSave(String name, String textContent)
    throws IOException{
        textContent =" "+connnt;
       File file = new File(name+".txt");
       if(!file.exists()){ file.createNewFile();}
       
            FileWriter fw = new FileWriter(file, true);
            
            fw.write(textContent);
            
           fw.close();
      
      
    }
}






