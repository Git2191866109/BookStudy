#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: test1.py
@time: 2020/4/29 16:09
@desc: 测试专用
"""

from sklearn.datasets import load_iris

from MySVM import MySVM
import numpy as np

iris = load_iris()
iris_data = iris['data']
iris_target = iris['target']
iris_names = iris['target_names']

svm = MySVM()
# svm.simple_model(iris_data, iris_target)
print(svm.aim_model(iris_data, iris_target, {'C': 0.1}))
print(svm.model)

