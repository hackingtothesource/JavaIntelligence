ww=open(r"tt.txt","w",encoding='utf-8')

for line in open(r"junkjava.txt",encoding='utf-8'):

    #去除首尾换行制表符等等
    line=line.strip()
    words=line.split(",")
    #print("外层循环："+line.strip())
    for w in words:
        #w=w.strip();
        #print("内层循环："+w.strip())
     #  print("================")
        ww.write(w.strip()+"\n")




ww.flush()
ww.close()
print("写入成功！！！")