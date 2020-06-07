package gov.nist.hla.te.matlabclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestServer {
    private static final Logger log = LogManager.getLogger();
    
    private static final int PORT_NUMBER = 1345;
    
    private static final int SEND_BUFFER_SIZE = 1977;

    public static void main(String[] args) {
        TestServer server = new TestServer();
        
        try {
            server.start();
        } catch (IOException e) {
            log.fatal("server crashed due to exception", e);
        }
    }
    
    public void start()
            throws IOException {
        try (ServerSocket server = new ServerSocket(PORT_NUMBER)) {
            log.info("started server on port {}", PORT_NUMBER);
            
            while (true) {
                try (Socket client = server.accept()) {
                    log.info("accepted new client connection");
                    handleClientConnection(client);
                }
            }
        }
    }
    
    private void handleClientConnection(Socket client)
            throws IOException {
        byte[] receiveBuffer = receivedMessage(client.getInputStream());
        double[] dataFromClient = byteToDoubleArray(receiveBuffer);
        log.info("received {}", dataFromClient);
        
        double[] data = new double[SEND_BUFFER_SIZE];
        for(int i = 0; i < data.length; i++) {
            data[i] = Math.random();
        }
        
        byte[] sendBuffer = doubleArrayToByte(data);
        client.getOutputStream().write(sendBuffer);
        log.info("responded with {}", data);
    }

    private byte[] receivedMessage(InputStream inputStream)
            throws IOException {
        byte buffer[] = new byte[524288];
        
        int numberOfBytesReceived = inputStream.read(buffer);
        
        byte bytesReceived[] = new byte[numberOfBytesReceived];
        for (int i = 0; i < numberOfBytesReceived; i++) {
            bytesReceived[i] = buffer[i];
        }
        return bytesReceived;
    }
    
    private double[] byteToDoubleArray(byte[] buffer) {
        if (buffer.length % 8 != 0) {
            log.warn("buffer will be truncated; length is not a multiple of 8");
        }
        
        final int numberOfDoubles = buffer.length / 8;
        double[] result = new double[numberOfDoubles];
        byte[] doubleAsByte = new byte[8];

        for (int i = 0; i < numberOfDoubles; i++) {
            System.arraycopy(buffer, i * 8, doubleAsByte, 0, 8);
            result[i] = toDouble(doubleAsByte);
        }
        return result;
    }
    
    private double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
    
    private byte[] doubleArrayToByte(double[] buffer) {
        byte[] result = new byte[buffer.length * 8];
        
        for (int i = 0; i < buffer.length; i++) {
            byte[] doubleAsByte = toByteArray(buffer[i]);
            System.arraycopy(doubleAsByte, 0, result, i * 8, 8);
        }
        return result;
    }
    
    private byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }
}
