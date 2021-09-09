

public class SimpleClass {

    @Test()
    public static void methodTest1() {
        System.out.println("methodTest1 default");
    }

    @Test(priority = 2)
    public static void methodTest2() {
        System.out.println("methodTest2 2");
    }

    @Test(priority = 8)
    public static void methodTest3() {
        System.out.println("methodTest3 8");
    }

    @Test(priority = 7)
    public static void methodTest4() {
        System.out.println("methodTest4 7");
    }

    @Test()
    public static void methodTest5() {
        System.out.println("methodTest5 default");
    }

    @BeforeSuite
    public static void methodBefore() {
        System.out.println("Before");
    }

    @AfterSuite
    public static void methodAfter() {
        System.out.println("After");
    }
}