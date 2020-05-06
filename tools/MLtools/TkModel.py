#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: TkModel.py
@time: 2020/5/6 21:50
@desc: 可视化专用
        [各个组件介绍](https://blog.csdn.net/ahilll/article/details/81531587)
        [布局详解](https://www.cnblogs.com/jerryspace/p/9836034.html)
        [配色方案](https://www.cnblogs.com/kongzhagen/p/6154826.html)

        [Text详解](https://blog.csdn.net/qq_41556318/article/details/85112829)

"""
import tkinter as tk
from tkinter.filedialog import askopenfilename, askdirectory
import threading
import os
import re


class TkModel():

    def __init__(self):
        """gui配置文件"""
        self.label_width = 10
        self.label_height = 1
        self.entry_width = 50
        self.button_width = 5
        self.button_height = None
        self.button_height_large = None
        self.ratio_height = 2

        """字体设置"""
        self.font_title = ("Microsoft YaHei", 14)
        self.button_font = ("Microsoft YaHei", 14)
        self.ratio_font = ("Microsoft YaHei", 8)

        """Root 元素"""
        self.root = tk.Tk()
        self.root.title('机器学习工具v1.0【作者：李英俊小朋友】')
        root_width = 1200
        root_height = 900

        # 计算居中的位置，默认分辨率为1920*1080
        left_pad = int(1920 / 2 - root_width / 2)
        up_pad = int(1080 / 2 - root_height / 2)

        self.root.geometry(str(root_width) + "x" + str(root_height) + "+" + str(left_pad) + "+" + str(up_pad))
        self.root.resizable(width=False, height=False)

        """Frame 颜色和大小"""
        f1_color = 'Gainsboro'
        f2_color = 'white'
        f1_height = root_height
        f1_width = 2 * root_width / 3
        f2_height = root_height
        f2_width = 1 * root_width / 3
        # 功能区
        self.f_func = tk.Frame(self.root, height=f1_height, width=f1_width, bg=f1_color)
        # 输出区
        self.f_print = tk.Frame(self.root, height=f2_height, width=f2_width, bg=f2_color)

        """Text 功能区默认放置信息，以及滚动条"""
        # state:
        #   1. 默认情况下 Text 组件响应键盘和鼠标事件（"normal"）
        #   2. 如果将该选项的值设置为 "disabled"，那么上述响应就不会发生，并且你无法修改里边的内容
        # wrap:
        #   1. 设置当一行文本的长度超过 width 选项设置的宽度时，是否自动换行
        #   2. 该选项的值可以是："none"（不自动换行），"char"（按字符自动换行）和 "word"（按单词自动换行）
        # takefocus:
        #   1. 指定使用 Tab 键可以将焦点移动到 Text 组件中
        #   2. 默认是开启的，可以将该选项设置为 False 避免焦点在此 Text 组件中
        # spacing2:
        #   1. 指定 Text 组件的文本块中自动换行的各行间的空白间隔
        #   2. 注意：换行符（'\n'）不算
        #   3. 默认值是 0
        # font:
        #   1. 设置 Text 组件中文本的默认字体
        #   2. 注意：通过使用 Tags 可以使 Text 组件中的文本支持多种字体显示
        self.t_print = tk.Text(self.f_print, state=tk.DISABLED, wrap="char", takefocus=False, spacing2=1,
                               font=('Microsoft YaHei', 14))

        # 设置滚动条
        self.scroll = tk.Scrollbar()
        # 放到窗口的右侧, 填充Y竖直方向
        self.scroll.pack(side=tk.RIGHT, fill=tk.Y)

        # 两个控件关联
        self.scroll.config(command=self.t_print.yview)
        self.t_print.config(yscrollcommand=self.scroll.set)
        self.t_print.pack(fill=tk.BOTH, expand=tk.YES)

    def finish(self):
        """布局root和frame"""
        self.f_func.pack(side=tk.LEFT, fill=tk.BOTH)
        self.f_print.pack(side=tk.RIGHT, fill=tk.BOTH)
        self.root.mainloop()

    def info(self, inf: str, focus_words: list = None, fg_color: str = 'black', bg_color: str = 'white', font_type: str = 'Microsoft YaHei',
             font_size: int = 14):
        """
        写入右边信息，报错啊，运行到哪里了呀，等等
        支持多个重点内容，但不能重复，重点内容的传入为list
        :param inf: 写入的信息
        :param focus_words: 需要强调的内容，最好用【】表示，类型是list，一个值也要用list
        :param fg_color:
        :param bg_color:
        :param font_type:
        :param font_size:
        :return:
        """
        self.t_print.config(state=tk.NORMAL)

        # 设置字体和大小
        font = (font_type, font_size)
        self.t_print.tag_config("tag_temp", background=bg_color, foreground=fg_color, font=font)
        self.t_print.tag_config("tag_temp1", background=bg_color, foreground=fg_color, font=font)
        self.t_print.tag_config("tag_temp_imp", background=bg_color, foreground='red', font=font)

        # 若没有需要重点标明的内容
        if focus_words is None:
            self.t_print.insert("end", inf + '\n', "tag_temp")
            self.t_print.config(state=tk.DISABLED)
        else:
            # 正则表达式分割重点内容，很明显，非重点语句的数量比重点内容多1
            regx_focus = "|".join(focus_words)
            words_list = re.split(regx_focus, inf)
            # 用空值补充重点内容
            fw = focus_words + ['\n']

            # 开始写出内容，重点内容默认字体大小不变，但颜色为红色
            for inf1, inf_imp in zip(words_list, fw):
                self.t_print.insert("end", inf1, "tag_temp1")
                self.t_print.insert("end", inf_imp, "tag_temp_imp")

    def path_select(self):
        """
        路径选择模块
        :return: 标题Label，输入框Entry，按钮Button
        """
        # 路径选择保存 变量
        path_file = tk.StringVar()

        def select_file():
            path_file.set(askopenfilename())
            self.e1.configure(fg='black')
            self.write_info('写入文件1成功！（默认保存路径获取！）', 'green')
            table1_path = os.path.dirname(self.path1_file.get())
            self.save_file.set(table1_path)

        l = tk.Label(self.root, text='表格1路径', width=self.label_width, height=self.label_height, font=self.font_title)
        e1 = tk.Entry(self.root, width=self.entry_width, textvariable=path_file, font=self.font_title)
        b1 = tk.Button(self.root, text='选择', font=self.button_font, width=self.button_width, height=self.button_height,
                       command=select_file)

    def test(self):
        self.finish()


if __name__ == '__main__':
    t = TkModel()
    t.test()
