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

        [常用降维方法](https://www.jianshu.com/p/ad2ac2bda87b)
        [PCA和FA的区别](https://blog.csdn.net/yujianmin1990/article/details/49247307)
"""
import os

from sklearn.decomposition import PCA
import copy
import pandas as pd

from MLTools import Tools
from TimeTool import TimeTool


class DimensionReduction(Tools):
    def __init__(self, save_path):
        super().__init__(save_path)
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


class MyPCA(DimensionReduction):
    """对相关系性较高的特征进行降维处理"""

    def __init__(self, path):
        super().__init__(path)
        self.name = 'PCA'
        self.model = PCA()

    def aim(self, t=0):
        """
        通过t来控制运行所有程序还是哪一个
        :return:
        """
        # 获取投射到新空间后每个特征的方差
        ii = self.get_vr(self.X)
        self.get_aim_percent(self.X, self.y)
        self.return_inf = '---> 原始数据投射到新空间后每个特征的方差为：\n' + ii + '\n'
        # 根据不同的信息保留比例获取操作后的数据
        self.return_inf += '---> 已保存阈值设置为【0.8, 0.9, 0.95】降维后的数据...'

    def get_vr(self, X):
        """获取保留的各个特征的方差"""
        model = copy.deepcopy(self.model)
        model.fit(X)
        ii = model.explained_variance_ratio_.tolist()
        ii = ["{:.4f}".format(s) for s in ii]
        return '       | ' + ' | '.join(ii) + ' |'

    def get_aim_percent(self, X, y, p=None):
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

        # 保存降维后的数据，并根据是否有y整合为新的数据
        for per in p:
            params = {'n_components': per}
            model.set_params(**params)
            new_data = model.fit_transform(X)
            df = pd.DataFrame(new_data)
            excel_name = '[' + str(per) + ']' + TimeTool().getCurrentTime() + '.xls'

            file_savePath = os.path.join(self.save_path, excel_name)

            if y is not None:
                df['label'] = y

            df.to_excel(file_savePath, index=False)



