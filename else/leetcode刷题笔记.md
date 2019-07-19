# leetcode刷题笔记

[TOC]

## 写在前面

leetcode连接：https://leetcode-cn.com/problemset/all/

刷题顺序：从简单->中等->困难

- [简单题库](https://leetcode-cn.com/problemset/all/?difficulty=%E7%AE%80%E5%8D%95)

解题思路：做一个自己的答案，再参考评论中的思路，自己编写更加高效的代码（下面给出两个百分数，分别代表**执行用时**和**内存消耗**击败了%多少的用户）





## [9. 回文数](https://leetcode-cn.com/problems/palindrome-number/)

```python
class Solution:
    def isPalindrome(self, x: int) -> bool:
        if x < 0:
            return False
        else:
            temp = str(x)
            l = len(temp)
            for i in range(int(l/2)):
                if temp[i] == temp[l-1-i]:
                    continue
                else:
                    return False
            return True
```

99.38	26.19	我觉得还ok

> 大佬一句话给我整哭了
>
> 方法：转字符串啊。。。

```python
class Solution:
    def isPalindrome(self, x: int) -> bool:
        return str(x) == str(x)[::-1]
```

## [14. 最长公共前缀](https://leetcode-cn.com/problems/longest-common-prefix/)

```python
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        if len(strs) == 0:
            return ""
        elif len(strs) == 1:
            return strs[0]
        length = len(strs[0])
        
        for x in strs[1:]:
            if len(x) < length:
                length = len(x)
        if length == 0:
            return ""
        
        temp = ""       
        for i in range(length):
            for j in range(1, len(strs)):
                if strs[j-1][0:i+1] == strs[j][0:i+1]:
                    continue
                else:
                    return temp
            temp = temp + strs[0][i]
        return temp
```

21.22	5.53	我哭了

> 方法：利用好zip和set
>
> ![img](https://img-blog.csdnimg.cn/20190718221501874.png)

参考文献：[python中zip()与zip(*)的用法解析](https://blog.csdn.net/ezio23/article/details/81414092)

```python
class Solution:
    def longestCommonPrefix(self, strs: List[str]) -> str:
        # 最后加一个0就可以包含字符创一毛一样问题，即没有False
        temp = [len(set(x)) == 1 for x in zip(*strs)] + [0]
        if strs:
            index = temp.index(False)
            return strs[0][:index]            
        else:
            return ""
```

98.52	45.90	抄大佬作业太可怕了。。。

## [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

```python
class Solution:
    def isValid(self, s: str) -> bool:
        one = ['(', '[', '{']
        two = [')', ']', '}']
        kuohao = []
        if len(s) == 0:
            return True
        elif s[0] in two or len(s)%2 == 1:
            return False
        else:
            for x in s:
                if x in one:
                    kuohao.append(x)
                else:
                    index = two.index(x)
                    if kuohao.pop() == one[index]:
                        continue
                    else:
                        return False
            if len(kuohao) == 0:
                return True
            else:
                return False
```

31.05	83.11	我哭了

> 方法：栈
>
> ![img](https://img-blog.csdnimg.cn/20190718215031607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxNTc5MDQ1,size_16,color_FFFFFF,t_70)

```python
class Solution:
    def isValid(self, s: str) -> bool:
        one = ['(', '[', '{']
        two = [')', ']', '}']
        mapping = dict(zip(one, two))
        stack = []
        for i in s:
            if len(stack) == 0 or i in one:
                stack.append(i)
            elif (stack[-1] in one) and (mapping[stack[-1]] == i):
                stack.pop()
            else:
                return False
        if len(stack) == 0:
            return True
        else:
            return False
```

99.96	5.51	我很快乐

## [26. 删除排序数组中的重复项](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/)

```python
class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        x = list(set(nums))
        x.sort(key=nums.index)
        nums[:len(x)] = x
        return len(x)
```

5.01	19.67	我哭了

> 方法：双指针法
>
> ![img](https://img-blog.csdnimg.cn/20190718213525809.png)

```python
class Solution:
    def removeDuplicates(self, nums: List[int]) -> int:
        if len(nums) == 0:
            return 0
        i = 0
        for j in range(1, len(nums)):
            if nums[j] == nums[i]:
                continue
            else:
                i += 1
                nums[i] = nums[j]
        return i + 1
```

修改后	96.39	8.35	快乐！

## [27. 移除元素](https://leetcode-cn.com/problems/remove-element/)

```python
class Solution:
    def removeElement(self, nums: List[int], val: int) -> int:   
        if val not in nums:
            return len(nums)
        i = nums.index(val)
        for j in range(i+1, len(nums)):
            if nums[j] == val:
                continue
            else:
                nums[i] = nums[j]
                i += 1
        return i
```

85.85	8.97	快乐

天啦，我基本上就是官方答案了，还参考个锤子

