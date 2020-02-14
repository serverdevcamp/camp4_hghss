import socket 
import struct
clientsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
clientsock.connect(('13.125.68.49',8000))
l_onoff = 1
l_linger = 0
clientsock.setsockopt(socket.SOL_SOCKET, socket.SO_LINGER, struct.pack('ii', l_onoff, l_linger))
# clientsock.send('나나나')
msg = clientsock.recv(128)

print("응", msg)