
import java.net.*;
import java.util.ArrayList;
import java.io.*;
public class senderclass extends Thread
{ 
	String name;
	ArrayList al;
	int serverPort;
	senderclass(String s,int serverPort)
	{
		name=s;
		this.serverPort=serverPort;
	}
	
public void run()
{
DatagramSocket aSocket = null;
try
{
aSocket = new DatagramSocket();

	String s1=name.concat(",");
byte [] m = s1.getBytes();
InetAddress aHost = InetAddress.getByName("localhost");

DatagramPacket request =
new DatagramPacket(m,m.length, aHost, serverPort);
aSocket.send(request);
byte[] buffer = new byte[1000];
DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
aSocket.receive(reply);
String s=new String(reply.getData());
String[] tokens = s.split(",");
//String n2=tokens[0];
String ts=tokens[3];
//System.out.println("Reply " + s);
String sn="s,"+name+","+tokens[1]+","+ts+",";
DatagramPacket request1 =
new DatagramPacket(sn.getBytes(),sn.getBytes().length, aHost, 8000);
aSocket.send(request1);
}
catch (SocketException e)
{System.out.println("Socket: " + e.getMessage());
}
catch (IOException e)
{System.out.println("IO: " + e.getMessage());}

finally 
{
	if(aSocket != null) aSocket.close();
} 
} 
}
