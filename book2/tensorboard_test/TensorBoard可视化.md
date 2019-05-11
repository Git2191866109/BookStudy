# TensorBoard可视化

[TOC]

## 0. 写在前面

**参考书**

《TensorFlow：实战Google深度学习框架》（第2版）

**工具**

python3.5.1，pycharm

## 1. TensorBoard简介

一个简单的TensorFlow程序，在这个程序中完成了TensorBoard日志输出的功能。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tensorboard_test1.py
@time: 2019/5/10 9:27
@desc: TensorBoard简介。一个简单的TensorFlow程序，在这个程序中完成了TensorBoard日志输出的功能。
"""

import tensorflow as tf


# 定义一个简单的计算图，实现向量加法的操作。
input1 = tf.constant([1.0, 2.0, 3.0], name="input1")
input2 = tf.Variable(tf.random_uniform([3], name="input2"))
output = tf.add_n([input1, input2], name="add")

# 生成一个写日志的writer，并将当前的TensorFlow计算图写入日志。TensorFlow提供了
# 多种写日志文件的API，在后面详细介绍。
writer = tf.summary.FileWriter('./log/', tf.get_default_graph())
writer.close()
```

运行之后输入：`tensorboard --logdir=./log`查看TensorBoard。

![img](https://img-blog.csdnimg.cn/20190510101539968.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

然后在浏览器中输入下面的网址。

![img](https://img-blog.csdnimg.cn/20190510101623358.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

## 2. TensorFlow计算图可视化

### 2.1 命名空间与TensorBoard图上节点

**tf.variable_scope与tf.name_scope函数的区别**

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tensorboard_test2.py
@time: 2019/5/10 10:26
@desc: tf.variable_scope与tf.name_scope函数的区别
"""

import tensorflow as tf


with tf.variable_scope("foo"):
    # 在命名空间foo下获取变量"bar"，于是得到的变量名称为“foo/bar”。
    a = tf.get_variable("bar", [1])
    # 输出：foo/bar: 0
    print(a.name)

with tf.variable_scope("bar"):
    # 在命名空间bar下获取变量“bar”，于是得到的变量名称为“bar/bar”。此时变量
    # “bar/bar”和变量“foo/bar”并不冲突，于是可以正常运行。
    b = tf.get_variable("bar", [1])
    # 输出：bar/bar：0

with tf.name_scope("a"):
    # 使用tf.Variable函数生成变量会受到tf.name_scope影响，于是这个变量的名称为“a/Variable”。
    a = tf.Variable([1])
    # 输出：a/Variable：0
    print(a.name)

    # tf.get_variable函数不受tf.name_scope函数的影响。
    # 于是变量并不在a这个命名空间中。
    a = tf.get_variable("b", [1])
    # 输出：b：0
    print(a.name)

with tf.name_scope("b"):
    # 因为tf.get_variable不受tf.name_scope影响，所以这里试图获取名称为
    # “a”的变量。然而这个变量已经被声明了，于是这里会报重复声明的错误
    tf.get_variable("b", [1])
```

对不起，这一段代码，我知道作者想要表达什么意思。。。但我实在是觉得不知所云。

![img](https://img-blog.csdnimg.cn/20190510104032333.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

**改进向量相加的样例代码**

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tensorboard_test3.py
@time: 2019/5/10 10:41
@desc: 改进向量相加的样例代码
"""

import tensorflow as tf


# 将输入定义放入各自的命名空间中，从而使得TensorBoard可以根据命名空间来整理可视化效果图上的节点。
with tf.name_scope("input1"):
    input1 = tf.constant([1.0, 2.0, 3.0], name="input1")
with tf.name_scope("input2"):
    input2 = tf.Variable(tf.random_uniform([3]), name="input2")
output = tf.add_n([input1, input2], name="add")

writer = tf.summary.FileWriter("./log", tf.get_default_graph())
writer.close()
```

得到改进后的图：

![img](https://img-blog.csdnimg.cn/20190510104903562.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)\

展开input2节点的可视化效果图：

![img](https://img-blog.csdnimg.cn/20190510110449891.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

**可视化一个真实的神经网络结构图**

> 我是真的佛了。。。这里原谅我真的又要喷。。。首先是用之前的mnist_inference文件就已经炸了，然后下面还有一句跟前面一样的方式训练神经网络。。。我特么。。。。你你听，这说的是人话吗？我已经无力吐槽了。。。这本书用来作为我的TensorFlow启蒙书，真的是后悔死了。。。

下面的代码，依然是我自己凭借自己的理解，改后的，这本书是真的垃圾。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tensorboard_test4.py
@time: 2019/5/10 11:06
@desc: 可视化一个真实的神经网络结构图。
"""

import tensorflow as tf
import os
from tensorflow.examples.tutorials.mnist import input_data
# mnist_inference中定义的常量和前向传播的函数不需要改变，因为前向传播已经通过
# tf.variable_scope实现了计算节点按照网络结构的划分。
import BookStudy.book2.mnist_inference as mnist_inference


INPUT_NODE = 784
OUTPUT_NODE = 10
LAYER1_NODE = 500

# 配置神经网络的参数。
BATCH_SIZE = 100
LEARNING_RATE_BASE = 0.8
LEARNING_RATE_DECAY = 0.99
REGULARAZTION_RATE = 0.0001
TRAINING_STEPS = 30000
MOVING_AVERAGE_DECAY = 0.99
# 模型保存的路径和文件名。
MODEL_SAVE_PATH = './model/'
MODEL_NAME = 'model.ckpt'


def train(mnist):
    # 将处理输入数据的计算都放在名字为“input”的命名空间下。
    with tf.name_scope('input'):
        x = tf.placeholder(tf.float32, [None, mnist_inference.INPUT_NODE], name='x-input')
        y_ = tf.placeholder(tf.float32, [None, mnist_inference.OUTPUT_NODE], name="y-input")

    regularizer = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
    y = mnist_inference.inference(x, regularizer)
    global_step = tf.Variable(0, trainable=False)

    # 将处理滑动平均相关的计算都放在名为moving_average的命名空间下。
    with tf.name_scope("moving_average"):
        variable_averages = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
        variable_averages_op = variable_averages.apply(tf.trainable_variables())

    # 将计算损失函数相关的计算都放在名为loss_function的命名空间下。
    with tf.name_scope("loss_function"):
        cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))
        cross_entropy_mean = tf.reduce_mean(cross_entropy)
        loss = cross_entropy_mean + tf.add_n(tf.get_collection('losses'))

    # 将定义学习率、优化方法以及每一轮训练需要训练的操作都放在名字为“train_step”的命名空间下。
    with tf.name_scope("train_step"):
        learning_rate = tf.train.exponential_decay(
            LEARNING_RATE_BASE,
            global_step,
            mnist.train.num_examples / BATCH_SIZE,
            LEARNING_RATE_DECAY,
            staircase=True
        )
        train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
        with tf.control_dependencies([train_step, variable_averages_op]):
            train_op = tf.no_op(name='train')

    # 初始化Tensorflow持久化类。
    saver = tf.train.Saver()
    with tf.Session() as sess:
        tf.global_variables_initializer().run()

        # 在训练过程中不再测试模型在验证数据上的表现，验证和测试的过程将会有一个独立的程序来完成。
        for i in range(TRAINING_STEPS):
            xs, ys = mnist.train.next_batch(BATCH_SIZE)
            _, loss_value, step = sess.run([train_op, loss, global_step], feed_dict={x: xs, y_: ys})

            # 每1000轮保存一次模型。
            if i % 1000 == 0:
                # 输出当前的训练情况。这里只输出了模型在当前训练batch上的损失函数大小。通过损失函数的大小可以大概了解到
                # 训练的情况。在验证数据集上的正确率信息会有一个独立的程序来生成。
                print("After %d training step(s), loss on training batch is %g." % (step, loss_value))

                # 保存当前的模型。注意这里给出了global_step参数，这样可以让每个被保存的模型的文件名末尾加上训练的轮数，
                # 比如“model.ckpt-1000” 表示训练1000轮之后得到的模型。
                saver.save(sess, os.path.join(MODEL_SAVE_PATH, MODEL_NAME), global_step=global_step)

    # 将当前的计算图输出到TensorBoard日志文件。
    writer = tf.summary.FileWriter("./log", tf.get_default_graph())
    writer.close()


def main(argv = None):
    mnist = input_data.read_data_sets("D:/Python3Space/BookStudy/book2/MNIST_data", one_hot=True)
    train(mnist)


if __name__ == '__main__':
    tf.app.run()
```

运行之后：

![img](https://img-blog.csdnimg.cn/20190510165524835.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

召唤tensorboard：

![img](https://img-blog.csdnimg.cn/20190510170554666.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

改进后的MNIST样例程序TensorFlow计算图可视化效果图：

![img](https://img-blog.csdnimg.cn/20190510170716715.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

### 2.2 节点信息

修改前面的代码，将不同迭代轮数的每个TensorFlow计算节点的运行时间和消耗的内存写入TensorBoard的日志文件中。

果然。。。这次又是只给出一部分代码。。。并且这个train_writer是什么啊，在哪里定义也没看到，拿来就用，真的是服了。。。（写的也太烂了，佛了，刷完这本书我就烧了。。。）

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tensorboard_test5.py
@time: 2019/5/10 21:13
@desc: 修改前面的代码，将不同迭代轮数的每个TensorFlow计算节点的运行时间和消耗的内存写入TensorBoard的日志文件中。
"""

import tensorflow as tf
import os
from tensorflow.examples.tutorials.mnist import input_data
# mnist_inference中定义的常量和前向传播的函数不需要改变，因为前向传播已经通过
# tf.variable_scope实现了计算节点按照网络结构的划分。
import BookStudy.book2.mnist_inference as mnist_inference


INPUT_NODE = 784
OUTPUT_NODE = 10
LAYER1_NODE = 500

# 配置神经网络的参数。
BATCH_SIZE = 100
LEARNING_RATE_BASE = 0.8
LEARNING_RATE_DECAY = 0.99
REGULARAZTION_RATE = 0.0001
TRAINING_STEPS = 30000
MOVING_AVERAGE_DECAY = 0.99
# 模型保存的路径和文件名。
MODEL_SAVE_PATH = './model/'
MODEL_NAME = 'model.ckpt'


def train(mnist):
    # 将处理输入数据的计算都放在名字为“input”的命名空间下。
    with tf.name_scope('input'):
        x = tf.placeholder(tf.float32, [None, mnist_inference.INPUT_NODE], name='x-input')
        y_ = tf.placeholder(tf.float32, [None, mnist_inference.OUTPUT_NODE], name="y-input")

    regularizer = tf.contrib.layers.l2_regularizer(REGULARAZTION_RATE)
    y = mnist_inference.inference(x, regularizer)
    global_step = tf.Variable(0, trainable=False)

    # 将处理滑动平均相关的计算都放在名为moving_average的命名空间下。
    with tf.name_scope("moving_average"):
        variable_averages = tf.train.ExponentialMovingAverage(MOVING_AVERAGE_DECAY, global_step)
        variable_averages_op = variable_averages.apply(tf.trainable_variables())

    # 将计算损失函数相关的计算都放在名为loss_function的命名空间下。
    with tf.name_scope("loss_function"):
        cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))
        cross_entropy_mean = tf.reduce_mean(cross_entropy)
        loss = cross_entropy_mean + tf.add_n(tf.get_collection('losses'))

    # 将定义学习率、优化方法以及每一轮训练需要训练的操作都放在名字为“train_step”的命名空间下。
    with tf.name_scope("train_step"):
        learning_rate = tf.train.exponential_decay(
            LEARNING_RATE_BASE,
            global_step,
            mnist.train.num_examples / BATCH_SIZE,
            LEARNING_RATE_DECAY,
            staircase=True
        )
        train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
        with tf.control_dependencies([train_step, variable_averages_op]):
            train_op = tf.no_op(name='train')

    # 初始化Tensorflow持久化类。
    saver = tf.train.Saver()
    # 将当前的计算图输出到TensorBoard日志文件。
    train_writer = tf.summary.FileWriter("./log", tf.get_default_graph())
    with tf.Session() as sess:
        tf.global_variables_initializer().run()

        # 在训练过程中不再测试模型在验证数据上的表现，验证和测试的过程将会有一个独立的程序来完成。
        for i in range(TRAINING_STEPS):
            xs, ys = mnist.train.next_batch(BATCH_SIZE)

            # 每1000轮保存一次模型。
            if i % 1000 == 0:

                # 配置运行时需要记录的信息。
                run_options = tf.RunOptions(trace_level=tf.RunOptions.FULL_TRACE)
                # 运行时记录运行信息的proto。
                run_metadata = tf.RunMetadata()
                # 将配置信息和记录运行信息的proto传入运行的过程，从而记录运行时每一个节点的时间、空间开销信息。
                _, loss_value, step = sess.run([train_op, loss, global_step], feed_dict={x: xs, y_: ys}, options=run_options, run_metadata=run_metadata)
                # 将节点在运行时的信息写入日志文件。
                train_writer.add_run_metadata(run_metadata, 'step%03d' % i)

                # 输出当前的训练情况。这里只输出了模型在当前训练batch上的损失函数大小。通过损失函数的大小可以大概了解到
                # 训练的情况。在验证数据集上的正确率信息会有一个独立的程序来生成。
                print("After %d training step(s), loss on training batch is %g." % (step, loss_value))

                # 保存当前的模型。注意这里给出了global_step参数，这样可以让每个被保存的模型的文件名末尾加上训练的轮数，
                # 比如“model.ckpt-1000” 表示训练1000轮之后得到的模型。
                saver.save(sess, os.path.join(MODEL_SAVE_PATH, MODEL_NAME), global_step=global_step)
            else:
                _, loss_value, step = sess.run([train_op, loss, global_step], feed_dict={x: xs, y_: ys})

    train_writer.close()


def main(argv = None):
    mnist = input_data.read_data_sets("D:/Python3Space/BookStudy/book2/MNIST_data", one_hot=True)
    train(mnist)


if __name__ == '__main__':
    tf.app.run()
```

运行后：

![img](https://img-blog.csdnimg.cn/20190510221013979.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

启动TensorBoard，这样就可以可视化每个TensorFlow计算节点在某一次运行时所消耗的时间和空间。

![img](https://img-blog.csdnimg.cn/20190510221454807.png)

![img](https://img-blog.csdnimg.cn/20190510221349112.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20190510223855239.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

TensorBoard左侧的Color栏中Compute和Memory这两个选项将可以被选择。

![img](https://img-blog.csdnimg.cn/20190510224605105.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20190510224650152.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

**颜色越深的节点，时间和空间的消耗越大。**

![img](https://img-blog.csdnimg.cn/20190510225421473.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

1. 空心的小椭圆对应了TensorFlow计算图上的一个计算节点。
2. 一个矩形对应了计算图上的一个命名空间。

## 3. 监控指标可视化













------

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~

by 李英俊小朋友