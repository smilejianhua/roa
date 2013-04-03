/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-2
 */
package com.wondersgroup.roa.event;

import com.wondersgroup.roa.context.ROARequestContext;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class AfterDoServiceEvent extends ROAEvent {

	private static final long serialVersionUID = -6676028544390720591L;

	private ROARequestContext roaRequestContext;

    public AfterDoServiceEvent(Object source, ROARequestContext roaRequestContext) {
        super(source, roaRequestContext.getROAContext());
        this.roaRequestContext = roaRequestContext;
    }

    public long getServiceBeginTime() {
        return roaRequestContext.getServiceBeginTime();
    }

    public long getServiceEndTime() {
        return roaRequestContext.getServiceEndTime();
    }

    public ROARequestContext getROARequestContext() {
        return roaRequestContext;
    }
}

