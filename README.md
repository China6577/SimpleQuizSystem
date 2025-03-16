# SimpleQuizSystem
## 简单的Quiz做题系统
### 使用方法一：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;1.在idea中找到 左上角的文件->项目结构->库->+  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;2.添加SimpleQuizSystem\repositories\maven\xjtlu\cpt111\xjtlu.cpt111.assignment.quiz.lib\0.0.1\xjtlu.cpt111.assignment.quiz.lib-0.0.1库  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;3.找到SimpleQuizSystem\QuizSystem\src\QuizSystem\Main.java文件运行即可  
### 使用方法二：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;1.进入SimpleQuizSystem\QuizSystem\目录输入cmd  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;2.输入compile回车编译  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;3.输入run回车运行  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;4.输入clean回车清除生成的javac文件  
### 注意：运行JDK版本尽量新(22.0.2)  
### 功能说明：  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;1.log in登录：user id和password在SimpleQuizSystem\QuizSystem\resources\users.csv文件的第一列和第三列 第二列是用户名  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;2.register注册：同样，注册成功的数据会保存在SimpleQuizSystem\QuizSystem\resources\users.csv文件  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;3.log out注销：注销成功SimpleQuizSystem\QuizSystem\resources\users.csv文件中的数据会被删除  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;4.exit退出：退出系统  
&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;5.如果想修改题库则可以向SimpleQuizSystem\QuizSystem\resources\questionsBank\目录下添加或修改xml文件
