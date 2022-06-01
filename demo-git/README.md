## Git 语法使用

1. 提交
> git commit -m ""
> git push 

2. 修改提交信息
> git commit --amend --only -m ""

3. 查看提交信息
> git show / git log -n1 -p

4. 移除commit中的一个文件
> git checkout HEAD^ 文件  
> git add -A  
> git commit --amend  

5.删除最新一次提交（push commit）
> git reset HEAD^ --hard  (已推送远程)
> git reset HEAD^ --soft  (未推送远程)
> git push -f

6.删除任意一次提交
> git rebase --onto SHA1_OF_BAD_COMMIT^ SHA1_OF_BAD_COMMIT  
> git push -f [remote] [branch]