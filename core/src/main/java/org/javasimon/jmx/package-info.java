/**
 * JMX cabalities for Simons. Package provides two ways of working with Simons via JMX:
 * <ul>
 *     <li>generic single-point Simon MBean;</li>
 *     <li>MX Bean for every Simon.</li>
 * </ul>
 * <b>Simon MBean</b> ({@link org.javasimon.jmx.SimonMXBean}) implements jmx support for core functionality,
 * it means firstly management of java Simons during runtime and secondly, very requested,
 * way how to get gathered data by Simons out of application (jvm) for aditional processing.
 * <p>
 * From management point of view it provides similar functionality like {@link org.javasimon.Manager},
 * but this functionality is accessible remotely. Management features are:
 * <ul>
 * <li>enable/disable whole manager, what practically means turn on/off all Simons</li>
 * <li>enable/disable particular Simon without touching linked hierarchy</li>
 * <li>list all existing Simons</li>
 * <li>clear all Simons (all values are cleared)</li>
 * </ul>
 * For retrieving data from Simons is used new feature: <b>return custom object from MBean's method</b>.
 * This is one of news in JMX 1.4 introduced with Java 6. Against standard MBean this has its pros: all
 * retrieved data are from one moment and also all data are retrieved within single call, so
 * for retrieving data from one Simon only one network roundtrip is needed. However, a con is that
 * classes are not compatible with Java 5 anymore and have to be compiled with Java 6 logicaly. This
 * creates ugly dependency mess between javasimon modules. It complicates build script slightly, but
 * end-user wouldn't be touched by this.
 * <p>
 * Technically, there are two methods, each for one Simon
 * type, {@link org.javasimon.jmx.SimonMXBean#getCounterSample(String)} for retrieving data from Counter Simon
 * and {@link org.javasimon.jmx.SimonMXBean#getStopwatchSample(String)} for retrieving data from Stopwatch Simon. Returned
 * value object is basically Sample object for each type of Simon as is known from core package. Each
 * sample has all needed access methods of its Simon. Parameter for retrieving methods is Simon hierarchical
 * name. To know all existing Simons and its type, use {@link org.javasimon.jmx.SimonMXBean#getSimonInfos()}.
 * <p>
 * Both MBean needs to be registered first. Snippet bellow registers the Simon MBean:
 * <pre>
MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
try {
	ObjectName name = new ObjectName("org.javasimon.jmx.example:type=Simon");
	if (mbs.isRegistered(name)) {
		mbs.unregisterMBean(name);
	}
	SimonMXBean simon = new SimonMXBeanImpl(SimonManager.manager());
	mbs.registerMBean(simon, name);
	System.out.println("SimonMXBean registerd under name: "+name);
} catch (JMException e) {
	System.out.println("SimonMXBean registration failed!\n"+e);
}
 * </pre>
 * Following code then unregisters the MBean.
 * <pre>
MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
try {
	ObjectName name = new ObjectName("org.javasimon.jmx.example:type=Simon");
	if (mbs.isRegistered(name)) {
		mbs.unregisterMBean(name);
	}
	System.out.println("SimonMXBean was unregisterd");
} catch (JMException e) {
	System.out.println("SimonMXBean unregistration failed!\n"+e);
}
 * </pre>
 * Java Simon doesn't provide any automatic mechanism or util functions to register
 * or unregister Simon MBean becouse there are simply too many things which could be customized. It is
 * programmer's responsibility to properly register and later unregister MBean from MBean server.
 */
package org.javasimon.jmx;
