package org.agilewiki.jasocket.jid.echo;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAFuture;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jasocket.jid.TransportFactory;
import org.agilewiki.jid.JidFactories;

public class JidEchoTest extends TestCase {
    public void test() throws Exception {
        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(10);
        Mailbox mailbox = mailboxFactory.createMailbox();
        JAFactory factory = new JAFactory();
        factory.initialize(mailbox);
        JidFactories factories = new JidFactories();
        factories.initialize(mailbox, factory);
        factory.registerActorFactory(TransportFactory.fac);
        Driver driver = new Driver();
        driver.initialize(mailbox, factory);
        try {
            DoIt.req.send(new JAFuture(), driver);
        } finally {
            driver.close();
            mailboxFactory.close();
        }
    }
}