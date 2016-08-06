# 多线程项目
多线程学习DEMO
## rxjava
>	Observable & Observer

1. subscription
	Observable与Observer连接的方式


	```java
	class Observable<T> {
	    Subscription subscribe(Observer s)
	}
	```	

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
4. Operators
	- 大多数的操作都为同步的
5. 


