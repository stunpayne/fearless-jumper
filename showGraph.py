import BaseHTTPServer
import SimpleHTTPServer
import re
import socket
import sys
from subprocess import call
from threading import Timer

# Global variables
server_thread = None
server_running = False
system_times_neg_pattern = "^.*: "
csv_file_location = "graph_rendering.csv"


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


def filter_logs(home_path):
    lines = []
    sdk_path = "/Library/Android/sdk/platform-tools/logcat.txt"
    file_path = home_path + sdk_path
    with open(file_path, mode='r') as fp:
        line = fp.readline()
        while line:
            search_result = re.search(system_times_neg_pattern, line)
            if search_result != None:
                lines.append(line[len(search_result.group(0)):-1])
            line = fp.readline()
    return lines


def write_logs_to_csv(lines):
    writeFile = open(csv_file_location, 'w')
    writeFile.writelines("\n".join(lines))
    writeFile.close()


def start_server(host, port):
    global server_running
    server_address = (host, port)
    httpd = BaseHTTPServer.HTTPServer(server_address, SimpleHTTPServer.SimpleHTTPRequestHandler)
    server_running = True
    httpd.serve_forever()


def show_graph():
    if server_running:
        call(["open", "-aGoogle Chrome", "rendering.html"])
    else:
        print "Server is not running, not displaying graph"


if __name__ == "__main__":
    home_path = sys.argv[1]
    host = sys.argv[2]
    port = int(sys.argv[3])
    if servertest(host, port):
        print("Server is already running")
    else:
        lines = filter_logs(home_path)
        write_logs_to_csv(lines)
        # print str(lines)
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
