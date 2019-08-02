import subprocess, shlex, json


command = 'java -cp JavaIntelligence.jar org.hacksource.cli.IntelligenceCLI --transferForToWhile'
args = shlex.split(command)

p = subprocess.Popen(args, stdin=open('junkjava.txt','r'),stdout = subprocess.PIPE)
p.wait()
fuck=p.stdout.read()
#print(fuck[1200:1230])
return_json = (json.loads(str(fuck, encoding='utf-8')))
print(type(return_json),return_json)
# with open('test.txt','w+') as file:
#     print(return_json)
#     file.write(return_json)
