package org.javasimon;

import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;

/**
 * SimonManager is the central-point of the API. Manager provides access to all available Simons
 * and is also responsible for creating them when necessary.
 *
 * @author <a href="mailto:virgo47@gmail.com">Richard "Virgo" Richter</a>
 * @created Aug 4, 2008
 */
public final class SimonManager {
	/**
	 * Hierarchy delimiter in Simon name.
	 */
	public static final String HIERARCHY_DELIMITER = ".";

	/**
	 * Name of the root Simon.
	 */
	public static final String ROOT_SIMON_NAME = "";

	private static Manager manager = EnabledManager.INSTANCE;

	static {
		clear();
		try {
			SimonConfigManager.init();
		} catch (IOException e) {
			Logger.getLogger(SimonConfigManager.class.getName()).log(Level.SEVERE, "Simon config couldn't be processed correctly", e);
		}
	}

	private SimonManager() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns Simon by its name if it exists.
	 *
	 * @param name name of the Simon
	 * @return Simon object
	 */
	public static Simon getSimon(String name) {
		return manager.getSimon(name);
	}

	/**
	 * Destroys Simon or replaces it with UnknownSimon if it's necessary to preserve the hierarchy.
	 *
	 * @param name name of the Simon
	 */
	public static void destroySimon(String name) {
		manager.destroySimon(name);
	}

	/**
	 * Returns existing Counter or creates new if necessary.
	 *
	 * @param name name of the Counter
	 * @return counter object
	 */
	public static Counter getCounter(String name) {
		return manager.getCounter(name);
	}

	/**
	 * Returns existing Stopwatch or creates new if necessary.
	 *
	 * @param name name of the Stopwatch
	 * @return stopwatch object
	 */
	public static Stopwatch getStopwatch(String name) {
		return manager.getStopwatch(name);
	}

	/**
	 * Returns existing UnknownSimon or creates new if necessary.
	 *
	 * @param name name of the Simon
	 * @return stopwatch object
	 */
	static Simon getUnknown(String name) {
		return manager.getUnknown(name);
	}

	/**
	 * Autogenerates name for the Simon using the class name and (optionaly) the method name.
	 *
	 * @param suffix name suffix for eventual Simon discrimination
	 * @param includeMethodName if true, method name will be included in the name thus effectively adding another level
	 * of hierarchy
	 * @return autogenerated name for Simon
	 */
	public static String generateName(String suffix, boolean includeMethodName) {
		return manager.generateName(suffix, includeMethodName);
	}

	/**
	 * Enables the whole Java Simon API. Enabled manager provides real Simons.
	 */
	public static void enable() {
		manager = EnabledManager.INSTANCE;
	}

	/**
	 * Disables the whole Java Simon API. Disabled manager provides null Simons that actually do nothing.
	 */
	public static void disable() {
		manager = DisabledManager.INSTANCE;
	}

	/**
	 * Returns true if the Java Simon API is enabled.
	 *
	 * @return true if the API is enabled
	 */
	public static boolean isEnabled() {
		return manager instanceof EnabledManager;
	}

	/**
	 * Returns root Simon. Type of the Simon is unknown at the start but it can be replaced
	 * by the real Simon later. Specific get method with root simon name constant can be used
	 * in that case.
	 *
	 * @return root Simon
	 */
	public static Simon getRootSimon() {
		return manager.getRootSimon();
	}

	/**
	 * Returns collection containing names of all existing Simons.
	 *
	 * @return collection of all Simon names
	 */
	public static Collection<String> simonNames() {
		return manager.simonNames();
	}

	/**
	 * Clears the SimonManager (ignored if manager is disabled). All Simons are lost,
	 * but configuration is preserved.
	 */
	public static void clear() {
		manager.clear();
	}
}
