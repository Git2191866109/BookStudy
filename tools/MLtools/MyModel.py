#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: MyModel.py
@time: 2020/4/29 15:19
@desc: 基类，
        参考sklearn官网：https://scikit-learn.org/stable/index.html
        itertools参考连接：https://www.jianshu.com/p/73b17486ef8c
        注意：交叉验证的抽样方式默认是分层抽样
        每个函数操作的模型是默认模型的深复制
"""
from sklearn.model_selection import cross_val_score
import numpy as np
import itertools
from collections import OrderedDict
import copy


class MyModel:

    def __init__(self):
        # 模型名
        self.name = "主类"
        # 默认采用宏平均的计算结果
        self.scoring = 'f1_macro'
        # 模型类
        self.model = None
        # 随机种子
        self.random_state = 42
        # 图片保存路径
        self.pic_savePath = './pics/'
        # 模型保存路径
        self.model_savePath = './models/'
        # 报告保存路径
        self.report_savePath = './reports/'

    def simple_model(self, X, y, cv, scoring):
        """
        用默认参数进行建模
        :param X: 数据集
        :param y: 数据标签
        :param cv: 交叉验证折数
        :param scoring: 评分方法
        :return: 用X，y训练的支持向量机
        """
        f_name = "默认参数建模"
        model = copy.deepcopy(self.model)
        return self.modeling(f_name, model, X, y, cv, scoring)

    def aim_model(self, X, y, params, cv=3, scoring=None):
        """
        用指定参数进行建模
        :param X: 数据集
        :param y: 数据标签
        :param params: 参数集，字典
        :param cv: 交叉验证折数
        :param scoring: 评分方法
        :return: 用X，y训练的支持向量机
        """
        f_name = "指定参数建模"
        model = copy.deepcopy(self.model)
        model.set_params(**params)
        return self.modeling(f_name, model, X, y, cv, scoring)

    def get_params(self):
        """获取对应模型的参数集"""
        params_dict = self.model.get_params()
        params = list(params_dict.keys())
        return params

    def modeling(self, f_name, model, X, y, cv, scoring):
        """
        根据不同的模型建模，基础函数
        :param f_name: 建模方式名称
        :param model: 模型
        :param X: 数据集
        :param y: 数据标签
        :param cv: 交叉验证折数
        :param scoring: 评分方法
        :return: 用X，y训练的模型
        """
        if not scoring:
            scoring = self.scoring

        output_info = "你正在进行【" + f_name + "】（默认参数），结果输出【" + str(cv) + "折】交叉验证结果（" + self.scoring + "）..."
        scores = cross_val_score(model, X, y, cv=cv, scoring=scoring)
        output_info += "\n输出各折的" + self.scoring + "：" + str(scores)
        mean_score = np.mean(scores)
        output_info += "\n输出各折的平均" + self.scoring + "：" + "{:.4f}".format(mean_score)
        print(output_info)

        model.fit(X, y)
        return model, output_info

    def paramsAdjustment_byStep(self, X, y, params):
        """分步调参"""
        params_names = params.keys()
        params_scores = OrderedDict()
        better_params = OrderedDict()
        for name in params_names:
            params_values = params[name]
            scores = []
            max_score = 0
            better_value = None
            for value in params_values:
                param = {name: value}
                _, score = self.aim_model(X, y, param)
                scores.append(score)

                if score > max_score:
                    max_score = score
                    better_value = value

            params_scores[name] = scores
            better_params[name] = better_value

        # 绘制调参图
        self.plot2list(params, params_scores, better_params)

    def plot2list(self, params, params_scores, better_params):
        pass