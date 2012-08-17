package org.agilewiki.jasocket.client;

import org.agilewiki.jactor.lpc.JLPCActor;

import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public class RawWriter extends JLPCActor {
    int maxPacketSize;
    SocketChannel socketChannel;

    public void open(InetSocketAddress inetSocketAddress, int maxPacketSize)
            throws Exception {
        this.maxPacketSize = maxPacketSize;
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.setOption(StandardSocketOptions.SO_SNDBUF, maxPacketSize);
        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);
        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        socketChannel.connect(inetSocketAddress);
    }

    public void writeByteBuffer(ByteBuffer byteBuffer) throws Exception {
        byteBuffer.flip();
        try {
            while (byteBuffer.hasRemaining())
                socketChannel.write(byteBuffer);
        } catch (ClosedChannelException ex) {
        }
    }

    public void close() {
        try {
            socketChannel.close();
        } catch (Exception ex) {
        }
    }
}