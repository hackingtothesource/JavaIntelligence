function change_java() {
    window.python_choose = false;
     var loc = document.getElementById("opchoosef");
     var lung = document.getElementById("language");
     lung.innerText = "选择语言(Java)"
     loc.innerHTML = "                     <div style=\"margin-top: 10px\" id=\"opchoose\">\n" +
         "                <label  class=\"checkbox-inline\">\n" +
         "                           <input type=\"checkbox\" id=\"choose0\" value=\"option0\"> 生成分析报告\n" +
         "                       </label>"+
        "                       <label  class=\"checkbox-inline\">\n" +
        "                           <input type=\"checkbox\" id=\"choose1\" value=\"option1\"> 展开if语句条件\n" +
        "                       </label>\n" +
        "                         <label class=\"checkbox-inline\">\n" +
        "                            <input type=\"checkbox\" id=\"choose2\" value=\"option2\"> switch转if\n" +
        "                        </label>\n" +
        "                        <label  class=\"checkbox-inline\">\n" +
        "                        <input type=\"checkbox\" id=\"choose3\" value=\"option3\"> for转while\n" +
        "                        </label>";
    }
function change_python() {
    $("#editor1").children().remove();
        //init monaco
        require.config({
            paths: { 'vs': '../static/node_modules/monaco-editor/min/vs' }, 'vs/nls': {
                availableLanguages: {
                    '*': 'zh-cn'
                }
            }
        });
        require(['vs/editor/editor.main'], function () {

    editor_1 = monaco.editor.create(document.getElementById("editor1"), {
	value: `core_value`,
	language: "python",
	lineNumbers: "on",
	roundedSelection: true,
	scrollBeyondLastLine: true,
	readOnly: false,
	theme: "vs-dark",
	automaticLayout: true,
	glyphMargin: true,
});




        });
    $("#editor2").children().remove();
        //init monaco
        require.config({
            paths: { 'vs': '../static/node_modules/monaco-editor/min/vs' }, 'vs/nls': {
                availableLanguages: {
                    '*': 'zh-cn'
                }
            }
        });
        require(['vs/editor/editor.main'], function () {

    editor_2 = monaco.editor.create(document.getElementById("editor2"), {
	value: `core_value`,
	language: "python",
	lineNumbers: "on",
	roundedSelection: true,
	scrollBeyondLastLine: true,
	readOnly: true,
	theme: "vs-dark",
	automaticLayout: true,
	glyphMargin: true,
});




        });

 var child = document.getElementById("opchoose");
 // child.parentNode.removeChild(child);
      var lung = document.getElementById("language");
     lung.innerText = "选择语言(Python)";

     child.innerHTML = "                     <div style=\"margin-top: 10px\" id=\"opchoose\">\n" +
         "                <label  class=\"checkbox-inline\">\n" +
         "                           <input type=\"checkbox\" id=\"choose0\" value=\"option0\"> 生成分析报告\n" +
         "                       </label>";

 window.python_choose = true;
 var tmp = "import os\n" +
     "import subprocess, shlex, json\n" +
     "\n" +
     "def beauty_dirlog(root_deep):\n" +
     "    for root,dirs,files in os.walk(root_deep):\n" +
     "        for file in files:\n" +
     "\n" +
     "            if('.java' in file):\n" +
     "                #获取文件所属目录\n" +
     "                print(root)\n" +
     "                #获取文件路径\n" +
     "                path = (os.path.join(root,file))\n" +
     "                print(path)\n" +
     "                command = 'java -cp JavaIntelligence.jar org.hacksource.cli.IntelligenceCLI'\n" +
     "                print(command)\n" +
     "\n" +
     "                args = shlex.split(command)\n" +
     "                p = subprocess.Popen(args, stdin=open(path, 'r'), stdout=subprocess.PIPE)\n" +
     "                p.wait()\n" +
     "\n" +
     "                return_json  = (json.loads(str(p.stdout.read(), encoding='utf-8')))\n" +
     "\n" +
     "                if(return_json['success'] == False):\n" +
     "                    beauty_code = json.dumps(return_json['error']).split('Problem stacktrace')[0]\n" +
     "                    with open(os.path.join(root_deep,'error_list.txt'), 'a+') as file:\n" +
     "                        file.write(path + '\\n')\n" +
     "\n" +
     "                else:\n" +
     "                    beauty_code = (return_json['fixedCode'])\n" +
     "                    # print(beauty_code)\n" +
     "\n" +
     "                with open(path,'w') as file:\n" +
     "                    file.write(beauty_code)\n" +
     "\n" +
     "\n";
     editor_1.setValue(tmp);
}

function startdownload() {
    var loc = document.getElementById("download2");
    loc.innerHTML = "                    <label for=\"f12\" class=\" btn btn-default\" style=\"margin-top: 10px;\"'>请上传您选择的源文件</label>\n" +
        "                               <input type=\"file\"  name=\"fafafa\" id=\"f12\"  style=\"display:none\">\n" +
        "                              <input type=\"submit\" value=\"一键生成\" class=\"btn btn-primary\" style=\"margin-top: 10px;\">";
    // var loc2 = document.getElementById("download3");
    // loc2.innerHTML = "<input type=\"submit\"  class=\"btn btn-primary\" id=\"f13\" value=\"下载转换文件\"  style=\"margin-top: 5px;\">";
}

function startdownloadreport() {
    var loc2 = document.getElementById("download4");
    loc2.innerHTML = "<input type=\"submit\"  class=\"btn btn-primary\" id=\"f14\" value=\"下载报告\"  style=\"margin-top: 5px;\">";
}

function getPhrases(text, wordsPerPhrase) {
  var words = text.split(/\s+/);
  var result = [];
  for (var i = 0; i < words.length; i += wordsPerPhrase) {
    result.push(words.slice(i, i + wordsPerPhrase).join(" "))
  }
  return result
}


function beauty() {

    try {
                    var choose0 = document.getElementById("choose0").checked;
            var choose1 = document.getElementById("choose1").checked;
    var choose2 = document.getElementById("choose2").checked;
    var choose3 = document.getElementById("choose3").checked;
    }
    catch (e) {
        
    }
    if(choose0){
                var loc3 = document.getElementById("download4");
                loc3.innerHTML = "";
            }
    // var choose4 = document.getElementById("choose4").checked;
    var choose4 = window.python_choose;
    try {
           // for ( var i = 0; i <window.decorations.length; i++){
           //  decorations = editor_1.deltaDecorations(window.decorations[i], []);
           //  }
        decorations = editor_1.deltaDecorations(decorations, [{ range: new monaco.Range(1,1,1,1), options : { } }]);

} catch (e) {

}

    //jquery天下第一
    var data = {core: editor_1.getValue(),choose1:choose1,choose2:choose2,choose3:choose3,choose4:choose4};
            showdiv =document.getElementById("problems");
        showdiv.innerHTML = "";
        loading = document.getElementById("loading")
    loading.innerHTML =
        "    <style type=\"text/css\">\n" +
        "body {\n" +
        "  background: white;\n" +
        "}\n" +
        "\n" +
        ".loading {\n" +
        "  position: absolute;\n" +
        "  left: 50%;\n" +
        "  top: 50%;\n" +
        "  margin: -60px 0 0 -60px;\n" +
        "  background: #fff;\n" +
        "  width: 100px;\n" +
        "  height: 100px;\n" +
        "  border-radius: 100%;\n" +
        "  border: 10px solid #19bee1;\n" +
        "}\n" +
        ".loading:after {\n" +
        "  content: '';\n" +
        "  background: trasparent;\n" +
        "  width: 140%;\n" +
        "  height: 140%;\n" +
        "  position: absolute;\n" +
        "  border-radius: 100%;\n" +
        "  top: -20%;\n" +
        "  left: -20%;\n" +
        "  opacity: 0.7;\n" +
        "  box-shadow: rgba(127,128,124,0.6) -4px -5px 3px -3px;\n" +
        "  animation: rotate 2s infinite linear;\n" +
        "}\n" +
        "\n" +
        "@keyframes rotate {\n" +
        "  0% {\n" +
        "    transform: rotateZ(0deg);\n" +
        "  }\n" +
        "  100% {\n" +
        "    transform: rotateZ(360deg);\n" +
        "  }\n" +
        "}\n" +
        "</style>\n" +
        "<div class=\"loading\"></div>";
$.ajax({
    url: '/editor/beauty/',
    type: 'POST',
    data: data,
    dataType: 'json',
    success: function(result) {
                loading = document.getElementById("loading");
    loading.innerHTML = "";
        console.log(result);
        try
        {
            var status = JSON.parse(result).success;
        }
        catch(err){
     var status = result.success;
}

        if (status == false)
        {
            try {
                 var error_text = JSON.parse(result).error[0];
            }
           catch (e) {
                                var error_text = result.error[0];

           }
            //console.log(error_text);
            var line = error_text.split(' ')[1].split(',')[0];
            console.log(line);
            error_text = error_text.split('\n')[0];
            //error_text = error_text.split(')')[1].replace(new RegExp(",",'g'),'\n');
            // var tmp = error_text.split(')')[1];
            var tmp = error_text;
            tmp = getPhrases(tmp, 6).join('\n');

            var score = '0';

            var html2 = `<font size="3" face="Times" color="red">`;
            document.getElementById("scores").innerHTML= html2 + "代码得分:" + "</font>" +score+html2+"分"+ "</font>";
            editor_2.setValue(tmp);

            if(choose0)
            {
                startdownloadreport();
            }
            else{
                var loc2 = document.getElementById("download4");
                loc2.innerHTML = "";
            }


var decorations = editor_1.deltaDecorations([], [
	{
		range: new monaco.Range(line,1,line,1),
		options: {
			isWholeLine: true,
			className: 'myContentClass',
			glyphMarginClassName: 'myGlyphMarginClass'
		}
	}

]);
	window.decorations.push(decorations);
            //alert("代码有致命错误");
        }
        else
        {

            try {
                var beauty_code = (JSON.parse(result).fixedCode);
            }
            catch(err)
            {
                var beauty_code = result.fixedCode;
            }

            try {
                var arr = JSON.parse(result).problems;
            }
            catch (e) {
                var arr = result.problems;
            }
            
            try {


                arr.sort(function (a, b) {
                    var pos = a.split(':')[0];
                    var pos2 = b.split(':')[0];
                    var line = pos.split(' ')[1].split(',')[0];
                    var line2 = pos2.split(' ')[1].split(',')[0];
                    return line - line2;
                });
                console.log('排序之后为' + arr);

            }
            catch (e) {
                
            }
            var len = arr.length;
            if(len == 0)
            {
                var score = 100.0;
            }
            else {
                var score = 100 - 5 * len - Math.round(Math.random() * 2 * 10) / 10;
            }
            var html1 = `<font size="3" face="Times" color="red">`;
            document.getElementById("scores").innerHTML= html1 + "代码得分:" + "</font>" +score+html1+"分"+ "</font>";
             for ( var i = 0; i <arr.length; i++){
                   var problems = arr[i];
                   var pos = problems.split(':')[0];
                   var id = problems.split(':')[1];
                   console.log(pos,id);
                   try {
                       get_help(pos,id);
                   }
                   catch (e) {
                       
                   }
            }


            editor_2.setValue(beauty_code);
                    if(choose0)
            {
                startdownloadreport();
            }
                             else{
                var loc3 = document.getElementById("download4");
                loc3.innerHTML = "";
            }
            //alert("美化成功");
        }

    }
})
}
function get_help(pos,id) {
    //jquery天下第一
    var data = {help_id : '#'+id};
    console.log(data);
$.ajax({
    url: '/editor/get_help/',
    type: 'POST',
    data: data,
    async: false,      //ajax同步
    success: function(result) {
        var line = pos.split(' ')[1].split(',')[0];
        var line2 = pos.split(' ')[3].split(',')[0];
        console.log('help'+line2);
        var decorations = editor_1.deltaDecorations([], [
	{
		range: new monaco.Range(line,1,line2,1),
		options: {
			isWholeLine: true,
			className: 'myContentClass',
			glyphMarginClassName: 'myGlyphMarginClass'
		}
	}

]);
	window.decorations.push(decorations);
        showdiv =document.getElementById("problems");
        //showdiv.addClass("alert alert-warning alert-dismissible");
        // showdiv.setAttribute("class","alert alert-warning alert-dismissible")
        // showdiv.setAttribute("role","alert")
        // showdiv.innerHTML = showdiv.innerHTML + pos + "<br>" + result;
        showdiv.innerHTML = showdiv.innerHTML + "<div class=\"alert alert-success alert-dismissible\" role=\"alert\" >"  + "<b>" + pos + "</b>"+ "<br>" + result + "</div>"

        console.log(result);
    }
})
}


//以下是废弃的axois
    // var editor1_text = editor_1.getValue();
    //     axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
    //     axios.post('/editor/beauty/',{
    //         core:editor1_text,
    //         headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    //     })
    // // var send = `core=` + editor1_text;
    // // axios.post('/editor/beauty/',send)
    //     .then(function (response) {
    //         // var class_info = response.data.fixedCode;
    //         console.log(response.data);
    //
    //     })
    //     .catch(function (error) {
    //         console.log(error);
    //     });

function get_example1() {
    var tmp = "package\n" +
        "        Graph;\n" +
        "\n" +
        "import java. util .LinkedList;\n" +
        "\n" +
        "\n" +
        "public class  Graph {\n" +
        "\n" +
        "    private final   int V;private int E ;\n" +
        "             private LinkedList<Integer>[]  adj;\n" +
        "\n" +
        "    public Graph(int     V)\n" +
        "    {\n" +
        "        this.V= V;\n" +
        "        this.E=0;\n" +
        "        adj =    (LinkedList  <Integer>[  ]) new LinkedList[   V];\n" +
        "        for (int v =0; v<    V; v  ++)\n" +
        "            adj[v  ] = new  LinkedList<  >();\n" +
        "    }\n" +
        "\n" +
        "        public int V(    )\n" +
        "        {\n" +
        "\n" +
        "\n" +
        "\n" +
        "            return V;\n" +
        "        }\n" +
        "\n" +
        "    public\n" +
        "    int E() {\n" +
        "                return  E;\n" +
        "    }\n" +
        "\n" +
        "    public void addEdge(int v, int w) {\n" +
        "           adj[v].add( w);\n" +
        "        adj[w ].add(v );\n" +
        "        E ++ ;\n" +
        "    }\n" +
        "\n" +
        "    public LinkedList<  Integer> adj(int v) {\n" +
        "        return adj[v]\n" +
        "                ;\n" +
        "    }\n" +
        "\n" +
        "    public    int degree(  int v,Graph g)    {\n" +
        "        int count =   0;\n" +
        "        for(int s :   adj(v)  )\n" +
        "            count++ ;\n" +
        "            return  count;\n" +
        "        }\n" +
        "\n" +
        "}";
    editor_1.setValue(tmp);

}
function get_example2() {
    var tmp = "package Graph;\n" +
        "\n" +
        "import java.util.LinkedList;\n" +
        "\n" +
        "public class Graph {\n" +
        "\n" +
        "    private final int V, X;\n" +
        "\n" +
        "    private int E;\n" +
        "\n" +
        "    private LinkedList<Integer>[] adj;\n" +
        "\n" +
        "    public Graph(int ver_num) {\n" +
        "        V = ver_num;\n" +
        "        E = 0;\n" +
        "        adj = (LinkedList<Integer>[]) new LinkedList[ver_num];\n" +
        "        for (int i = 0; i < ver_num; i++) {\n" +
        "            adj[i] = new LinkedList<>();\n" +
        "        }\n" +
        "    }\n" +
        "\n" +
        "    public int V() {\n" +
        "        return V;\n" +
        "    }\n" +
        "\n" +
        "    public int E() {\n" +
        "        return E;\n" +
        "    }\n" +
        "\n" +
        "    public void addEdge(int a, int b) {\n" +
        "        adj[a].add(b);\n" +
        "        adj[b].add(a);\n" +
        "        E++;\n" +
        "    }\n" +
        "\n" +
        "    public LinkedList<Integer> adj(int x) {\n" +
        "        return adj[x];\n" +
        "    }\n" +
        "\n" +
        "    public int degree(int Vertex, Graph g) {\n" +
        "        int Count = 0;\n" +
        "        for (int s : adj(Vertex)) {\n" +
        "            Count++;\n" +
        "        }\n" +
        "        return Count;\n" +
        "    }\n" +
        "}";
    editor_1.setValue(tmp);

}

function get_example3() {
    var tmp = "class IfElseDemo {\n" +
        "    public static void main(String[] args) {\n" +
        "\n" +
        "        int testscore = 76;\n" +
        "        char grade;\n" +
        "\n" +
        "        if (testscore >= 90) {\n" +
        "            grade = 'A';\n" +
        "        } else if (testscore >= 80) {\n" +
        "            grade = 'B';\n" +
        "        } else if (testscore >= 70) {\n" +
        "            grade = 'C';\n" +
        "        } else if (testscore >= 60) {\n" +
        "            grade = 'D';\n" +
        "        } else {\n" +
        "            grade = 'F';\n" +
        "        }\n" +
        "\n" +
        "        System.out.println(\"Grade = \" + grade);\n" +
        "\n" +
        "        boolean isFailed = grade == 'F';\n" +
        "\n" +
        "        if (grade == 'A' || grade == 'B') {\n" +
        "            System.out.println(\"Good!\");\n" +
        "        } else if (isfailed && (isFailed || testscore < 50) && testscore < 50) {\n" +
        "            System.out.println(\"Fatal failed.\");\n" +
        "        }\n" +
        "\n" +
        "        switch (grade) {\n" +
        "            case 'A':\n" +
        "                break;\n" +
        "            case 'B':\n" +
        "                System.out.println(\"hi\");\n" +
        "                break;\n" +
        "            case 'C':\n" +
        "                System.out.println(\"hello\");\n" +
        "                break;\n" +
        "            default:\n" +
        "                System.out.println(\"how are you\");\n" +
        "                break;\n" +
        "        }\n" +
        "\n" +
        "        if (isFailed) {\n" +
        "            System.out.println(\"Your parents will be invited to the school.\");\n" +
        "        }\n" +
        "    }\n" +
        "}";
    editor_1.setValue(tmp);

}

function get_example4() {
    var tmp = "public class findsushu {\n" +
        "public static void main(String[] args) {\n" +
        "    int i,j;\n" +
        "    int a[]=new int[101];\n" +
        "    for (i=1; i<101;i++){\n" +
        "        a[i]=1;//init\n" +
        "    }\n" +
        "    for(i=2;i<101;i++){\n" +
        "        if(a[i]!=0)\n" +
        "        {\n" +
        "\n" +
        "            for(j=i+i;j<101;){\n" +
        "                if(j%i==0)\n" +
        "                {\n" +
        "                    a[j]=0;\n" +
        "//pass\n" +
        "                    j=j+i;\n" +
        "\n" +
        "                }\n" +
        "            }\n" +
        "\n" +
        "        }\n" +
        "    }\n" +
        "    for(i=2;i<101;i++){\n" +
        "        if(a[i]!=0)\n" +
        "        {\n" +
        "            System.out.print(i+\" \");\n" +
        "            }\n" +
        "    }\n" +
        "\n" +
        "    }\n" +
        "\n" +
        "\n" +
        "}";
    editor_1.setValue(tmp);

}

function get_example5() {
    var tmp = "package cn.migu.newportal.activity.service.activity;\n" +
        "import java.util.ArrayList;\n" +
        "import java.util.LinkedList;\n" +
        "import java.util.List;\n" +
        "\n" +
        "public class Test\n" +
        "{\n" +
        "    public static void main(String[] args)\n" +
        "    {\n" +
        "\n" +
        "\n" +
        "        List<Integer> arrayList = new ArrayList<Integer>();\n" +
        "\n" +
        "        List<Integer> linkList = new LinkedList<Integer>();\n" +
        "\n" +
        "\n" +
        "        for (int i = 0; i < 100000; i++)\n" +
        "        {\n" +
        "            arrayList.add(i);\n" +
        "            linkList.add(i);\n" +
        "        }\n" +
        "\n" +
        "        int array = 0;\n" +
        "\n" +
        "        long arrayForStartTime = System.currentTimeMillis();\n" +
        "        for (int i = 0; i < arrayList.size(); i++)\n" +
        "        {\n" +
        "            array = arrayList.get(i);\n" +
        "        }\n" +
        "        long arrayForEndTime = System.currentTimeMillis();\n" +
        "        System.out.println(\"cal\" + (arrayForEndTime - arrayForStartTime) + \"ms\");\n" +
        "        long arrayForeachStartTime = System.currentTimeMillis();\n" +
        "\n" +
        "        long arrayForeachEndTime = System.currentTimeMillis();\n" +
        "        System.out.println(\"foreach arrayList:\" + (arrayForeachEndTime - arrayForeachStartTime) + \"ms\");\n" +
        "\n" +
        "\n" +
        "        long linkForStartTime = System.currentTimeMillis();\n" +
        "        int link = 0;\n" +
        "        for (int i = 0; i < linkList.size(); i++)\n" +
        "        {\n" +
        "            link = linkList.get(i);\n" +
        "        }\n" +
        "        long linkForEndTime = System.currentTimeMillis();\n" +
        "        System.out.println(\"for linkList\" + (linkForEndTime - linkForStartTime) + \"ms\");\n" +
        "\n" +
        "\n" +
        "        long linkForeachStartTime = System.currentTimeMillis();\n" +
        "\n" +
        "        long linkForeachEndTime = System.currentTimeMillis();\n" +
        "        System.out.println(\"foreach linkList ms:\" + (linkForeachEndTime - linkForeachStartTime) + \"ms\");\n" +
        "    }\n" +
        "}\n";
    editor_1.setValue(tmp);

}

function get_example6() {
    var tmp = "package\n" +
        "        Graph;\n" +
        "\n" +
        "import java. util .LinkedList;\n" +
        "\n" +
        "\n" +
        "public class  Graph {\n" +
        "\n" +
        "    private final   int V;private int E ;\n" +
        "             private LinkedList<Integer>[]  adj;\n" +
        "    public String bar(String string) {\n" +
        "        // should be &&\n" +
        "        if (string!=null || !string.equals(\"\"))\n" +
        "            return string;\n" +
        "        // should be ||\n" +
        "        if (string==null && string.equals(\"\"))\n" +
        "            return string;\n" +
        "    }\n" +
        "    public Graph(int     V)\n" +
        "    {\n" +
        "        this.V= V;\n" +
        "        this.E=0;\n" +
        "        adj =    (LinkedList  <Integer>[  ]) new LinkedList[   V];\n" +
        "        for (int v =0; v<    V; v  ++)\n" +
        "            adj[v  ] = new  LinkedList<  >();\n" +
        "    }\n" +
        "\n" +
        "        public int V(    )\n" +
        "        {\n" +
        "\n" +
        "\n" +
        "\n" +
        "            return V;\n" +
        "        }\n" +
        "\n" +
        "    public\n" +
        "    int E() {\n" +
        "                return  E;\n" +
        "    }\n" +
        "\n" +
        "    public void addEdge(int v, int w) {\n" +
        "           adj[v].add( w);\n" +
        "        adj[w ].add(v );\n" +
        "        E ++ ;\n" +
        "    }\n" +
        "\n" +
        "    public LinkedList<  Integer> adj(int v) {\n" +
        "        return adj[v]\n" +
        "                ;\n" +
        "    }\n" +
        "\n" +
        "    public    int degree(  int v,Graph g)    {\n" +
        "        int count =   0;\n" +
        "        for(int s :   adj(v)  )\n" +
        "            count++ ;\n" +
        "            return  count;\n" +
        "        }\n" +
        "\n" +
        "}";
    editor_1.setValue(tmp);

}
function get_example7() {
    var tmp = "class IfElseDemo {\n" +
        " class Foo {\n" +
        "\tvoid bar() {\n" +
        "\t\tif (a.equals(baz) && a != null) {}\n" +
        "\t\t}\n" +
        "}\n" +
        "    public static void main(String[] args) {\n" +
        "\n" +
        "        int testscore = 76;\n" +
        "        char grade;\n" +
        "\n" +
        "        if (testscore >= 90) {\n" +
        "            grade = 'A';\n" +
        "        } else if (testscore >= 80) {\n" +
        "            grade = 'B';\n" +
        "        } else if (testscore >= 70) {\n" +
        "            grade = 'C';\n" +
        "        } else if (testscore >= 60) {\n" +
        "            grade = 'D';\n" +
        "        } else {\n" +
        "            grade = 'F';\n" +
        "        }\n" +
        "\n" +
        "        System.out.println(\"Grade = \" + grade);\n" +
        "\n" +
        "        boolean isFailed = grade == 'F';\n" +
        "\n" +
        "        if (grade == 'A' || grade == 'B') {\n" +
        "            System.out.println(\"Good!\");\n" +
        "        } else if (isfailed && (isFailed || testscore < 50) && testscore < 50) {\n" +
        "            System.out.println(\"Fatal failed.\");\n" +
        "        }\n" +
        "\n" +
        "        switch (grade) {\n" +
        "            case 'A':\n" +
        "                break;\n" +
        "            case 'B':\n" +
        "                System.out.println(\"hi\");\n" +
        "                break;\n" +
        "            case 'C':\n" +
        "                System.out.println(\"hello\");\n" +
        "                break;\n" +
        "            default:\n" +
        "                System.out.println(\"how are you\");\n" +
        "                break;\n" +
        "        }\n" +
        "\n" +
        "        if (isFailed) {\n" +
        "            System.out.println(\"Your parents will be invited to the school.\");\n" +
        "        }\n" +
        "\n" +
        "        for(int i = 0, j = 0; i < 10; i++, j++) {\n" +
        "            System.out.println(i);\n" +
        "        }\n" +
        "    }\n" +
        "}";
    editor_1.setValue(tmp);

}