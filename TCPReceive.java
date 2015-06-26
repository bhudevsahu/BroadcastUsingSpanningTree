import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class TCPReceive extends Thread
{
	private static int pNode=-1;
	private static final boolean bool = true;
	public static int listen_port;
	String line;
	int ack_counter=0;
	private static TCPReceive sync = new TCPReceive();
	private static int parentNode = -1;
	// protected ExecutorService threadPool = Executors.newCachedThreadPool();

	public static void doWait() {
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

	@Override
	public void run() 
	{
		// Create a listener port and receive incoming
		// application messages from other clients.
		try (ServerSocket srvsocket = new ServerSocket(listen_port);) 
		{
			
			TCPReceive.doNotify();
			while (bool) 
			{
				try 
				{
					
					Socket recv_socket = srvsocket.accept();					
					BufferedReader reader = new BufferedReader(new InputStreamReader(recv_socket.getInputStream()));	
					String message = reader.readLine();
					if(message.contains("BROADCAST_MSG"))
					{						
						Logger.writeFlowEntry("Received "+message);
						Logger.writeNodeEntry("Received "+message);
						String[] part = message.split(":");
						int sender = Integer.parseInt(part[1].replaceAll("\\s",""));
						//System.out.println(message);
						Logger.writeFlowEntry("Forwarding broadcast message to my children");
						Logger.writeNodeEntry("Forwarding broadcast message to my children");
						TreeGeneration.sendBroadcast();
						pNode=sender;
						if(TreeGeneration.children.size()==0)
						{
							Logger.writeFlowEntry("I am leaf node, sending broadcast ack. back to my parent node: "+sender);
							Logger.writeNodeEntry("I am leaf node, sending broadcast ack. back to my parent node: "+sender);
							TCPSend.Tcpsend_broadcastAck(sender, TreeGeneration.getPort(sender));
						}
																						
					}
					else if(message.contains("BROADCAST_ACK_MSG"))
					{
						//System.out.println(message);
						Logger.writeFlowEntry("Received "+message);
						Logger.writeNodeEntry("Received "+message);
						ack_counter++;
						Logger.writeFlowEntry("No. of broadcast acks received: "+ack_counter);
						Logger.writeNodeEntry("No. of broadcast acks received: "+ack_counter);
						if(ack_counter == TreeGeneration.children.size())
						{	
							Logger.writeFlowEntry("All broadcast ack. received from children");
							Logger.writeNodeEntry("All broadcast ack. received from children");
							ack_counter=0;
							//System.out.println("Parent node value: "+pNode);
							if(pNode<0)
							{
								Logger.writeFlowEntry("######Broadcast operation completed######\n");
								Logger.writeNodeEntry("######Broadcast operation completed######\n");		
								System.out.println("\n######Broadcast operation completed######\n\n");								
								pNode=-1;
								if(TreeGeneration.noOfBroadcast==1)
									System.exit(0);
								
								if(TreeGeneration.noOfBroadcast>1)
								{
									TreeGeneration.noOfBroadcast--;
									System.out.println(TreeGeneration.noOfBroadcast+" time(s) remaining\n");
									try 
									{
										Logger.writeFlowEntry("Thread in sleep for 2 secs");
										Thread.sleep(2000);
									} 
									catch (InterruptedException e) 
									{		
										e.printStackTrace();
									}
									new BroadcastMain().doBroadcast();									
								}
							}
							else
							{
								Logger.writeFlowEntry("Sending broadcast ack back to my parent");
								Logger.writeNodeEntry("Sending broadcast ack back to my parent");
								TCPSend.Tcpsend_broadcastAck(pNode, TreeGeneration.getPort(pNode));
							}
						}
					}
					else if(message.contains("APP_MSG"))
					{						
						//System.out.println("application: "+message);
						String[] part = message.split(":");
						String[] part2 = part[1].replaceAll("\\s","").split(",");
						int senderNode = Integer.parseInt(part2[0]);
						int rootNode = Integer.parseInt(part2[1]);
						//System.out.println("parent node value: "+parentNode);
						//System.out.println(rootNode==TCPSend.getnodenumber());
						//System.out.println("overall value: "+(parentNode<0 && !(rootNode==TCPSend.getnodenumber())));
						if(parentNode<0 && !(rootNode==TCPSend.getnodenumber()))
						{
							Logger.writeFlowEntry("Received "+message);
							Logger.writeNodeEntry("Received "+message);
							parentNode = senderNode;
							TreeGeneration.neighbours.add(parentNode+"*");
							TCPSend.Tcpsend_applicationAck(senderNode, TreeGeneration.getPort(senderNode));
							TreeGeneration.generateSpanningTree(TCPSend.getnodenumber(), rootNode);
							
						}
					}
					else if(message.contains("APP_ACK"))
					{
						Logger.writeFlowEntry("Received "+message);
						Logger.writeNodeEntry("Received "+message);
						//System.out.println("acknowledgement: "+message);
						String[] part = message.split(":");
						int senderNode = Integer.parseInt(part[1].replaceAll("\\s",""));
						TreeGeneration.children.add(senderNode);
						TreeGeneration.neighbours.add(Integer.toString(senderNode));					
					}
					reader.close();				
				} 
				
				catch (IOException e) 
				{
					System.out.println("Exception while performing accept");
					System.out.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Exception while performing port ops");
			System.out.println(e.getMessage());
		}
	}
	
}
