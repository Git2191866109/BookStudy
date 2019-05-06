# TensorFlow高层封装：从入门到喷这本书

## 0. 写在前面

**参考书**

《TensorFlow：实战Google深度学习框架》（第2版）

**划重点**

==从今天开始（20190505-1521），我的博客都用**Markdown**语法来编写啦，也不知道以后的自己会不会被人所知，会不会有大佬来看过去的我，给我挖坟呢。想想就有点期待呢！希望自己还能更加努力！更加优秀吧！==

## 1. TensorFlow高层封装总览

目前比较主流的TensorFlow高层封装有4个，分别是TensorFlow-Slim、TFLearn、Keras和Estimator。

首先，这里介绍先用TensorFlow-Slim在MNIST数据集上实现LeNet-5模型。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: slim_learn.py
@time: 2019/4/22 10:53
@desc: 使用TensorFlow-Slim在MNIST数据集上实现LeNet-5模型。
"""

import tensorflow as tf
import tensorflow.contrib.slim as slim
import numpy as np

from tensorflow.examples.tutorials.mnist import input_data


# 通过TensorFlow-Slim来定义LeNet-5的网络结构
def lenet5(inputs):
    # 将输入数据转化为一个4维数组。其中第一维表示batch大小，另三维表示一张图片。
    inputs = tf.reshape(inputs, [-1, 28, 28, 1])
    # 定义第一层卷积层。从下面的代码可以看到通过TensorFlow-Slim定义的网络结构
    # 并不需要用户去关心如何声明和初始化变量，而只需要定义网络结构即可。下一行代码中
    # 定义了一个卷积层，该卷积层的深度为32，过滤器的大小为5x5，使用全0补充。
    net = slim.conv2d(inputs, 32, [5, 5], padding='SAME', scope='layer1-conv')
    # 定义一个最大池化层，其过滤器大小为2x2，步长为2.
    net = slim.max_pool2d(net, 2, stride=2, scope='layer2-max-pool')
    # 类似的定义其他网络层结构
    net = slim.conv2d(net, 64, [5, 5], padding='SAME', scope='layer3-conv')
    net = slim.max_pool2d(net, 2, stride=2, scope='layer4-max-pool')
    # 直接使用TensorFlow-Slim封装好的flatten函数将4维矩阵转为2维，这样可以
    # 方便后面的全连接层的计算。通过封装好的函数，用户不再需要自己计算通过卷积层之后矩阵的大小。
    net = slim.flatten(net, scope='flatten')
    # 通过TensorFlow-Slim定义全连接层，该全连接层有500个隐藏节点。
    net = slim.fully_connected(net, 500, scope="layer5")
    net = slim.fully_connected(net, 10, scope="output")
    return net


# 通过TensorFlow-Slim定义网络结构，并使用之前章节中给出的方式训练定义好的模型。
def train(mnist):
    # 定义输入
    x = tf.placeholder(tf.float32, [None, 784], name='x-input')
    y_ = tf.placeholder(tf.float32, [None, 10], name='y-input')
    # 使用TensorFLow-Slim定义网络结构
    y = lenet5(x)

    # 定义损失函数和训练方法
    cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))   # 1 means axis=1
    loss = tf.reduce_mean(cross_entropy)
    train_op = tf.train.GradientDescentOptimizer(0.01).minimize(loss)

    # 训练过程
    with tf.Session() as sess:
        tf.global_variables_initializer().run()
        for i in range(10000):
            xs, ys = mnist.train.next_batch(100)
            _, loss_value = sess.run([train_op, loss], feed_dict={x: xs, y_: ys})

            if i % 1000 == 0:
                print("After %d training step(s), loss on training batch is %g." % (i, loss_value))


def main(argv=None):
    mnist = input_data.read_data_sets('D:/Python3Space/BookStudy/book2/MNIST_data', one_hot=True)
    train(mnist)


if __name__ == '__main__':
    main()
```

OK！运行吧皮卡丘！

第一个例子都报错。。。（ValueError: Rank mismatch: Rank of labels (received 1) should equal rank of logits minus 1 (received 4).）

![img](https://img-blog.csdnimg.cn/20190505144938848.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

我哭了！找了我半天错误，才发现少写了一句。

>  net = slim.flatten(net, scope='flatten')

可把我愁坏了，整了半天才弄好。。。

网上都是什么神仙回答，解释的有板有眼的，都说这本书是垃圾，害得我差点立刻在我对这本书评价的博客上再加上几句芬芳。

好歹是学到了知识了。对logits和labels加深了印象了。

> `cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=tf.argmax(y_, 1))`
> 
> logits：是计算得到的结果
>
> labels：是原来的数据标签。
>
> **千万不要记混了！**
>
> `labels=tf.argmax(y_, 1)`
>
> labels输入的是[0, 0, 0, 1, 0, 0, 0, 0, 0, 0]（以MNIST为例），
>
> 而在tf.nn.sparse_softmax_cross_entropy_with_logits函数中
>
> labels的输入格式需要是[3]，也就是说，是类别的编号。
>
> **诶！问题来了！**
>
> `logits=y`
>
> logits的格式与labels一样吗？
>
> **不一样！**
>
> logits的格式与labels转换前的一样，也就是
>
> [0.2, 0.3, 0.1, 0.9, 0.1, 0.1, 0.2, 0.2, 0.4, 0.6]
>
> **如果不转换labels的话，可以用tf.nn.softmax_cross_entropy_with_logits达到同样的效果**。
>
> 诶？那为什么非要转换一下labels呢？
>
> 我也没看懂，非要骚一下吧。。。

---

好了正确的运行结果出来了：

![img](https://img-blog.csdnimg.cn/20190505160554900.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

如果我们把刚才说的那句代码改为：

`cross_entropy = tf.nn.softmax_cross_entropy_with_logits(logits=y, labels=y_)`

试试看？

哇哦~正常运行了有没有！！！

![img](https://img-blog.csdnimg.cn/20190505160619774.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

**所以呢？所以为什么这里要非要用有sparse的这个函数呢？**

反正我是没看懂（摊手┓( ´∀` )┏）。。。

---

与TensorFlow-Slim相比，TFLearn是一个更加简洁的高层封装。

因为TFLearn并没有集成在TensorFlow中，所以首先是用pip安装。

安装完后，下面是用TFLearn在MNIST数据集上实现LeNet-5模型。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: tflearn_learn.py
@time: 2019/5/5 16:53
@desc: 使用TFLearn在MNIST数据集上实现LeNet-5模型。
"""

import tflearn
from tflearn.layers.core import input_data, fully_connected
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.estimator import regression

import tflearn.datasets.mnist as mnist


# 读取mnist数据
trainX, trainY, testX, testY = mnist.load_data(data_dir="D:/Python3Space/BookStudy/book2/MNIST_data", one_hot=True)

# 将图像数据reshape成卷积神经网络输入的格式
trainX = trainX.reshape([-1, 28, 28, 1])
testX = testX.reshape([-1, 28, 28, 1])

# 构建神经网络，这个过程和TensorFlow-Slim比较类似。input_data定义了一个placeholder来接入输入数据。
net = input_data(shape=[None, 28, 28, 1], name='input')
# 通过TFLearn封装好的API定义一个深度为5，过滤器为5x5，激活函数为ReLU的卷积层
net = conv_2d(net, 32, 5, activation='relu')
# 定义一个过滤器为2x2的最大池化层
net = max_pool_2d(net, 2)
# 类似地定义其他的网络结构。
net = conv_2d(net, 64, 5, activation='relu')
net = max_pool_2d(net, 2)
net = fully_connected(net, 500, activation='relu')
net = fully_connected(net, 10, activation='softmax')

# 使用TFLearn封装好的函数定义学习任务。指定优化器为sgd，学习率为0.01，损失函数为交叉熵。
net = regression(net, optimizer='sgd', learning_rate=0.01, loss='categorical_crossentropy')

# 通过定义的网络结构训练模型，并在指定的验证数据上验证模型的效果。
# TFLearn将模型的训练过程封装到了一个类中，这样可以减少非常多的冗余代码。
model = tflearn.DNN(net, tensorboard_verbose=0)

model.fit(trainX, trainY, n_epoch=20, validation_set=([testX, testY]), show_metric=True)
```

**个人感相较于Slim，TFLearn好用太多了吧。。。特别是model.fit真的是给我眼前一亮的感觉，这也太帅了吧，瞧这交叉熵小黄字，瞧这epoch，瞧这step。。。封装万岁！！！（对我这种菜鸡而言，不要跟我谈底层，我！不！够！格！）**

运行结果：

![img](https://img-blog.csdnimg.cn/20190505175913836.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

## 2. Keras介绍

### 2.1 Keras基本用法

下面是用原生态的Keras在MNIST数据集上实现LeNet-5模型。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: keras_learn.py
@time: 2019/5/5 17:42
@desc: 使用Keras在MNIST数据集上实现LeNet-5模型。
"""

import keras
from keras.datasets import mnist
from keras.models import Sequential
from keras.layers import Dense, Flatten, Conv2D, MaxPooling2D
from keras import backend as K


num_calsses = 10
img_rows, img_cols = 28, 28

# 通过Keras封装好的API加载MNIST数据。其中trainX就是一个60000x28x28的数组，
# trainY是每一张图片对应的数字。
(trainX, trainY), (testX, testY) = mnist.load_data()

# 因为不同的底层（TensorFlow或者MXNet）对输入的要求不一样，所以这里需要根据对图像
# 编码的格式要求来设置输入层的格式。
if K.image_data_format() == 'channels_first':
    trainX = trainX.reshape(trainX.shape[0], 1, img_rows, img_cols)
    testX = testX.reshape(trainX.shape[0], 1, img_rows, img_cols)
    # 因为MNIST中的图片是黑白的，所以第一维的取值为1
    input_shape = (1, img_rows, img_cols)
else:
    trainX = trainX.reshape(trainX.shape[0], img_rows, img_cols, 1)
    testX = testX.reshape(testX.shape[0], img_rows, img_cols, 1)
    input_shape = (img_rows, img_cols, 1)

# 将图像像素转化为0到1之间的实数。
trainX = trainX.astype('float32')
testX = testX.astype('float32')
trainX /= 255.0
testX /= 255.0

# 将标准答案转化为需要的格式（One-hot编码）。
trainY = keras.utils.to_categorical(trainY, num_calsses)
testY = keras.utils.to_categorical(testY, num_calsses)

# 使用Keras API定义模型
model = Sequential()
# 一层深度为32，过滤器大小为5x5的卷积层
model.add(Conv2D(32, kernel_size=(5, 5), activation='relu', input_shape=input_shape))
# 一层过滤器大小为2x2的最大池化层。
model.add(MaxPooling2D(pool_size=(2, 2)))
# 一层深度为64， 过滤器大小为5x5的卷积层。
model.add(Conv2D(64, (5, 5), activation='relu'))
# 一层过滤器大小为2x2的最大池化层。
model.add(MaxPooling2D(pool_size=(2, 2)))
# 将卷积层的输出拉直后作为下面全连接的输入。
model.add(Flatten())
# 全连接层，有500个节点。
model.add(Dense(500, activation='relu'))
# 全连接层，得到最后的输出。
model.add(Dense(num_calsses, activation='softmax'))

# 定义损失函数、优化函数和测评的方法。
model.compile(loss=keras.losses.categorical_crossentropy, optimizer=keras.optimizers.SGD(), metrics=['accuracy'])

# 类似TFLearn中的训练过程，给出训练数据，batch大小、训练轮数和验证数据，Keras可以自动完成模型的训练过程。
model.fit(trainX, trainY, batch_size=128, epochs=20, validation_data=(testX, testY))

# 在测评数据上计算准确率
score = model.evaluate(testX, testY)
print('Test loss: ', score[0])
print('Test accuracy: ', score[1])
```

运行之后（跑了我一夜呀我滴妈。。。）：

![img](https://img-blog.csdnimg.cn/20190506085957926.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

下面是用原生态的Keras实现循环神经网络。

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: keras_rnn.py
@time: 2019/5/6 12:30
@desc: 用原生态的Keras实现循环神经网络
"""

from keras.preprocessing import sequence
from keras.models import Sequential
from keras.layers import Dense, Embedding, LSTM
from keras.datasets import imdb

# 最多使用的单词数
max_features = 20000
# 循环神经网络的截断长度。
maxlen = 80
batch_size = 32
# 加载数据并将单词转化为ID，max_features给出了最多使用的单词数。和自然语言模型类似，
# 会将出现频率较低的单词替换为统一的的ID。通过Keras封装的API会生成25000条训练数据和
# 25000条测试数据，每一条数据可以被看成一段话，并且每段话都有一个好评或者差评的标签。
(trainX, trianY), (testX, testY) = imdb.load_data(num_words=max_features)
print(len(trainX), 'train sequences')
print(len(testX), 'test sequences')

# 在自然语言中，每一段话的长度是不一样的，但循环神经网络的循环长度是固定的，所以这里需要先将
# 所有段落统一成固定长度。对于长度不够的段落，要使用默认值0来填充，对于超过长度的段落
# 则直接忽略掉超过的部分。
trainX = sequence.pad_sequences(trainX, maxlen=maxlen)
testX = sequence.pad_sequences(testX, maxlen=maxlen)

print('trainX shape', trainX.shape)
print('testX shape: ', testX.shape)

# 在完成数据预处理之后构建模型
model = Sequential()
# 构建embedding层。128代表了embedding层的向量维度。
model.add(Embedding(max_features, 128))
# 构建LSTM层
model.add(LSTM(128, dropout=0.2, recurrent_dropout=0.2))
# 构建最后的全连接层。注意在上面构建LSTM层时只会得到最后一个节点的输出，
# 如果需要输出每个时间点的结果，呢么可以将return_sequence参数设为True。
model.add(Dense(1, activation='sigmoid'))

# 与MNIST样例类似的指定损失函数、优化函数和测评指标。
model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])

# 在测试数据上评测模型。
score = model.evaluate(testX, testY, batch_size=batch_size)
print('Test loss: ', score[0])
print('Test accuracy: ', score[1])
```

睡了个午觉就跑完啦：

![img](https://img-blog.csdnimg.cn/20190506140733504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20190506142508986.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

### 2.2 Keras高级用法

面对上面的例子，都是顺序搭建的神经网络模型，类似于Inception这样的模型结构，就需要更加灵活的模型定义方法了。

> 在这里我真的是忍不住要吐槽一下书上的内容，简直完全没有讲清楚在说什么鬼。。。没说清楚究竟是用的那一部分的数据，是MNIST还是rnn的数据。。。捣鼓了半天才知道是MNIST。然后这里的意思应该是用全连接的方式，即输入数据为(60000, -1)，也就是说样本是60000个，然后把图片的维度拉伸为1维。（这里我也是摸索了好久才知道的），所以在代码中需要对数据进行reshape处理。不然会报错：
>
> **ValueError: Error when checking input: expected input_1 to have 2 dimensions, but got array with shape (60000, 28, 28)**
>
> 参考链接：https://blog.csdn.net/u012193416/article/details/79399679

是真的坑爹，只能说。。。什么也没有说清楚，就特么瞎指挥。。。（然鹅，我是真的菜。。。摊手。。。）

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: keras_inception.py
@time: 2019/5/6 14:29
@desc: 用更加灵活的模型定义方法在MNIST数据集上实现全连接层模型。
"""

import keras
from keras.layers import Input, Dense
from keras.models import Model
from keras.datasets import mnist


# 使用前面介绍的类似方法生成trainX、trainY、testX、testY，唯一的不同是这里只用了
# 全连接层，所以不需要将输入整理成三维矩阵。
num_calsses = 10
img_rows, img_cols = 28, 28

# 通过Keras封装好的API加载MNIST数据。其中trainX就是一个60000x28x28的数组，
# trainY是每一张图片对应的数字。
(trainX, trainY), (testX, testY) = mnist.load_data()

trainX = trainX.reshape(len(trainX), -1)
testX = testX.reshape(len(testX), -1)

# 将图像像素转化为0到1之间的实数。
trainX = trainX.astype('float32')
testX = testX.astype('float32')
trainX /= 255.0
testX /= 255.0

# 将标准答案转化为需要的格式（One-hot编码）。
trainY = keras.utils.to_categorical(trainY, num_calsses)
testY = keras.utils.to_categorical(testY, num_calsses)

# 定义输入，这里指定的维度不用考虑batch大小。
inputs = Input(shape=(784, ))
# 定义一层全连接层，该层有500隐藏节点，使用ReLU激活函数。这一层的输入为inputs
x = Dense(500, activation='relu')(inputs)
# 定义输出层。注意因为keras封装的categorical_crossentropy并没有将神经网络的输出
# 再经过一层softmax，所以这里需要指定softmax作为激活函数。
predictions = Dense(10, activation='softmax')(x)

# 通过Model类创建模型，和Sequential类不同的是Model类在初始化的时候需要指定模型的输入和输出
model = Model(inputs=inputs, outputs=predictions)

# 使用与前面类似的方法定义损失函数、优化函数和评测方法。
model.compile(loss=keras.losses.categorical_crossentropy, optimizer=keras.optimizers.SGD(), metrics=['accuracy'])

# 使用与前面类似的方法训练模型。
model.fit(trainX, trainY, batch_size=128, epochs=10, validation_data=(testX, testY))

```

修改之后运行可以得到：

![img](https://img-blog.csdnimg.cn/20190506154300672.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

---

通过这样的方式，Keras就可以实现类似Inception这样的模型结构了。

**现在又要说坑爹的部分了，这本书在这里直接照抄的Keras的手册中的例子，来解释用Keras实现Inception-v3的模型结构，所以给出的代码是这样的**

```python
from keras.layers import Conv2D, MaxPooling2D, Input
# 定义输入图像尺寸
input_img = Input(shape=(256, 256, 3))

# 定义第一个分支。
tower_1 = Conv2D(64, (1, 1), padding='same', activation='relu')(input_img)
tower_1 = Conv2D(64, (3, 3), padding='same', activation='relu')(tower_1)

# 定义第二个分支。与顺序模型不同，第二个分支的输入使用的是input_img，而不是第一个分支的输出。
tower_2 = Conv2D(64, (1, 1), padding='same', activation='relu')(input_img)
tower_2 = Conv2D(64, (5, 5), padding='same', activation='relu')(tower_2)

# 定义第三个分支。类似地，第三个分支的输入也是input_img。
tower_3 = MaxPooling2D((3, 3), strides=(1, 1), padding='same')(input_img)
tower_3 = Conv2D(64, (1, 1), padding='same', activation='relu')(tower_3)

# 将三个分支通过concatenate的方式拼凑在一起。
output = keras.layers.concatenate([tower_1, tower_2, tower_3], axis=1)
```

你可能要问“这就完啦？”，我想告诉你的是，对的。关于Inception-v3的部分就这么点。然后我给你看一眼**网上官方的代码**：

参考链接：https://keras.io/zh/getting-started/functional-api-guide/

![img](https://img-blog.csdnimg.cn/20190506165619785.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

是不是有种似曾相识的感觉。。。

踏马的根本就没有想着去实现好吗？

我也是醉了的，我就问一句，不是一直在用MNIST数据集作为例子吗！那这个

`input_img = Input(shape=(256, 256, 3))`

图像尺寸怎么突然就编程(256, 256, 3)了呢？而不是(28, 28, 1)呢？

==这本书一点都不走心好吗！==

**我也是佛了，那么我只能靠自己理解，并自己写例子了。这里面的艰辛我就不说了，不卖惨了，是真的恨，我只希望每一个例子都能够有始有终，都能够有输出有结果，能运行！**

下面贴一下我自己想的改的代码吧：

```python
#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: keras_inception2.py
@time: 2019/5/6 15:43
@desc: 用原生态的Keras实现Inception
"""

from keras.layers import Conv2D, MaxPooling2D, Input, Dense, Flatten
import keras
from keras.models import Model
from keras.datasets import mnist
from keras import backend as K


# 使用前面介绍的类似方法生成trainX、trainY、testX、testY，唯一的不同是这里只用了
# 全连接层，所以不需要将输入整理成三维矩阵。
num_calsses = 10
img_rows, img_cols = 28, 28

# 通过Keras封装好的API加载MNIST数据。其中trainX就是一个60000x28x28的数组，
# trainY是每一张图片对应的数字。
(trainX, trainY), (testX, testY) = mnist.load_data()

if K.image_data_format() == 'channels_first':
    trainX = trainX.reshape(trainX.shape[0], 1, img_rows, img_cols)
    testX = testX.reshape(trainX.shape[0], 1, img_rows, img_cols)
    # 因为MNIST中的图片是黑白的，所以第一维的取值为1
    input_shape = (1, img_rows, img_cols)
else:
    trainX = trainX.reshape(trainX.shape[0], img_rows, img_cols, 1)
    testX = testX.reshape(testX.shape[0], img_rows, img_cols, 1)
    input_shape = (img_rows, img_cols, 1)

# 将图像像素转化为0到1之间的实数。
trainX = trainX.astype('float32')
testX = testX.astype('float32')
trainX /= 255.0
testX /= 255.0

# 将标准答案转化为需要的格式（One-hot编码）。
trainY = keras.utils.to_categorical(trainY, num_calsses)
testY = keras.utils.to_categorical(testY, num_calsses)

# 定义输入图像尺寸
input_img = Input(shape=(28, 28, 1))

# 定义第一个分支。
tower_1 = Conv2D(64, (1, 1), padding='same', activation='relu')(input_img)
tower_1 = Conv2D(64, (3, 3), padding='same', activation='relu')(tower_1)

# 定义第二个分支。与顺序模型不同，第二个分支的输入使用的是input_img，而不是第一个分支的输出。
tower_2 = Conv2D(64, (1, 1), padding='same', activation='relu')(input_img)
tower_2 = Conv2D(64, (5, 5), padding='same', activation='relu')(tower_2)

# 定义第三个分支。类似地，第三个分支的输入也是input_img。
tower_3 = MaxPooling2D((3, 3), strides=(1, 1), padding='same')(input_img)
tower_3 = Conv2D(64, (1, 1), padding='same', activation='relu')(tower_3)

# 将三个分支通过concatenate的方式拼凑在一起。
output = keras.layers.concatenate([tower_1, tower_2, tower_3], axis=1)

# 将卷积层的输出拉直后作为下面全连接的输入。
tower_4 = Flatten()(output)
# 全连接层，有500个节点。
tower_5 = Dense(500, activation='relu')(tower_4)
# 全连接层，得到最后的输出。
predictions = Dense(num_calsses, activation='softmax')(tower_5)

# 通过Model类创建模型，和Sequential类不同的是Model类在初始化的时候需要指定模型的输入和输出
model = Model(inputs=input_img, outputs=predictions)

# 使用与前面类似的方法定义损失函数、优化函数和评测方法。
model.compile(loss=keras.losses.categorical_crossentropy, optimizer=keras.optimizers.SGD(), metrics=['accuracy'])

# 使用与前面类似的方法训练模型。
model.fit(trainX, trainY, batch_size=128, epochs=10, validation_data=(testX, testY))

# 在测试数据上评测模型。
score = model.evaluate(testX, testY, batch_size=128)
print('Test loss: ', score[0])
print('Test accuracy: ', score[1])
```

运行结果：

![img](https://img-blog.csdnimg.cn/2019050617024791.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

说明，我改了之后是能跑的。。。

对了，如果有杠精问我，人家只是抛砖引玉，让读者举一反三。。。那我没什么好说的。。。









未完待续。。。

---

我的CSDN：https://blog.csdn.net/qq_21579045

我的博客园：https://www.cnblogs.com/lyjun/

我的Github：https://github.com/TinyHandsome

纸上得来终觉浅，绝知此事要躬行~

欢迎大家过来OB~