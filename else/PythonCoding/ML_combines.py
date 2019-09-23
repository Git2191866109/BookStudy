#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: 694317828@qq.com
@software: pycharm
@file: ML_combines.py
@time: 2019/9/23 8:53
@desc: 机器学习工具集合，直接写一个类，传参训练集，验证集就能生成报告
"""


class MLTools:
    """
    包含：多项式朴素贝叶斯, 高斯朴素贝叶斯, K最近邻, 逻辑回归, 支持向量机, 决策树, 随机森林, Adaboost, GBDT, xgboost
    """

    def __init__(self, X_train, y_train, X_test, y_test):
        self.X_train = X_train
        self.y_train = y_train
        self.X_test = X_test
        self.y_test = y_test

    # Multinomial Naive Bayes Classifier / 多项式朴素贝叶斯
    def multinomial_naive_bayes_classifier(self):
        from sklearn.naive_bayes import MultinomialNB
        model = MultinomialNB(alpha=0.01)
        model.fit(self.X_train, self.y_train)
        return model

    # Gaussian Naive Bayes Classifier / 高斯朴素贝叶斯
    def gaussian_naive_bayes_classifier(self):
        from sklearn.naive_bayes import GaussianNB
        model = GaussianNB()
        model.fit(self.X_train, self.y_train)
        return model

    # KNN Classifier / K最近邻
    def knn_classifier(self):
        from sklearn.neighbors import KNeighborsClassifier
        model = KNeighborsClassifier()
        model.fit(self.X_train, self.y_train)
        return model

    # Logistic Regression Classifier / 逻辑回归
    def logistic_regression_classifier(self):
        from sklearn.linear_model import LogisticRegression
        model = LogisticRegression(penalty='l2')
        model.fit(self.X_train, self.y_train)
        return model

    # SVM Classifier / 支持向量机
    def svm_classifier(self):
        from sklearn.svm import SVC
        model = SVC(kernel='rbf', probability=True)
        model.fit(self.X_train, self.y_train)
        return model

    # Decision Tree Classifier / 决策树
    def decision_tree_classifier(self):
        from sklearn import tree
        model = tree.DecisionTreeClassifier()
        model.fit(self.X_train, self.y_train)
        return model

    # Random Forest Classifier / 随机森林
    def random_forest_classifier(self):
        from sklearn.ensemble import RandomForestClassifier
        model = RandomForestClassifier(n_estimators=8)
        model.fit(self.X_train, self.y_train)
        return model

    # AdaBoost Classifier / 自适应提升法
    def adaboost_classifier(self):
        from sklearn.ensemble import AdaBoostClassifier
        model = AdaBoostClassifier()
        model.fit(self.X_train, self.y_train)
        return model

    # GBDT(Gradient Boosting Decision Tree) Classifier / 梯度提升决策树
    def gradient_boosting_classifier(self):
        from sklearn.ensemble import GradientBoostingClassifier
        model = GradientBoostingClassifier(n_estimators=200)
        model.fit(self.X_train, self.y_train)
        return model

    # xgboost / 极端梯度提升
    def xgboost_classifier(self):
        import xgboost
        model = xgboost.XGBClassifier()
        model.fit(self.X_train, self.y_train)
        return model


def model_building(X_train, y_train, X_test, y_test, save_path, target_names=None):
    """
    训练模型，并得到结果，并重新训练所有数据，保存模型
    :param save_path: 模型的保存路径
    :param target_names: 样本标签名
    """
    from sklearn.metrics import classification_report
    import joblib
    import os
    import numpy as np

    tool = MLTools(X_train, y_train, X_test, y_test)
    models = [tool.multinomial_naive_bayes_classifier(),
              tool.gaussian_naive_bayes_classifier(),
              tool.knn_classifier(),
              tool.logistic_regression_classifier(),
              tool.svm_classifier(),
              tool.decision_tree_classifier(),
              tool.random_forest_classifier(),
              tool.adaboost_classifier(),
              tool.gradient_boosting_classifier(),
              tool.xgboost_classifier()]
    model_names = ['多项式朴素贝叶斯', '高斯朴素贝叶斯', 'K最近邻', '逻辑回归', '支持向量机', '决策树', '随机森林', 'Adaboost', 'GBDT', 'xgboost']

    # 遍历每个模型
    with open(save_path + 'report.txt', 'w+') as f:

        for count in range(len(models)):
            model = models[count]
            model_name = model_names[count]
            print(str(count+1) + '. 正在运行：', model_name, '...')
            train_pred = model.predict(X_train)
            test_pred = model.predict(X_test)

            train = classification_report(y_train, train_pred, target_names=target_names)
            test = classification_report(y_test, test_pred, target_names=target_names)

            f.write('- ' + model_name + '\n')
            f.write('-- 【训练集】'+ '\n')
            f.writelines(train)
            f.write('\n')
            f.write('-- 【测试集】' + '\n')
            f.writelines(test)
            f.write('\n')

            model.fit(np.r_[np.array(X_train), np.array(X_test)], np.r_[np.array(y_train), np.array(y_test)])
            joblib.dump(model, os.path.join(save_path, model_name + '.plk'))


if __name__ == '__main__':
    from sklearn.datasets import load_iris
    from sklearn.model_selection import train_test_split
    iris = load_iris()
    iris_data = iris['data']
    iris_target = iris['target']
    iris_names = iris['target_names']
    X_train, X_test, y_train, y_test = train_test_split(iris_data, iris_target, test_size=0.2, random_state=42)
    model_building(X_train, y_train, X_test, y_test, save_path='./models', target_names=iris_names)