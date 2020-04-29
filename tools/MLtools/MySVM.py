#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: MySVM.py
@time: 2020/4/29 15:44
@desc: 
"""
from MyModel import MyModel
from sklearn.svm import SVC
import numpy as np


class MySVM(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "SVM"
        self.is_ensemble = False
        self.chinese_name = "支持向量机"
        self.english_name = "Support Vector Machine"
        self.model = SVC()
        # 固定每个模型的随机种子
        self.model.set_params(**{'random_state': self.random_state})






