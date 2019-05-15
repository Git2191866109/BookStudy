#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: gpu_test5.py
@time: 2019/5/15 22:27
@desc: 在本地运行有两个任务的TensorFlow集群。
"""

import tensorflow as tf
c = tf.constant("Hello from server1!")

# 生成一个有两个任务的集群，一个任务跑在本地2222端口，另外一个跑在本地2223端口。
