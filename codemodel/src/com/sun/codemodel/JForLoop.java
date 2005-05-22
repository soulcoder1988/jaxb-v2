/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.codemodel;

import java.util.ArrayList;
import java.util.List;


/**
 * For statement
 */

public class JForLoop implements JStatement {

    private List inits = new ArrayList();
    private JExpression test = null;
    private List<JExpression> updates = new ArrayList<JExpression>();
    private JBlock body = null;

    public JVar init(int mods, JType type, String var, JExpression e) {
	JVar v = new JVar(JMods.forVar(mods), type, var, e);
	inits.add(v);
	return v;
    }

    public JVar init(JType type, String var, JExpression e) {
	return init(JMod.NONE, type, var, e);
    }

    public void init(JVar v, JExpression e) {
	inits.add(JExpr.assign(v, e));
    }

    public void test(JExpression e) {
	this.test = e;
    }

    public void update(JExpression e) {
	updates.add(e);
    }

    public JBlock body() {
	if (body == null) body = new JBlock();
	return body;
    }

    public void state(JFormatter f) {
        f.p("for (");
        boolean first = true;
        for (Object o : inits) {
            if (!first) f.p(',');
            if (o instanceof JVar)
                f.b((JVar) o);
            else
                f.g((JExpression) o);
            first = false;
        }
        f.p(';').g(test).p(';');
        first = true;
        for (JExpression e : updates) {
            if (!first) f.p(',');
            f.g(e);
            first = false;
        }
        f.p(')');
        if (body != null)
            f.g(body).nl();
        else
            f.p(';').nl();
    }

}
