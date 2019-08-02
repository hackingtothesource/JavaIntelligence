import os
import subprocess, shlex, json

def beauty_dirlog(root_deep):
    for root,dirs,files in os.walk(root_deep):
        for file in files:

            if('.java' in file):
                #获取文件所属目录
                print(root)
                #获取文件路径
                path = (os.path.join(root,file))
                print(path)
                command = 'java -cp JavaIntelligence.jar org.hacksource.cli.IntelligenceCLI'
                print(command)

                args = shlex.split(command)
                p = subprocess.Popen(args, stdin=open(path, 'r'), stdout=subprocess.PIPE)
                p.wait()

                return_json  = (json.loads(str(p.stdout.read(), encoding='utf-8')))
                print(1)
                if(return_json['success'] == False):
                    beauty_code = json.dumps(return_json['error']).split('Problem stacktrace')[0]
                    with open(os.path.join(root_deep,'error_list.txt'), 'a+') as file2:
                        path.replace("\\",'/')
                        print('path=' ,path)
                        file2.write(file + '\n')

                else:
                    beauty_code = (return_json['fixedCode'])
                    # print(beauty_code)

                with open(path,'w') as file3:
                    file3.write(beauty_code)


