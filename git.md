# git常用的指令

git init 		--把当前的文件夹设置为本地仓库

git touch file01.txt 	--创建file01.txt文件

git

git branch dev01 --创建分支dev01

git checkout dev01 --切换到分支dev01

git checkout -b dev02  	--创建并切换到dev02分支

git add .		--添加到暂存区

git commit -m "内容提示"   	--这里是提交到仓库，提交记录为：内容提示

git status 		--查看状态

git reset --hard <commitID>  	--版本回退



git log 	--查看提交记录

git-log

git checkout master  + git merge dev01	--为了将dev01合并到master，需要先切换到master，然后合并

git branch -d dev01 	--删除该分支（注意，要删除某个分支，当前不可用在这个分支里面）



分支：

master：主分支

develop：开发分支



# git的远程仓库：

常用的有GitHub，码云（gitee），gitlab等。

git remote add origin ssh地址		--添加到ssh地址的远程仓库，命名为origin

git remote 	--看当前的远程仓库有哪些

git push origin master		--把本地的master分支传到叫origin的仓库

git push [-f]		--表示强制覆盖



git push --set--upstream origin master:master		--表示推送 并 设置origin的master与本地仓库的master绑定

git branch -vv		--显示绑定关系



## 克隆clone

git clone ssh地址 命名（默认为最后的）	--得到同云端的文件夹



抓取(fetch)与拉取

git fetch + git merge origin/master   <==>  git pull

抓取是拉到本地，但是不合并；

抓取是直接抓取并合并





# 在idea中使用git

第一步：在VCS里面创建本地仓库

第二部：创建.gitignore文件，并设置不导入.idea

![image-20231005143624255](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005143624255.png)

勾表示提交，然后选择

![image-20231005145052541](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005145052541.png)

![image-20231005145014966](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005145014966.png)



推送是遇到了如下错误，是因为我的git账号对应的是GitHub而不是gitee，然后我选择了GitHub上传，久没有报错了

![image-20231005144858345](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005144858345.png)





拉去的方式，然后选择url和文件位置

![image-20231005145221341](C:\Users\xbt\AppData\Roaming\Typora\typora-user-images\image-20231005145221341.png)



工作中，先点蓝色的箭头，然后跟新，解决冲突（有的话），之后再往上推



创建分支上，可以再分支的节点上，右键创建分支，是以那个节点内容为基础来创建分支。