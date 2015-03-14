package com.madhu;

import java.lang.reflect.Proxy;

public class MainClass {

	public static void main(String[] args) {
		Class[] proxyInterfaces = new Class[] { Foo.class };
		Delegator object = new Delegator(proxyInterfaces,
				new Object[] { new FooImpl() });
		ClassLoader classLoader = Foo.class.getClassLoader();
		
		Foo foo = (Foo) Proxy.newProxyInstance(classLoader, proxyInterfaces,
				object);
		foo.bar(null);
		
		  /*Foo foo = (Foo) DebugProxy.newInstance(new FooImpl());
		    foo.bar(null);*/
	}

}
