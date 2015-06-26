import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class TCPSend {

	//private static final String[] hostname = { "dc01.utdallas.edu",
		//	"dc02.utdallas.edu", "dc03.utdallas.edu", "dc04.utdallas.edu",
			//"dc05.utdallas.edu" };
	
	
	
	private static TCPSend sync = new TCPSend();

	private static int process_node;
	private static String host;
	
	

	public static void nodeProcess()
	{
		try {
			host = InetAddress.getLocalHost().getHostName();
			int itr = 0;
			for (String h : TreeGeneration.hostname) 
			{
				itr++;				
				if (h.equalsIgnoreCase(host)) 
				{
					process_node = itr;
					Logger.writeFlowEntry("I am node: " + process_node +", listening on port: "+TreeGeneration.getPort(process_node));
					Logger.writeNodeEntry("I am node: " + process_node +", listening on port: "+TreeGeneration.getPort(process_node));
					System.out.println("Process node: "+process_node +", listening on port: "+TreeGeneration.getPort(process_node));
				}
				
			}
		} catch (UnknownHostException e)
		{
			System.out.println("Failed : Host name");
			e.printStackTrace();
		}
		try 
		{
			Logger.writeFlowEntry("Thread in sleep for 5 secs");
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		if(process_node==new BroadcastMain().getInitiator())
		{
			Logger.writeFlowEntry("I am the initiator and starting broadcast");
			Logger.writeNodeEntry("I am the initiator and starting broadcast");
			TreeGeneration.generateSpanningTree(process_node, process_node);	
		}
	}

	public static void doWait()
	{
		synchronized (sync) {
			try {
				sync.wait();
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}

	public static void doNotify() {
		synchronized (sync) {
			sync.notify();
		}
	}
	
	
	// Fn to send broadcast msg to the selected node.
	public static void Tcpsend_broadcastMsg(int node, int port) 
	{	
		Logger.writeFlowEntry("Inside Tcpsend_broadcastMsg() method");			
		Socket srv_socket;
		try {
			srv_socket = new Socket(TreeGeneration.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			Logger.writeFlowEntry("Sending broadcast message from node: "+process_node+" to node: "+node+" on port: "+port);
			Logger.writeNodeEntry("Sending broadcast message from node: "+process_node+" to node: "+node+" on port: "+port);
			msg_send.println("BROADCAST_MSG from node: "+process_node);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.writeFlowEntry("Exiting Tcpsend_broadcastMsg() method");	
	}
	// Fn to send broadcast ack to the selected node.
	public static void Tcpsend_broadcastAck(int node, int port) 
	{	
		Logger.writeFlowEntry("Inside Tcpsend_broadcastAck() method");
		Socket srv_socket;
		try {
			srv_socket = new Socket(TreeGeneration.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			Logger.writeFlowEntry("Sending broadcast ack. from node: "+process_node+" to node: "+node+" on port: "+port);
			Logger.writeNodeEntry("Sending broadcast ack. from node: "+process_node+" to node: "+node+" on port: "+port);
			msg_send.println("BROADCAST_ACK_MSG from node: "+process_node);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.writeFlowEntry("Exiting Tcpsend_broadcastAck() method");	
	}
	// Fn to send application msg to the selected node.
	public static void Tcpsend_applicationMsg(int node, int port, int root) 
	{	
		Logger.writeFlowEntry("Inside Tcpsend_applicationMsg() method");		
		Socket srv_socket;
		try {
			srv_socket = new Socket(TreeGeneration.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			Logger.writeFlowEntry("Sending application msg. from node: "+process_node+" to node: "+node+" on port: "+port);
			Logger.writeNodeEntry("Sending application msg. from node: "+process_node+" to node: "+node+" on port: "+port);
			msg_send.println("APP_MSG from node, root-node: "+process_node+", "+root);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.writeFlowEntry("Exiting Tcpsend_applicationMsg() method");	
	}
	//Fn to send application ack to the selected node.
	public static void Tcpsend_applicationAck(int node, int port) 
	{		
		Logger.writeFlowEntry("Inside Tcpsend_applicationAck() method");				
		Socket srv_socket;
		try {
			srv_socket = new Socket(TreeGeneration.hostname.get(node-1), port);
			PrintWriter msg_send = new PrintWriter(
					srv_socket.getOutputStream(), true);
			Logger.writeFlowEntry("Sending application ack. from node: "+process_node+" to node: "+node+" on port: "+port);
			Logger.writeNodeEntry("Sending application ack. from node: "+process_node+" to node: "+node+" on port: "+port);			
			msg_send.println("APP_ACK from node: "+process_node);
			

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Logger.writeFlowEntry("Exiting Tcpsend_applicationAck() method");
	}
	public static int getnodenumber()
	{
		Logger.writeFlowEntry("Inside getnodenumber() method");	
		return process_node;
	}
	public static String getHostName()
	{
		Logger.writeFlowEntry("Inside getHostName() method");	
		return host;
	}	
}
