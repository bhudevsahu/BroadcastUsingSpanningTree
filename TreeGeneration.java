import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

public class TreeGeneration
{
	static BufferedReader br = null;
	static ArrayList<String> hosts = new ArrayList<String>();
	static ArrayList<String> hostname = new ArrayList<String>();
	static ArrayList<Integer> ports = new ArrayList<Integer>();
	static ArrayList<String> topology = new ArrayList<String>();
	static ArrayList<Integer> children = new ArrayList<Integer>();
	static ArrayList<String> neighbours = new ArrayList<String>();
	static int noOfBroadcast = 0;
	
	// Fn to collect the details about the topology and ports.
	public static void checkNodes()
	{
		try {
			 
			String sCurrentLine; 				
			br = new BufferedReader(new FileReader("conf.txt")); 
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(sCurrentLine.startsWith("#"))
					continue;
				if(sCurrentLine.startsWith("Broadcast"))
				{
					String[] part = sCurrentLine.split(":");
					noOfBroadcast = Integer.parseInt(part[1].replaceAll("\\s",""));
					continue;
				}
				String[] parts = sCurrentLine.split("\t");
				String oneNode = parts[0];	
				int port = Integer.parseInt(parts[1]);
				String topo = parts[2];
				//System.out.println(oneNode);
				//System.out.println(ports);
				hostname.add(oneNode+".utdallas.edu");	
				topology.add(topo);
				ports.add(port);
			}			
			for(int i=0;i<hostname.size();i++)				
			{
				//System.out.println(hostname.get(i)+" : "+ports.get(i));			
				String[] parts = topology.get(i).split(" ");
				for(int j=0;j<parts.length;j++)
				{
					int index= Integer.parseInt(parts[j]);
					//System.out.println(hostname.get(index-1)+", listening on: "+ports.get(index-1));
				}
				//System.out.println();
			}
			//Start listening to the port provided.
			//System.out.println(TCPSend.getnodenumber());
			for(int i=0;i<hostname.size();i++)
			{				
				if(hostname.get(i).trim().equals(InetAddress.getLocalHost().getHostName()))
				{
					hosts.add(hostname.get(i));					
					TCPReceive.listen_port = ports.get(i);
					// Start the listener port to accept any incoming connections.					
					//System.out.println("Starting Tcp listener thread");
					Thread listener = new Thread(new TCPReceive());
					listener.start();
				}
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void generateSpanningTree(int node, int root)
	{
		Logger.writeFlowEntry("Inside generateSpanningTree() method");
			String[] parts = topology.get(node-1).split(" ");
			for(int j=0;j<parts.length;j++)
			{
				int index= Integer.parseInt(parts[j]);
				//System.out.println(index+", "+ports.get(index-1)+", "+root);
				TCPSend.Tcpsend_applicationMsg(index, ports.get(index-1), root);
				//System.out.println(hostname.get(index-1)+", listening on: "+ports.get(index-1));
			}	
			Logger.writeFlowEntry("Exiting generateSpanningTree() method");
	}
	public static void sendBroadcast()
	{	
		Logger.writeFlowEntry("Inside sendBroadcast() method");
			for(int childNode: children)
			{
				TCPSend.Tcpsend_broadcastMsg(childNode, getPort(childNode));
			}	
			Logger.writeFlowEntry("Exiting sendBroadcast() method");
	}
	public static void printNeighbours()
	{
		Logger.writeFlowEntry("Inside printNeighbours() method");		
		//System.out.println("Printing neighbours");
		Logger.writeFlowEntry("Number of neighbours: "+neighbours.size());
		Logger.writeNodeEntry("Number of neighbours: "+neighbours.size());
		//System.out.println();
		Logger.writeFlowEntry("Printing neighbours");
		Logger.writeNodeEntry("Printing neighbours");
		for(String neighbour: neighbours)
		{
			Logger.writeFlowEntry(neighbour+", ");
			Logger.writeNodeEntry(neighbour+", ");
			//System.out.print(neighbour+", ");
		}
		Logger.writeFlowEntry("Inside printNeighbours() method");
	}
	public static void printChildren()
	{
		Logger.writeFlowEntry("Inside printChildren() method");		
		//System.out.println("Printing neighbours");
		Logger.writeFlowEntry("Number of children: "+children.size());
		Logger.writeNodeEntry("Number of children: "+children.size());
		//System.out.println();
		Logger.writeFlowEntry("Printing children");
		Logger.writeNodeEntry("Printing children");
		for(int child: children)
		{
			Logger.writeFlowEntry(child+", ");
			Logger.writeNodeEntry(child+", ");
			//System.out.print(neighbour+", ");
		}
		Logger.writeFlowEntry("Inside printChildren() method");			
	}
	
	public static int getPort(int node)
	{
		Logger.writeFlowEntry("Inside getPort() method");	
		return ports.get(node-1);
	}
}
