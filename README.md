## Distributed Systems - Assignment 3
####  Client Server Architecture using RMI (Remote Method Invocation)
---
##### Anishka Sachdeva (2018101112)
###### 11th March, 2021
---
#### Question : 
#### Implement Minimum Spanning Tree using RMI(Remote Method Invocation) in Java
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
5. Open one or more new windows/terminals and start the Clients using the command : **java Client**
---

#### RMI(Remote Method Invocation)
RMI stands for **Remote Method Invocation**. It is a mechanism that allows an object residing in one system (JVM) to access/invoke an object running on another JVM.
RMI is used to build distributed applications; it provides remote communication between Java programs. It is provided in the package java.rmi.

#### High-Level RMI Architecture
In an RMI application, we write two programs, a server program (resides on the server) and a client program (resides on the client).
1. Inside the server program, a remote object is created and reference of that object is made available for the client (using the registry).
2. The client program requests the remote objects on the server and tries to invoke its methods.

Let's talk about two essential components of this architecture. 
1. **Stub** : A stub is a representation (proxy) of the remote object at client. It resides in the client system; it acts as a gateway for the client program.
2. **Skeleton** : This is the object which resides on the server side. stub communicates with this skeleton to pass request to the remote object.

---
#### Working of RMI
The following points summarize how an RMI application works :
1. When the client makes a call to the remote object, it is received by the stub which eventually passes this request to the RRL.
2. When the client-side RRL receives the request, it invokes a method called invoke() of the object remoteRef. It passes the request to the RRL on the server side.
3. The RRL on the server side passes the request to the Skeleton (proxy on the server) which finally invokes the required object on the server.
4. The result is passed all the way back to the client.
---
#### Implementation Strategy
To write an RMI Java application, you would have to follow the steps given below :
1. Defining a remote interface.
2. Implementing the remote interface
3. Creating Stub and Skeleton objects from the implementation class using rmic (rmi complier).
4. Start the rmiregistry.
5. Create and execute the server application program.
6. Create and execute the client application program.

The entire Application is created in two files namely :
1. **Server.java File**
2. **Client.java File**
---
#### Major RMI Commands Used
**RMI registry** is a namespace on which all server objects are placed. 
1. **bind or rebind** : Each time the server creates an object, it registers this object with the RMIregistry (using bind() or reBind() methods). These are registered using a unique name known as bind name.
2. **lookup** : To invoke a remote object, the client needs a reference of that object. At that time, the client fetches the object from the registry using its bind name (using lookup() method).
---