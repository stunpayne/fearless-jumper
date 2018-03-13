import BaseHTTPServer
import SimpleHTTPServer
import socket
import sys
from subprocess import call
from threading import Timer

# Global variables
server_thread = None
server_running = False


def servertest(host, port):
    args = socket.getaddrinfo(host, port, socket.AF_INET, socket.SOCK_STREAM)
    for family, socktype, proto, canonname, sockaddr in args:
        s = socket.socket(family, socktype, proto)
        try:
            s.connect(sockaddr)
        except socket.error:
            return False
        else:
            s.close()
            return True


def start_server(host, port):
    global server_running
    # try:
    server_address = (host, port)
    httpd = BaseHTTPServer.HTTPServer(server_address, SimpleHTTPServer.SimpleHTTPRequestHandler)
    server_running = True
    httpd.serve_forever()


def show_graph():
    if server_running:
        call(["open", "-aGoogle Chrome", "graph.html"])
    else:
        print "Server is not running, not displaying graph"


if __name__ == "__main__":
    host = sys.argv[1]
    port = int(sys.argv[2])
    if servertest(host, port):
        print("Server is already running")
    else:
        print("Server is not running")
        print("Starting server on host: " + host + " port: " + str(port))

        try:
            graph_timer = Timer(1, show_graph)
            graph_timer.start()
            start_server(host, port)
        except KeyboardInterrupt:
            print "Stopping server"
        except Exception, e:
            print "Stopping server on exception: " + str(e)
