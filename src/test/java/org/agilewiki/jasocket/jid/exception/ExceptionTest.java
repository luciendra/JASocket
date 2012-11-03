package org.agilewiki.jasocket.jid.exception;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jasocket.jid.ExceptionJidFactory;
import org.agilewiki.jasocket.jid.TransportJidFactory;
import org.agilewiki.jasocket.server.SocketAcceptor;
import org.agilewiki.jid.JidFactories;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ExceptionTest extends TestCase {
    public void test() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        JidFactories factories = new JidFactories();
        factories.initialize(mailbox, factory);
        factory.registerActorFactory(TransportJidFactory.fac);
        factory.registerActorFactory(ExceptionJidFactory.fac);
        int maxPacketSize = 300;
        InetAddress inetAddress = InetAddress.getLocalHost();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 8884);
        SocketAcceptor socketAcceptor = new ExceptionSocketAcceptor();
        socketAcceptor.initialize(mailboxFactory.createMailbox(), factory);
        socketAcceptor.open(inetSocketAddress, maxPacketSize);
        DriverProtocol driverProtocol = new DriverProtocol();
        driverProtocol.initialize(mailbox, factory);
        driverProtocol.clientOpen(inetSocketAddress, maxPacketSize, socketAcceptor);
        try {
            DoIt.req.send(new JAFuture(), driverProtocol);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            socketAcceptor.close();
            mailboxFactory.close();
        }
    }
}
