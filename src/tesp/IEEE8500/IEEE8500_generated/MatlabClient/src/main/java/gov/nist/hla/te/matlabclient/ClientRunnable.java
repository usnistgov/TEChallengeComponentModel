package gov.nist.hla.te.matlabclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Farhad Omar
 *         National Institute of Standards and Technology
 *         farhad.omar@nist.gov
 */

public class ClientRunnable implements Runnable {
    private static final Logger log = LogManager.getLogger();
    
    private static final String HOST = "localhost";
    
    private int portNumber;
    
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean readyToSend = new AtomicBoolean(false);
    private AtomicBoolean readyToReceive = new AtomicBoolean(false);
    
    private double[] dataToServer = null;
    private double[] dataFromServer = null;

    private Thread worker;
    
    ClientRunnable(int portNumber) {
        this.portNumber = portNumber;
    }
    
    public void start() {
        worker = new Thread(this);
        worker.start();
    }
    
    public void interrupt() {
        if (!running.compareAndSet(true, false)) {
            log.warn("unable to interrupt: client not running");
        }
    }
    
    @Override
    public void run() {
        if (!running.compareAndSet(false, true)) {
            log.warn("client already connected to {}:{}", HOST, portNumber);
            return;
        }
        
        try (Socket client = new Socket(HOST, portNumber)) {
            log.info("Connected to {}:{}", HOST, portNumber);

            while (running.get() == true) {
                try {
                    exchangeDataWithServer(client);
                } catch (InterruptedException e) {
                    log.warn("client thread interrupted");
                    running.set(false);
                }
            }
            log.info("Disconnecting from {}:{}", HOST, portNumber);
        } catch (IOException e) {
            log.error("failed to handle client connection", e);
        }
    }
    
    private void exchangeDataWithServer(Socket client)
            throws IOException, InterruptedException {
        // receive data from GridLAB-D
        log.debug("waiting for data from GridLAB-D...");
        while (!readyToSend.compareAndSet(true, false)) {
            Thread.sleep(200); // sleep until readyToSend becomes true
            if (running.get() == false) {
                log.info("interrupted client connection");
                return;
            }
        }
        log.debug("...received data from GridLAB-D");
        
        // process GridLAB-D data
        byte[] sendBuffer = null;
        synchronized (this) {
            sendBuffer = doubleArrayToByte(dataToServer);
        }
        
        // send processed data to server
        client.getOutputStream().write(sendBuffer);

        // receive response from the server
        byte[] receiveBuffer = receivedMessage(client.getInputStream());
        
        // process server response
        dataFromServer = byteToDoubleArray(receiveBuffer);
        
        // send processed response to GridLAB-D
        readyToReceive.set(true);
    }
    
    public boolean hasData() {
        return readyToReceive.get();
    }
    
    // can only be executed once; returns null if no new data
    public double[] getData() {
        if (!readyToReceive.compareAndSet(true, false)) {
            log.warn("client has no new data to read");
            return null;
        }
        return dataFromServer.clone();
    }
    
    // returns false on failure
    public boolean setData(double[] data) {
        synchronized (this) { // guard dataToServer
            if (!readyToSend.compareAndSet(false, true)) {
                log.warn("client is not ready to send new data");
                return false;
            }
            dataToServer = data.clone();
            return true;
        }
    }
    
    // extract the correct number of bytes from the input stream
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
    
    // it seems like this could all be done with ByteBuffer instead
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
