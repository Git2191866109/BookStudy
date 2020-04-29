#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: MyAdaboost.py
@time: 2020/4/29 15:22
@desc: 
"""
from MyModel import MyModel
from sklearn.ensemble import AdaBoostClassifier


class MyAdaboost(MyModel):

    def __init__(self):
        super().__init__()
        self.name = "Adaboost"
        self.is_ensemble = True
        self.chinese_name = "自适应提升算法"
        self.english_name = "Adaptive Boosting"
        self.model = AdaBoostClassifier()
        # 固定每个模型的随机种子
        self.model.set_params(**{'random_state': self.random_state})