# java-reflection-api
자바 Reflection API 정리

## Java Reflection API
컴파일 타임에 구체적인 클래스 타입을 알 수 없는 클래스, 인터페이스, 필드, 메서드에 대한 런타임 접근 또는 수정을 가능하게 해주는 자바 기본 API이다.

실제 코드 작성 시에 Reflection API를 사용하게 될 일은 많이 없다. 대부분의 경우에 구체 클래스 타입을 아는 상태에서 코드를 작성하기 때문이다. 또한, 컴파일 타임이 아니라 런타임에 동적으로 타입을 분석하고 정보를 가져와 JVM을 최적화 할 수 없어 성능 오버헤드가 발생할 수 있다는 문제점도 갖고 있다.

Reflection API는 주로 Spring Framework와 같이 프레인워크나 라이브러리가 런타임에 동적으로 사용자가 만들 클래스를 생성해야 할 때 사용한다.

## 클래스 생성 방법
- `클래스명.class`
- `인스턴스명.getClass()`
- `Class.forName("클래스의 Full Qualified Class Name(FQCN)")`

```java
// Parent 클래스를 클래스 객체로 호출
Class<?> clazz = Class.forName("com.mdg.Parent");
```

## 객체 생성(인스턴스화)
```java
// 클래스 객체에서 생성자에 접근하여 객체(인스턴스) 생성
// 클래스 객체에서 newInstance() 메서드로 객체 생성이 가능하지만
// 자바 9 이후로 해당 메서드가 deprecated됨
Parent parent = (Parent)clazz.getConstructor().newInstance();
```

## 필드 접근

### public일 경우
```java
// 클래스 객체에서 필드 접근(getField() 메서드 이용)
// getField() 메서드는 public 필드에만 접근 가능
Field fieldA = clazz.getField("fieldA");

System.out.println("fieldA.get(parent) = " + fieldA.get(parent));
```

### private일 경우(public일 경우에도 가능)
```java
// private 필드에 접근하기 위해서는
// 필드 객체의 accessible 값을 true로 변경해야 함
Field fieldB = clazz.getDeclaredField("fieldB");
fieldB.setAccessible(true);

System.out.println("fieldB.get(parent) = " + fieldB.get(parent));
```

### 필드 값 변경
```java
// 필드 값을 변경하기(set() 메서드 이용)
fieldB.set(parent, "modifiedValue");

System.out.println("fieldB.get(parent) = " + fieldB.get(parent));
```

## 메서드 접근
```java
// 클래스 객체에서 메서드 접근
Method methodA = clazz.getDeclaredMethod("methodA");
```

## 메서드 호출
```java
// 메서드를 호출하여 실행
methodA.invoke(parent);
```

## 생성자 접근
```java
// Child 클래스를 클래스 객체로 호출
Class<?> clazz2 = Class.forName("com.mdg.Child");

// 클래스 객체로 생성자 접근
Constructor<?> constructor = clazz2.getConstructor(String.class);

System.out.println("constructor.getName() = " + constructor.getName());
```

## 부모 클래스 접근
```java
// 클래스 객체로 부모 클래스 접근
Class superClass = clazz2.getSuperclass();

// 자식 클래스 객체에서 얻은 부모 클래스의 필드에 접근
Field parentFieldA = superClass.getField("fieldA");
System.out.println("parentFieldA.get(parent) = " + parentFieldA.get(parent));
```

