package com.madhu;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dispatcher {

	class Request {
		String serviceName;
		Object[] input;
		public Request() {
		}
	}

	class Response {
		String response;
	}

	public String DispatchAndInvoke(Request input) {

		String name = input.getClass().getPackage().getName();
		System.out.println("Dispatcher.DispatchAndInvoke():" + name);
		Class<? extends Dispatcher> class1 = this.getClass();
		// Process @Test
		for (Method method : class1.getDeclaredMethods()) {
			System.out.println("Dispatcher.DispatchAndInvoke():"+method.getName());
			if (method.isAnnotationPresent(ServiceName.class)) {

				Annotation annotation = method.getAnnotation(ServiceName.class);
				ServiceName test = (ServiceName) annotation;

				if (input.serviceName.equals(test.value())) {
					try {
						System.out.println("Dispatcher.DispatchAndInvoke()"+test.value());
						Object response = method.invoke(this,"");
					} catch (IllegalAccessException e) {
						// TODO Auto-`generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		return "";
	}

	@ServiceName("Test")
	public void target(String args) {
       System.out.println("Dispatcher.target()");
	}

	public static void main(String[] arg) {

		System.out.println("Testing...");
		Dispatcher dis=new Dispatcher();
		Request input=dis.new Request();
		input.serviceName="Test";
		dis.DispatchAndInvoke(input);

		//test();

	}

	/*private static void test() {
		int passed = 0, failed = 0, count = 0, ignore = 0;

		Class<Dispatcher> obj = Dispatcher.class;

		// Process @TesterInfo
		if (obj.isAnnotationPresent(ServiceName.class)) {

			Annotation annotation = obj.getAnnotation(ServiceName.class);
			ServiceName testerInfo = (ServiceName) annotation;

			System.out.printf("%nPriority :%s", testerInfo.priority());
			System.out.printf("%nCreatedBy :%s", testerInfo.createdBy());
			System.out.printf("%nTags :");

		}

		// Process @Test
		for (Method method : obj.getDeclaredMethods()) {

			// if method is annotated with @Test
			if (method.isAnnotationPresent(ServiceName.class)) {

				Annotation annotation = method.getAnnotation(ServiceName.class);
				ServiceName test = (ServiceName) annotation;

				// if enabled = true (default)
				if (test.enabled()) {

					try {
						method.invoke(obj.newInstance());
						System.out.printf("%s - Test '%s' - passed %n",
								++count, method.getName());
						passed++;
					} catch (Throwable ex) {
						System.out.printf("%s - Test '%s' - failed: %s %n",
								++count, method.getName(), ex.getCause());
						failed++;
					}

				} else {
					System.out.printf("%s - Test '%s' - ignored%n", ++count,
							method.getName());
					ignore++;
				}

			}

		}
		System.out.printf(
				"%nResult : Total : %d, Passed: %d, Failed %d, Ignore %d%n",
				count, passed, failed, ignore);
	}*/

}
