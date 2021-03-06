/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipselabs.simpleocl.tests;

import org.eclipselabs.simpleocl.OclMetamodel;
import org.eclipselabs.simpleocl.SimpleoclFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Ocl Metamodel</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class OclMetamodelTest extends OclModelTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(OclMetamodelTest.class);
	}

	/**
	 * Constructs a new Ocl Metamodel test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OclMetamodelTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Ocl Metamodel test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected OclMetamodel getFixture() {
		return (OclMetamodel)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(SimpleoclFactory.eINSTANCE.createOclMetamodel());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //OclMetamodelTest
