#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: MLtools_v1_1.py
@time: 2020/5/3 19:29
@desc: 改名为MLtools，重构代码
        各种函数调用工具
        注意：
            1. collections.OrderDict不能用OrderDict({})，否则会依然没有顺序
               [参考链接](https://cloud.tencent.com/developer/ask/53926)
"""
import Models as ms
from collections import OrderedDict
import pandas as pd


class MLTools:

    def __init__(self):
        self.m1 = ms.MyMNB()
        self.m2 = ms.MyGNB()
        self.m3 = ms.MyKNN()
        self.m4 = ms.MyLR()
        self.m5 = ms.MySVM()
        self.m6 = ms.MyDT()
        self.m7 = ms.MyRF()
        self.m8 = ms.MyGBDT()
        self.m9 = ms.MyXGBoost()
        self.m10 = ms.MyAdaboost()

        self.models = OrderedDict([
            (self.m1.name, self.m1),
            (self.m2.name, self.m2),
            (self.m3.name, self.m3),
            (self.m4.name, self.m4),
            (self.m5.name, self.m5),
            (self.m6.name, self.m6),
            (self.m7.name, self.m7),
            (self.m8.name, self.m8),
            (self.m9.name, self.m9),
            (self.m10.name, self.m10)
        ])

        self.model_names = list(self.models.keys())

    def evaluate_origin_model(self, X, y):
        """
        使用所有的模型，【不调参】，进行分类，并输出评价指标
        :return:
        """
        result = []
        items = None

        # 获取未调参的模型的结果
        for model_name, model in self.models.items():
            eval = model.score_model(model.model, X, y)
            r = [model_name] + list(eval.values())
            result.append(r)
            if items is None:
                items = ['model_name'] + list(eval.keys())

        data = pd.DataFrame(result, columns=items)
        data.set_index('model_name', inplace=True)
        return data

    def evaluate_adjust_model(self, X, y):
        """
        使用所有的模型，【调参】，进行分类，并输出评价指标
        :return:
        """
        result = []
        items = None

        # 获取网格调参的模型的结果
        for model_name, model in self.models.items():
            eval = model.score_model(model.paramsAdjustment_byGridSearch(X, y), X, y)
            r = [model_name] + list(eval.values())
            result.append(r)
            if items is None:
                items = ['model_name'] + list(eval.keys())

        data = pd.DataFrame(result, columns=items)
        data.set_index('model_name', inplace=True)
        return data

    def evaluate(self, X, y):
        """
        建立未调参和网格调参后的模型，获取交叉验证的评价指标
        :param X:
        :param y:
        :return:
        """

        # 所有模型未调参结果
        un_paramed_result = self.evaluate_origin_model(X, y)
        # 网格调参后结果
        paramed_result = self.evaluate_adjust_model(X, y)

        print(un_paramed_result)
        print(paramed_result)


