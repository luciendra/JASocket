package org.agilewiki.jasocket.jid.echo;

import org.agilewiki.jactor.RP;
import org.agilewiki.jasocket.jid.JidApplication;
import org.agilewiki.jid.Jid;

public class JidEcho extends JidApplication {
    @Override
    protected void receiveRequest(Jid jid, RP<Jid> rp) throws Exception {
        rp.processResponse(jid);
    }
}
