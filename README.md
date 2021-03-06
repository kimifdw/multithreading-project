# 多线程项目
多线程学习DEMO
## rxjava
>	Observable & Observer

1. subscription
	- Observable与Observer连接的方式


	```java
	class Observable<T> {
	    Subscription subscribe(Observer s)
	}
	```	

	- 允许client取消subscribe
	- 可在Observer或callback外进行控制

2.	Observer：订阅者
	
	```java
	void onNext(T t)
    void onError(Throwable t)
    void onCompleted()
	```

3. Observable
	- 同步代码


	```java
	Observable.create(s -> {    
		s.onNext("Hello World!");
		s.onCompleted();
	}).subscribe(s -> System.out.println(s));
	```
	
	- 决定是否使用同步或异步策略，完全取决于production是否为block.
	- 如果在内存中则使用同步，如果是通过网络获取，则使用异步
	- 如code中存在timeout、observeOn，则可以使用async
	- 在多线程环境下，每个Observable都是独立的，可以利用merge将多个Observable合并，但结果输出结果不确定
	- scan和reduce无法在并发环境下使用
	- 懒加载：即只有subscription才会被触发并且可被重复使用；
	- Single：Observable of One；Completable：Observable of None;
	- 利用cache方法，可以将create的数据缓存起来
	- ConnectableObservable
	- 创建
		1. JUST(VALUE)
			- 创建用于emits指定data的Observable,完成后立即结束；
			- 可以发送2到9个数据【集合】
			- 不支持scheduler
			
			```java
				Observable.just(1, 2, 3)
				.subscribe(new Subscriber<Integer>() {
		        @Override
		        public void onNext(Integer item) {
		            System.out.println("Next: " + item);
		        }

		        @Override
		        public void onError(Throwable error) {
		            System.err.println("Error: " + error.getMessage());
		        }

		        @Override
		        public void onCompleted() {
		            System.out.println("Sequence complete.");
		        }
    			});
			```
		2. FROM(VALUES)
			- 接收数组或集合
			- 可接收Future<T>的数据集
		3. RANGE(FROM,N)
			- 从FROM开始emitsN个数
		4. EMPTY
			- no emits any value
			- 订阅后立即完成
		5. NEVER
			- 不发送任何消息（包括完成、异常，常用于测试）
		6. ERROR
			- emits 错误异常消息
		7. CREATE
			- 不能使用多线程
	- timer和interval
		1. timer：延迟执行,类似于Thread.sleep
		2. interval：每隔某一时间执行一次
	- cold和hot Observable
		1.	cold Observable：只有当Observable被订阅时，才创建，JUST、FROM和RANGE为cold Observable；
		2. 
4. Operators
	- 大多数的操作都为同步的
5. Subscriber
	- 同时实现了Observer和Subscription的功能
6. Subject
	- 继承Observable并实现Observer
	1.	AsyncSubject
		当onComplete（）执行时，emits最后一条记录
	2. BehaviorSubject
	3. ReplaySubject
> core operators
1. mapping 和 filtering

> Schedulers

1. Schedulers.newThread()
2. Schedulers.io()
	- 线程可被回收利用，线程池无限制
	- 类似于ThreadPoolExecutor实现
	- 适用于network请求和disk请求
3. Schedulers.computation()
	- 受到当时cpu运行环境的线程数限制【Runtime.getRuntime()】
4. Schedulers.from(Executor executor)
5. Schedulers.immediate()
	- 非异步执行
	- 实际开发中避免使用
6. Schedulers.trampoline()
	- 与Schedulers.immediate()类似，在当前线程中执行
	- 会等待当前的task执行完，再执行下一个task
7. Schedulers.test()
8. observeOn() && subscribeOn()
	- 在observeOn()之上的代码，在当前线程中运行；在observeOn()之下的代码在Scheduler中执行
- 总结：
	1. Observable不使用Scheduler，类似于单线程
	2. Observable使用单个subscribeOn，类似于使用后台线程
	3. Observable使用flatMap() ,为内部的每个Observable创建一个线程


