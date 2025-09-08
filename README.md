This project demonstrates the difference between Single-threaded, Multi-threaded, and Thread Pool-based server implementations using Java Sockets (TCP/IP protocol).

=> Single-threaded Server

    A single thread is used in the server for the entire process.

    A ServerSocket is created at a specific port, which acts as the listener.

    The accept() function is used to accept incoming client connections.

    For each new client, a new socket is created, and the server can print statements and receive messages from the client.

=> Multi-threaded Server

    The accept() method is overridden using the Consumer Functional Interface with a lambda expression.

    The task for each thread is to print the client’s message and send a greeting back.

    In the main function, an infinite loop handles multiple client requests.

    For every client request, a new thread is created, and the client socket is accepted inside the run() method, which defines the thread’s activity.

    Each thread is then started using thread.start().

=> Thread Pool-based Server

    A thread pool is introduced using the ExecutorService.

    The pool size is defined using Executors.newFixedThreadPool().

    Similar to the multi-threaded approach, an infinite loop continuously accepts client requests.

    Instead of creating a new thread for each client, the execute() method is used to submit tasks to the thread pool, making the server more efficient and resource-friendly.
