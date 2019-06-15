from django.shortcuts import render
import os,re
import subprocess, shlex, json
from django.http.response import HttpResponse
from . import get_help

# Create your views here.

def index(request):
    # core_value = request.POST['core']
    # core_id = request.POST['id']
    core_id = 'editor1'
    with open('editor_core/editor.txt','r') as file:
        text = file.read()
    # with open('editor_core/ans.txt','r') as file:
    #     core_value = file.read()
    text = text.replace('editor_name', 'editor_1')
    text = text.replace('core_id',core_id)
    text = text.replace('core_value', 'input_your_code')
    text = text.replace('read_choose', 'false')

    context = {}
    context['core_editor'] = text

    core_id = 'editor2'
    with open('editor_core/editor.txt','r') as file:
        text2 = file.read()
    text2 = text2.replace('core_id',core_id)
    text2 = text2.replace('core_value', 'beauty_code')
    text2 = text2.replace('read_choose', 'true')
    text2 = text2.replace('editor_name', 'editor_2')
    context['core_editor_beauty'] = text2

    return render(request, 'index.html', context)

def test_index(request):
    return render(request, 'test_index.html')

def beautiful_core(request):
    choose1 = request.POST.get('choose1')
    choose2 = request.POST.get('choose2')
    choose3 = request.POST.get('choose3')
    print(choose1,choose2,choose3)
    core_value = request.POST.get('core')
    # print(core_value)

    with open('editor_core/junkjava.txt', 'w+',newline='') as file:
        file.write(core_value)

    add = ""
    if(str(choose1) == 'true'):
        add += '  --expandSingleIf'
    if(str(choose2) == 'true'):
        add += '  --transferSwitchToIf'
    if(str(choose3) == 'true'):
        add += '  --transferForToWhile'

    command = 'java -cp JavaIntelligence.jar org.hacksource.cli.IntelligenceCLI' + add
    print(command)

    args = shlex.split(command)
    p = subprocess.Popen(args, stdin=open('editor_core/junkjava.txt', 'r'), stdout=subprocess.PIPE)
    p.wait()
    # return_json  = (json.loads(str(p.stdout.read(), encoding='utf-8')))

    try:
        return_json = (json.dumps(str(p.stdout.read(), encoding='utf-8')))
    except:
        return_json = (json.dumps(str(p.stdout.read(), encoding='gbk')))

    print(return_json)
    return HttpResponse(return_json,content_type='application/json,charset=utf-8')

def get_help_hint(request):
    help_id = request.POST.get('help_id')
    hint = get_help.get_help(help_id)
    return HttpResponse(hint)
