/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package be.ac.vub.simpleocl.resource.simpleocl.util;

/**
 * Class ResourceUtil can be used to perform common tasks on resources, such as
 * resolving proxy object, saving resources, as well as, checking them for errors.
 */
public class SimpleoclResourceUtil {
	
	/**
	 * Searches for all unresolved proxy objects in the given resource set.
	 * 
	 * @param resourceSet
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public static java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.ResourceSet resourceSet) {
		return new be.ac.vub.simpleocl.resource.simpleocl.util.SimpleoclInterruptibleEcoreResolver().findUnresolvedProxies(resourceSet);
	}
	
	/**
	 * Searches for all unresolved proxy objects in the given resource.
	 * 
	 * @param resource
	 * 
	 * @return all proxy objects that are not resolvable
	 */
	public static java.util.Set<org.eclipse.emf.ecore.EObject> findUnresolvedProxies(org.eclipse.emf.ecore.resource.Resource resource) {
		return new be.ac.vub.simpleocl.resource.simpleocl.util.SimpleoclInterruptibleEcoreResolver().findUnresolvedProxies(resource);
	}
	
	/**
	 * Tries to resolve all unresolved proxy objects in the given resource. If all
	 * proxies were resolved true is returned. If some could not be resolved, false is
	 * returned.
	 * 
	 * @param resource the resource containing the proxy object
	 * 
	 * @return true on success
	 */
	public static boolean resolveAll(org.eclipse.emf.ecore.resource.Resource resource) {
		org.eclipse.emf.ecore.util.EcoreUtil.resolveAll(resource);
		if (findUnresolvedProxies(resource).size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String getProxyIdentifier(org.eclipse.emf.ecore.EObject eObject) {
		String deresolvedReference = null;
		if (eObject instanceof org.eclipse.emf.ecore.EObject) {
			org.eclipse.emf.ecore.EObject eObjectToDeResolve = (org.eclipse.emf.ecore.EObject) eObject;
			if (eObjectToDeResolve.eIsProxy()) {
				deresolvedReference = ((org.eclipse.emf.ecore.InternalEObject) eObjectToDeResolve).eProxyURI().fragment();
				// If the proxy was created by EMFText, we can try to recover the identifier from
				// the proxy URI
				if (deresolvedReference != null && deresolvedReference.startsWith(be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX)) {
					deresolvedReference = deresolvedReference.substring(be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclContextDependentURIFragment.INTERNAL_URI_FRAGMENT_PREFIX.length());
					deresolvedReference = deresolvedReference.substring(deresolvedReference.indexOf("_") + 1);
				}
			}
		}
		return deresolvedReference;
	}
	
	public static be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclResource getResource(java.io.File file) {
		return getResource(file, null);
	}
	
	public static be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclResource getResource(java.io.File file, java.util.Map<?,?> options) {
		return getResource(org.eclipse.emf.common.util.URI.createFileURI(file.getAbsolutePath()), options);
	}
	
	public static be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclResource getResource(org.eclipse.emf.common.util.URI uri) {
		return getResource(uri, null);
	}
	
	public static be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclResource getResource(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		new be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclMetaInformation().registerResourceFactory();
		org.eclipse.emf.ecore.resource.ResourceSet rs = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		if (options != null) {
			rs.getLoadOptions().putAll(options);
		}
		org.eclipse.emf.ecore.resource.Resource resource = rs.getResource(uri, true);
		return (be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclResource) resource;
	}
	
	/**
	 * Returns the resource after parsing the given text.
	 */
	public static org.eclipse.emf.ecore.resource.Resource getResource(String text) {
		be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclMetaInformation metaInformation = new be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclMetaInformation();
		metaInformation.registerResourceFactory();
		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI("temp." + metaInformation.getSyntaxName());
		org.eclipse.emf.ecore.resource.ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		org.eclipse.emf.ecore.resource.Resource resource = resourceSet.createResource(uri);
		if (resource == null) {
			return null;
		}
		java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(text.getBytes());
		try {
			resource.load(inputStream, null);
		} catch (java.io.IOException ioe) {
			return null;
		}
		return resource;
	}
	
	/**
	 * Returns the root element of the resource with the given URI.
	 */
	public static be.ac.vub.simpleocl.Module getResourceContent(org.eclipse.emf.common.util.URI uri) {
		return getResourceContent(uri, null);
	}
	
	/**
	 * Returns the root element of the resource with the given URI.
	 */
	public static be.ac.vub.simpleocl.Module getResourceContent(org.eclipse.emf.common.util.URI uri, java.util.Map<?,?> options) {
		org.eclipse.emf.ecore.resource.Resource resource = getResource(uri, options);
		if (resource == null) {
			return null;
		}
		java.util.List<org.eclipse.emf.ecore.EObject> contents = resource.getContents();
		if (contents == null || contents.isEmpty()) {
			return null;
		}
		org.eclipse.emf.ecore.EObject root = contents.get(0);
		return (be.ac.vub.simpleocl.Module) root;
	}
	
	/**
	 * Returns the root element after parsing the given text.
	 */
	public static be.ac.vub.simpleocl.Module getResourceContent(String text) {
		org.eclipse.emf.ecore.resource.Resource resource = getResource(text);
		if (resource == null) {
			return null;
		}
		java.util.List<org.eclipse.emf.ecore.EObject> contents = resource.getContents();
		if (contents == null || contents.isEmpty()) {
			return null;
		}
		org.eclipse.emf.ecore.EObject root = contents.get(0);
		return (be.ac.vub.simpleocl.Module) root;
	}
	
	public static void saveResource(java.io.File file, org.eclipse.emf.ecore.resource.Resource resource) throws java.io.IOException {
		java.util.Map<?, ?> options = java.util.Collections.EMPTY_MAP;
		java.io.OutputStream outputStream = new java.io.FileOutputStream(file);
		resource.save(outputStream, options);
		outputStream.close();
	}
	
	public static String getText(org.eclipse.emf.ecore.EObject eObject) {
		be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclMetaInformation metaInformation = new be.ac.vub.simpleocl.resource.simpleocl.mopp.SimpleoclMetaInformation();
		metaInformation.registerResourceFactory();
		org.eclipse.emf.ecore.resource.ResourceSet rs = null;
		be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclTextResource resource = (be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclTextResource) eObject.eResource();
		if (resource != null) {
			rs = resource.getResourceSet();
		}
		if (rs == null) {
			rs = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
		}
		if (resource == null) {
			org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI("temp." + metaInformation.getSyntaxName());
			resource = (be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclTextResource) rs.createResource(uri);
		}
		java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
		be.ac.vub.simpleocl.resource.simpleocl.ISimpleoclTextPrinter printer = metaInformation.createPrinter(outputStream, resource);
		try {
			printer.print(eObject);
		} catch (java.io.IOException e) {
			return null;
		}
		return outputStream.toString();
	}
	
	public static boolean containsErrors(org.eclipse.emf.ecore.resource.Resource resource) {
		return !resource.getErrors().isEmpty();
	}
	
	public static boolean containsWarnings(org.eclipse.emf.ecore.resource.Resource resource) {
		return !resource.getWarnings().isEmpty();
	}
	
	public static boolean containsProblems(org.eclipse.emf.ecore.resource.Resource resource) {
		return containsErrors(resource) || containsWarnings(resource);
	}
	
}
