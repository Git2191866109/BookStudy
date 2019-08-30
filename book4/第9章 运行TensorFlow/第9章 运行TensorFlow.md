# 第9章 运行TensorFlow

**参考书**

《机器学习实战——基于Scikit-Learn和TensorFlow》

**工具**

python3.5.1，Jupyter Notebook, Pycharm

## 创建一个计算图并在会话中执行

```python
x = tf.Variable(3, name="x")
y = tf.Variable(4, name="y")
f = x*x*y + y + 2

sess = tf.Session()
sess.run(x.initializer)
sess.run(y.initializer)
result = sess.run(f)
print(result)
sess.close()

# 每次重复sess.run()看起来有些笨拙，好在有更好的方式
with tf.Session() as sess:
    x.initializer.run()
    y.initializer.run()
    result = f.eval()
    print(result)
```

- 其中x.initializer.run()等价于tf.get_default_session().run(x.initializer)
- 同样，f.eval()等价于tf.get_default_session().run(f)
- 除了手工为每个变量调用初始化器之外，还可以使用`global_variables_initializer()`函数来完成同样的动作。<u>注意：这个操作并不会立刻做初始化，它只是在图中创建了一个节点，这个节点会在会话执行时初始化所有变量。</u>

```python
init = tf.global_variables_initializer()

with tf.Session() as sess:
    init.run()
    result = f.eval()
    print(result)
```

- 在Jupyter或者Python shell中，可以创建一个InteractiveSession。它和常规会话的不同之处在于InteractiveSession在创建时会将自己设置为默认会话，因此你无须使用with块（不过需要在结束之后手动关闭会话）。

```python 
sess = tf.InteractiveSession()
init.run()
result = f.eval()
print(result)
sess.close()
```

## 管理图

- 你创建的所有节点都会自动添加到默认图上

  ```python
  x1 = tf.Variable(1)
  x1.graph is tf.get_default_graph()
  ```

- 有时候你可能想要管理多个互不依赖的图。可以创建一个新的图，然后用with块临时将它设置为默认图

  ```python
  graph = tf.Graph()
  with graph.as_default():
      x2 = tf.Variable(2)
  
  x2.graph is graph
  x2.graph is tf.get_default_graph()
  ```

- 在Jupyter中，做实验时你经常会多次执行同一条命令。这样可能会在同一个图上添加了很多重复的节点。

  - 一种做法是重启Jupyter内核
  - 更方便的做法是通过tf.reset_default_graph()来重置默认图

## 节点值的生命周期

```python
w = tf.constant(3)
x = w + 2
y = x + 5
z = x * 3

with tf.Session() as sess:
    print(y.eval())
    print(z.eval())
```

- TensorFlow不会重复用上一步求值的w和x的结果。简而言之，w和x的值会被计算两次。

- 在图的每次执行间，所有节点值都会被丢弃，但是变量的值不会，因为变量的值是由会话维护的（队列和阅读器也会维护一些状态）。**变量的生命周期从初始化器的执行开始，到关闭会话才结束。**

- 对于上述代码，如果你不希望对y和z重复求值，那么必须告诉TensorFlow在一次图执行中就完成y和z的求值

  ```python
  with tf.Session() as sess:
      y_val, z_val = sess.run([y, z])
      print(y_val)
      print(z_val)
  ```

- **在单进程的TensorFlow中**，即使它们共享同一个计算图，多个会话之间仍然互相隔离，不共享任何状态（每个会话对每个变量都有自己的拷贝）。

- **对于分布式TensorFlow**，变量值保存在每个服务器上，而不是会话中，所以多个会话可以共享同一变量。

## TensorFlow中的线性回归

```python
import numpy as np
from sklearn.datasets import fetch_california_housing

housing = fetch_california_housing()
m, n = housing.data.shape
housing_data_plus_bias = np.c_[np.ones((m, 1)), housing.data]

X = tf.constant(housing_data_plus_bias, dtype=tf.float32, name="X")
y = tf.constant(housing.target.reshape(-1, 1), dtype=tf.float32, name="y")
XT = tf.transpose(X)
theta = tf.matmul(tf.matmul(tf.matrix_inverse(tf.matmul(XT, X)), XT), y)

with tf.Session() as sess:
    theta_value = theta.eval()
    print(theta_value)
```

## 实现梯度下降

- 手动计算梯度

  ```python
  n_epochs = 1000
  learning_rate = 0.01
  
  X = tf.constant(scaled_housing_data_plus_bias, dtype=tf.float32, name="X")
  y = tf.constant(housing.target.reshape(-1, 1), dtype=tf.float32, name="y")
  theta = tf.Variable(tf.random_uniform([n+1, 1], -1.0, 1.0), name="theta")
  y_pred = tf.matmul(X, theta, name="perdictions")
  error = y_pred - y
  mse = tf.reduce_mean(tf.square(error), name="mse")
  gradients = 2/m * tf.matmul(tf.transpose(X), error)
  training_op = tf.assign(theta, theta - learning_rate * gradients)
  
  init = tf.global_variables_initializer()
  
  with tf.Session() as sess:
      sess.run(init)
      
      for epoch in range(n_epochs):
          if epoch % 100 == 0:
              print("Epoch", epoch, "MSE = ", mse.eval())
          sess.run(training_op)
      
      best_theta = theta.eval()
  ```

  - 函数random_uniform()会在图中创建一个节点，这个节点会生成一个张量。函数会根据传入的形状和值域来生成随机值来填充这个张量，这和Numpy的rand()函数很相似。
  - 函数assign()创建一个为变量赋值的节点。这里，它实现了批量梯度下降。

- 使用自动微分

  - TensorFlow的autodiff功能可以自动而且高效的算出梯度。只需要把上述例子中的对gradients的赋值的语句换成下面的代码即可。

    `gradients = tf.gradients(mse, [theta])[0]`

  - gradients()函数接受一个操作符（这里是mse）和一个参数列表（这里是theta）作为参数，然后它会创建一个操作符的列表来计算每个变量的梯度。所以梯度节点将计算MSE相对于theta的梯度向量。

  - TensorFlow使用了反向的autodiff算法，它非常适用于有多个输入和少量输出的场景，在神经网络中这种场景非常常见。<u>它只需要$n_{outputs}+1$次遍历，就可以求出所有输出相对于输入的偏导。</u>

  - 四种自动计算梯度的主要方法

    | 方法         | 精确度 | 是否支持任意代码 | 备注                   |
    | ------------ | ------ | ---------------- | ---------------------- |
    | 数值微分     | 低     | 是               | 实现琐碎               |
    | 符号微分     | 高     | 否               | 会构建一个完全不同的图 |
    | 前向自动微分 | 高     | 是               | 基于二元树             |
    | 反向自动微分 | 高     | 是               | 由TensorFlow实现       |

- 实现优化器

  - TensorFlow会帮你计算梯度，不过它还提供更容易的方法：它内置了很多的优化器，其中就包含梯度下降优化器。你只需要把上面对gradients=…和training_op=…赋值的语句修改成下面的代码即可：

    ```python
    optimizer = tf.train.GradientDescentOptimizer(learning_rate=learning_rate)
    training_op = optimizer.minimize(mse)
    ```

  - 动量优化器（比梯度下降优化器的收敛速度快很多）

    ```python
    optimizer = tf.train.MomentumOptimizer(learning_rate=learning_rate, momentum=0.9)
    ```

## 给训练算法提供数据

- 占位符节点

  ```python
  A = tf.placeholder(tf.float32, shape=(None, 3))
  B = A + 5
  with tf.Session() as sess:
      B_val_1 = B.eval(feed_dict={A: [[1, 2, 3]]})
      B_val_2 = B.eval(feed_dict={A: [[4, 5, 6], [7, 8, 9]]})
      print(B_val_1)
      print(B_val_2)
  ```

- Mini-batch Gradient Descent

  ```python
  learning_rate = 0.01
  
  X = tf.placeholder(tf.float32, shape=(None, n + 1), name="X")
  y = tf.placeholder(tf.float32, shape=(None, 1), name="y")
  
  theta = tf.Variable(tf.random_uniform([n + 1, 1], -1.0, 1.0, seed=42), name="theta")
  y_pred = tf.matmul(X, theta, name="predictions")
  error = y_pred - y
  mse = tf.reduce_mean(tf.square(error), name="mse")
  optimizer = tf.train.GradientDescentOptimizer(learning_rate=learning_rate)
  training_op = optimizer.minimize(mse)
  
  init = tf.global_variables_initializer()
  
  n_epochs = 10
  batch_size = 100
  n_batches = int(np.ceil(m / batch_size))
  
  def fetch_batch(epoch, batch_index, batch_size):
      np.random.seed(epoch * n_batches + batch_index)  # not shown in the book
      indices = np.random.randint(m, size=batch_size)  # not shown
      X_batch = scaled_housing_data_plus_bias[indices] # not shown
      y_batch = housing.target.reshape(-1, 1)[indices] # not shown
      return X_batch, y_batch
  
  with tf.Session() as sess:
      sess.run(init)
  
      for epoch in range(n_epochs):
          for batch_index in range(n_batches):
              X_batch, y_batch = fetch_batch(epoch, batch_index, batch_size)
              sess.run(training_op, feed_dict={X: X_batch, y: y_batch})
  
      best_theta = theta.eval()
  ```

## 保存和恢复模型

- 保存模型：`saver = tf.train.Saver()`

  ```python
  n_epochs = 1000                                                                       # not shown in the book
  learning_rate = 0.01                                                                  # not shown
  
  X = tf.constant(scaled_housing_data_plus_bias, dtype=tf.float32, name="X")            # not shown
  y = tf.constant(housing.target.reshape(-1, 1), dtype=tf.float32, name="y")            # not shown
  theta = tf.Variable(tf.random_uniform([n + 1, 1], -1.0, 1.0, seed=42), name="theta")
  y_pred = tf.matmul(X, theta, name="predictions")                                      # not shown
  error = y_pred - y                                                                    # not shown
  mse = tf.reduce_mean(tf.square(error), name="mse")                                    # not shown
  optimizer = tf.train.GradientDescentOptimizer(learning_rate=learning_rate)            # not shown
  training_op = optimizer.minimize(mse)                                                 # not shown
  
  init = tf.global_variables_initializer()
  saver = tf.train.Saver()
  
  with tf.Session() as sess:
      sess.run(init)
  
      for epoch in range(n_epochs):
          if epoch % 100 == 0:
              print("Epoch", epoch, "MSE =", mse.eval())                                # not shown
              save_path = saver.save(sess, "D:/Python3Space/BookStudy/book4/model/my_model.ckpt")
          sess.run(training_op)
      
      best_theta = theta.eval()
      save_path = saver.save(sess, "D:/Python3Space/BookStudy/book4/model/tmp/my_model_final.ckpt")
  ```

- 恢复模型：恢复模型同样简单，与之前一样，在构造期末尾创建一个Saver节点，不过在执行期开始的时候，不是用init节点来初始化变量，而是调用Saver对象上的restore()方法。

  ```python
  with tf.Session() as sess:
      saver.restore(sess, "D:/Python3Space/BookStudy/book4/model/tmp/my_model_final.ckpt")
      best_theta_restored = theta.eval() # not shown in the book
      print(best_theta_restored)
  ```

- 默认的，Saver会按照变量名来保留和恢复变量，不过如果你想做更多的控制，也可以在保存和恢复时自己指定名称。比如，在下面的代码中，Saver只会保存theta，并将其命名为weights：

  `saver = tf.train.Saver({"weights": theta})`

## 用TensorBoard来可视化图和训练曲线





------

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~

by 李英俊小朋友