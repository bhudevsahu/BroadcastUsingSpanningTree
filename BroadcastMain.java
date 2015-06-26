import java.util.Scanner;

import javax.naming.InitialContext;



public class BroadcastMain 
{	
	private int initiator=1;
	public int getInitiator() 
	{
		return initiator;
	}

	public void setInitiator(int initiator)
	{
		this.initiator = initiator;
	}

	public static void main(String[] args) 
	{
		Logger.writeFlowEntry("Entering main method");
		
		Logger.writeFlowEntry("Calling checkNodes() method");
		//Check the topology/nodes and the ports.
		TreeGeneration.checkNodes();
	
		try 
		{	
			Logger.writeFlowEntry("Thread in sleep for 10 secs");
			Thread.sleep(10000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		// Wait till the TCP listener is up and running.
		//TCPReceive.doWait();
		
		//Process the nodes, number them and if I am node 1, build spanning tree by sending
		//application messages.
		//System.out.println("calling node process");
		TCPSend.nodeProcess();
		Logger.writeFlowEntry("Calling method nodeProcess() completed");
		try 
		{
			Logger.writeFlowEntry("Thread in sleep for 8 secs");
			Thread.sleep(8000);
		} 
		catch (InterruptedException e) 
		{		
			e.printStackTrace();
		}
		
		Logger.writeFlowEntry("Calling method printNeighbours()");
		//Print the neighbours of individual node
		TreeGeneration.printNeighbours();
		
		Logger.writeFlowEntry("Calling method printChildren()");
		//Print the children of individual node
		TreeGeneration.printChildren();
		if(TCPSend.getnodenumber()==new BroadcastMain().getInitiator())
		{	
				System.out.println("\n\nSpanning tree built\n");
				System.out.println("Initiating broadcast...\n");
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
		
		//If I am initiator start broadcasting.
		Logger.writeFlowEntry("Calling doBroadcast() method");
		new BroadcastMain().doBroadcast();
		
		
			
		
	}
	
	public void doBroadcast()
	{
		Logger.writeFlowEntry("Inside doBroadcast() method");
		if(TCPSend.getnodenumber()==new BroadcastMain().getInitiator())
		{	
				System.out.println("Broadcast initiated\n");
				Logger.writeFlowEntry("I am the initiator and starting broadcast");
				TreeGeneration.sendBroadcast();			
		}	
		Logger.writeFlowEntry("Exiting doBroadcast() method");
	}
	
	
}