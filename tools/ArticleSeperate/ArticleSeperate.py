#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: ArticleSeperate.py
@time: 2019/11/29 9:36
@desc: 根据章节名把文章分成两部分
"""
import os


def article_seperate(chaper_name, file_path, save_path):
    """
    根据章节名把文章分成两部分
    :param formate: 文件格式，默认md
    :param chaper_name: 章节名（+#的）
    :param save_path: 分开后文章的保存路径
    :param file_path: 需要处理的文件名
    """

    end = "./end.md"
    with open(end, "rb") as f:
        end_article = f.readlines()

    file_root, tempfilename = os.path.split(file_path)
    file_name, extension = os.path.splitext(tempfilename)
    path1 = save_path + file_name + "_1/2" + extension
    path2 = save_path + file_name + "_2/2" + extension
    w1 = open(path1, "wb")
    w2 = open(path2, "wb")

    flag = True
    x = None
    start_label = "第1章"
    start = b''
    start_flag = True
    with open(file_path, "rb") as f:
        while b'' != x:
            x = f.readline()

            if start_label in bytes.decode(x):
                start_flag = False
            if start_flag:
                start += x

            if chaper_name in bytes.decode(x):
                flag = False
                w1.writelines(end_article)
                w1.close()
                w2.write(start)

            if flag:
                w1.write(x)
            if not flag:
                w2.write(x)

        w2.close()


def main():
    file_path = "D:/李添的数据哦！！！/BookStudy/else/【自学】4. JAVA基础.md"
    save_path = "C:/Users/handsome_liyingjun/Desktop/"
    article_seperate("第10章", file_path, save_path)


if __name__ == '__main__':
    main()
