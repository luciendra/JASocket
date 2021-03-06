/*
 * Copyright 2012 Bill La Forge
 *
 * This file is part of AgileWiki and is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License (LGPL) as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * or navigate to the following url http://www.gnu.org/licenses/lgpl-2.1.txt
 *
 * Note however that only Scala, Java and JavaScript files are being covered by LGPL.
 * All other files are covered by the Common Public License (CPL).
 * A copy of this license is also included and can be
 * found as well at http://www.opensource.org/licenses/cpl1.0.txt
 */
package org.agilewiki.jasocket.commands;

import org.agilewiki.jactor.RP;
import org.agilewiki.jactor.factory.JAFactory;
import org.agilewiki.jasocket.jid.agent.AgentJid;
import org.agilewiki.jid.JidFactories;
import org.agilewiki.jid.collection.vlenc.BListJid;
import org.agilewiki.jid.scalar.vlens.string.StringJid;

import java.util.Iterator;

abstract public class CommandAgent extends AgentJid {
    protected BListJid<StringJid> out;

    public void setCommandLineString(String commandLine) throws Exception {
    }

    protected Commands commands() {
        return (Commands) getAncestor(Commands.class);
    }

    protected Command getCommand(String name) {
        return commands().get(name);
    }

    protected Iterator<String> commandIterator() {
        return commands().iterator();
    }

    protected void println(String v) throws Exception {
        out.iAdd(-1);
        StringJid sj = out.iGet(-1);
        sj.setValue(v);
    }

    abstract protected void process(RP rp) throws Exception;

    @Override
    public void start(RP rp) throws Exception {
        out = (BListJid<StringJid>) JAFactory.newActor(
                this, JidFactories.STRING_BLIST_JID_TYPE, getMailboxFactory().createMailbox());
        process(rp);
    }
}
