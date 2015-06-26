# BroadcastUsingSpanningTree
Assuming a distributed system in which nodes are arranged in a certain topology (specified in a configuration file). Built a spanning tree using a distributed algorithm. Once the spanning tree construction completes, each node should know which subset of its neighbors are also its tree neighbor. Using the spanning tree constructed above implemented a broadcast service that allows any node to send a message to all nodes in the system. The broadcast service informs the source node of the completion of the broadcast operation. Assumption is made that only one broadcast operation can occur at any time.



-	The zip file contains all the required .java files to compile and run the program.

-	It also contains the script files to launch and clean-up the process.

-	Along with all this files a configuration file “conf.txt” is there to help the program build the tree which later be used to start the broadcast service.

-	With all the file logs of one run is also in the file.


To run:

a.	Keep all the file in a folder “AOS” under default location that is $HOME.

In my case it is (absolute location): /home/004/b/bx/bxs123330/AOS

b.	Compile the main program BroadcastMain.java

{dc01:~/AOS} javac BroadcastMain.java


c.	Once the program is compiled it generates all the required class file.

Now run the launcher script:

{dc01:~/AOS} sh launcher.sh


d.	It reads all the parameters from the configuration file. Inside which it is also specified, how many times system will broadcast messages. In my case I have dedicated node 1 to be the initiator of the broadcast for all time (can be changed if needed).



e.	The service writes all its activity in the folder called “Logs” and ca be used to debug as well as check its overall activity node-wise.

/home/004/b/bx/bxs123330/AOS/Logs

Logs on one run is there in the file.

f.	Once it runs the specified times, use the clean-up script to kill the processes initiated by you and are still running in background.
{dc01:~/AOS} sh cleanup.sh





