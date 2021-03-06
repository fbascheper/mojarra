/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2015 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.faces.test.javaee7.multiFieldValidation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import static org.junit.Assert.assertTrue;

public class Spec1IT {

    private String webUrl;
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.closeAllWindows();
    }
    
    @Test
    public void testSimpleInvalidField() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foofoofoo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("bar");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        assertTrue(!pageText.contains("[foofoofoo]"));
        assertTrue(pageText.contains("[bar]"));
        
        assertTrue(!pageText.contains("Password fields must match"));
        
        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(password1Value.asText().isEmpty());
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(password2Value.asText().isEmpty());
    }
    
    @Test
    public void testSimpleInvalidFields() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("bar");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        assertTrue(pageText.contains("[foo]"));
        assertTrue(pageText.contains("[bar]"));
        
        assertTrue(!pageText.contains("Password fields must match"));
        
        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(password1Value.asText().isEmpty());
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(password2Value.asText().isEmpty());
    }
    

    @Test
    public void testSimpleValidFieldsInvalidBean() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foofoofoo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("barbarbar");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        assertTrue(!pageText.contains("[foofoofoo]"));
        assertTrue(!pageText.contains("[barbarbar]"));
        
        assertTrue(pageText.contains("Password fields must match"));

        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(password1Value.asText().isEmpty());
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(password2Value.asText().isEmpty());
        
    }
    
    @Test
    public void testSimpleValidFieldsValidBean() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foofoofoo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("foofoofoo");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        assertTrue(!pageText.contains("[foofoofoo]"));
        assertTrue(!pageText.contains("[barbarbar]"));
        
        assertTrue(!pageText.contains("Password fields must match"));

        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(password1Value.asText().contains("foofoofoo"));
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(password2Value.asText().contains("foofoofoo"));
        
    }
    
    @Test
    public void testFailingPreconditionsInvalidField() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/failingDevTimePreconditions.xhtml");
        
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foofoofoo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("bar");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        
        assertTrue(pageText.contains("Password fields must match: password1 [foofoofoo] password2 []"));
        
        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(password1Value.asText().isEmpty());
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(password2Value.asText().isEmpty());
    }
    
    @Test
    public void testFailingPreconditionsValidFieldValidBean() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/failingDevTimePreconditions.xhtml");
        
        
        // Even though we are submitting values, the <f:validateWholeBean >
        // is misplaced, so the whole bean validator cannot have access to the 
        // valid property for password2.
        HtmlTextInput password1 = page.getHtmlElementById("password1");
        password1.setValueAttribute("foofoofoo");

        HtmlTextInput password2 = page.getHtmlElementById("password2");
        password2.setValueAttribute("foofoofoo");
        
        HtmlSubmitInput button = page.getHtmlElementById("submit");
        
        page = button.click();
        
        String pageText = page.asXml();
        
        assertTrue(pageText.contains("Password fields must match: password1 [foofoofoo] password2 []"));
        
        HtmlParagraph password1Value = page.getHtmlElementById("password1Value");
        assertTrue(!password1Value.asText().contains("foofoofoo"));
        
        HtmlParagraph password2Value = page.getHtmlElementById("password2Value");
        assertTrue(!password2Value.asText().contains("foofoofoo"));
    }
    
    
}
