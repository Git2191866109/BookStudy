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
        [数据预处理流程](https://www.jianshu.com/p/2339b0005405)
        [数据预处理全过程](https://blog.csdn.net/Monk_donot_know/article/details/86479176?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase)

        [缺失值处理方法](https://baijiahao.baidu.com/s?id=1665989715432866346&wfr=spider&for=pc)
            检查缺失值的两种方式：缺失热图/相关图，缺失树状图

"""
import os

import pandas as pd

from MLTools import Tools
from MyMatplotlib import MyMatplotlib
from TimeTool import TimeTool


class PreAnalysis(Tools):
    def __init__(self, save_path):
        super().__init__(save_path)
        self.hue = None
        # 功能集成：字典
        self.funcs = {
            '特征矩阵图': self.correlationPair,
            '相关性分析': self.correlationHotMap,
        }

    def aim(self, t=0):
        """
        通过t来控制运行所有程序还是哪一个
        :param t:
            1：返回矩阵分析图的调用和参数，默认两个都生成
               根据是否是监督学习，选择性生成，非监督学习就不能
            2. 返回相关性分析的调用和参数，
        :return:
        """

        self.return_inf = None
        if t == 1:
            self.correlationPair(self.X)
        elif t == 1.1:
            self.correlationPair(self.X, self.y, hue=True)
        elif t == 1.2:
            self.correlationPair(self.X, self.y, hue=True, reg=True)
        elif t == 2:
            self.correlationHotMap(self.X)

    def correlationHotMap(self, X, column_names=None, annot=False, change_ticks_fontsize=False, rotation_ticks=False):
        """
        关联矩阵分析-->热力图
        热力图没有label，不分因此只要X不要y
        :param X: 二维矩阵
        :param column_names: 属性的列名，如果没有传入列名的话，自动生成列名
        :param annot: 是否在格子里标注数据
        :param change_ticks_fontsize: 是否改变ticks的字体大小
        :param rotation_ticks: 是否旋转ticks（名字太长了就要这样做）
        :return:
        """

        func_name = '相关性分析'

        # 将二维矩阵转化为DataFrame
        if isinstance(X, pd.DataFrame):
            df = X
        else:
            df = pd.DataFrame(X, columns=column_names)
        corr_mat = df.corr()

        # 保存热力图元数据
        excel_name = TimeTool().getCurrentTime() + '.xls'
        file_savePath = os.path.join(self.save_path, excel_name)
        corr_mat.to_excel(file_savePath)

        # 绘制热力图
        self.fig_path = MyMatplotlib(self.save_path).plot_heatmap(corr_mat, annot, change_ticks_fontsize,
                                                                  rotation_ticks)
        return None

    def correlationPair(self, X, y=None, names=None, hue=False, reg=False, vars=None):
        """
        所有变量中任意两个变量之间的图形， 用矩阵图探索多维数据不同维度间的相关性
        这里由于可以，对不同的类进行分析， 所以可以传入y
        实际使用时，可以通过plot_pair函数的传参来设置，仅分析属性，还是连类别也分析
        需要根据y是否存在分为监督学习还是非监督学习
        :param X:
        :param y:
        :param names: 列名，属性名
        :param hue: 是否分类
        :param reg:
        :param vars:
        :return:
        """
        func_name = '特征矩阵图'

        # 将二维矩阵转化为DataFrame
        if isinstance(X, pd.DataFrame):
            df = X
        else:
            df = pd.DataFrame(X, columns=names)

        if hue:
            labels = [str(i) for i in y]
            df['label'] = labels

        # 绘制矩阵图
        self.fig_path = MyMatplotlib(self.save_path).plot_pair(df, hue, reg, vars)
        return None