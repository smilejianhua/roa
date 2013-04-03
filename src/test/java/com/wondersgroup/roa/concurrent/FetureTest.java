/** 
 * Copyright (c) 2012-2015 Wonders Information Co.,Ltd. All Rights Reserved.
 * 5/F 1600 Nanjing RD(W), Shanghai 200040, P.R.C 
 *
 * This software is the confidential and proprietary information of Wonders Group.
 * (Public Service Division / Research & Development Center). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gun.org
 */
package com.wondersgroup.roa.concurrent;

import java.util.concurrent.*;

import org.junit.Assert;
import org.junit.Test;

public class FetureTest {

	@Test
	public void testFetureInterrupt() {
		final ExecutorService exec = Executors.newFixedThreadPool(5);
		Callable<String> call = new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(500);
				return "Feture";
			}
		};
		Future<String> task = exec.submit(call);
		String result = null;
		try {
			while (!task.isDone()) {
				result = task.get(200, TimeUnit.MILLISECONDS);
			}
		}
		catch (InterruptedException e) {
			result = "InterruptedException";
		}
		catch (ExecutionException e) {
			result = "ExecutionException";
		}
		catch (TimeoutException e) {
			result = "TimeoutException";
		}
		Assert.assertEquals(result, "TimeoutException");
		// 关闭线程池
		exec.shutdown();
	}
}
