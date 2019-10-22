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
from sklearn.metrics import f1_score


class MLTools:
    """
    包含：多项式朴素贝叶斯, 高斯朴素贝叶斯, K最近邻, 逻辑回归, 支持向量机, 决策树, 随机森林, Adaboost, GBDT, xgboost
    """
    random_state = 42

    # 粗略 随机森林调参数值
    # 参考链接1：https://blog.csdn.net/geduo_feng/article/details/79558572
    # 参考链接2：https://blog.csdn.net/qq_35040963/article/details/88832030
    parameter_tree = {
        'n_estimators': range(10, 200, 20),
        'max_depth': range(1, 10, 1),
        'min_samples_split': range(2, 10, 1),
    }

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
        return model, None

    # Gaussian Naive Bayes Classifier / 高斯朴素贝叶斯
    def gaussian_naive_bayes_classifier(self):
        from sklearn.naive_bayes import GaussianNB
        model = GaussianNB()
        model.fit(self.X_train, self.y_train)
        return model, None

    # KNN Classifier / K最近邻
    def knn_classifier(self):
        from sklearn.neighbors import KNeighborsClassifier
        model = KNeighborsClassifier()
        model.fit(self.X_train, self.y_train)
        return model, None

    # Logistic Regression Classifier / 逻辑回归
    def logistic_regression_classifier(self):
        from sklearn.linear_model import LogisticRegression
        model = LogisticRegression(penalty='l2')
        model.fit(self.X_train, self.y_train)
        return model, None

    # SVM Classifier / 支持向量机
    def svm_classifier(self):
        from sklearn.svm import SVC
        model = SVC(kernel='rbf', probability=True)
        model.fit(self.X_train, self.y_train)
        return model, None

    # Decision Tree Classifier / 决策树
    def decision_tree_classifier(self):
        from sklearn import tree
        model = tree.DecisionTreeClassifier()
        model.fit(self.X_train, self.y_train)
        return model, None

    # Random Forest Classifier / 随机森林
    def random_forest_classifier(self, is_adjust=True):
        from sklearn.ensemble import RandomForestClassifier

        # 训练普通模型
        model = RandomForestClassifier(n_estimators=8)
        model.fit(self.X_train, self.y_train)
        test_pred = model.predict(self.X_test)
        min_score = f1_score(self.y_test, test_pred, average='macro')
        if not is_adjust:
            return model

        max_score = 0
        best_param = [None, None, None]
        for p1 in MLTools.parameter_tree['n_estimators']:
            for p2 in MLTools.parameter_tree['max_depth']:
                for p3 in MLTools.parameter_tree['min_samples_split']:
                    test_model = RandomForestClassifier(random_state=MLTools.random_state, n_estimators=p1,
                                                        max_depth=p2, min_samples_split=p3, n_jobs=-1)
                    test_model.fit(self.X_train, self.y_train)
                    test_pred = test_model.predict(self.X_test)
                    new_score = f1_score(self.y_test, test_pred, average='macro')
                    if new_score >= max_score:
                        max_score = new_score
                        best_param = [p1, p2, p3]
        best_model = RandomForestClassifier(random_state=MLTools.random_state, n_estimators=best_param[0],
                                            max_depth=best_param[1], min_samples_split=best_param[2], n_jobs=-1)
        best_model.fit(self.X_train, self.y_train)
        word = '-- optimized parameters: \n'
        count = 0
        for name in MLTools.parameter_tree.keys():
            word = word + name + ' = ' + str(best_param[count]) + '\n'
            count += 1
        word = word + 'f1_macro: ' + '%.4f' % min_score + '-->' + '%.4f' % max_score + "\n"
        return best_model, word

    # AdaBoost Classifier / 自适应提升法
    def adaboost_classifier(self):
        from sklearn.ensemble import AdaBoostClassifier
        model = AdaBoostClassifier()
        model.fit(self.X_train, self.y_train)
        return model, None

    # GBDT(Gradient Boosting Decision Tree) Classifier / 梯度提升决策树
    def gradient_boosting_classifier(self):
        from sklearn.ensemble import GradientBoostingClassifier
        model = GradientBoostingClassifier(n_estimators=200)
        model.fit(self.X_train, self.y_train)
        return model, None

    # xgboost / 极端梯度提升
    def xgboost_classifier(self):
        import xgboost
        model = xgboost.XGBClassifier()
        model.fit(self.X_train, self.y_train)
        return model, None


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
    f = open(save_path + 'report.txt', 'w+')
    g = open(save_path + 'optimized.txt', 'w+')

    for count in range(len(models)):
        model, optimized = models[count]
        model_name = model_names[count]
        print(str(count + 1) + '. 正在运行：', model_name, '...')
        train_pred = model.predict(X_train)
        test_pred = model.predict(X_test)

        train = classification_report(y_train, train_pred, target_names=target_names)
        test = classification_report(y_test, test_pred, target_names=target_names)

        f.write('- ' + model_name + '\n')
        f.write('-- 【训练集】' + '\n')
        f.writelines(train)
        f.write('\n')
        f.write('-- 【测试集】' + '\n')
        f.writelines(test)
        f.write('\n')

        g.write('- ' + model_name + '\n')
        if optimized:
            g.write(optimized)
        g.write('\n')

        model.fit(np.r_[np.array(X_train), np.array(X_test)], np.r_[np.array(y_train), np.array(y_test)])
        joblib.dump(model, os.path.join(save_path, model_name + '.plk'))

    f.close()
    g.close()


if __name__ == '__main__':
    from sklearn.datasets import load_iris
    from sklearn.model_selection import train_test_split

    iris = load_iris()
    iris_data = iris['data']
    iris_target = iris['target']
    iris_names = iris['target_names']
    X_train, X_test, y_train, y_test = train_test_split(iris_data, iris_target, test_size=0.2, random_state=42)
    model_building(X_train, y_train, X_test, y_test, save_path='./models/', target_names=iris_names)
