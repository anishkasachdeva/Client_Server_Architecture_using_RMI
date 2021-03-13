## Distributed Systems - Assignment 3
####  Client Server Architecture using RMI (Remote Method Invocation)
---
##### Anishka Sachdeva (2018101112)
###### 11th March, 2021
---
#### Objective 
##### Implement Minimum Spanning Tree using RMI(Remote Method Invocation) in Java
---
# Description of Solution
#### Language Used
Java.

---
#### Steps to run the Code
1. Compile the server program : **javac Server.java**
2. Compile the client program : **javac Client.java**
or  directly compile all the Java files using the command : javac *.java
3. Start the rmi registry using the command : **rmiregistry**
4. Open a new window/terminal and start the Server using the command : **java Server**
5. Open one or more new windows/terminals and start the Clients using the command : **java Client < input_file**

###### Note : Output of the code will be displayed on the terminal.
---

#### RMI(Remote Method Invocation)
RMI stands for **Remote Method Invocation**. It is a mechanism that allows an object residing in one system (JVM) to access/invoke an object running on another JVM.
RMI is used to build distributed applications; it provides remote communication between Java programs. It is provided in the package java.rmi.

---

#### High-Level RMI Architecture
In an RMI application, we write two programs, a server program (resides on the server) and a client program (resides on the client).
1. Inside the server program, a remote object is created and reference of that object is made available for the client (using the registry).
2. The client program requests the remote objects on the server and tries to invoke its methods.

Let's talk about two essential components of this architecture. 
1. **Stub** : A stub is a representation (proxy) of the remote object at client. It resides in the client system; it acts as a gateway for the client program.
2. **Skeleton** : This is the object which resides on the server side. Stub communicates with this skeleton to pass request to the remote object.

---
#### Working of RMI
The following points summarize how an RMI application works :
1. When the client makes a call to the remote object, it is received by the stub which eventually passes this request to the RRL.
2. When the client-side RRL receives the request, it invokes a method called invoke() of the object remoteRef. It passes the request to the RRL on the server side.
3. The RRL on the server side passes the request to the Skeleton (proxy on the server) which finally invokes the required object on the server.
4. The result is passed all the way back to the client.
---
#### Implementation Strategy
To write an RMI Java application, we follow the steps given below :
1. Defining a remote interface.
2. Implementing the remote interface
3. Creating Stub and Skeleton objects from the implementation class using rmic (rmi complier).
4. Start the rmiregistry.
5. Create and execute the server application program.
6. Create and execute the client application program.

The entire application is created in two files namely :
#### Server.java File
1. It contains the definition od remote interface in the following manner : 
    1. Create an interface that extends the predefined interface Remote which belongs to the package.
    2. Declare all the business methods that can be invoked by the client in this interface.
    3. Since there is a chance of network issues during remote calls, an exception named RemoteException may occur; throw it.
2. It further contains the implementation of the remote interface in the following manner : 
    1. Implement the interface created in the previous step.
    2. Provide implementation to all the abstract methods of the remote interface.
3. This file creates a remote object by instantiating the implementation class.
4. Exported the remote object using the method **exportObject()** of the class named **UnicastRemoteObject** which belongs to the package java.rmi.server.
5. Got the RMI registry using the **getRegistry()** method of the **LocateRegistry** class which belongs to the package java.rmi.registry.
6. Bind the remote object created to the registry using the **bind()** method of the class named **Registry**. To this method, pass a string representing the bind name and the object exported, as parameters (Here I have used **RMISErver**).
7. Contains 3 major functions : 
    1. **addGraph**
        1. When the object of the Mst class is created, a hashmap is created where key = graph_identifier and value = object of the class Graph (adjList is the member of the class Graph and as soon as the object of class Graph is created in addGraph function, the constructor of the class Graph is called and adjList is created).
        2. **Structure of AdjList** : List of lists where every single list(corresponsing to a node u) stores a pair of integers {node v, edge_weight w} if there is an edge between nodes **u** and **v** with weight **w**.
        3. Once the object is created, it is put into the hashmap.
        4. **Note** : If a client requests to add graph with an already existing graph_identifier, the map value shall be replaced with an empty graph for the same graph identifier.
    2. **addEdge**
        1. The graph identifier helps getting the Graph object from the map.
        2. Now the edge is created with the help of the class Pair. And the edge is added in the following manner : 
            1. Pair (v, weight) is added to the list corresponding with node u.
            2. Pair (u, weight) is added to the list corresponding with node v. 
    3. **getMst** : This function implements the **Prim's Algorithm** for finding the Minimum Spanning Tree and calculates the minimum cost for it. Explanation of Prim's Algorithm is explained below : 
        1. Prim’s Algorithm uses Greedy approach to find the minimum spanning tree. In Prim’s Algorithm we grow the spanning tree from a starting position. In this algorithm, we add vertex to the growing spanning tree in Prim's.
        2. Maintain two disjoint sets of vertices. One containing vertices that are in the growing spanning tree and other that are not in the growing spanning tree.
        3. Select the cheapest vertex that is connected to the growing spanning tree and is not in the growing spanning tree and add it into the growing spanning tree. This can be done using Priority Queues. Insert the vertices, that are connected to growing spanning tree, into the Priority Queue.
        4. Check for cycles. To do that, mark the nodes which have been already selected and insert only those nodes in the Priority Queue that are not marked.
        5. **Note** -  To check the case of -1, we check the visited array. We iterate the visited array and if a vertex remains unvisited, the minimum cost of MST is reported as -1.
#### Client.java File
1. Created a client class from where we have to invoke the remote object.
2. Got the RMI registry using the **getRegistry()** method of the **LocateRegistry** class which belongs to the package java.rmi.registry.
3. Fetched the object from the registry using the method **lookup()** of the class Registry which belongs to the package java.rmi.registry. To this method, you need to pass a string value representing the bind (Here I have used **RMISErver**). This will return the remote object.
4. The lookup() returns an object of type remote, down cast it to the type Mst.
5. Now, the user input is taken using the BufferedReader Class of java.
6. The input is splitted by space and the client request type is checked for further processing.
7. The client requests are handled in the following manner : 
    1. If the client request corresponds to **add_graph** : Clients can request to add a new graph using ‘add graph <graph identifier> n’. This command will add a new graph on the server with the identifier graph identifier and n number of nodes.
    2. If the client request corresponds to **add_edge** : Clients can request to add a new edge in a graph using ‘add edge <graph identifier> <u> <v> <w>’. This will add an undirected edge between the nodes u and v with weight w.
    3. If the client request corresponds to **get_mst** : Clients can request for the total weight of the minimum weight spanning tree in a graph from the
server using ‘get mst <graph identifier>’. The client will print the solution the server returns. In case the graph does not have a spanning tree, -1 should be printed. A graph with identifier graph identifier
will already exist.
8. Once the client request is identified, accordingly the remote methods are invoked by the Stub in the following manner : 
    1. For **add_graph** : addGraph(String id, int vertices)
    2. For **add_edge** : addEdge(String id, int u, int v, int weight)
    3. For **get_mst** : getMst(String id)

---
#### Major RMI Commands Used
**RMI registry** is a namespace on which all server objects are placed. 
1. **bind or rebind** : Each time the server creates an object, it registers this object with the RMIregistry (using bind() or reBind() methods). These are registered using a unique name known as bind name.
2. **lookup** : To invoke a remote object, the client needs a reference of that object. At that time, the client fetches the object from the registry using its bind name (using lookup() method).
---