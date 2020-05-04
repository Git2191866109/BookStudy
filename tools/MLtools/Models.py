#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: Models.py
@time: 2020/4/29 15:22
@desc: 
"""
from xgboost import XGBClassifier
from MyModel import MyModel
from sklearn.ensemble import AdaBoostClassifier
from sklearn.svm import SVC
from sklearn.naive_bayes import MultinomialNB, GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.tree import DecisionTreeClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import GradientBoostingClassifier
from collections import OrderedDict


# 1. Multinomial Naive Bayes Classifier / 多项式朴素贝叶斯
class MyMNB(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "多项式朴素贝叶斯"
        self.is_ensemble = False
        self.chinese_name = "多项式朴素贝叶斯"
        self.english_name = "Multinomial Naive Bayes Classifier"
        self.model = MultinomialNB()


# 2. Gaussian Naive Bayes Classifier / 高斯朴素贝叶斯
class MyGNB(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "高斯朴素贝叶斯"
        self.is_ensemble = False
        self.chinese_name = "高斯朴素贝叶斯"
        self.english_name = "Gaussian Naive Bayes Classifier"
        self.model = GaussianNB()


# 3. KNN Classifier / K最近邻
class MyKNN(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "K最近邻"
        self.is_ensemble = False
        self.chinese_name = "K最近邻"
        self.english_name = "KNN Classifier"
        self.model = KNeighborsClassifier()


# 4. Logistic Regression Classifier / 逻辑回归
class MyLR(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "逻辑回归"
        self.is_ensemble = False
        self.chinese_name = "逻辑回归"
        self.english_name = "Logistic Regression Classifier"
        self.model = LogisticRegression()


# 5. SVM Classifier / 支持向量机
class MySVM(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "支持向量机"
        self.is_ensemble = False
        self.chinese_name = "支持向量机"
        self.english_name = "Support Vector Machine"
        self.model = SVC()


# 6. Decision Tree Classifier / 决策树
class MyDT(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "决策树"
        self.is_ensemble = False
        self.chinese_name = "决策树"
        self.english_name = "Decision Tree Classifier"
        self.model = DecisionTreeClassifier()


# 7. Random Forest Classifier / 随机森林
class MyRF(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "随机森林"
        self.is_ensemble = True
        self.chinese_name = "随机森林"
        self.english_name = "Random Forest Classifier"
        self.model = RandomForestClassifier()

        self.parameters = OrderedDict([
            # 集成模型数量越小越简单
            ('n_estimators', range(10, 200, 20)),
            # 最大树深度越小越简单
            ('max_depth', range(1, 10, 1)),
            # 最小样本分割数越大越简单
            ('min_samples_split', list(range(2, 10, 1))[::-1])
        ])


# 8. GBDT(Gradient Boosting Decision Tree) Classifier / 梯度提升决策树
class MyGBDT(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "GBDT"
        self.is_ensemble = True
        self.chinese_name = "梯度提升决策树"
        self.english_name = "Gradient Boosting Decision Tree Classifier"
        self.model = GradientBoostingClassifier()

        self.parameters = OrderedDict([
            # 集成模型数量越小越简单
            ('n_estimators', range(10, 200, 20)),
            # 最大树深度越小越简单
            ('max_depth', range(1, 10, 1)),
            # 最小样本分割数越大越简单
            ('min_samples_split', list(range(2, 10, 1))[::-1])
        ])


# 9. XGBoost / 极端梯度提升
class MyXGBoost(MyModel):
    def __init__(self):
        super().__init__()
        self.name = "XGBoost"
        self.is_ensemble = True
        self.chinese_name = "极端梯度提升树"
        self.english_name = "XGBoost"
        self.model = XGBClassifier()

        self.parameters = OrderedDict([
            # 集成模型数量越小越简单
            ('n_estimators', range(10, 200, 20)),
            # 最大树深度越小越简单
            ('max_depth', range(1, 10, 1)),
            # 最小样本分割数越大越简单
            ('min_samples_split', list(range(2, 10, 1))[::-1])
        ])


# 10. AdaBoost Classifier / 自适应提升法
class MyAdaboost(MyModel):
    """
    [AdaBoost参数详解](https://www.cnblogs.com/mdevelopment/p/9445090.html)
    """

    def __init__(self):
        super().__init__()
        self.name = "Adaboost"
        self.is_ensemble = True
        self.chinese_name = "自适应提升算法"
        self.english_name = "Adaptive Boosting"
        self.model = AdaBoostClassifier(base_estimator=DecisionTreeClassifier())

        self.parameters = OrderedDict([
            # 集成模型数量越小越简单
            ('n_estimators', range(10, 200, 20)),
            # 最大树深度越小越简单
            ('base_estimator__max_depth', range(1, 10, 1)),
            # 最小样本分割数越大越简单
            ('base_estimator__min_samples_split', list(range(2, 10, 1))[::-1])
        ])