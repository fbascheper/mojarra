/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

// TestLifecycleImpl_initial.java

package com.sun.faces.lifecycle;

import com.sun.faces.cactus.JspFacesTestCase;
import org.apache.cactus.WebRequest;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

/**
 * <B>TestLifecycleImpl_initial</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 */

public class TestLifecycleImpl_initial extends JspFacesTestCase {

//
// Protected Constants
//

    public static final String TEST_URI = "/greeting.jsp";


    public String getExpectedOutputFilename() {
        return "TestLifecycleImpl_initial_correct";
    }


    public static final String ignore[] = {
    };


    public String[] getLinesToIgnore() {
        return ignore;
    }


    public boolean sendResponseToFile() {
        return true;
    }

//
// Class Variables
//

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public TestLifecycleImpl_initial() {
        super("TestLifecycleImpl_initial");
	initLocalHostPath();
    }


    public TestLifecycleImpl_initial(String name) {
        super(name);
	initLocalHostPath();
    }

    private String localHostPath = "localhost:8080";

    private void initLocalHostPath() {
	String containerPort = System.getProperty("container.port");
	if (null == containerPort || 0 == containerPort.length()) {
	    containerPort = "8080";
	}
	localHostPath = "localhost:" + containerPort;
    }

//
// Class methods
//

//
// General Methods
//


    protected void initWebRequest(WebRequest theRequest) {
        theRequest.setURL(localHostPath, "/test", "/faces", TEST_URI, null);
    }


    public void beginExecuteInitial(WebRequest theRequest) {
        initWebRequest(theRequest);
    }


    public void testExecuteInitial() {
        boolean result = false;
        LifecycleImpl life = new LifecycleImpl();

	Object oldRequest = facesService.wrapRequestToHideParameters();
        ViewHandler vh = getFacesContext().getApplication().getViewHandler();
        getFacesContext().setViewRoot(vh.createView(getFacesContext(), "/greeting.jsp"));

        try {
            life.execute(getFacesContext());
	    facesService.unwrapRequestToShowParameters(oldRequest);
            life.render(getFacesContext());
        } catch (FacesException e) {
            System.err.println("Root Cause: " + e.getCause());
            if (null != e.getCause()) {
                e.getCause().printStackTrace();
            } else {
                e.printStackTrace();
            }

            assertTrue(e.getMessage(), false);
        }

        assertTrue(verifyExpectedOutput());

    }


} // end of class TestLifecycleImpl_initial
