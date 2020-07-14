import java.util.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
class maslistener extends Thread
{
	HashMap<String,ArrayList<String>> hm1=new HashMap<String,ArrayList<String>>(); 
	maslistener(HashMap<String,ArrayList<String>> hm)
	{
		hm1=hm;
	}
	public void run()
	{
		
		System.out.println("** Calls to be made **");
    	for(String keys : hm1.keySet())
    	{
    		System.out.println(keys+":"+hm1.get(keys));
    	}
    	System.out.print("\n");
	           DatagramSocket aSocket = null;
	try
	{
		
	aSocket = new DatagramSocket(8000);
	
	byte[] buffer = new byte[1000];
	while(true)
	{
	
	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
	aSocket.setSoTimeout(1500);
	aSocket.receive(request);
	String s=new String(request.getData());
	String[] tokens = s.split(",");
	//System.out.println("tok 0"+tokens[0]);
	//String ir=tokens[0].toString().trim();
	//String r=new String("r");
	//System.out.println(ir+"=="+r);
	
	//String es=new String("s");
	//for(String toks : tokens)
	//{
	//	System.out.print(toks);
	//}
	//System.out.println("\n");
	if(tokens[0].equals("s"))
	{
		System.out.println(tokens[1]+" recieved reply message from "+tokens[2]+" ["+tokens[3]+"]");
	}
	
	else if(tokens[0].equals("r"))
	{
		System.out.println(tokens[1]+" recieved intro message from "+tokens[2]+" ["+tokens[3]+"]");
	}
	else
	{
		System.out.print("\n");
	}
//	DatagramPacket reply = new DatagramPacket(request.getData(),
//	request.getLength(), request.getAddress(), request.getPort());
//	aSocket.send(reply);
//	}
	}
	}
	catch (SocketTimeoutException e)
	{
		System.out.println("Master has received no calls for 1.5 seconds,ending...");
	}
	catch (SocketException e)
	{
		System.out.println("Socket: " + e.getMessage());
	}
	catch (IOException e)
	{
		System.out.println("IO: " + e.getMessage());
		}
	finally {
		if(aSocket != null) aSocket.close();
		}
	}
	}
	
public class exchange
{
  public static List<String> readFileInList(String fileName)
  {
	  
	  
    List<String> lines = Collections.emptyList();
    try
    {
      lines =
       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }
 
    catch (IOException e)
    {
 
      // do something
      e.printStackTrace();
    }
    return lines;
  }
  public static void main(String[] args)
  {
    List l = readFileInList("C:/Erlang Code Rep/finproj/src/calls.txt");
    HashMap<String,ArrayList<String>> hm=new  HashMap<String,ArrayList<String>>();
    ArrayList<String> al=new ArrayList<String>();
    for(int i=0;i<l.size();i++)
    {
    	String[] tokens = l.get(i).toString().split(",");
    	for(int j=0;j<tokens.length;j++)
    	{
    		tokens[j]=tokens[j].replaceAll("[^a-zA-Z0-9_-]","");
    	
    		if(j==0)
    		{
    		hm.put(tokens[j],new ArrayList<String>());
    		}
    		else
    		{
    			hm.get(tokens[0]).add(tokens[j]);
    		}
    	}
    }
    maslistener a=new maslistener(hm);
	  a.start();
    	//System.out.println();
    	
    int i=6879;
    HashMap<String,Integer> lp=new HashMap<String,Integer> ();
    for(String keys:hm.keySet())
    {
    	lp.put(keys,i);
    	recieverclass rc=new recieverclass(keys,i);
    	rc.start();
    	i++;
    }
    for (String var :hm.keySet()) 
    { 
     	for (String var1 :hm.get(var)) 
    	{ 
     		
    		   senderclass sc=new senderclass(var,lp.get(var1));
        	sc.start();
    	}
     	
    	
    }
    
    
  }
}


