    import java.net.*;
	import java.io.*;
	 class recieverclass extends Thread{
	String threadname;	
	int servport;
    recieverclass(String s,int servport)
    {
    	threadname=s;
    	this.servport=servport;
    }
		
	public void run()
	{
		
	           DatagramSocket aSocket = null;
	try
	{
		aSocket = new DatagramSocket(servport);
		while(true)
		{
	
	
	byte[] buffer = new byte[1000];
	DatagramPacket request = new DatagramPacket(buffer, buffer.length);
	aSocket.setSoTimeout(1000);
	aSocket.receive(request);
	String s=new String(request.getData());
	String[] tokens = s.split(",");
	String names=tokens[0];
	String ts=new String("");
	ts=String.valueOf(System.nanoTime());

	Thread.sleep(200);
	//s=s.concat(",");
	String k="r"+","+threadname+","+names+","+ts+",";
	//System.out.println("Receive " + s);
	byte[] buffer1 = new byte[1000];
	buffer1=k.getBytes();
	DatagramPacket reply = new DatagramPacket(buffer1,
	buffer1.length, request.getAddress(), request.getPort());
	aSocket.send(reply);
	DatagramPacket mastreply = new DatagramPacket(buffer1,
			buffer1.length, request.getAddress(),8000);
	aSocket.send(mastreply);
	}}
	catch(SocketTimeoutException e)
	{
		System.out.println("Process "+threadname+" has received no calls for 1 second,ending...\n");
	}
	catch (SocketException e)
	{
		System.out.println("Socket: " + e.getMessage());
	}

	catch (IOException e)
	{
		System.out.println("IO: " + e.getMessage());
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	finally {
		if(aSocket != null) aSocket.close();
		}
	}
	}
	
	

