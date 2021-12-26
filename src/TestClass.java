import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestClass {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        start(SimpleClass.class);
    }

    public static void start(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getDeclaredMethods();

        checkBeforeAndAfter(methods);
        invokeBefore(methods, clazz);
        invokeTest(methods, clazz);
        invokeAfter(methods, clazz);
    }

    private static void invokeTest(Method[] methods, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Comparator<Method> comparator = ((o1, o2) -> {
            int a = o1.getAnnotation(Test.class).priority();
            int b = o2.getAnnotation(Test.class).priority();
            return Integer.compare(b, a);
        });

        List<Method> list = new ArrayList<>();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                list.add(method);
            }
        }
        list.sort(comparator);
        for (Method method : list) {
            method.invoke(clazz);
        }
    }

    private static void invokeAfter(Method[] methods, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                method.invoke(clazz);
                break;
            }
        }
    }

    private static void invokeBefore(Method[] methods, Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                method.invoke(clazz);
                break;
            }
        }
    }

    private static void checkBeforeAndAfter(Method[] methods) {
        int countBefore = 0;
        int countAfter = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                countBefore++;
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                countAfter++;
            }
        }
        if (countAfter != 1 || countBefore != 1) {
            throw new RuntimeException();
        }
    }
}
