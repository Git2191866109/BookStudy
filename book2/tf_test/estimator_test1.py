#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: estimator_test1.py
@time: 2019/5/7 16:22
@desc: 基于MNIST数据集，通过Estimator实现全连接神经网络。
"""

import numpy as np
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data


# 将TensorFlow日志信息输出到屏幕
tf.logging.set_verbosity(tf.logging.INFO)
mnist = input_data.read_data_sets('D:/Python3Space/BookStudy/book2/MNIST_data', one_hot=True)

# 指定神经网络的输入层，所有这里指定的输入都会拼接在一起作为整个神经网络的输入。
feature_columns = [tf.feature_column.numeric_column("image", shape=[784])]

# 通过TensorFlow提供的封装好的Estimator定义神经网络模型。feature_columns参数
# 给出了神经网络输入层需要用到的数据，hidden_units列表中给出了每一层
# 隐藏层的节点数。n_classes给出了总共类目的数量，optimizer给出了使用的优化函数。
# Estimator会将模型训练过程中的loss变化以及一些其他指标保存到model_dir目录下，
# 通过TensorFlow可以可视化这些指标的变化过程。并通过TensorBoard可视化监控指标结果。
estimator = tf.estimator.DNNClassifier(
    feature_columns=feature_columns,
    hidden_units=[500],
    n_classes=10,
    optimizer=tf.train.AdamOptimizer(),
    model_dir="./log"
)