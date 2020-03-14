#!/usr/bin/env python
# -*- coding: UTF-8 -*-
# coding=utf-8 

"""
@author: Li Tian
@contact: litian_cup@163.com
@software: pycharm
@file: TorrentObtain.py
@time: 2020/3/14 12:13
@desc: 获取网页中各个torrent
参考连接：https://www.jianshu.com/p/95b1bc3b2f73
"""

from urllib.request import urlopen
from bs4 import BeautifulSoup
import re

resp = urlopen('http://www.hanmi520.com/forum.php?mod=viewthread&tid=108187')
html = BeautifulSoup(resp.read(), 'html.parser')
html.find_all(name=)

pattern = re.compile(r".*1988.*")
result = pattern.search()