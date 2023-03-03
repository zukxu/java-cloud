## 设计模式
设计模式（Design Pattern）是一套被反复使用、经过分类的、代码设计经验的总结。设计模式主要是为了解决在面向对象软件设计过程中经常遇到的一些重复性问题，使得设计者能够更加专注于系统架构、提高设计的可复用性、灵活性、可维护性等。

常见的设计模式有以下几种：

### 创建型模式（Creational Pattern）
1. 单例模式（Singleton）  
   确保一个类只有一个实例，并提供全局访问点。

2. 工厂模式（Factory）  
   定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使得一个类的实例化延迟到其子类。

3. 抽象工厂模式（Abstract Factory）  
   提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们具体的类。

4. 建造者模式（Builder）  
   将一个复杂对象的构建与其表示分离，使得同样的构建过程可以创建不同的表示。

5. 原型模式（Prototype）  
   用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。

### 结构型模式（Structural Pattern）
1. 适配器模式（Adapter）  
   将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

2. 桥接模式（Bridge）  
   将抽象部分与它的实现部分分离，使它们都可以独立地变化。

3. 组合模式（Composite）  
   将对象组合成树形结构以表示“部分-整体”的层次结构。组合模式使得客户对单个对象和组合对象的使用具有一致性。

4. 装饰器模式（Decorator）  
   动态地给一个对象添加一些额外的职责。就增加功能而言，装饰器模式比生成子类更为灵活。

5. 外观模式（Facade）  
   为子系统中的一组接口提供一个一致的界面，此模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。

6. 享元模式（Flyweight）  
   运用共享技术有效地支持大量细粒度的对象。

7. 代理模式（Proxy）  
   为其他对象提供一种代理以控制对这个对象的访问。
### 行为型模式（Behavioral Pattern）
1. 模板方法模式（Template Method Pattern）  
定义了一个算法的骨架，将某些步骤延迟到子类中实现。这样，可以在不改变算法结构的情况下，重新定义算法中的某些步骤。

2. 策略模式（Strategy Pattern）  
定义了一系列算法，将每个算法都封装起来，并使它们可以相互替换。策略模式让算法独立于使用它的客户端，使算法可以独立变化。

3. 命令模式（Command Pattern）  
将请求封装成对象，以便使用不同的请求、队列或日志来参数化其他对象。命令模式也支持可撤销的操作。

4. 责任链模式（Chain of Responsibility Pattern）  
将请求的发送者和接收者解耦，并沿着处理链传递请求，直到有对象处理它为止。责任链模式可以避免请求发送者和接收者之间的耦合。

5. 状态模式（State Pattern）  
允许对象在其内部状态改变时改变它的行为。状态模式可以将状态的转换逻辑封装在状态对象内部，从而避免了大量的条件分支。

6. 观察者模式（Observer Pattern）  
定义了对象之间的一对多依赖关系，使得当一个对象改变状态时，所有依赖于它的对象都会被通知并自动更新。

7. 中介者模式（Mediator Pattern）  
用一个中介对象来封装一系列对象之间的交互。中介者模式使得各个对象之间的交互被松耦合，而且可以独立地改变它们之间的交互方式。

8. 访问者模式（Visitor Pattern）  
定义了一种新的操作，可以在不改变对象结构的情况下向对象中添加新的操作。访问者模式适用于数据结构相对稳定，但是其中的操作却经常变化的情况。

9. 迭代器模式（Iterator Pattern）  
提供一种方法来访问一个容器对象中的各个元素，而又不暴露该对象的内部细节。

10. 备忘录模式（Memento Pattern）  
允许在不破坏封装性的前提下捕获一个对象的内部状态，并在该对象之外保存这个状态。备忘录模式通常用于实现撤销操作。
### 总结
其中，创建型模式主要是解决对象的创建问题；结构型模式主要是解决对象的组合问题；行为型模式主要是解决对象之间的交互问题。每种设计模式都有其独特的应用场景和解决问题的方法，选择合适的设计模式能够有效地提高代码的可维护性、可扩展性和可复用性。