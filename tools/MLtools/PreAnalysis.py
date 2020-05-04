#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: PreAnalysis.py
@time: 2020/5/4 11:23
@desc: 对数据进行各种预分析，比如关联性分析等
        [特征关联](https://blog.csdn.net/FrankieHello/article/details/81604806)
"""
import pandas as pd

from MyMatplotlib import MyMatplotlib


class PreAnalysis:
    def __init__(self, save_path):
        self.save_path = save_path

    def correlationHotMap(self, X, names=None, annot=False, change_ticks_fontsize=False, rotation_ticks=False):
        """
        关联矩阵分析-->热力图
        :param X: 二维矩阵
        :param names: 属性的列名，如果没有传入列名的话，自动生成列名
        :param annot: 是否在格子里标注数据
        :param change_ticks_fontsize: 是否改变ticks的字体大小
        :param rotation_ticks: 是否旋转ticks（名字太长了就要这样做）
        :return:
        """

        # 将二维矩阵转化为DataFrame
        df = pd.DataFrame(X, columns=names)
        corr_mat = df.corr()

        # 绘制热力图
        MyMatplotlib(self.save_path).plot_heatmap(corr_mat, annot, change_ticks_fontsize, rotation_ticks)

    def correlationPair(self, X, y, names=None, hue=True, reg=False, keep_legend=False, vars=None):
        """
        所有变量中任意两个变量之间的图形， 用矩阵图探索多维数据不同维度间的相关性
        这里由于可以，对不同的类进行分析， 所以可以传入y
        实际使用时，可以通过plot_pair函数的传参来设置，仅分析属性，还是连类别也分析
        :param X:
        :param y:
        :param names: 列名，属性名
        :param hue:
        :param reg:
        :param keep_legend:
        :param vars:
        :return:
        """
        # 将二维矩阵转化为DataFrame
        df = pd.DataFrame(X, columns=names)
        labels = [str(i) for i in y]
        df['label'] = labels

        # 绘制矩阵图
        MyMatplotlib(self.save_path).plot_pair(df, hue, reg, keep_legend, vars)


