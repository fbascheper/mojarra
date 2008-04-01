/*
 * $Id: FacesEvent.java,v 1.5 2002/01/16 21:02:44 rogerk Exp $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces;

import java.util.EventObject;
import javax.servlet.ServletRequest;

/**
 * The base-class for JavaServer Faces event objects which
 * are generated by user-interface components.  An Event object
 * encapsulates any state associated with a particular event.
 */
public abstract class FacesEvent extends EventObject {
    private String sourceId;
    private ServletRequest request;

    /**
     * Initializes the request and sourceId properties of
     * this FacesEvent object.
     * @param request the ServletRequest object which this event was derived
     * @param sourceId the id of the component where this event originated
     * @throws NullPointerException if sourceId is null
     */
    protected FacesEvent(ServletRequest request, String sourceId) {
	super(sourceId);
	this.request = request;
	this.sourceId = sourceId;
    }

    /**
     * @return UIComponent instance representing the component where
     *         this event originated
     */
    public Object getSource() {
	//return (UIComponent)ObjectManager.get(request, sourceId);
	return null;
    }

    /**
     * @return String containing the id of the component where
     *         this event originated
     */
    public String getSourceId() { 
	return sourceId;
    }

    /**
     * @return ServletRequest object representing the request this
     *         event was derived from or null if this event did
     *         not originate from a request.
     */
    public ServletRequest getRequest() {
        return request;
    }
}
