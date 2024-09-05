
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Server extends JFrame implements ActionListener
{
   String type="Server";
    public  static JTextField T1;
    public JPanel pbt;
    public JScrollPane sp; 
    public JButton b1;
    public JMenu m;
    public JLabel l1;
    public JTextArea ta;
    public Font font=new Font("Serif", Font.PLAIN, 20);
    public ImageIcon logo = new ImageIcon("./Images/logo.png");
    public ImageIcon ichat = new ImageIcon("./Images/Chat.png");
    String str=" ",str1=" ";
    public DataOutputStream dr;
    public DataInputStream d;
  public Server(String temp) throws Exception
  {
    this.type=temp;
    ServerSocket ss=new ServerSocket(7777);
    Socket s=ss.accept();
    createGUI();
    d=new DataInputStream(s.getInputStream());
    dr=new DataOutputStream(s.getOutputStream());
    handleEvents();
  }

  public void handleEvents()
    {
        T1.addKeyListener(new KeyAdapter()  {
            public void keyTyped(KeyEvent e) 
            {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                try{
                  sendText();
                }
                catch(Exception ee){ee.printStackTrace();
              }
            }
          }
          });
        b1.addActionListener(this);
    }
  public void createGUI()
    {
        //JFrame Characteristics
        this.setTitle("ChatMate -"+this.type);
        this.setIconImage(logo.getImage());
        this.setSize(600,600);
        //this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(206, 235, 234));
        this.setLayout(new BorderLayout());

        //labels
        l1= new JLabel("Welcome to ChatMate!");
        l1.setFont(this.font);
        l1.setIcon(ichat);
        l1.setHorizontalTextPosition(SwingConstants.CENTER);
        l1.setVerticalTextPosition(SwingConstants.BOTTOM);
        l1.setHorizontalAlignment(SwingConstants.CENTER);
        l1.setBounds(0,0,300,20);
        l1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        T1=new JTextField("",30);
        T1.setFont(font);
        T1.setHorizontalAlignment(SwingConstants.CENTER);
        T1.addActionListener(this);
        
        //buttons
        Icon isend = new ImageIcon("./Images/send.png");
        b1=new JButton(isend);
        b1.setContentAreaFilled(false);
        b1.setFont(font);
        b1.setBounds(0,0,10,10);
        b1.setBorderPainted(false); 
        b1.setBackground(new Color(94, 251, 110));
        add(b1);

        //TextArea for Chat
        ta= new JTextArea();
        ta.setFont(font);
        ta.setBackground(new Color(173, 223, 255));
        ta.setEditable(false);

        //Panel for Buttons and Textfield
        pbt=new JPanel();
        pbt.setBackground(new Color(152, 175, 199));
        pbt.setSize(100,100);
        
        //Scrollpane
        // sp =new JScrollPane();
        // sp.setLayout(new ScrollPaneLayout());
        //adding
        this.add(l1,BorderLayout.NORTH);
        this.pbt.add(T1);
        this.pbt.add(b1);
        this.add(pbt,BorderLayout.SOUTH);
        //this.sp.add(ta);
        this.add(ta,BorderLayout.CENTER);
        this.setVisible(true);
    } 

  public void actionPerformed(ActionEvent ae)
    {
      try
        {
          sendText();
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
   }
  public void sendText() throws Exception
  {
    str1=Server.T1.getText();
    this.ta.append("Server>"+str1+"\n");
    this.T1.setText("");
    dr.writeUTF(str1);
  }
  public void receiveText() throws Exception
  {
    str1=d.readUTF();
    this.ta.append("Client>"+str1+"\n");
    this.T1.setText("");
  }
  public static void main(String args[]) throws Exception
  {
   Server sobj=new Server("Server");
    Thread t1=new Thread( new Thread() {
      public void run(){
        try{
          while(true){
          sobj.receiveText();
          }
            }
        catch(Exception e){}
      }
         });
    t1.start();
  }
}
 