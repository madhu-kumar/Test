package com.madhu;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Dispatcher {
	
	class ParamInfo{
		int index;
		String className;
		String packageName;
		String value;
	}

	class Request {
		private String serviceName;
		private List<ParamInfo> input;
		private String userId;
		private String userRole;
		public Request() {
		}
	}

	class Response {
		private String response;
		private boolean success;
		private String errorMessage; 
		private String exceptionClass;
		private String exceptionPackage;
	}

	public Response DispatchAndInvoke(Request input) {
		Response response=new Response();
		String packageName = input.getClass().getPackage().getName();
		Class<? extends Dispatcher> class1 = this.getClass();
		Object res=null;
		for (Method method : class1.getDeclaredMethods()) {
			if (method.isAnnotationPresent(ServiceName.class)) {
				Annotation annotation = method.getAnnotation(ServiceName.class);
				ServiceName test = (ServiceName) annotation;
				if (input.serviceName.equals(test.value())) {
					try {
						Class<?>[] parameterTypes = method.getParameterTypes();
						for (Class<?> class2 : parameterTypes) {
						}
						res = method.invoke(this,input.input);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}

		}
        response.response="";
		return response;
	}

	@ServiceName("Test")
	public void target(String args,String arg2) {
       System.out.println("Dispatcher.target()");
	}

	public static void main(String[] arg) {

		Dispatcher d=new Dispatcher();
		System.out.println("Testing...");
		String one="";
		String two="";
		String response=null;
		response=d.invoker("test",response.getClass(),one,two);
				
		
		
		Dispatcher dis=new Dispatcher();
		Request input=dis.new Request();
		input.serviceName="Test";
		
		dis.DispatchAndInvoke(input);

		//test();

	}

	private <T extends Object> T invoker(String serviceName,Class<T> type,Object... params) {
		for (Object object : params) {
			
		}
		return type.cast("");
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
