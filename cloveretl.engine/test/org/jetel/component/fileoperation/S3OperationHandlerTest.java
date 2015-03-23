/*
 * jETeL/CloverETL - Java based ETL application framework.
 * Copyright (c) Javlin, a.s. (info@cloveretl.com)
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.jetel.component.fileoperation;

import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jetel.component.fileoperation.SimpleParameters.CreateParameters;
import org.jetel.component.fileoperation.SimpleParameters.DeleteParameters;
import org.jetel.component.fileoperation.result.CreateResult;
import org.jetel.component.fileoperation.result.DeleteResult;

/**
 * @author krivanekm (info@cloveretl.com)
 *         (c) Javlin, a.s. (www.cloveretl.com)
 *
 * @created 18. 3. 2015
 */
public class S3OperationHandlerTest extends OperationHandlerTestTemplate {

	private S3OperationHandler handler;
	
	private static final String testingUri = "s3://AKIAIKRLDHY2RYHSICYA:eaA5%2fhPAsa9SNcagdNjn07SufxdNsyUQ5xsjuhzX@s3.amazonaws.com/cloveretl.engine.test/test-fo/";
	
	@Override
	protected IOperationHandler createOperationHandler() {
		return handler = new S3OperationHandler();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DefaultOperationHandler defaultHandler = new DefaultOperationHandler();
		manager.registerHandler(VERBOSE ? new ObservableHandler(defaultHandler) : defaultHandler);
	}

	@Override
	protected void tearDown() throws Exception {
		Thread.interrupted(); // reset the interrupted flag of the current thread
		DeleteResult result = manager.delete(CloverURI.createURI(baseUri), new DeleteParameters().setRecursive(true));
		if (!result.success()) {
			System.err.println("Failed to delete " + result.getURI(0));
		}
		super.tearDown();
		handler = null;
	}

	@Override
	protected URI createBaseURI() {
		try {
			URI base = new URI(testingUri);
			CloverURI tmpDirUri = CloverURI.createURI(base.resolve(String.format("CloverTemp%d/", System.nanoTime())));
			CreateResult result = manager.create(tmpDirUri, new CreateParameters().setDirectory(true).setMakeParents(true));
			assumeTrue(result.success());
			return tmpDirUri.getSingleURI().toURI();
		} catch (URISyntaxException ex) {
			return null;
		}
	}

	@Override
	public void testGetPriority() {
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.copy(S3OperationHandler.S3_SCHEME, S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.move(S3OperationHandler.S3_SCHEME, S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.delete(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.create(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.resolve(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.info(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.list(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.read(S3OperationHandler.S3_SCHEME)));
		assertEquals(IOperationHandler.TOP_PRIORITY, handler.getPriority(Operation.write(S3OperationHandler.S3_SCHEME)));
	}

	@Override
	public void testCanPerform() {
		assertTrue(handler.canPerform(Operation.copy(S3OperationHandler.S3_SCHEME, S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.move(S3OperationHandler.S3_SCHEME, S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.delete(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.create(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.resolve(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.info(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.list(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.read(S3OperationHandler.S3_SCHEME)));
		assertTrue(handler.canPerform(Operation.write(S3OperationHandler.S3_SCHEME)));
	}

	@Override
	public void testCreateDated() throws Exception {
		// FIXME overridden - setting last modified date is not supported
	}

	@Override
	protected void generate(URI root, int depth) throws IOException {
		int i = 0;
		for ( ; i < 2; i++) {
			String name = String.valueOf(i);
			URI child = URIUtils.getChildURI(root, name);
			manager.create(CloverURI.createSingleURI(child));
		}
	}

}
