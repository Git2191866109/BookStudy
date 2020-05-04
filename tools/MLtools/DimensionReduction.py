#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: DimensionReduction.py
@time: 2020/5/4 11:16
@desc: 降维工具
        [PCA](https://scikit-learn.org/stable/modules/generated/sklearn.decomposition.PCA.html?highlight=pca#sklearn.decomposition.PCA)
        [isinstance类型检测方法](https://www.runoob.com/python/python-func-isinstance.html)
"""

from sklearn.decomposition import PCA
import copy


class DimensionReduction:
    def __init__(self):
        # 降维方法名
        self.name = None
        # 随机种子
        self.random_state = 42
        # 模型类
        self.model = None
        # 若未给出，生成80%，90%，95%三个级别的数据
        self.p_list = [0.8, 0.9, 0.95]

        # 子类初始化
        try:
            # 固定每个模型的随机种子
            self.model.set_params(**{'random_state': self.random_state})
        except:
            pass

    def get_vr(self, X):
        """获取保留的各个特征的方差"""
        model = copy.deepcopy(self.model)
        model.fit(X)
        return model.explained_variance_ratio_

    def get_aim_percent(self, X, p=None):
        """
        获取保留p（1>p>0）以上信息的数据
        随着p的增大，保留的维度越高，数据越多
        """
        model = copy.deepcopy(self.model)
        if p is None:
            # 若未给出，生成80%，90%，95%三个级别的数据
            p = self.p_list
        else:
            if isinstance(p, float):
                p = [p]

        data_list = []
        for per in p:
            params = {'n_components': per}
            model.set_params(**params)
            data_list.append(model.fit_transform(X))

        return data_list


class MyPCA(DimensionReduction):
    """对相关系性较高的特征进行降维处理"""

    def __init__(self):
        super().__init__()
        self.name = 'PCA'
        self.model = PCA()
