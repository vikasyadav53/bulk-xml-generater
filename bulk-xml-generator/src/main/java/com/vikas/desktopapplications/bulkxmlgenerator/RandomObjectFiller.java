package com.vikas.desktopapplications.bulkxmlgenerator;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import javax.naming.spi.ObjectFactory;
 
public class RandomObjectFiller {
 
    private Random random = new Random();
    
    //STart
    public static void main(String[] args) {
    	RandomObjectFiller r = new RandomObjectFiller();
    	try {
			Object e1 = r.createAndFill(Employee.class);
			if (e1 instanceof Employee) {
				Employee e2 = (Employee)e1;
				System.out.println(true);
				System.out.println("Name " + e2.name);
				System.out.println("Salary " + e2.salary);
				System.out.println("Designation  " + e2.designation);
				System.out.println("City " + e2.getAddress().getCity());
				System.out.println("Line 1 " + e2.getAddress().getLine1());
				System.out.println("Line 2 " + e2.getAddress().getLine2());
				System.out.println("State " + e2.getAddress().getState());
				System.out.println("ZipCode " + e2.getAddress().getZipcode());
			} else {
				System.out.println(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    //End
 
    public <T> T createAndFill(Class<T> clazz) throws Exception {
        T instance = clazz.newInstance();
        for(Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = getRandomValueForField(field);
            field.set(instance, value);
        }
        return instance;
    }
 
    private Object getRandomValueForField(Field field) throws Exception {
        Class<?> type = field.getType();
 
        // Note that we must handle the different types here! This is just an 
        // example, so this list is not complete! Adapt this to your needs!
        if(type.isEnum()) {
            Object[] enumValues = type.getEnumConstants();
            return enumValues[random.nextInt(enumValues.length)];
        } else if(type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return random.nextInt();
        } else if(type.equals(Long.TYPE) || type.equals(Long.class)) {
            return random.nextLong();
        } else if(type.equals(Double.TYPE) || type.equals(Double.class)) {
            return random.nextDouble();
        } else if(type.equals(Float.TYPE) || type.equals(Float.class)) {
            return random.nextFloat();
        } else if(type.equals(String.class)) {
            return UUID.randomUUID().toString();
        } else if(type.equals(BigInteger.class)){
            return BigInteger.valueOf(random.nextInt());
        }
        return createAndFill(type);
    }
}
