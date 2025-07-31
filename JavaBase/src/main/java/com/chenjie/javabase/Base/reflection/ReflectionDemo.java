package com.chenjie.javabase.Base.reflection;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Java 反射示例类，展示反射的常见用法
 */
public class ReflectionDemo {
    private String privateField = "私有字段初始值";
    public int publicField = 100;

    public ReflectionDemo() {
    }

    public ReflectionDemo(String privateField, int publicField) {
        this.privateField = privateField;
        this.publicField = publicField;
    }

    private void privateMethod() {
        System.out.println("私有方法被调用: " + privateField);
    }

    public String publicMethod(String message) {
        System.out.println("公共方法被调用: " + message);
        return "处理后的消息: " + message;
    }

    public static void main(String[] args) throws Exception {
        // 1. 获取Class对象的三种方式
        Class<?> clazz1 = ReflectionDemo.class;
        Class<?> clazz2 = Class.forName("com.chenjie.javabase.Base.reflection.ReflectionDemo");
        Class<?> clazz3 = new ReflectionDemo().getClass();
        System.out.println("三种方式获取的Class对象是否相同: " +
                (clazz1 == clazz2 && clazz2 == clazz3));

        // 2. 创建实例
        System.out.println("\n=== 创建实例 ===");
        Object instance1 = clazz1.getDeclaredConstructor().newInstance();
        Object instance2 = clazz1.getDeclaredConstructor(String.class, int.class)
                .newInstance("构造参数", 200);
        System.out.println("instance2的publicField值: " +
                ((ReflectionDemo)instance2).publicField);

        // 3. 访问字段
        System.out.println("\n=== 访问字段 ===");
        Field privateField = clazz1.getDeclaredField("privateField");
        privateField.setAccessible(true); // 突破私有访问限制
        System.out.println("原始privateField值: " + privateField.get(instance1));
        privateField.set(instance1, "通过反射修改后的值");
        System.out.println("修改后privateField值: " + privateField.get(instance1));

        Field publicField = clazz1.getField("publicField");
        System.out.println("publicField值: " + publicField.get(instance1));

        // 4. 调用方法
        System.out.println("\n=== 调用方法 ===");
        Method privateMethod = clazz1.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(instance1);

        Method publicMethod = clazz1.getMethod("publicMethod", String.class);
        String result = (String) publicMethod.invoke(instance1, "测试消息");
        System.out.println("方法返回值: " + result);

        // 5. 获取类信息
        System.out.println("\n=== 类信息 ===");
        System.out.println("类名: " + clazz1.getName());
        System.out.println("简单类名: " + clazz1.getSimpleName());
        System.out.println("包名: " + clazz1.getPackage().getName());
        System.out.println("父类: " + clazz1.getSuperclass().getName());
        System.out.println("接口: " + Arrays.toString(clazz1.getInterfaces()));
        System.out.println("所有字段: " + Arrays.toString(clazz1.getDeclaredFields()));
        System.out.println("公共方法: " + Arrays.toString(clazz1.getMethods()));

        // 6. 动态代理
        System.out.println("\n=== 动态代理 ===");
        MyInterface proxy = (MyInterface) Proxy.newProxyInstance(
                clazz1.getClassLoader(),
                new Class[]{MyInterface.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("代理方法被调用: " + method.getName());
                        if (args != null) {
                            System.out.println("方法参数: " + Arrays.toString(args));
                        }
                        return null;
                    }
                });
        proxy.doSomething("代理测试");
    }

    interface MyInterface {
        void doSomething(String message);
    }
}