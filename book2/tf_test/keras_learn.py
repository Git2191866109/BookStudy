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
from keras.layers import  Dense, Flatten, Conv2D, MaxPooling2D
from keras import backend as K


num_calsses = 10
img_rows, img_cols = 28, 28

# 通过Keras封装好的API加载MNIST数据。其中trainX就是一个60000x28x28的数组，
# trainY是每一张图片对应的数字。
(trainX, trainY), (testX, testY) = mnist.load_data()

# 因为不同的底层（TensorFlow或者MXNet）对输入的要求不一样，所以这里需要根据对图像
# 编码的格式要求来设置输入层的格式。